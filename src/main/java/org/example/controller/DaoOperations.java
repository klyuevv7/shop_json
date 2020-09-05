package org.example.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DaoOperations {
    ResultSet findConsumerBySurname(String surname) throws SQLException;

    ResultSet findBuyByProduct(String ProductName);
//    List<ResultSet> findConsumersById(List<Long> ListId);

//    ResultSet findBuyByConsumer();
//    ResultSet findAllProduct();
//
//    ResultSet findProductById(long id);
//    ResultSet findBuyByConsumerId(long ConsumerId);


}
