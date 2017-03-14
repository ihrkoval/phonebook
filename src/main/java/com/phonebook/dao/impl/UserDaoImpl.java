package com.phonebook.dao.impl;

import com.phonebook.dao.UserDao;
import com.phonebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by jlab13 on 10.03.2017.
 */
public class UserDaoImpl implements UserDao {
    @Value("${spring.datasource.url}")
    String datasource;
    String path;

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager entityManager;

   private void setEntityManager(){
        entityManager = emf.createEntityManager();
    }


    public List<User> getAll() {
        setEntityManager();
        return  entityManager.createNativeQuery("SELECT * FROM User", User.class).getResultList();
    }

    public User findById(long id){
        setEntityManager();
        return (User) entityManager.createNativeQuery("Select * from User where id='"+id+"'", User.class).getSingleResult();
    }

    public User findByLogin(String login){
        setEntityManager();
        return (User) entityManager.createNativeQuery("Select * from User where login='"+login+"'", User.class).getSingleResult();
    }

    public void save(User user) {
        setEntityManager();
        path =datasource.substring(17);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }catch (UnsupportedOperationException e){
            e.printStackTrace();

            //присваиваем новому юзеру максимальный ИД и пишем в ФАйл
            long max_id = 0;
            List<User> users = getAll();

            for (User u: users){
                if ( u.getId()<=max_id){
                    max_id = u.getId()+1;
                }
            }
            if (user.getId()!=null && user.getId()!=0){
                max_id=user.getId();
            }
            user.setId(max_id);
            String userFields=user.getId()+","
                    +user.getLogin()
                    +","+user.getPassword()
                    +","+user.getName()
                    +"\n";
            try {
                Files.write(Paths.get(path+"/user.csv"), userFields.getBytes(), StandardOpenOption.APPEND);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }



}
