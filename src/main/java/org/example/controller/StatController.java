package org.example.controller;

import org.example.model.Buy;
import org.example.model.Consumer;
import org.example.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StatController implements StatControllerOperations {
    DaoOperations daoOperations;

    public StatController(DaoOperations daoOperations) {
        this.daoOperations = daoOperations;
    }

    /**
     * На вход передаётся интервал дат сбора статистики. Результат операции -
     * статистика по покупателям за период из двух дат, включительно, без выходных
     * @param startDate - Начальная дата
     * @param endDate - Конечная дата
     * @return Возвращает статистику по покупателям за период из двух дат, включительно, без выходных
     */
    @Override
    public Map<Long, Map<Long,Long>> statConsumerByPeriod(Date startDate, Date endDate,
                        List<Consumer> listConsumer, List<Product> listProduct) throws SQLException {
        ResultSet resultSet =  daoOperations.findAllBuy();
        List<Buy> listAllBuy = new ArrayList<>();
        while (resultSet.next())
            listAllBuy.add(new Buy(resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getLong(3),
                    resultSet.getDate(4)));
        resultSet =  daoOperations.findAllProduct();
        listConsumer = new ArrayList<>();
        while (resultSet.next())
            listConsumer.add(new Consumer(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3)));
        listProduct = new ArrayList<>();
        while (resultSet.next())
            listProduct.add(new Product(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getInt(3)));
// Создание списка покупок попадающих в период из двух дат, включительно, без выходных
        List<Buy> listAllBuyForPeriodWithoutWeekend = new ArrayList<>();
        for (Buy buy : listAllBuy){
            Date dateOfBuy = buy.getDate();
            if((dateOfBuy.after(startDate) || dateOfBuy.equals(startDate))
               && (dateOfBuy.before(endDate) || dateOfBuy.equals(endDate))){
                  Calendar c = Calendar.getInstance();
                  c.setTime(dateOfBuy);
                  int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                  if((dayOfWeek != 1) && (dayOfWeek != 7)){
                      listAllBuyForPeriodWithoutWeekend.add(buy);
                  }
            }
        }
// Создание множества: ключ - идентификатор покупателя, значение - множество,
// где ключ идентификатор товара, значение - количество покупок данного товара
        Map<Long, Map<Long,Long>> mapConsumerIdAndProductId = new HashMap<>();
        Map<Long,Long> mapProductIdAndCount = null;
        for (Buy buy: listAllBuyForPeriodWithoutWeekend) {
            if(mapConsumerIdAndProductId.containsKey(buy.getConsumerId())){
                if(mapConsumerIdAndProductId.get(buy.getConsumerId()).containsKey(buy.getProductId())) {
                    Long count =
                            mapConsumerIdAndProductId.get(buy.getConsumerId()).get(buy.getProductId()) + 1;
                    mapConsumerIdAndProductId.get(buy.getConsumerId()).put(buy.getProductId(),count);
                } else {
                    mapConsumerIdAndProductId.get(buy.getConsumerId()).put(buy.getProductId(),1L);
                }
            } else {
                mapProductIdAndCount = new HashMap<>();
                mapProductIdAndCount.put(buy.getProductId(),1L);
                mapConsumerIdAndProductId.put(buy.getConsumerId(),mapProductIdAndCount);
            }
        }
// Создание множества: ключ - идентификатор покупателя, значение - множество,
// где ключ идентификатор товара, значение - стоимость покупок данного товара
        Map<Long,Map<Long,Long>> mapConsumerIdAndProductExpenses = new HashMap<>();
        Map<Long,Long> mapProductIdAndExpenses = null;
        for (Map.Entry<Long,Map<Long,Long>> item : mapConsumerIdAndProductId.entrySet()) {
            mapProductIdAndExpenses = new HashMap<>();
            for (Product product: listProduct)
                if (item.getValue().containsKey(product.getId())) {
                    long count = item.getValue().get(product.getId());
                    long expenses = product.getPrice() * count;
                    mapProductIdAndExpenses.put(product.getId(), expenses);
                    break;
                }
            mapConsumerIdAndProductExpenses.put(item.getKey(),mapProductIdAndExpenses);
        }
       return mapConsumerIdAndProductExpenses;
    }
}
