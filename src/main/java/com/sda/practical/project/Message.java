package com.sda.practical.project;

public class Message {
    public void menuAfterOperation(){
        System.out.println("--------------------------------------");
        System.out.println("Puteti realiza urmatoarele operatiuni:\n- accounts - vizualizare conturi\n- add " +
                "(currency) - adauga" +
                " un cont nou\n- deposit" +
                " (accountId amount currency)" +
                " - depuneti bani\n- withdraw (accountId amount) - eliberare " +
                "numerar\n- transfer - transfera bani dintr-un cont in altul (implica conversie)\n- " +
                "LOGOUT - logout username\n- " +
                "EXIT - enter 'exit' to leave app");
    }
}
