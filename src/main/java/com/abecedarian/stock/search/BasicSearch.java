package com.abecedarian.stock.search;

import com.abecedarian.stock.utils.DBConnect;
import com.google.gson.*;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tianle.li on 2017/7/5.
 */
public class BasicSearch {


    public static void main(String[] args) throws IOException {
//        handleBaicStock("D:\\tushare\\stock_basics.json");
        JsonArray arrays = handleBaicStock2("D:\\tushare\\stock_basics.csv");

//        writeToFile(arrays, "D:\\tushare\\result\\stock_simple_basics.json");
        writeToDb(arrays, "stock_simple_basics");

        System.out.println("dd");
    }

    private static void writeToDb(JsonArray arrays, String table) {
        try {
            Connection conn = DBConnect.getConnection();
            Statement stat = conn.createStatement();
            for (JsonElement array : arrays) {
                JsonObject stockObj = array.getAsJsonObject();
                String code = stockObj.getAsJsonPrimitive("code").getAsString();
                String name = stockObj.getAsJsonPrimitive("name").getAsString();
                String industry = stockObj.getAsJsonPrimitive("industry").getAsString();
                String area = stockObj.getAsJsonPrimitive("area").getAsString();

                String insertSql = "insert into  stock_simple_basics(code,name,industry,area) values ('" + code + "','" + name + "','" + industry + "','" + area + "')";
                try {
                    System.out.println(insertSql);
                    stat.executeUpdate(insertSql);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(JsonArray arrays, String filePath) throws IOException {

        File file = new File(filePath);
        if (!file.exists()) {
            file.createNewFile();
            file.setExecutable(true, false);
            file.setReadable(true, false);
            file.setWritable(true, false);
        }

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        for (JsonElement array : arrays) {
            bw.write(array.toString());
            bw.newLine();
        }
        bw.close();

    }


    private static void handleBaicStock(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((new FileInputStream(filePath)), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                JsonArray arrays = new JsonParser().parse(line).getAsJsonArray();

                for (JsonElement array : arrays) {
                    String name = array.getAsJsonObject().getAsJsonPrimitive("name").getAsString();
                    String industry = array.getAsJsonObject().getAsJsonPrimitive("industry").getAsString();
                    String area = array.getAsJsonObject().getAsJsonPrimitive("area").getAsString();
                    System.out.println(array.toString());
                }
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JsonArray handleBaicStock2(String filePath) {

        JsonArray basicArrays = new JsonArray();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((new FileInputStream(filePath)), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] items = line.split(",");
                if (items.length < 20) {
                    continue;
                }
                String code = items[0];
                String name = items[1];
                String industry = items[2];
                String area = items[3];

                if ("code".equals(code)) {
                    continue;
                }
                JsonObject basicObj = new JsonObject();
                basicObj.add("code", new JsonPrimitive(code));
                basicObj.add("name", new JsonPrimitive(name));
                basicObj.add("industry", new JsonPrimitive(industry));
                basicObj.add("area", new JsonPrimitive(area));

                basicArrays.add(basicObj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return basicArrays;
    }
}
