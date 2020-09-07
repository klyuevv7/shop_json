package org.example.service;

import org.example.controller.StatControllerOperations;
import org.example.model.Consumer;
import org.example.model.Product;
import org.example.service.forStatService.Purchases;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
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
    public String result(String request) throws SQLException {
        JSONObject jsonRequest = new JSONObject(request);
        String stringstartDate = jsonRequest.getString("startDate");
        String stringendDate = jsonRequest.getString("endDate");
        Date startDate = null;
        Date endDate = null;
        try{
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(stringstartDate);
            endDate =  new SimpleDateFormat("yyyy-MM-dd").parse(stringendDate);
        }catch(Exception e){
            System.out.println(e);
        }
        List<Consumer> listConsumer = new ArrayList<>();
        List<Product> listProduct = new ArrayList<>();
// Создание множества: ключ - идентификатор покупателя, значение - множество,
// где ключ идентификатор товара, значение - стоимость покупок данного товара
        Map<Long, Map<Long,Long>> mapConsumerIdAndProductExpenses
           = statController.statConsumerByPeriod(startDate, endDate, listConsumer, listProduct);

        StringBuilder result = new StringBuilder("{\"type\":\"stat\",\"totalDays\":" +
                (endDate.getDay() - startDate.getDay()) + ",\"customers\":[");
        long totalExpensesAllConsumer = 0;
        for (Map.Entry<Long,Map<Long,Long>> itemByConsumerId : mapConsumerIdAndProductExpenses.entrySet()) {
            String consumerSurname = null;
            String consumerName = null;
            for (Consumer consumer : listConsumer){
                if(consumer.getId() == itemByConsumerId.getKey()){
                    consumerName = consumer.getName();
                    consumerSurname = consumer.getSurname(); break;
                }
            }
            result.append("{\"name\":\"" + consumerSurname + " " + consumerName + ",\"purchases\":[");
            Map<Long,Long> mapProductExpenses = mapConsumerIdAndProductExpenses.get(itemByConsumerId.getKey());
//            List<JSONObject> jsonObjectListPurchases = new ArrayList<>();
            long totalExpenses = 0;
            for (Map.Entry<Long,Long> itemByProductId : mapProductExpenses.entrySet()){
                String productName = null;
                for (Product product : listProduct){
                    if(product.getId() == itemByProductId.getKey()){
                        productName = product.getName(); break;
                    }
                }
                long expenses = itemByProductId.getValue();
//                jsonObjectListPurchases.add(new Purchases(productName, expenses).toJSONObject());
                result.append(new Purchases(productName, expenses).toJSONObject() + ",");
                totalExpenses =+ expenses;
//                return jsonObjectList.toString();
            }
            int endChar = result.length();
            result.delete(endChar-1, endChar);
            result.append("\"totalExpenses\":" + totalExpenses + "},");
            totalExpensesAllConsumer =+ totalExpenses;
        }
        int endChar = result.length();
        result.delete(endChar-1, endChar);
        result.append("],\"totalExpenses\":" + totalExpensesAllConsumer + ",");
        double avgExpenses = totalExpensesAllConsumer /
                (double) mapConsumerIdAndProductExpenses.entrySet().size();
        result.append("\"avgExpenses\":" + avgExpenses + "}");
        return result.toString();
    }
}
