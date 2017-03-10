package com.phonebook.dao;

import com.phonebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlab13 on 10.03.2017.
 */
public class UserDaoImpl implements UserDao {
    @Value("${spring.datasource.url}")
    String datasource;

    @Autowired
    private EntityManager entityManager;

    private Statement statment() throws SQLException {
        Connection conn = DriverManager.getConnection(datasource);
        Statement stmt = conn.createStatement();
        return stmt;
    }


    public List<User> getAll() {
       List<User> usrs =  entityManager.createNativeQuery("SELECT * FROM Users", User.class).getResultList();
        return usrs;
    }




}
