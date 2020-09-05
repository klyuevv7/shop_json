package org.example.dao;

import org.example.controller.DaoOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Получает значения из таблиц базы данных
 */
public class DaoShop implements DaoOperations {
    private Connection connection;
    /**
     * @param connection -  соединение с базой данных
     */
    public DaoShop(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ResultSet findConsumerBySurname(String surname) throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.consumer where surname = '"+ surname + "'");
            return result;
        }
        return null;
    }
    public static void main(String[] args) throws SQLException {
        ResultSet resultSet = new DaoShop(new JDBCPostgreSQL().connection()).findConsumerBySurname("Иванов");

    }

}
