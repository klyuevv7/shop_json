package org.example.service;

import org.example.controller.ServiceControllerOperations;
import org.json.JSONArray;
import org.json.JSONObject;
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
    public String result(String request) throws Exception {
        JSONObject jsonRequest = null;
        JSONArray criterias = null;
        try {
            jsonRequest = new JSONObject(request);
            criterias = jsonRequest.getJSONArray("criterias");
        } catch (Exception e){
            return "{\"type\":\"error\",\"message\":\"Ошибка получения критериев поиска\"}";
        }
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
                return "{\"type\":\"error\",\"message\":\"Ключ не поддерживается " +
                         "в критерии поиска: " + criterion +"\"}";
            }
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
