package com.naztech.naztechjdbc.test;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.naztech.naztechjdbc.service.NaztechJdbcService;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        // Create the DataSource
        DataSource dataSource = DataSourceConfig.createDataSource();

        // Call your library function with the DataSource
        NaztechJdbcService jdbcService = new NaztechJdbcService(dataSource);

        Student student = new Student();
        student.setName("jewel");
        student.setId(1);
        student.setAge(34);
        student.setGrade("G");
        
        // Call the executeStoreProcedure method to execute the stored procedure
        List<Student> students = jdbcService.executeStoreProcedure("GetStudentDetails", student);

        // Print the retrieved student details
        Utils.PrintList(students);
        
    }


}
