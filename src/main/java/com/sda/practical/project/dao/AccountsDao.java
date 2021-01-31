package com.sda.practical.project.dao;

import com.sda.practical.project.model.AccountsModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class AccountsDao {

    private SessionFactory sessionFactory;

    public AccountsDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<AccountsModel> findAllAccountsById(int accountId) {
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from AccountsModel a where a.userId = '" + accountId + "'");
        List<AccountsModel> accountsModelList = query.list();
        session.close();
        return accountsModelList;
    }

    public void createAccount(AccountsModel accountsModel){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(accountsModel);
        transaction.commit();
        session.close();
    }

    public void updateAccount(AccountsModel accountsModel) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(accountsModel);
        transaction.commit();
        session.close();
    }

}


