package com.sda.practical.project.model;

import javax.persistence.*;

@Entity
@Table(name = "Transactions")

public class TransactionsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

}
