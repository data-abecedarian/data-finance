package com.abecedarian.stock.search;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by tianle.li on 2017/7/6.
 */
public class PoolSearch {

    public static void main(String[] args) throws IOException {

        List<String> stocks = traverseFolder1("D:\\tushare\\result\\stock");

        for (String stock : stocks) {
            handleStock(stock);
        }
        System.out.println("dd");
    }

    private static void handleStock(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((new FileInputStream(filePath)), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] items = line.split(",");
                if (items.length < 10 || items[0].equals("date")) {
                    continue;
                }
                String date=items[0];
                String open=items[1];
                String high=items[2];
                String close=items[3];
                String low=items[4];
                String volume=items[5];
                String price_change=items[6];
                String p_change=items[7];
                String ma5=items[8];
                String ma10=items[9];
                String ma20=items[10];
                String v_ma5=items[11];
                String v_ma10=items[12];
                String v_ma20=items[13];
                String turnover=items[14];
                System.out.println(line);

                System.out.println("dd");
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
