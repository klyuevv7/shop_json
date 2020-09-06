package org.example.controller;

import org.example.model.Consumer;

import java.util.Date;
import java.util.List;

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
    public List<Consumer> statConsumerByPeriod(Date startDate, Date endDate) {
        return null;
    }
}
