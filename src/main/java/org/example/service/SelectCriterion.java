package org.example.service;

import org.example.controller.ServiceControllerOperations;
import org.example.service.searchofcriterians.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Набор возможных критериев поиска в связке с исполняемым классом
 * реализующим интерфейс SearchOfCriterion
 */
public class SelectCriterion {
    private final Map<String, SearchOfCriterion> criterion = new HashMap<>();

    public SelectCriterion(ServiceControllerOperations searchController) {
        criterion.put("lastName", new SearchOfCriterionLastName(searchController));
        criterion.put("productName", new SearchOfCriterionProductName(searchController));
        criterion.put("minExpenses", new SearchOfCriterionExpenses(searchController));
        criterion.put("badCustomers", new SearchOfCriterionBadCustomers(searchController));
    }

    public Map<String, SearchOfCriterion> getCriterion() {
        return criterion;
    }
}
