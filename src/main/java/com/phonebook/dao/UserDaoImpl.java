package com.phonebook.dao;

import com.phonebook.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlab13 on 10.03.2017.
 */
public class UserDaoImpl implements UserDao {
    @Value("${spring.datasource.url}")
    String datasource;

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        User u = new User();
        System.out.println(datasource + " DATASOURCE");
        try {
            Connection conn = DriverManager.getConnection(datasource);
            Statement stmt = conn.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM Users");
            int i = 0;
            while (results.next())
            {
                i++;
                System.out.println(results.getString("name") + " SIZEEEEEE");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /*u.setName("VASYA");
        u.setId(1);*/
        users.add(u);
        return users;
    }




}
