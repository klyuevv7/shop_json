package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * В этой программе проверяется правильность конфигурирования
 * базы данных и драйвера JDBC, выполняется соединение с базой данных
 */
public class JDBCPostgreSQL {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/db_shop";
    static final String USER = "postgres";
    static final String PASS = "postgres";

    /**
     * @return Возвращает соединение с базой данных
     * или null, если соединение с БД не установлено.
     */
    public Connection connection() throws Exception {
//        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("PostgreSQL JDBC Driver is not found. Include it in your library path");
//            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path");
//            e.printStackTrace();
//            return null;
        }

//        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            throw new Exception("Connection to DB failed");
//            System.out.println("Connection Failed");
//            e.printStackTrace();
//            return null;
        }

        if (connection != null) {
//            System.out.println("You successfully connected to database now");
        } else {
            throw new Exception("Failed to make connection to database");
//            System.out.println("Failed to make connection to database");
        }
        return connection;
    }
}
