package com.sda.practical.project.dao;

import org.hibernate.SessionFactory;

public class UsersDao {

    private SessionFactory sessionFactory;

    public UsersDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

}
