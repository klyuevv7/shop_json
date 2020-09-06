package org.example.service.searchofcriterians;

import org.example.controller.ServiceControllerOperations;
import org.example.model.Consumer;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SearchOfCriterionBadCustomers implements SearchOfCriterion {
    private ServiceControllerOperations searchController;

    public SearchOfCriterionBadCustomers(ServiceControllerOperations searchController) {
        this.searchController = searchController;
    }

    public String findBadConsumerByCountProductBuy(int countBadConsumer) throws SQLException {
        List<Consumer> listConsumers =
                searchController.findBadConsumerByCountProductBuy(countBadConsumer);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for(Consumer consumer : listConsumers)
            jsonObjectList.add(consumer.toJSONObject());
        return jsonObjectList.toString();
    }

    @Override
    public String result(JSONObject jsonObject) throws SQLException {
        return findBadConsumerByCountProductBuy(jsonObject.getInt("badCustomers"));
    }
}
