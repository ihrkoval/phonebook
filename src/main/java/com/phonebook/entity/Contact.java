package com.phonebook.entity;

import javax.persistence.*;

/**
 * Created by anton on 11.03.2017.
 */
@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String surname; //(обязательный, минимум 4 символа)
    private String name; //Имя (обязательный, минимум 4 символа)
    private String fathername; //Отчество (обязательный, минимум 4 символа)
    private String phonenumber; //Телефон мобильный (обязательный)
    private String homenumber; //Телефон домашний (не обязательный)
    private String adress; //Адрес (не обязательный)
    private String email; //e-mail (не обязательный, общепринятая валидация)

    public Contact(String name, String surname, String fathername, String phonenumber, String homenumber, String adress, String email, User user) {
        this.name = name;
        this.surname = surname;
        this.fathername = fathername;
        this.phonenumber = phonenumber;
        this.homenumber = homenumber;
        this.adress = adress;
        this.email = email;
        this.user = user;
    }

    public Contact(){
    }

    public Long getId() {
        return id;
    }

    public User user() {
        return user;
    }

    public void User(User user) {
        this.user = user;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getHomenumber() {
        return homenumber;
    }

    public void setHomenumber(String homenumber) {
        this.homenumber = homenumber;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
