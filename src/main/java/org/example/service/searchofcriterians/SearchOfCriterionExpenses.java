package org.example.service.searchofcriterians;

import org.example.controller.ServiceControllerOperations;
import org.example.model.Consumer;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchOfCriterionExpenses implements SearchOfCriterion{
    private ServiceControllerOperations searchController;

    public SearchOfCriterionExpenses(ServiceControllerOperations searchController) {
        this.searchController = searchController;
    }

    public String findConsumerByIntervalExpensesAllBuy(int minExpensesAllBuy, int maxExpensesAllBuy)
                                                                            throws SQLException {
        List<Consumer> listConsumers =
                searchController.findConsumerByIntervalExpensesAllBuy(minExpensesAllBuy, maxExpensesAllBuy);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for(Consumer consumer : listConsumers)
            jsonObjectList.add(consumer.toJSONObject());
        return jsonObjectList.toString();
    }

    @Override
    public String result(JSONObject jsonObject) throws SQLException {
        return findConsumerByIntervalExpensesAllBuy(jsonObject.getInt("minExpenses"),
                                                    jsonObject.getInt("maxExpenses"));
    }
}
