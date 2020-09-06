package org.example.dao;

import java.sql.*;

/**
 * Вводит значения в таблицы базы данных
 */
public class FillTablesDbShop {
    private Connection connection;
    /**
     * @param connection -  соединение с базой данных
     */
    public FillTablesDbShop(Connection connection) {
        this.connection = connection;
    }

    public void fillExecute() throws SQLException {
        if (connection != null) {
            Statement statement = connection.createStatement();
            System.out.println("Таблица consumer:");
//            statement.executeUpdate("INSERT INTO public.consumer(name, surname) VALUES ('Антон', 'Иванов')");
//            statement.executeUpdate("INSERT INTO public.consumer(name, surname) VALUES ('Николай', 'Иванов')");
//            statement.executeUpdate("INSERT INTO public.consumer(name, surname) VALUES ('Валентин', 'Петров')");
//            statement.executeUpdate("INSERT INTO public.consumer(name, surname) VALUES ('Антон', 'Сидоров')");
//            statement.executeUpdate("UPDATE public.consumer SET name = 'Николай' where id = 5");
//            statement.executeUpdate("DELETE FROM public.consumer where name = 'Ivan'");

            try (ResultSet result = statement.executeQuery("SELECT * FROM public.consumer")){
                while (result.next()){
                    System.out.println(result.getString(1) + " " +
                                       result.getString(2) + " " +
                                       result.getString(3));
                }
            }
            System.out.println();
            System.out.println("Таблица product:");
//            statement.executeUpdate("INSERT INTO public.product(name, price) VALUES ('Минеральная вода', 50)");
//            statement.executeUpdate("INSERT INTO public.product(name, price) VALUES ('Хлеб', 40)");
//            statement.executeUpdate("INSERT INTO public.product(name, price) VALUES ('Сметана', 60)");
//            statement.executeUpdate("INSERT INTO public.product(name, price) VALUES ('Колбаса', 230)");
//            statement.executeUpdate("INSERT INTO public.product(name, price) VALUES ('Сыр', 180)");

            try (ResultSet result = statement.executeQuery("SELECT * FROM public.product")){
                while (result.next()){
                    System.out.println(result.getString(1) + " " +
                            result.getString(2) + " " +
                            result.getString(3));
                }
            }
            System.out.println();
            System.out.println("Таблица buy:");
//            for (int i = 0; i < 2; i++)
//            statement.executeUpdate("INSERT INTO public.buy(consumer, product, date) VALUES (6, 1, '2020-05-29')");
//            statement.executeUpdate("INSERT INTO public.buy(consumer, product, date) VALUES (5, 4, '2020-05-25')");
//            statement.executeUpdate("INSERT INTO public.buy(consumer, product, date) VALUES (7, 2, '2020-05-26')");
//            statement.executeUpdate("INSERT INTO public.product(name, price) VALUES ('Хлеб', 40)");
//            statement.executeUpdate("INSERT INTO public.product(name, price) VALUES ('Сметана', 60)");
//            statement.executeUpdate("INSERT INTO public.product(name, price) VALUES ('Колбаса', 230)");
//            statement.executeUpdate("INSERT INTO public.product(name, price) VALUES ('Сыр', 180)");
//            statement.executeUpdate("INSERT INTO public.buy(consumer, product, date) VALUES (6, 1, '2020-01-10')");
//            statement.executeUpdate("INSERT INTO public.buy(consumer, product, date) VALUES (5, 4, '2020-01-25')");
//            statement.executeUpdate("INSERT INTO public.buy(consumer, product, date) VALUES (7, 2, '2020-01-26')");

            try (ResultSet result = statement.executeQuery("SELECT * FROM public.buy")){
//                Date date[] = new Date[5]; int i = 0;
                while (result.next()){
//                    date[i] = result.getDate(4); i++;
                    System.out.println(result.getString(1) + " " +
                            result.getString(2) + " " +
                            result.getString(3) + " " +
                            result.getDate(4));
                }
//                System.out.println(date[1].getDate()-date[0].getDate());
            }


        }
    }
    public static void main(String[] args) throws SQLException {
        new FillTablesDbShop(new JDBCPostgreSQL().connection()).fillExecute();
    }

}
