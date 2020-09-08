package org.example.controller;

import org.example.model.Consumer;

import java.sql.SQLException;
import java.util.List;

public interface ServiceControllerOperations {
    List<Consumer> findConsumerBySurname(String lastName) throws Exception;
    List<Consumer> findConsumerByCountProductBuy(String nameProduct, int CountProductBuy) throws Exception;
    List<Consumer> findConsumerByIntervalExpensesAllBuy(int minExpensesAllBuy, int maxExpensesAllBuy) throws Exception;
    List<Consumer> findBadConsumerByCountProductBuy(int countBadConsumer) throws Exception;
}
