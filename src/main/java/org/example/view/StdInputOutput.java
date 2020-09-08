package org.example.view;

import org.example.dao.DaoShop;
import org.example.dao.JDBCPostgreSQL;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

public class StdInputOutput {

    public static String readFile(String fileName) throws Exception {
        StringBuilder result = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = input.readLine()) != null)
                result.append(line.split("/")[0]);
        } catch (IOException e) {
//            System.out.println("Ошибка чтения файла " + fileName);
//            e.printStackTrace();
//            return null;
            throw new Exception("Ошибка чтения файла " + fileName);
        }
        return result.toString();
    }

    public static void writeFile(String fileName, String result) {
        try (FileWriter fw = new FileWriter(fileName)){
            fw.write(result);
        } catch (IOException e) {
            System.out.println("Ошибка записи файла " + fileName);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if(args.length != 3){
            System.out.println("Нужно указать 3 параметра: тип оперции, входной и выходной файл.");
            System.out.println("Пример аргментов: search input.json output.json");
            return;
        }
        String operation = args[0].toLowerCase();
        String inputFileName = args[1];
        String outputFileName = args[2];
        if (!(new File(inputFileName)).exists()) {
            String result =
                    "{\"type\":\"error\",\"message\":\"Файла с именем " + inputFileName + " не существует.\"}";
            writeFile(outputFileName, result);
            return;
        }
        String contentOfInputFile = null;
        try {
            contentOfInputFile = readFile(inputFileName);
        } catch (Exception e) {
            String result =
                    "{\"type\":\"error\",\"message\":\"" + e.getMessage() + "\"}";
            writeFile(outputFileName, result);
            return;
        }
        Connection connection = null;
        try {
            connection = new JDBCPostgreSQL().connection();
        } catch (Exception e) {
            String result =
                    "{\"type\":\"error\",\"message\":\"" + e.getMessage() + "\"}";
            writeFile(outputFileName, result);
            return;
        }
        TypeOfOperation typeOfOperation = new TypeOfOperation(new DaoShop(connection));
        if (!typeOfOperation.getOperation().containsKey(operation)){
            System.out.println("Операции " + operation + " нет. Укажите одну из:");
            for (String key : typeOfOperation.getOperation().keySet())
                System.out.println(key);
            return;
        }
        try {
            String result = typeOfOperation.getOperation().get(operation).result(contentOfInputFile);
            writeFile(outputFileName, result);
        } catch (Exception e) {
            String result =
                    "{\"type\":\"error\",\"message\":\"" + e.getMessage() + "\"}";
            writeFile(outputFileName, result);
        }
    }
}
