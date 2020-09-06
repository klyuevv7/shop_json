package org.example.controller;

import org.example.model.Consumer;

import java.sql.SQLException;
import java.util.List;

public interface ServiceControllerOperations {
    List<Consumer> findConsumerBySurname(String lastName) throws SQLException;
    List<Consumer> findConsumerByCountProductBuy(String nameProduct, int CountProductBuy) throws SQLException;
    List<Consumer> findConsumerByIntervalExpensesAllBuy(int minExpensesAllBuy, int maxExpensesAllBuy) throws SQLException;
    List<Consumer> findBadConsumerByCountProductBuy(int countBadConsumer) throws SQLException;
}
