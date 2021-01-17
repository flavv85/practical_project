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
import com.sda.practical.project.model.UsersModel;
import org.hibernate.SessionFactory;

public class Service {

    private DBConfig dbConfig;
    SessionFactory sessionFactory;
    UsersDao usersDao;
    AccountsDao accountsDao;

    private String currentUser;

    public Service() {
        dbConfig = new DBConfig();
        sessionFactory = dbConfig.getSessionFactory();
        usersDao = new UsersDao(sessionFactory);
        accountsDao = new AccountsDao(sessionFactory);
    }


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

    public boolean logout(String username) {
        if (currentUser == null) {
            return false;
        }
        currentUser = username;
        currentUser = null;
        return true;
    }

}
