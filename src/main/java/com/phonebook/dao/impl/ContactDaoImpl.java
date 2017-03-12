package com.phonebook.dao.impl;

import com.phonebook.dao.ContactDao;
import com.phonebook.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
   public List<Contact> findByUser(long userid){
       return entityManager.createNativeQuery("SELECT * FROM contact where user_id='"+userid+"'", Contact.class).getResultList();
   }

    @Override
    public void delete(long contactId) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(findById(contactId));
            entityManager.getTransaction().commit();
        }catch (IllegalStateException e) {
            System.out.println("TRY REMOVE FROM FILE");

            Scanner scanner = new Scanner(path+"/contact.csv");
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path+"/contact.csv"));
//TODO write to file
               // writer.write(currentLine + System.getProperty("line.separator"));

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.equals(contactToString(findById(contactId)))){
                   // writer.write();
                }
            }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }

    public Long maxId(){
        List<Long> ids = (List<Long>)entityManager.createNativeQuery("SELECT id from contact").getResultList();
        Collections.sort(ids);
        try {
            return ids.get(ids.size());
        } catch (Exception e){
            return new Long(0);
        }
    }

    public String contactToString(Contact contact) {
        String contactFields=contact.getId()+","
                +contact.user().getId()+","
                +contact.getName()+","
                +contact.getSurname()+","
                +contact.getFathername()+","
                +contact.getPhonenumber()+","
                +contact.getPhonenumber()+","
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
            if (contact.getId() == 0 || (contact.getId()== null)){
                contact.setId(maxId()+1);
            }

            String contactFields=contactToString(contact);
            try {
                Files.write(Paths.get(path+"/contact.csv"), contactFields.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
