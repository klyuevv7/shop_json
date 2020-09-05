package org.example.view;

import org.example.dao.DaoShop;
import org.example.dao.JDBCPostgreSQL;

import java.io.*;
import java.sql.SQLException;

public class StdInputOutput {

    public static String readFile(String fileName) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new FileReader(fileName))) {
            String line = "";
            while ((line = input.readLine()) != null)
                result.append(line.split("/")[0]);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла " + fileName);
            e.printStackTrace();
            return null;
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

    public static void main(String[] args) throws IOException, SQLException {
        if(args.length != 3){
            System.out.println("Нужно указать 3 параметра: тип оперции, входной и выходной файл.");
            System.out.println("Пример аргментов: search input.json output.json");
            return;
        }
        String operation = args[0].toLowerCase();
        TypeOfOperation typeOfOperation = new TypeOfOperation(new DaoShop(new JDBCPostgreSQL().connection()));
        if (!typeOfOperation.getOperation().containsKey(operation)){
            System.out.println("Операции " + operation + " нет. Укажите одну из:");
            for (String key : typeOfOperation.getOperation().keySet())
                System.out.println(key);
            return;
        }
        String inputFileName = args[1];
        String outputFileName = args[2];
        if (!(new File(inputFileName)).exists()) {
            System.out.println("Файла с именем " + inputFileName + " не существует.");
            return;
        }
        String contentOfInputFile = readFile(inputFileName);

        String result = typeOfOperation.getOperation().get(operation).result(contentOfInputFile);

        writeFile(outputFileName, result);
    }
}
