package org.example.controller;

import org.example.model.Buy;
import org.example.model.Consumer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchController implements ServiceControllerOperations {
    DaoOperations daoOperations;

    public SearchController(DaoOperations daoOperations) {
        this.daoOperations = daoOperations;
    }

    @Override
    public List<Consumer> findConsumerBySurname(String lastName) throws SQLException {
        ResultSet resultSet =  daoOperations.findConsumerBySurname(lastName);
        List<Consumer> listConsumer = new ArrayList<>();
        while (resultSet.next())
            listConsumer.add(new Consumer(resultSet.getLong(1),
                                          resultSet.getString(2),
                                          resultSet.getString(3)));
        return listConsumer;
    }

    @Override
    public List<Consumer> findConsumerByCountProductBuy(String nameProduct, int CountProductBuy)
                                             throws SQLException {
        ResultSet resultSet =  daoOperations.findBuyByProduct(nameProduct);
        List<Buy> listBuyByProduct = new ArrayList<>();
        while (resultSet.next())
            listBuyByProduct.add(new Buy(resultSet.getLong(1),
                                         resultSet.getLong(2),
                                         resultSet.getLong(3),
                                         resultSet.getDate(4)));
        return ;
    }

    @Override
    public List<Consumer> findConsumerByIntervalExpensesAllBuy(int minExpensesAllBuy, int maxExpensesAllBuy) {
        return null;
    }

    @Override
    public List<Consumer> findBadConsumerByCountProductBuy(int countBadConsumer) {
        return null;
    }
}
