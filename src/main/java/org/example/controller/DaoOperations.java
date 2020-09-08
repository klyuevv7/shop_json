package org.example.controller;

import java.sql.ResultSet;

public interface DaoOperations {
    ResultSet findConsumerBySurname(String surname) throws Exception;

    ResultSet findBuyByProduct(String productName) throws Exception;
    ResultSet findConsumersById(long id) throws Exception;

    ResultSet findAllBuy() throws Exception;
    ResultSet findAllProduct() throws Exception;

    ResultSet findAllConsumer() throws Exception;
}
