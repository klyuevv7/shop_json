package org.example.controller;

import org.example.model.Consumer;

import java.util.Date;
import java.util.List;

public interface StatControllerOperations {
    List<Consumer> statConsumerByPeriod(Date startDate, Date endDate);
}
