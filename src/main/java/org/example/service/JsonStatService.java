package org.example.service;

import org.example.controller.StatControllerOperations;
import org.example.model.Consumer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

public class JsonStatService implements RequestService {
    StatControllerOperations statController;

    public JsonStatService(StatControllerOperations statController) {
        this.statController = statController;
    }

    public List<Consumer> statConsumerByPeriod(Date startDate, Date endDate) {
        return null;
    }


    @Override
    public String result(String request) {
        JSONObject jsonObject = new JSONObject(request);
        String stringstartDate = jsonObject.getString("startDate");
        String stringendDate = jsonObject.getString("endDate");
        Date startDate = null;
        Date endDate = null;
        try{
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(stringstartDate);
            endDate =  new SimpleDateFormat("yyyy-MM-dd").parse(stringendDate);
        }catch(Exception e){
            System.out.println(e);
        }
        List<Consumer> listConsumers =
                statController.statConsumerByPeriod(startDate, endDate);
        List<JSONObject> jsonObjectList = new ArrayList<>();
        for(Consumer consumer : listConsumers)
            jsonObjectList.add(consumer.toJSONObject());
//        return jsonObjectList.toString();

//----

//        JSONArray criterias = jsonRequest.getJSONArray("criterias");
// Список всех критериев поиска (могут повторяться)
//        List<String> listCriterias = new ArrayList<>();
// Набор всех критериев поиска с результатами поиска
//        Map<String, String> mapCriteriasAndResult = new HashMap<>();
//        SelectCriterion selectCriterion = new SelectCriterion(searchController);
//        for (Object object : criterias) {
//            String criterion = object.toString();
//            listCriterias.add(criterion);
// Если критерий поиска уже был, то искать результат не нужно, он есть в данном наборе
//            if(mapCriteriasAndResult.containsKey(criterion)) continue;
//            JSONObject jsonObject = ((JSONObject) object);
//            String result = null;
//            boolean keyCriterionFinded = false;
//            for (String keyCriterion : selectCriterion.getCriterion().keySet()){
//                if(jsonObject.has(keyCriterion)) {
//                    keyCriterionFinded = true;
//                    result = selectCriterion.getCriterion().get(keyCriterion).result(jsonObject);
//                }
//            }
//            if(!keyCriterionFinded){
//                return "{\"type\":\"error\",\"message\":\"Ключ не поддерживается " +
//                         "в критерии поиска: " + criterion +"\"}";
//            }
//            mapCriteriasAndResult.put(criterion,result);
//        }
//        StringBuilder result = new StringBuilder("{\"type\":\"search\",\"results\":[");
//        for (String criterion : listCriterias){
//            result.append("{\"criteria\":");
//            result.append(criterion);
//            result.append(",\"results\":");
//            result.append(mapCriteriasAndResult.get(criterion));
//            result.append("},");
//        }
//        int endChar = result.length();
//        result.delete(endChar-1, endChar);
//        result.append("]}");
//        return result.toString();
        return "None";
    }
}
