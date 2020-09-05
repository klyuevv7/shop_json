package org.example.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dao.DaoShop;
import org.example.dao.JDBCPostgreSQL;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.util.List;

interface Criterion {
}

class InputJsonOfTypeSearch {
    @JsonProperty("criterias")
    JSONArray criterias;
    @Override
    public String toString(){
        return criterias.toString();
    }
}

class Criterion1 implements Criterion {
    @JsonProperty("lastName")
    String lastName = "Иванов";

}

class Criterion2 implements Criterion {
    String productName = "Минеральная вода";
    int minTimes = 5;
}
class Criterion3 implements Criterion {
    int minExpenses = 112;
    int maxExpenses = 4000;
}
class Criterion4 implements Criterion {
    int badCustomers = 3;
}

public class JsonInputOutput {
//    private final static String baseFile = "user.json";
//    public static void toJSON(User user) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(new File(baseFile), user);
//        System.out.println("json created!");
//    }
//
//    public static User toJavaObject() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        return mapper.readValue(new File(baseFile), User.class);
//    }

    static InputJsonOfTypeSearch readFileJsonToJavaObject(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(fileName), InputJsonOfTypeSearch.class);
    }
    static boolean writeJavaObjectToFileJson(Object object, String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File(fileName), object);
        return true;
    }

    public static void main(String[] args) throws IOException {
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
        String contentOfInputFile = readFileJsonToJavaObject(inputFileName).toString();
        System.out.println(contentOfInputFile);
//        Criterion1 criterion1 = new Criterion1();
//        if(writeJavaObjectToFileJson(criterion1,outputFileName)){
//            System.out.println(readFileJsonToJavaObject(outputFileName).toString());
//        }
//        if(contentOfInputFile == null){
//            System.out.println("Файла с именем " + inputFileName + " не был прочитан.");
//            return;
//        }
//
//        String result = typeOfOperation.getOperation().get(operation).result(contentOfInputFile);
//
//
//        if(!writeFile(outputFileName, result)){
//            System.out.println("Файла с именем " + inputFileName + " не был записан.");
//        }
//
    }
}
