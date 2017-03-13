package com.phonebook.utils;

import com.phonebook.entity.Contact;

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

       /* if ((c.getPhonenumber().length() < 13)){
            return "phone format error (must be like <b>+380</b>661234567) + LENGTH";
        }
        if (!c.getPhonenumber().startsWith("+380")){
            return "phone format error (must be like <b>+380</b>661234567) + +380e";
        }
        if (c.getPhonenumber().substring(1).matches(".*[^0-9].*")){
            return "phone format error (must be like <b>+380</b>661234567) + 132456789";
        }*/
        if (!c.getHomenumber().isEmpty()) {
            if ((c.getHomenumber().length() < 13) || !(c.getHomenumber().startsWith("+380")) || (c.getHomenumber().substring(1).matches(".*[^0-9].*"))) {
                return "home phone format error (must be like <b>+380</b>661234567)";
            }
        }
        return responce;
    }
}
