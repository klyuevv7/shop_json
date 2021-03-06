package org.example.controller;

import org.example.model.Buy;
import org.example.model.Consumer;
import org.example.model.Product;
import java.sql.ResultSet;
import java.util.*;

public class SearchController implements ServiceControllerOperations {
    DaoOperations daoOperations;

    public SearchController(DaoOperations daoOperations) {
        this.daoOperations = daoOperations;
    }

    /**
     * Поиск покупателей с этой фамилией
      * @param lastName - Фамилия
     * @return Возвращает список покупателей (объектов Consumer) с этой фамилией
     */
    @Override
    public List<Consumer> findConsumerBySurname(String lastName) throws Exception {
        ResultSet resultSet =  daoOperations.findConsumerBySurname(lastName);
        List<Consumer> listConsumer = new ArrayList<>();
        while (resultSet.next())
            listConsumer.add(new Consumer(resultSet.getLong(1),
                                          resultSet.getString(2),
                                          resultSet.getString(3)));
        return listConsumer;
    }

    /**
     * Поиск покупателей, купивших этот товар не менее, чем указанное число раз
     * @param nameProduct - Название товара
     * @param countProductBuy -  Число раз
     * @return Возвращает список покупателей (объектов Consumer),
     *         купивших этот товар не менее, чем указанное число раз
     */
    @Override
    public List<Consumer> findConsumerByCountProductBuy(String nameProduct, int countProductBuy)
                                                                            throws Exception {
        ResultSet resultSet =  daoOperations.findBuyByProduct(nameProduct);
        List<Buy> listBuyByProduct = new ArrayList<>();
        while (resultSet.next())
            listBuyByProduct.add(new Buy(resultSet.getLong(1),
                                         resultSet.getLong(2),
                                         resultSet.getLong(3),
                                         resultSet.getDate(4)));
// Создание множества: ключ - идентификатор покупателя, значение - количество покупок товара
        final Map<Long, Long> mapOfConsumerIdAndCountBuy = new HashMap<>();
        listBuyByProduct.forEach((a) -> mapOfConsumerIdAndCountBuy.put(a.getConsumerId(),
                (mapOfConsumerIdAndCountBuy.containsKey(a.getConsumerId())) ?
                        mapOfConsumerIdAndCountBuy.get(a.getConsumerId()) + 1 : 1L));
// Формируется список покупателей, купивших этот товар не менее, чем указанное число раз
        List<Consumer> resultListConsumer = new ArrayList<>();
        for(Map.Entry<Long, Long> item : mapOfConsumerIdAndCountBuy.entrySet()){
            if(item.getValue() >= countProductBuy){
                resultSet =  daoOperations.findConsumersById(item.getKey());
                if (resultSet.next())
                    resultListConsumer.add(new Consumer(resultSet.getLong(1),
                                                        resultSet.getString(2),
                                                        resultSet.getString(3)));
            }
        }
        return resultListConsumer;
    }

    /**
     * Поиск покупателей, у которых общая стоимость всех покупок за всё время попадает в интервал
     * @param minExpensesAllBuy - Минимальная стоимость всех покупок
     * @param maxExpensesAllBuy - Максимальная стоимость всех покупок
     * @return Возвращает список покупателей (объектов Consumer),
     *         у которых общая стоимость всех покупок за всё время попадает в интервал
     */
    @Override
    public List<Consumer> findConsumerByIntervalExpensesAllBuy(int minExpensesAllBuy, int maxExpensesAllBuy)
                                                                                        throws Exception {
        ResultSet resultSet =  daoOperations.findAllBuy();
        List<Buy> listAllBuy = new ArrayList<>();
        while (resultSet.next())
            listAllBuy.add(new Buy(resultSet.getLong(1),
                                   resultSet.getLong(2),
                                   resultSet.getLong(3),
                                   resultSet.getDate(4)));

        resultSet =  daoOperations.findAllProduct();
        List<Product> listAllProduct = new ArrayList<>();
        while (resultSet.next())
            listAllProduct.add(new Product(resultSet.getLong(1),
                                           resultSet.getString(2),
                                           resultSet.getInt(3)));
// Создание множества: ключ - идентификатор покупателя, значение - множество,
// где ключ идентификатор товара, значение - количество покупок данного товара
        Map<Long,Map<Long,Long>> mapConsumerIdAndProductId = new HashMap<>();
        Map<Long,Long> mapProductIdAndCount = null;
        for (Buy buy: listAllBuy) {
                if(mapConsumerIdAndProductId.containsKey(buy.getConsumerId())){
                    mapConsumerIdAndProductId.get(buy.getConsumerId()).put(
                            buy.getProductId(),
                            mapConsumerIdAndProductId.get(buy.getConsumerId()).containsKey(buy.getProductId()) ?
                            (mapConsumerIdAndProductId.get(buy.getConsumerId()).get(buy.getProductId()) + 1) :
                            1L);
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
            for (Product product: listAllProduct)
                if (item.getValue().containsKey(product.getId())) {
                    long count = item.getValue().get(product.getId());
                    long expenses = product.getPrice() * count;
                    mapProductIdAndExpenses.put(product.getId(), expenses);
                    break;
                }
            mapConsumerIdAndProductExpenses.put(item.getKey(),mapProductIdAndExpenses);
        }
// Создание множества: ключ - идентификатор покупателя, значение - все расходы
        Map<Long,Long> mapConsumerIdAndAllExpenses = new HashMap<>();
        for (Map.Entry<Long,Map<Long,Long>> item : mapConsumerIdAndProductExpenses.entrySet()) {
            long allExpenses = 0;
            for (Long productId : item.getValue().keySet())
                allExpenses += item.getValue().get(productId);
            mapConsumerIdAndAllExpenses.put(item.getKey(),allExpenses);
        }
// Создание списка покупателей, у которых общая стоимость всех покупок за всё время попадает в интервал
        List<Consumer> resultListConsumer = new ArrayList<>();
        for (Long consumerId : mapConsumerIdAndAllExpenses.keySet()) {
            long allExpenses = mapConsumerIdAndAllExpenses.get(consumerId);
            if(allExpenses > minExpensesAllBuy && allExpenses < maxExpensesAllBuy) {
                resultSet =  daoOperations.findConsumersById(consumerId);
                if (resultSet.next())
                    resultListConsumer.add(new Consumer(resultSet.getLong(1),
                                                        resultSet.getString(2),
                                                        resultSet.getString(3)));
            }
        }
        return resultListConsumer;
    }
    /**
     *  Поиск покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей.
      * @param countBadConsumer - Число пассивных покупателей
     * @return Возвращает список покупателей (объектов Consumer),
     *         купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей.
     */
    @Override
    public List<Consumer> findBadConsumerByCountProductBuy(int countBadConsumer) throws Exception {
        ResultSet resultSet =  daoOperations.findAllBuy();
        List<Buy> listAllBuy = new ArrayList<>();
        while (resultSet.next())
            listAllBuy.add(new Buy(resultSet.getLong(1),
                    resultSet.getLong(2),
                    resultSet.getLong(3),
                    resultSet.getDate(4)));
// Создание множества: ключ - идентификатор покупателя, значение - количество покупок товара
        final Map<Long, Long> mapOfConsumerIdAndCountBuy = new HashMap<>();
        listAllBuy.forEach((a) -> mapOfConsumerIdAndCountBuy.put(a.getConsumerId(),
                (mapOfConsumerIdAndCountBuy.containsKey(a.getConsumerId())) ?
                        mapOfConsumerIdAndCountBuy.get(a.getConsumerId()) + 1 : 1L));
// Создание списка количества покупок, его сортировка,
// взять первые не более, чем указанное число покупателей, поместить в новый список
        List<Long> listCountBuy = new ArrayList<>();
        for (Long consumerId : mapOfConsumerIdAndCountBuy.keySet())
            listCountBuy.add(mapOfConsumerIdAndCountBuy.get(consumerId));
        Collections.sort(listCountBuy);
        List<Long> listCountBuyBadConsumer = new ArrayList<>();
        for (int i = 0; i < countBadConsumer && i < listCountBuy.size(); i++){
            listCountBuyBadConsumer.add(listCountBuy.get(i));
        }
// Создание списка идентификаторов плохих покупателей
        List<Long> listIdBadConsumer = new ArrayList<>();
        for (Long elementListCountBuyBadConsumer : listCountBuyBadConsumer){
            for (Long consumerId : mapOfConsumerIdAndCountBuy.keySet())
                if(elementListCountBuyBadConsumer.equals(mapOfConsumerIdAndCountBuy.get(consumerId))
                   && !listIdBadConsumer.contains(consumerId)){
                    listIdBadConsumer.add(consumerId); break;
                }
        }
// Создание списка покупателей
        List<Consumer> resultListConsumer = new ArrayList<>();
        for (Long elementListIdBadConsumer : listIdBadConsumer) {
            resultSet =  daoOperations.findConsumersById(elementListIdBadConsumer);
            if (resultSet.next())
                resultListConsumer.add(new Consumer(resultSet.getLong(1),
                                                    resultSet.getString(2),
                                                    resultSet.getString(3)));
        }
        return resultListConsumer;
    }
}
