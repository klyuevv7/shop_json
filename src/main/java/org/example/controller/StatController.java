package org.example.controller;

import org.example.model.Consumer;

import java.util.Date;
import java.util.List;

public class StatController implements StatControllerOperations {
    DaoOperations daoOperations;

    public StatController(DaoOperations daoOperations) {
        this.daoOperations = daoOperations;
    }

    @Override
    public List<Consumer> statConsumerByPeriod(Date startDate, Date endDate) {
        return null;
    }
}
