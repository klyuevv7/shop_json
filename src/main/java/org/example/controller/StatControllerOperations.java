package org.example.controller;

import org.example.model.Buy;
import org.example.model.Consumer;
import org.example.model.Product;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatControllerOperations {
    List<Consumer> findAllConsumers() throws Exception;
    List<Product> findAllProducts() throws Exception;
    List<Buy> findAllBuy() throws Exception;
    Map<Long, Map<Long,Long>> statConsumerByPeriod(Date startDate, Date endDate)
            throws Exception;
}
