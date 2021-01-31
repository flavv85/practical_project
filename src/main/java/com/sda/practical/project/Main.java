package com.sda.practical.project;

import com.sda.practical.project.dao.AccountsDao;
import com.sda.practical.project.model.AccountsModel;
import com.sda.practical.project.service.Service;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Service service = new Service();
        Message message1 = new Message();

        String command = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--------------\nEnter command: \n- LOGIN: login username pin\n- EXIT: exit");
        while (!command.equals("exit")) {
            command = scanner.next();
            //login
            if (command.equals("login")) {
                String username = scanner.next();
                String pin = scanner.next();

                boolean success = service.login(username, pin);
                if (success) {
                    System.out.println("\n--------------------------------------");
                    System.out.println("Autentificarea s-a realizat cu succes!");
                    message1.menuAfterOperation();
                } else {
                    System.out.println("\n------------------");
                    System.out.println("A aparut o eroare!\nReintroduceti login username si pin...\nsau introduceti " +
                            "'exit' " +
                            "pentru a parasi aplicatia.");
                }
            }
            //logout
            if (command.equals("logout")) {
                boolean success = service.logout();
                if (success) {
                    System.out.println("\nV-ati delogat!\n--------------\nEnter command: \n- LOGIN: login username " +
                            "pin\n- EXIT: exit");
                } else {
                    System.out.println("\n---------------------------\nA aparut o eroare. Nu exista utilizator " +
                            "logat!\n--------------\nEnter command: \n- LOGIN: login username pin\n- EXIT: exit");
                }
            }
            // afisare situatie conturi curente
            if (command.equals("accounts")) {
                List<AccountsModel> accountsModelList = service.getAccountsForLoggedUser();
                if (accountsModelList.isEmpty()) {
                    System.out.println("Utilizatorul curent nu are conturi deschise sau nu este logat.");
                } else {
                    System.out.println("--------------------------");
                    System.out.println("Accounts for current user:");

                    for (AccountsModel x : accountsModelList
                    ) {
                        System.out.println("Account Id: " + x.getId() + " - amount: " + x.getAmount() + " " + x.getCurrency());
                    }
                    message1.menuAfterOperation();
                }
            }
            // depunere
            if (command.equals("deposit")) {
                int accountId = scanner.nextInt();
                double amount = scanner.nextDouble();
                String currency = scanner.next();

                boolean success = service.accountDeposit(accountId, amount, currency.toUpperCase());
                if (success) {
                    System.out.println("Ati depus suma de " + amount);
                } else {
                    System.out.println("Contul nu va apartine sau valuta nu corespunde.");
                }
                message1.menuAfterOperation();

            }
            // retragere
            if (command.equals("withdraw")) {
                int accountId = scanner.nextInt();
                double amount = scanner.nextDouble();

                boolean success = service.accountWithdraw(accountId, amount);
                if (success == true) {
                    System.out.println("Ati retras suma de " + amount);
                } else {
                    System.out.println("Fonduri Insuficiente!");
                }
                System.out.println("\nSituatia conturilor este:");
                List<AccountsModel> accountsModelList = service.getAccountsForLoggedUser();
                for (AccountsModel accountsModel : accountsModelList) {
                    System.out.println("Account Id: " + accountsModel.getId() + " amount left: " + accountsModel.getAmount() + " " + accountsModel.getCurrency());
                }
                message1.menuAfterOperation();
            }
            // adaugare cont la user curent
            if(command.equals("add")){
                String currency = scanner.next();
                currency = currency.toUpperCase();
                service.newAccount(currency);
                System.out.println("Contul a fost creat cu succes!\n");
                message1.menuAfterOperation();
            }
        }
    }
}
