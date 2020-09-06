package org.example.controller;

import org.example.model.Buy;
import org.example.model.Consumer;
import org.example.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
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
     * @throws SQLException
     */
    @Override
    public List<Consumer> findConsumerBySurname(String lastName) throws SQLException {
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
     * @throws SQLException
     */
    @Override
    public List<Consumer> findConsumerByCountProductBuy(String nameProduct, int countProductBuy)
                                             throws SQLException {
        ResultSet resultSet =  daoOperations.findBuyByProduct(nameProduct);
        List<Buy> listBuyByProduct = new ArrayList<>();
        while (resultSet.next())
            listBuyByProduct.add(new Buy(resultSet.getLong(1),
                                         resultSet.getLong(2),
                                         resultSet.getLong(3),
                                         resultSet.getDate(4)));
// Множество: идентификатор покупателя и сумма покупок
        Map <Long, Long> mapOfSumBuyAndConsumerId = new HashMap<>();
        for (Buy buy : listBuyByProduct) {
            Long consumerId = buy.getConsumerId();
            if (mapOfSumBuyAndConsumerId.containsKey(consumerId)) {
                Long countProduct = mapOfSumBuyAndConsumerId.get(consumerId) + 1;
                mapOfSumBuyAndConsumerId.put(consumerId, countProduct);
            } else {
                mapOfSumBuyAndConsumerId.put(consumerId, 1L);
            }
        }
// Формируется список покупателей, купивших этот товар не менее, чем указанное число раз
        List<Consumer> resultListConsumer = new ArrayList<>();
        for(Map.Entry<Long, Long> item : mapOfSumBuyAndConsumerId.entrySet()){
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
                                                                throws SQLException {
        ResultSet resultSet =  daoOperations.findAllBuy();
        List<Buy> listAllBuy = new ArrayList<>();
        while (resultSet.next())
            listAllBuy.add(new Buy(resultSet.getLong(1),
                                   resultSet.getLong(2),
                                   resultSet.getLong(3),
                                   resultSet.getDate(4)));
//----------
        

        resultSet =  daoOperations.findAllProduct();
        List<Product> listAllProduct = new ArrayList<>();
        while (resultSet.next())
            listAllProduct.add(new Product(resultSet.getLong(1),
                                           resultSet.getString(2),
                                           resultSet.getInt(3)));

        return null
    }

    /**
     *  Поиск покупателей, купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей.
      * @param countBadConsumer - Число пассивных покупателей
     * @return Возвращает список покупателей (объектов Consumer),
     *         купивших меньше всего товаров. Возвращается не более, чем указанное число покупателей.
     */

    @Override
    public List<Consumer> findBadConsumerByCountProductBuy(int countBadConsumer) {
        return null;
    }
}
