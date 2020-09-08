package org.example.service.searchofcriterians;

import org.example.controller.ServiceControllerOperations;
import org.example.model.Consumer;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class SearchOfCriterionProductName implements SearchOfCriterion {
    private final ServiceControllerOperations searchController;

    public SearchOfCriterionProductName(ServiceControllerOperations searchController) {
        this.searchController = searchController;
    }

    public String findConsumerByCountProductBuy(String nameProduct, int CountProductBuy) throws Exception {
        List<Consumer> listConsumers =
             searchController.findConsumerByCountProductBuy(nameProduct, CountProductBuy);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for(Consumer consumer : listConsumers)
            jsonObjectList.add(consumer.toJSONObject());
        return jsonObjectList.toString();
    }

    @Override
    public String result(JSONObject jsonObject) throws Exception {
        return findConsumerByCountProductBuy(jsonObject.getString("productName"),
                                             jsonObject.getInt("minTimes"));
    }
}
