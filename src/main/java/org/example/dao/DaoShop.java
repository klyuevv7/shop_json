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
    private final Connection connection;
    /**
     * @param connection -  соединение с базой данных
     */
    public DaoShop(Connection connection) {
        this.connection = connection;
    }

    @Override
    public ResultSet findConsumerBySurname(String surName) throws Exception {
        try {
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(
                        "SELECT * FROM public.consumer where surname = '"+ surName + "'");
                return result;
        } catch (Exception e) {
            throw new Exception("Ошибка получения данных из таблицы покупателей в БД");
        }
    }

    @Override
    public ResultSet findBuyByProduct(String productName) throws Exception {
        ResultSet result = null;
        try {
                Statement statement = connection.createStatement();
                result = statement.executeQuery(
                        "SELECT id FROM public.product where name = '"+ productName + "'");
        } catch (Exception e) {
            throw new Exception("Ошибка получения данных из таблицы товаров в БД");
        }
        try {
            Statement statement = connection.createStatement();
            long productId = 0;
            if (result.next()) productId = result.getLong(1);
            result = statement.executeQuery(
                    "SELECT * FROM public.buy where product = '"+ productId + "'");
            return result;
        } catch (Exception e) {
            throw new Exception("Ошибка получения данных из таблицы покупок в БД");
        }
    }

    @Override
    public ResultSet findConsumersById(long id) throws Exception {
        try {
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(
                        "SELECT * FROM public.consumer where id = '"+ id + "'");
                return result;
        } catch (Exception e) {
            throw new Exception("Ошибка получения данных из таблицы покупателей в БД");
        }
    }

    @Override
    public ResultSet findAllBuy() throws Exception {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.buy");
            return result;
        } catch (Exception e) {
            throw new Exception("Ошибка получения данных из таблицы покупок в БД");
        }
    }

    @Override
    public ResultSet findAllProduct() throws Exception {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.product");
            return result;
        } catch (Exception e) {
            throw new Exception("Ошибка получения данных из таблицы товаров в БД");
        }
    }

    @Override
    public ResultSet findAllConsumer() throws Exception {
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(
                    "SELECT * FROM public.consumer");
            return result;
        } catch (Exception e) {
            throw new Exception("Ошибка получения данных из таблицы покупателей в БД");
        }
    }

    public static void main(String[] args) throws Exception {
        ResultSet resultSet = new DaoShop(new JDBCPostgreSQL().connection()).findConsumerBySurname("Иванов");
    }
}
