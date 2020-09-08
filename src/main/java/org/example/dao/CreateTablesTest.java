package org.example.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Выполняет тест, создавая таблицу, вводя в нее значение,
 * отображая содержимое таблицы и, наконец, удаляя ее
 */
public class CreateTablesTest {
    private Connection connection;
    /**
     * @param connection -  соединение с базой данных
     */
    public CreateTablesTest(Connection connection){
        this.connection = connection;
    }

    public void createGreetings() throws SQLException {
        if (connection != null){
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE Greetings (Message CHAR(20))");
            statement.executeUpdate("INSERT INTO Greetings VALUES ('Hello, World!')");

            try (ResultSet result = statement.executeQuery("SELECT * FROM Greetings")){
                while (result.next()){
                    System.out.println(result.getString(1));
                }
                statement.executeUpdate("DROP TABLE Greetings");
            }

        }
    }
    public static void main(String[] args) throws Exception {
        new CreateTablesTest(new JDBCPostgreSQL().connection()).createGreetings();
    }
}
