package org.maxim.crud.Utilc;


import liquibase.command.CommandScope;
import liquibase.command.core.UpdateCommandStep;
import liquibase.command.core.helpers.DbUrlConnectionCommandStep;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class LiquibaseUtils {
    public void liquibaseRun() {
        final Properties properties = new Properties();
        final Connection connection;

        try (InputStream fileInputStream = new FileInputStream("src/main/resources/jdbc.properties")) {
            properties.load(fileInputStream);
            String URL  = properties.getProperty("url");
            String USERNAME = properties.getProperty("username");
            String PASSWORD = properties.getProperty("password");

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            CommandScope updateCommand = new CommandScope(UpdateCommandStep.COMMAND_NAME);
            updateCommand.addArgumentValue(DbUrlConnectionCommandStep.DATABASE_ARG, database);
            updateCommand.addArgumentValue(UpdateCommandStep.CHANGELOG_FILE_ARG, "db/changelog/changelog-main.sql");
            updateCommand.execute();
            connection.close();
        } catch (LiquibaseException | IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
