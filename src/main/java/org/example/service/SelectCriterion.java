package org.example.service;

import org.example.controller.ServiceControllerOperations;
import org.example.service.searchofcriterians.SearchOfCriterion;
import org.example.service.searchofcriterians.SearchOfCriterionLastName;
import org.example.service.searchofcriterians.SearchOfCriterionProductName;

import java.util.HashMap;
import java.util.Map;

/**
 * Набор возможных критериев поиска в связке с исполняемым классом
 * реализующим интерфейс SearchOfCriterion
 */
public class SelectCriterion {
    private Map<String, SearchOfCriterion> criterion
                    = new HashMap<String, SearchOfCriterion>();

    public SelectCriterion(ServiceControllerOperations searchController) {
        criterion.put("lastName", new SearchOfCriterionLastName(searchController));
        criterion.put("productName", new SearchOfCriterionProductName(searchController));

    }

    public Map<String, SearchOfCriterion> getCriterion() {
        return criterion;
    }
}
