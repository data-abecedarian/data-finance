package com.abecedarian.stock;

import com.abecedarian.stock.utils.DBConnect;
import com.abecedarian.stock.utils.DBUtils;
import com.abecedarian.stock.utils.FileUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.abecedarian.stock.utils.FileUtils.getFileDir;


/**
 * Created by tianle.li on 2017/3/9.
 */
public class Basic {

    private Connection conn = null;
    private Statement stat = null;

    private Basic() {

    }

    private Basic withConnect() {
        try {
            conn = DBConnect.getConnection();
            stat = conn.createStatement();
            if (conn.isClosed()) {
                System.err.println("Failed connecting to the Database!");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public static Basic DEFAULT = new Basic().withConnect();

    public static void main(String[] args) {
        DEFAULT.handleBasicInfo();

        DEFAULT.close();
    }

    private void close() {
        try {
            conn.close();
            DBConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public enum Headers {
        code,//代码
        name,//名称
        industry,//所属行业
        area,//地区
        pe,//市盈率
        outstanding,//流通股本(亿)
        totals,//总股本(亿)
        totalAssets,//总资产(万)
        liquidAssets,//流动资产
        fixedAssets,//固定资产
        reserved,//公积金
        reservedPerShare,//每股公积金
        esp,//每股收益
        bvps,//每股净资
        pb,//市净率
        timeToMarket,//上市日期
        undp,//未分利润
        perundp,// 每股未分配
        rev,//收入同比(%)
        profit,//利润同比(%)
        gpr,//毛利率(%)
        npr,//净利润率(%)
        holders,//股东人数
    }

    private void handleBasicInfo() {
        String filePath = getFileDir() + File.separator + "stock_basics.csv";
        try {
            String createFilePath = getFileDir() + File.separator + "stock_basics.txt";
            File file = FileUtils.createFile(createFilePath);
            if (file == null) {
                System.err.println("create file error!!");
                System.exit(1);
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(Headers.class).parse(new FileReader(filePath));
            for (CSVRecord record : records) {
                String code = record.get(Headers.code);
                if ("code".equals(code.trim())) {
                    continue;
                }
                for (String recordName : record) {
                    bw.write(recordName + "\t");
                }
                bw.newLine();

//                String code = record.get(Headers.code);
                String name = record.get(Headers.name);
                String industry = record.get(Headers.industry);
                String area = record.get(Headers.area);
                String pe = record.get(Headers.pe);
                String outstanding = record.get(Headers.outstanding);
                String totals = record.get(Headers.totals);
                String totalAssets = record.get(Headers.totalAssets);
                String liquidAssets = record.get(Headers.liquidAssets);
                String fixedAssets = record.get(Headers.fixedAssets);
                String reserved = record.get(Headers.reserved);
                String reservedPerShare = record.get(Headers.reservedPerShare);
                String esp = record.get(Headers.esp);
                String bvps = record.get(Headers.bvps);
                String pb = record.get(Headers.pb);
                String timeToMarket = record.get(Headers.timeToMarket);
                String undp = record.get(Headers.undp);
                String perundp = record.get(Headers.perundp);
                String rev = record.get(Headers.rev);
                String profit = record.get(Headers.profit);
                String gpr = record.get(Headers.gpr);
                String npr = record.get(Headers.npr);
                String holders = record.get(Headers.holders);

            }
            bw.close();
            DBUtils.loadFileToDB(stat,createFilePath,"stock_basics");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
