package com.sda.practical.project.service;
/*


        /*
        - citeste infinit de la tastatara
        - pentru fiecare comanda introdusa, citim restul param (daca exista) -> apelam metoda corespunz din Service

        - Ce face service? apeleaza din DAO conform logicii de bussines
        - Ce face DAO? foloseste entity-uri pentru a face operatii pe DB

        Definiti entity-uri (in pac model)
        Definiti operatii pe baza de date (pac dao)
        Definiti operatii pe aplicatie

        // Ce se intampla cand introducem ACCOUNTS?
        - ia userul curent logat
        - apeleaza DAO (getAllAccountsForUserName) cu username-ul introdus
        - rezultatele sunt afisate in ecran


        //LOGIN Mihai 1234
        //service.login("Mihai", 1234);

1. conex la db
2. cream entitati -  user cu Id name si password; account cu Id userid, currency, amount ;transaction
3. cream dao

         */

import com.sda.practical.project.DBConfig;
import com.sda.practical.project.dao.AccountsDao;
import com.sda.practical.project.dao.UsersDao;
import com.sda.practical.project.model.AccountsModel;
import com.sda.practical.project.model.UsersModel;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class Service {

    private DBConfig dbConfig;
    private SessionFactory sessionFactory;
    private UsersDao usersDao;
    private AccountsDao accountsDao;

    private String currentUser;
    private int currentId;

    public Service() {
        dbConfig = new DBConfig();
        sessionFactory = dbConfig.getSessionFactory();
        usersDao = new UsersDao(sessionFactory);
        accountsDao = new AccountsDao(sessionFactory);
    }

    // login
    public boolean login(String username, String pin) {
        if (currentUser != null) {
            System.out.println("Deja sunteti autentificat cu utilizatorul: " + currentUser);
            return false;
        }

        UsersModel usersModel = usersDao.getUserByUserName(username);
        if (!pin.equals(usersModel.getPin())) {
            return false;
        }
        currentUser = username;
        return true;
    }

    // logout
    public boolean logout() {
        if (currentUser == null) {
            return false;
        }
        currentUser = null;
        return true;
    }

    // list accounts

    public List<AccountsModel> getAccountsForLoggedUser() {
        if (currentUser == null) {
//            System.out.println("Nici un user nu este logat!");
            return Collections.emptyList();
        }
        UsersModel usersModel = usersDao.getUserByUserName(currentUser);
        int id = usersModel.getId();
        return accountsDao.findAllAccountsById(id);
    }

    public void accountDeposit(int accountId, double amount, String currency) {
        UsersModel usersModel = usersDao.getUserByUserName(currentUser);
        int id = usersModel.getId();

        AccountsModel accountsModel = new AccountsModel();
//        accountsModel.setUserId(id);

        int validateAccountId = accountsDao.getAccountById(id);


        accountsModel.setId(accountId);
        double currentAmount = accountsModel.getAmount();
        accountsModel.setAmount(currentAmount + amount);
        accountsModel.setCurrency(currency);

        if (id != validateAccountId) {
            System.out.println("Ne pare rau. Contul selectat nu este valid sau apartine unei alte persoane.\nVa rugam" +
                    " " +
                    "reintroduceti comanda de depunere.");
            System.out.println("id = " +id+"\nvalidateAccountId = "+validateAccountId);
        } else {
            accountsDao.updateAccount(accountsModel);
        }
    }
/*
- de luat toate conturile utilizatorului curent cu metoda de mai sus (list accounts)
- selectez contul in care vreau sa depun (.get....) - account model
- .setamount de getamount + amount
sout ca s -a depus, sau ca nu se poate
- apelam medota din DAO cu updateAccount(


 */

}
