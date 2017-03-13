package com.phonebook.dao;

import com.phonebook.entity.Contact;
import com.phonebook.entity.User;

import java.util.List;

/**
 * Created by anton on 12.03.2017.
 */
public interface ContactDao {
    void save(Contact contact);
    List<Contact> findByUser(long userid, String name, String surname, String phone);
    void delete(long contactId);
    Contact findById(long contactId);
    User findUserBycontact(long id);
}
