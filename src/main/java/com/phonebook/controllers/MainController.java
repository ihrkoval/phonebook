package com.phonebook.controllers;

import com.phonebook.dao.ContactDao;
import com.phonebook.dao.UserDao;
import com.phonebook.entity.Contact;
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
import java.security.Principal;

/**
 * Created by jlab13 on 10.03.2017.
 */
@Controller
public class MainController  {

    @Autowired
    UserDao userDao;
    @Autowired
    ContactDao contactDao;

    @RequestMapping(value ="/edit")
    public ModelAndView editcontact(@RequestParam(value = "id") long id) {
        ModelAndView editpage = new ModelAndView("index");
        Contact c = contactDao.findById(id);
        User user = contactDao.findUserBycontact(id);
        editpage.addObject("id", c.getId());
        editpage.addObject("userid", user.getId());
        editpage.addObject("name", c.getName());
        editpage.addObject("surname", c.getSurname());
        editpage.addObject("fathername", c.getFathername());
        editpage.addObject("phonenumber", c.getPhonenumber());
        editpage.addObject("homenumber", c.getHomenumber());
        editpage.addObject("adress", c.getAdress());
        editpage.addObject("email", c.getEmail());
        return editpage;
    }

    @RequestMapping("/")
    public String orders() {
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
