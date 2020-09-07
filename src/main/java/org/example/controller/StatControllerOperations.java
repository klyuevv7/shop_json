package org.example.controller;

import org.example.model.Consumer;
import org.example.model.Product;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StatControllerOperations {
    Map<Long, Map<Long,Long>> statConsumerByPeriod(Date startDate, Date endDate,
               List<Consumer> listConsumer, List<Product> listProduct) throws SQLException;
}
