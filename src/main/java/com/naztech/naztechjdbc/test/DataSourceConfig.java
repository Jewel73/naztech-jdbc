package com.naztech.naztechjdbc.test;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSourceConfig {

    public static DataSource createDataSource() {
        String jdbcUrl = "jdbc:mysql://bank-db.mysql.database.azure.com:3306/userservice";
        String username = "azuredb";
        String password = "jewelrana@1971";

        // Create the DataSource
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(5); // Set the initial pool size
        // Other configuration options can be set here

        return dataSource;
    }
}
