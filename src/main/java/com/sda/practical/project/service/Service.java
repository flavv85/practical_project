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

import javax.persistence.PersistenceException;
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

        UsersModel usersModel = null;
        try {
            usersModel = usersDao.getUserByUserName(username);
        } catch (PersistenceException e) {
            return false;
        }

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
            //  System.out.println("Nici un user nu este logat!");
            return Collections.emptyList();
        }
        UsersModel usersModel = usersDao.getUserByUserName(currentUser);
        int id = usersModel.getId();
        return accountsDao.findAllAccountsById(id);
    }

    // deposit in account
    public boolean accountDeposit(int accountId, double amount, String currency) {
        UsersModel usersModel = usersDao.getUserByUserName(currentUser);
        int currentId = usersModel.getId();
        // luat toate conturile utilizatorului curent
        List<AccountsModel> accounts = accountsDao.findAllAccountsById(currentId);
        boolean accountOwnedByUser = false;
        // vreau sa bag in accountId 4
        // accounts 4 si 5 din db
        AccountsModel accountsModel = null;
        for (AccountsModel account : accounts) {
            if (account.getId() == accountId) {
                accountOwnedByUser = true;
                // iau contul corespunzator din lista de conturi ale utilizatorului curent
                accountsModel = account;
                break;
            }
        }
        // la && validam ca input-ul currency sa corespunda ca cel din db aferenta account ID-ului selectat
        if (accountOwnedByUser == true && accountsModel.getCurrency().equals(currency)) {
            accountsModel.setId(accountId);
            double currentAmount = accountsModel.getAmount();
            accountsModel.setAmount(currentAmount + amount);
            accountsModel.setCurrency(currency);
            accountsDao.updateAccount(accountsModel);
            return true;
        }
        return false;
    }

    // withdraw from account
    public boolean accountWithdraw(int accountId, double amount) {
        UsersModel usersModel = usersDao.getUserByUserName(currentUser);
        int currentId = usersModel.getId();
        List<AccountsModel> accounts = accountsDao.findAllAccountsById(currentId);
        boolean accountOwnedByUser = false;
        AccountsModel accountsModel = null;
        for (AccountsModel account : accounts) {
            if (account.getId() == accountId) {
                accountOwnedByUser = true;
                accountsModel = account;
                break;
            }
        }
        if (accountOwnedByUser == true) {
            accountsModel.setId(accountId);
            double currentAmount = accountsModel.getAmount();
            if (amount <= currentAmount) {
                accountsModel.setAmount(currentAmount - amount);
                accountsDao.updateAccount(accountsModel);
                return true;

            }
        }
        return false;
    }

    // add account
    public void newAccount(String currency) {
        UsersModel usersModel = usersDao.getUserByUserName(currentUser);
        int currentId = usersModel.getId();
        usersModel.setId(currentId);

        int initialAmount = 0;
        AccountsModel accountsModel = new AccountsModel();
        accountsModel.setCurrency(currency);
        accountsModel.setAmount(initialAmount);
        accountsModel.setUserId(currentId);

        accountsDao.createAccount(accountsModel);
    }
}
