package org.example.service;

import java.sql.SQLException;

public interface RequestService {
    String result(String request) throws SQLException;
}
