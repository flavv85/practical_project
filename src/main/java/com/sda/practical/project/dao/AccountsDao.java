package com.sda.practical.project.dao;

import org.hibernate.SessionFactory;

public class AccountsDao {

    private SessionFactory sessionFactory;

    public AccountsDao(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

}
