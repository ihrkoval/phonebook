package com.phonebook.dao.impl;

import com.phonebook.dao.ContactDao;
import com.phonebook.entity.Contact;
import com.phonebook.entity.User;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
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
    private EntityManagerFactory emf;

    private EntityManager entityManager;

    private void setEntityManager(){
        entityManager = emf.createEntityManager();
    }

    //отдает контакт по его ИД
    public Contact findById(long id){
        setEntityManager();
        Contact c = (Contact) entityManager.createNativeQuery("Select * from Contact where id='"+id+"'", Contact.class).getSingleResult();
        return c;
    }
    //отдает контакты юзера и использует фильтр по имени\фамилии\телефону(если требуется фильтр)
   public List<Contact> findByUser(long userid, String name, String surname, String phone){
       setEntityManager();
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
    //Отдает юзера(владельца) по номеру контакта потому что csvJdbc
    public User findUserBycontact(long id){
        setEntityManager();
        Object userid = (Object) entityManager.createNativeQuery("SELECT user_id FROM contact WHERE id = '"+id+"'").getSingleResult();
        return (User) entityManager.createNativeQuery("SELECT * FROM user WHERE id='"+userid.toString()+"'", User.class).getSingleResult();
    }

    //пробует удалить контакт из базы. если исключение = удаляем из файла
    @Override
    public void delete(long contactId) {
        setEntityManager();
        try {
            Contact c = findById(contactId);
            entityManager.getTransaction().begin();
            entityManager.remove(c);
            entityManager.getTransaction().commit();
        }catch (Exception e) {
            deleteFromFile(contactId);

        }


    }
//возвращает максимальный ИД контакта (для файла с контактами первичный ключ)
    public Long maxId(){
        setEntityManager();
        long id = 0;
        try {
            List<String> ids = (List<String>)entityManager.createNativeQuery("SELECT id from contact").getResultList();
            for (String i : ids){
                long temp = Long.valueOf(i);
                if (temp>id)
                    id=temp;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

//сохраняем контакт. Если UnsupportedOperationException - сохраняем контакт в файл
    public void save(Contact contact) {
        setEntityManager();
        path =datasource.substring(17);
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(contact);
            entityManager.getTransaction().commit();
        }catch (UnsupportedOperationException|NoResultException e){
                saveToFile(contact);
        }
    }

    //переводим контакт в строку для записи в файл //todo override toString() method in contact class
    public String contactToString(Contact contact) {
//        User user  = findUserBycontact(contact.getId());
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

    //сохраняет контакт в файл
    private void saveToFile(Contact contact){
        //если новый контакт - присваиваем ему ИД и пишем в файл
        System.out.println("NEW CONTACT TRY "+contact.getId()+" "+maxId());
        if (contact.getId()== null || contact.getId() == 0){
            contact.setId(maxId()+1);
            try {
                String contactFields=contactToString(contact);
                Files.write(Paths.get(path + "/contact.csv"), contactFields.getBytes(), StandardOpenOption.APPEND);
                System.out.println("NEW CONTACT "+contact.getId());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        //если у контакта уже есть ИД - значит редактируем контакт и пишем в файл
        else {
            try {
                String contactFields = contactToString(contact);
                Path filepath = Paths.get(path + "/contact.csv");
                Contact replacecont = findById(contact.getId());
                //потому что csvJdbc не умеет в join
                User user  = findUserBycontact(contact.getId());
                replacecont.setUser(user);
                String replace = contactToString(replacecont);
                String content = new String(Files.readAllBytes(filepath), "UTF-8");
                content = content.replace(replace, contactFields);
                Files.write(filepath, content.getBytes("UTF-8"));
            }catch(IOException e1){
                e1.printStackTrace();
            }
        }
    }
    //удаляет контакт из файла
    private void deleteFromFile(long contactId){
        path = datasource.substring(17);
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
