package com.abecedarian.stock.search;

import java.io.*;

/**
 * Created by tianle.li on 2017/7/5.
 */
public class BasicSearch {


    public static void main(String[] args) {
        handleBaicStock("D:\\tushare\\stock_basics.json");

    }

    private static void handleBaicStock(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader((new FileInputStream(filePath)), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
