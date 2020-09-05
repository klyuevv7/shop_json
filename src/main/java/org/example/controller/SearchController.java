package org.example.controller;

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
        List<Consumer> list = new ArrayList<>();
        while (resultSet.next())
            list.add(new Consumer(Long.parseLong(resultSet.getString(1)),
                                                 resultSet.getString(2),
                                                 resultSet.getString(3)));
        return list;
    }

    @Override
    public List<Consumer> findConsumerByCountProductBuy(String nameProduct, int CountProductBuy) {
        return null;
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
