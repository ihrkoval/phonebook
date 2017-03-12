package com.phonebook.controllers;

import com.phonebook.dao.ContactDao;
import com.phonebook.dao.UserDao;
import com.phonebook.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.security.Principal;
import java.util.List;

/**
 * Created by anton on 12.03.2017.
 */
@RestController
@RequestMapping(value = "/contact")
public class ContactController {

    @Autowired
    UserDao userDao;

    @Autowired
    ContactDao contactDao;

    @RequestMapping("/all")
    public List<Contact> orders(Principal principal) {
        return  contactDao.findByUser(userDao.findByLogin(principal.getName()).getId());
    }

    @RequestMapping("/delete")
    public void orders(@RequestParam(value = "id") long id) {
        contactDao.delete(id);
    }

    @RequestMapping("/add")
    public String add(Principal principal,
    @RequestParam(value = "name") String name,
    @RequestParam(value = "surname") String surname,
    @RequestParam(value = "fathername") String fathername,
    @RequestParam(value = "phonenumber") String phonenumber,
    @RequestParam(value = "homenumber", required = false) String homenumber,
    @RequestParam(value = "adress", required = false) String adress,
    @RequestParam(value = "email", required = false) String email){

        Contact contact = new Contact(name,surname,fathername,phonenumber,homenumber,adress,email,userDao.findByLogin(principal.getName()));

        contactDao.save(contact);
        return "ok";
    }
}
