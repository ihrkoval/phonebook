package com.phonebook.dao;

import com.phonebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jlab13 on 10.03.2017.
 */
@Repository
public interface UserRepo extends JpaRepository<User,Long> {
}
