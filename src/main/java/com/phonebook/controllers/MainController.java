package com.phonebook.controllers;

import com.phonebook.dao.UserDao;
import com.phonebook.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jlab13 on 10.03.2017.
 */
@Controller
public class MainController  {

    @Autowired
    UserDao userDao;

    @Autowired
    UserRepo userRepo;

    @RequestMapping("/")
    public String orders() {
        System.out.println(userDao.getAll().get(0).getName());
        //System.out.println(userRepo.getUsers().get(0).getName());
        return "index";
    }

}
