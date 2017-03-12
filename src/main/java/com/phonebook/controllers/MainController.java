package com.phonebook.controllers;

import com.phonebook.dao.UserDao;
import com.phonebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by jlab13 on 10.03.2017.
 */
@Controller
public class MainController  {

    @Autowired
    UserDao userDao;

    @RequestMapping("/")
    public String orders() {
        System.out.println(userDao.getAll().size() + " ALL SIZEEE");
       // System.out.println(userDao.findById(1).getName());

        //System.out.println(userRepo.getUsers().get(0).getName());
        return "index";
    }

    @RequestMapping("/register")
    public String reg() {
        return "register";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(value="/register" , method = RequestMethod.POST)
    public ModelAndView register(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "login") String login,
                                 @RequestParam(value = "password") String password,
                                 HttpServletRequest request) {
        ModelAndView reg = new ModelAndView("register");

        if (name.length() < 5){
            reg.addObject("error", "ФИО меньше 5 символов");
            return reg;
        } else
        if (login.length() < 3 ){
            reg.addObject("error","Логин  не меньше 3х");
            return reg;
        } else
        if (login.toLowerCase().matches(".*[^a-z].*")){
            reg.addObject("error","Логин-только английские символы без спецсимволов " + login);
            return reg;
        }
        if (password.length() < 5){
            reg.addObject("error","Пароль - минимум 5 символов");
            return reg;
        } else {
            try{
                User u = userDao.findByLogin(login);
                reg.addObject("error","username already exist");
                return reg;
            } catch (NoResultException exception){
                User user = new User(login,password,name);
                userDao.save(user);
                return new ModelAndView("login");
            }


        }

    }

}
