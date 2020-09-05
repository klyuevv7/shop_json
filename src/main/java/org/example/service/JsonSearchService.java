package org.example.service;

import org.example.controller.ServiceControllerOperations;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonSearchService implements RequestService {
    ServiceControllerOperations searchController;

    public JsonSearchService(ServiceControllerOperations searchController) {
        this.searchController = searchController;
    }

    @Override
    public String result(String request) throws SQLException {
        JSONObject jsonRequest = new JSONObject(request);
        JSONArray criterias = jsonRequest.getJSONArray("criterias");
// Список всех критериев поиска (могут повторяться)
        List<String> listCriterias = new ArrayList<>();
// Набор всех критериев поиска с результатами поиска
        Map<String, String> mapCriteriasAndResult = new HashMap<>();
        SelectCriterion selectCriterion = new SelectCriterion(searchController);
        for (Object object : criterias) {
            String criterion = object.toString();
            listCriterias.add(criterion);
// Если критерий поиска уже был, то искать результат не нужно, он есть в данном наборе
            if(mapCriteriasAndResult.containsKey(criterion)) continue;
            JSONObject jsonObject = ((JSONObject) object);
            String result = null;
            boolean keyCriterionFinded = false;
            for (String keyCriterion : selectCriterion.getCriterion().keySet()){
                if(jsonObject.has(keyCriterion)) {
                    keyCriterionFinded = true;
                    result = selectCriterion.getCriterion().get(keyCriterion).result(jsonObject);
                }
            }
            if(!keyCriterionFinded){
//                return "{\"type\":\"error\",\"message\":\"Ключ не поддерживается " +
//                         "в критерии поиска: " + criterion +"\"}";
            }

//            if (jsonObject.has("lastName")){
//                List<String> keysOfCriterians = new ArrayList<>();
//                keysOfCriterians.add((String) jsonObject.get("lastName"));
//                result = selectCriterion.getCriterion().get("lastName").result(keysOfCriterians);
//            } else
//            if(jsonObject.has("productName")){
//                String productName = (String) jsonObject.get("productName");
//                int minTimes = (int) jsonObject.get("minTimes");
//                result = findConsumerByCountProductBuy(productName, minTimes);
//            } else
//            if(jsonObject.has("minExpenses")){
//                int minExpenses = (int) jsonObject.get("minExpenses");
//                int maxExpenses = (int) jsonObject.get("maxExpenses");
//                result = findConsumerByIntervalExpensesAllBuy(minExpenses,maxExpenses);
//            } else
//            if(jsonObject.has("badCustomers")){
//                int badCustomers = (int) jsonObject.get("badCustomers");
//                result = findBadConsumerByCountProductBuy(badCustomers);
//            }
            mapCriteriasAndResult.put(criterion,result);
        }
        StringBuilder result = new StringBuilder("{\"type\":\"search\",\"results\":[");
        for (String criterion : listCriterias){
           result.append("{\"criteria\":");
           result.append(criterion);
           result.append(",\"results\":");
           result.append(mapCriteriasAndResult.get(criterion));
           result.append("},");
        }
        int endChar = result.length();
        result.delete(endChar-1, endChar);
        result.append("]}");
        return result.toString();
    }
}
