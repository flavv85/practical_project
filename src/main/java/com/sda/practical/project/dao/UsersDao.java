package com.sda.practical.project.dao;

import com.sda.practical.project.model.UsersModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class UsersDao {

    private SessionFactory sessionFactory;

    public UsersDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public UsersModel getUserByUserName(String username) {
        Session session = sessionFactory.openSession();
        Query selectUserByUserName =
                session.createQuery("from UsersModel u where u.name = '" + username +
                        "'");
        UsersModel returnedUser = (UsersModel)selectUserByUserName.getSingleResult();
        session.close();
        return returnedUser;

    }

}
