package com.phonebook.controllers;

import com.phonebook.dao.ContactDao;
import com.phonebook.dao.UserDao;
import com.phonebook.entity.Contact;
import com.phonebook.utils.Validator;
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
    public List<Contact> orders(Principal principal,
                                @RequestParam(value = "name", required = false) String name,
                                @RequestParam(value = "surname", required = false) String surname,
                                @RequestParam(value = "phone", required = false) String phone) {
        return  contactDao.findByUser(userDao.findByLogin(principal.getName()).getId(), name, surname, phone);
    }

    @RequestMapping("/delete")
    public void orders(@RequestParam(value = "id") long id) {
        contactDao.delete(id);
    }

    @RequestMapping("/add")
    public String add(Principal principal,
    @RequestParam(value = "id", required = false) Long id,
    @RequestParam(value = "name") String name,
    @RequestParam(value = "surname") String surname,
    @RequestParam(value = "fathername") String fathername,
    @RequestParam(value = "phone") String phone,
    @RequestParam(value = "homenumber", required = false) String homenumber,
    @RequestParam(value = "adress", required = false) String adress,
    @RequestParam(value = "email", required = false) String email){

        Contact contact = new Contact(name,surname,fathername,phone,homenumber,adress,email,userDao.findByLogin(principal.getName()));
        Contact  c = new Contact();
        if (id!= null){
            c = contactDao.findById(id);
        }
            c.setUser(contact.getUser());
            c.setName(contact.getName());
            c.setSurname(contact.getSurname());
            c.setFathername(contact.getFathername());
            c.setPhonenumber(contact.getPhonenumber());
            c.setHomenumber(contact.getHomenumber());
            c.setEmail(contact.getEmail());
            c.setAdress(contact.getAdress());

        if (new Validator().checkContact(contact).equals("ok")) {
            contactDao.save(c);
            return "Contact was added<script>location.href = \"/\"</script>";
        }
        else return new Validator().checkContact(contact);
    }
}
