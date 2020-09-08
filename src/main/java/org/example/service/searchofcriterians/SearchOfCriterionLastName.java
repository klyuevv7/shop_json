package org.example.service.searchofcriterians;

import org.example.controller.ServiceControllerOperations;
import org.example.model.Consumer;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SearchOfCriterionLastName implements SearchOfCriterion {
    private final ServiceControllerOperations searchController;

    public SearchOfCriterionLastName(ServiceControllerOperations searchController) {
        this.searchController = searchController;
    }

    public String findConsumerBySurname(String lastName) throws Exception {
        List<Consumer> listConsumers =  searchController.findConsumerBySurname(lastName);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for(Consumer consumer : listConsumers)
            jsonObjectList.add(consumer.toJSONObject());
        return jsonObjectList.toString();
    }

    @Override
    public String result(JSONObject jsonObject) throws Exception {
        return findConsumerBySurname(jsonObject.getString("lastName"));
    }
}
