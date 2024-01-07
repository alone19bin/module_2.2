package org.maxim.crud;

import org.maxim.crud.Utilc.JDBCUtils;
import org.maxim.crud.Utilc.LiquibaseUtils;
import org.maxim.crud.view.MainView;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
       // LiquibaseUtils liquibaseUtils = new LiquibaseUtils();
       // liquibaseUtils.liquibaseRun();
        MainView mainView = new MainView();
        mainView.run();
    }
}
