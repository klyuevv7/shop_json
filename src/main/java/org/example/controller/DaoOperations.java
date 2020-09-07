package org.example.controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DaoOperations {
    ResultSet findConsumerBySurname(String surname) throws SQLException;

    ResultSet findBuyByProduct(String productName) throws SQLException;
    ResultSet findConsumersById(long id) throws SQLException;

    ResultSet findAllBuy() throws SQLException;
    ResultSet findAllProduct() throws SQLException;

    ResultSet findAllConsumer() throws SQLException;
}
