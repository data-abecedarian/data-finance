package com.abecedarian.stock.search;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tianle.li on 2017/7/6.
 */
public class PoolSearch {

    public static void main(String[] args) throws IOException {

        File file = new File("D:\\tushare\\result\\all\\all_history.txt");
        if (!file.exists()) {
            file.createNewFile();
            file.setExecutable(true, false);
            file.setReadable(true, false);
            file.setWritable(true, false);
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);


        List<String> stocks = traverseFolder1("D:\\tushare\\result\\stock");

        for (String stock : stocks) {
            handleStock(stock, bw);
        }
        bw.close();
        System.out.println("dd");
    }

    private static void handleStock(String filePath, BufferedWriter bw) {
        try {


            BufferedReader br = new BufferedReader(new InputStreamReader((new FileInputStream(filePath)), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                try {
                    String[] items = line.split(",");
                    if (items.length < 10 || items[0].equals("date")) {
                        continue;
                    }
                    String code = filePath.split("\\\\")[4].substring(0, 6);
                    String date = items[0];
                    String open = items[1];
                    String high = items[2];
                    String close = items[3];
                    String low = items[4];
                    String volume = items[5];
                    String price_change = items[6];
                    String p_change = items[7];
                    String ma5 = items[8];
                    String ma10 = items[9];
                    String ma20 = items[10];
                    String v_ma5 = items[11];
                    String v_ma10 = items[12];
                    String v_ma20 = items[13];
                    String turnover = items[14];

                    JsonObject obj = new JsonObject();
                    obj.add("code", new JsonPrimitive(code));
                    obj.add("date", new JsonPrimitive(date));
                    obj.add("open", new JsonPrimitive(open));
                    obj.add("high", new JsonPrimitive(high));
                    obj.add("close", new JsonPrimitive(close));
                    obj.add("low", new JsonPrimitive(low));
                    obj.add("volume", new JsonPrimitive(volume));
                    obj.add("price_change", new JsonPrimitive(price_change));
                    obj.add("p_change", new JsonPrimitive(p_change));
                    obj.add("ma5", new JsonPrimitive(ma5));
                    obj.add("ma10", new JsonPrimitive(ma10));
                    obj.add("ma20", new JsonPrimitive(ma20));
                    obj.add("v_ma5", new JsonPrimitive(v_ma5));
                    obj.add("v_ma10", new JsonPrimitive(v_ma10));
                    obj.add("v_ma20", new JsonPrimitive(v_ma20));
                    obj.add("turnover", new JsonPrimitive(turnover));

                    bw.write(obj.toString());
                    bw.newLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> traverseFolder1(String path) {
        List<String> stock_lists = new ArrayList<>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹1:" + file2.getAbsolutePath());
                    list.add(file2);
                    fileNum++;
                } else {
                    stock_lists.add(file2.getAbsolutePath());
                    System.out.println("文件1:" + file2.getAbsolutePath());
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹2:" + file2.getAbsolutePath());
                        list.add(file2);
                        folderNum++;
                    } else {
                        System.out.println("文件2:" + file2.getAbsolutePath());
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);

        return stock_lists;

    }


}
