package com.sda.practical.project;

import com.sda.practical.project.dao.AccountsDao;
import com.sda.practical.project.dao.UsersDao;
import com.sda.practical.project.service.Service;
import org.hibernate.SessionFactory;

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
                String username = scanner.next();

                boolean success = service.logout(username);
                if (success) {
                    System.out.println("\nV-ati delogat!\n--------------\nEnter command: \n- LOGIN: login username " +
                            "pin\n- EXIT: exit");
                } else {
                    System.out.println("\n---------------------------\nA aparut o eroare. Nu exista utilizator " +
                            "logat!\n--------------\nEnter command: \n- LOGIN: login username pin\n- EXIT: exit");
                }
            }
        }
    }
}
