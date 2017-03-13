package com.phonebook.dao.impl;

import com.phonebook.dao.ContactDao;
import com.phonebook.entity.Contact;
import com.phonebook.entity.User;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;


/**
 * Created by anton on 12.03.2017.
 */
public class ContactDaoImpl implements ContactDao {

    @Value("${spring.datasource.url}")
    String datasource;
    String path;

    @Autowired
    private EntityManager entityManager;

    public Contact findById(long id){
        return (Contact) entityManager.createNativeQuery("Select * from Contact where id='"+id+"'", Contact.class).getSingleResult();
    }
   public List<Contact> findByUser(long userid, String name, String surname, String phone){
       String query = ("SELECT * FROM contact where user_id ='"+userid+"'");
       if (name != null){
           query += " and name like'%"+name+"%'";
       }
       if (surname != null){
           query += " and surname like'%"+surname+"%'";
       }
       if (phone != null){
           query += " and phonenumber like'%"+phone+"%'";
       }
       List<Contact> conts = entityManager.createNativeQuery(query, Contact.class).getResultList();
       return conts;
   }

    public User findUserBycontact(long id){
        String userid = (String) entityManager.createNativeQuery("SELECT user_id FROM contact WHERE id = '"+id+"'").getSingleResult();
        return (User) entityManager.createNativeQuery("SELECT * FROM user WHERE id='"+userid+"'", User.class).getSingleResult();
    }

    @Override
    public void delete(long contactId) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(findById(contactId));
            entityManager.getTransaction().commit();
        }catch (IllegalStateException e) {
            path =datasource.substring(17);
            Path filepath = Paths.get(path+"/contact.csv");
            Contact cont = findById(contactId);
            User user =findUserBycontact(contactId) ;
            cont.setUser(user);
            String contact  = contactToString(cont);
            try {
                String content = new String(Files.readAllBytes(filepath), "UTF-8");
                content = content.replace(contact,"");
                Files.write(filepath, content.getBytes("UTF-8"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }

    public Long maxId(){

        long id = 0;
        try {
            List<String> ids = (List<String>)entityManager.createNativeQuery("SELECT id from contact").getResultList();
            Collections.sort(ids);
            id = Long.valueOf(ids.get(ids.size()-1));
        } catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    public String contactToString(Contact contact) {
        String contactFields=contact.getId()+","
                +contact.getUser().getId()+","
                +contact.getName()+","
                +contact.getSurname()+","
                +contact.getFathername()+","
                +contact.getPhonenumber()+","
                +contact.getHomenumber()+","
                +contact.getAdress()+","
                +contact.getEmail()
                +"\n";
        return contactFields;
    }

    public void save(Contact contact) {
        path =datasource.substring(17);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(contact);
            entityManager.getTransaction().commit();
        }catch (IllegalStateException e){

            if (contact.getId()== null || contact.getId() == 0){
                contact.setId(maxId()+1);
                try {
                    String contactFields=contactToString(contact);
                    Files.write(Paths.get(path + "/contact.csv"), contactFields.getBytes(), StandardOpenOption.APPEND);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else {
                try {
                    String contactFields = contactToString(contact);
                    Path filepath = Paths.get(path + "/contact.csv");
                    Contact replacecont = findById(contact.getId());
                    String replace = contactToString(replacecont);
                    String content = new String(Files.readAllBytes(filepath), "UTF-8");
                    content = content.replace(replace, contactFields);
                    Files.write(filepath, content.getBytes("UTF-8"));
                }catch(IOException e1){
                    e1.printStackTrace();
                }
            }

        }
    }
}
