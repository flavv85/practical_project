package com.sda.practical.project;

import com.sda.practical.project.model.AccountsModel;
import com.sda.practical.project.service.Service;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Service service = new Service();

        String command = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--------------\nEnter command: \n- LOGIN: login username pin\n- EXIT: exit");
        while (!command.equals("exit")) {
            command = scanner.next();
            if (command.equals("login")) {
                String username = scanner.next();
                String pin = scanner.next();

                boolean success = service.login(username, pin);
                if (success) {
                    System.out.println("\n--------------------------------------");
                    System.out.println("Autentificarea s-a realizat cu succes!");
                    System.out.println("--------------------------------------");
                    System.out.println("Puteti realiza urmatoarele operatiuni:\n- accounts - vizualizare " +
                            "conturi\n- add - adauga un cont nou\n- deposit - depuneti bani\n- withdraw - eliberare " +
                            "numerar\n- transfer - transfera bani dintr-un cont in altul (implica conversie)\n- " +
                            "LOGOUT - logout username\n- " +
                            "EXIT - enter 'exit' to leave app");
                } else {
                    System.out.println("\n------------------");
                    System.out.println("A aparut o eroare!\nReintroduceti login username si pin...\nsau introduceti " +
                            "'exit' " +
                            "pentru a parasi aplicatia.");
                }
            }
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
                    System.out.println("--------------------------------------");
                    System.out.println("Puteti realiza urmatoarele operatiuni:\n- add - adauga un cont nou\n- deposit" +
                            " - depuneti bani\n- withdraw - eliberare " +
                            "numerar\n- transfer - transfera bani dintr-un cont in altul (implica conversie)\n- " +
                            "LOGOUT - logout username\n- " +
                            "EXIT - enter 'exit' to leave app");
                }
            }
            if (command.equals("deposit")) {
                int accountId = scanner.nextInt();
                double amount = scanner.nextDouble();
                String currency = scanner.next();

                service.accountDeposit(accountId, amount, currency);
            }

        }
    }
}
