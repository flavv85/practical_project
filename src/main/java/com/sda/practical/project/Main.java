package com.sda.practical.project;

import com.sda.practical.project.dao.AccountsDao;
import com.sda.practical.project.dao.UsersDao;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {

        DBConfig dbConfig = new DBConfig();

        SessionFactory sessionFactory = dbConfig.getSessionFactory();
        UsersDao usersDao = new UsersDao(sessionFactory);
        AccountsDao accountsDao = new AccountsDao(sessionFactory);

    }
}
