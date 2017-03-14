package com.phonebook.utils;

import com.phonebook.dao.UserDao;
import com.phonebook.entity.Contact;
import com.phonebook.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.NoResultException;

/**
 * Created by jlab13 on 13.03.2017.
 */
public class Validator {

    public String checkContact(Contact c){
        String responce = "ok";
        if (c.getSurname().length() <4){
            return "surname too short";
        }
        if (c.getName().length() <4){
            return "name too short";
        }
        if (c.getFathername().length() <4){
            return "fathername too short";
        }
        if ((c.getPhonenumber().length() < 13) || !(c.getPhonenumber().startsWith("+380")) || (c.getPhonenumber().substring(1).matches(".*[^0-9].*"))){
            return "phone format error (must be like <b>+380</b>661234567)";
        }

        if (!c.getHomenumber().isEmpty()) {
            if ((c.getHomenumber().length() < 13) || !(c.getHomenumber().startsWith("+380")) || (c.getHomenumber().substring(1).matches(".*[^0-9].*"))) {
                return "home phone format error (must be like <b>+380</b>661234567)";
            }
        }
        return responce;
    }

    public String checkUser(String name, String login, String password){
        if (name.length() < 5){
            return "ФИО меньше 5 символов";
        } else
        if (login.length() < 3 ){
            return "Логин  не меньше 3х";
        } else
        if (login.toLowerCase().matches(".*[^a-z].*")){
            return "Логин-только английские символы без спецсимволов " + login;
        }
        if (password.length() < 5){
            return "Пароль - минимум 5 символов";
        }
        return null;
    }
}
