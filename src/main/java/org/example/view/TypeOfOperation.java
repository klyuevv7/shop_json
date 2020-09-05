package org.example.view;

import org.example.controller.DaoOperations;
import org.example.controller.SearchController;
import org.example.service.JsonSearchService;
import org.example.service.RequestService;
import java.util.HashMap;
import java.util.Map;

/**
 * Набор возможных операций в связке с исполняемым классом
 * реализующим интерфейс RequestService
 */
public class TypeOfOperation {
    private final Map<String, RequestService> operation;

    public TypeOfOperation(DaoOperations daoShop) {
        operation = new HashMap<String, RequestService>();
        operation.put("search",
                new JsonSearchService(new SearchController(daoShop)));
//        operation.put("stat", new JsonStatService(new StatController(daoShop)));
    }

    public Map<String, RequestService> getOperation() {
        return operation;
    }
}
