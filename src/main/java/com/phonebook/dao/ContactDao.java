package com.phonebook.dao;

import com.phonebook.entity.Contact;

import java.util.List;

/**
 * Created by anton on 12.03.2017.
 */
public interface ContactDao {
    void save(Contact contact);
    List<Contact> findByUser(long userid);
    void delete(long contactId);
}
