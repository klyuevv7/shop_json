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
    public ResultSet findConsumerBySurname(String surName) throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.consumer where surname = '"+ surName + "'");
            return result;
        }
        return null;
    }

    @Override
    public ResultSet findBuyByProduct(String productName) throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT id FROM public.product where name = '"+ productName + "'");
            long productId = 0;
            if (result.next()) productId = result.getLong(1);
            result = statement.executeQuery(
                    "SELECT * FROM public.buy where product = '"+ productId + "'");
            return result;
        }
        return null;
    }

    @Override
    public ResultSet findConsumersById(long id) throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.consumer where id = '"+ id + "'");
            return result;
        }
        return null;
    }

    @Override
    public ResultSet findAllBuy() throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.buy");
            return result;
        }
        return null;
    }

    @Override
    public ResultSet findAllProduct() throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.product");
            return result;
        }
        return null;
    }

    @Override
    public ResultSet findProductById(long id) throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.product where id = '"+ id + "'");
            return result;
        }
        return null;
    }

    @Override
    public ResultSet findBuyByConsumerId(long ConsumerId) throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.buy where consumer = '"+ ConsumerId + "'");
            return result;
        }
        return null;
    }

    public static void main(String[] args) throws SQLException {
        ResultSet resultSet = new DaoShop(new JDBCPostgreSQL().connection()).findConsumerBySurname("Иванов");

    }
}
