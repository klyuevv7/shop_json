package org.example.service.searchofcriterians;

import org.json.JSONObject;
import java.sql.SQLException;

public interface SearchOfCriterion {
    String result(JSONObject jsonObject) throws SQLException;
}
