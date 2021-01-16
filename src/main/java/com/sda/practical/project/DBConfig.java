package com.sda.practical.project;

import com.sda.practical.project.model.AccountsModel;
import com.sda.practical.project.model.TransactionsModel;
import com.sda.practical.project.model.UsersModel;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class DBConfig {

    private SessionFactory sessionFactory;

    public DBConfig() {

        Configuration configuration = new Configuration();

        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/banking_012021");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "12345");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect"); // facultativ
        properties.put(Environment.SHOW_SQL, true); // facultativ sa ne ajute sa vedem ca se executa query-ul

        properties.put(Environment.HBM2DDL_AUTO, "update");

        configuration.addAnnotatedClass(UsersModel.class);
        configuration.addAnnotatedClass(AccountsModel.class);
//        configuration.addAnnotatedClass(TransactionsModel.class);



        configuration.setProperties(properties);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;

    }

}
