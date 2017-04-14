package com.abecedarian.stock;

import com.abecedarian.stock.utils.DBConnect;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.abecedarian.stock.utils.FileUtils.getFileDir;

/**
 * Created by tianle.li on 2017/3/9.
 */
public class Detail {

    private Connection conn = null;
    private Statement stat = null;

    private Detail() {

    }

    private Detail withConnect() {
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

    public static Detail DEFAULT = new Detail().withConnect();

    public static void main(String[] args) throws IOException {
        DEFAULT.handleDetailInfo();
    }

    public void handleDetailInfo() throws IOException {
        String filePath = getFileDir() + File.separator + "600886.csv";

//        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(new FileReader(filePath));
        Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader(Headers.class).parse(new FileReader(filePath));
        for (CSVRecord record : records) {
            String date = record.get(Headers.date);
            String open = record.get(Headers.open);
            String high = record.get(Headers.high);
            String close = record.get(Headers.close);
            String low = record.get(Headers.low);
            String volume = record.get(Headers.volume);
            String price_change = record.get(Headers.price_change);
            String p_change = record.get(Headers.p_change);
            String ma5 = record.get(Headers.ma5);
            String ma10 = record.get(Headers.ma10);
            String ma20 = record.get(Headers.ma20);
            String v_ma5 = record.get(Headers.v_ma5);
            String v_ma10 = record.get(Headers.v_ma10);
            String v_ma20 = record.get(Headers.v_ma20);
            String turnover = record.get(Headers.turnover);

            System.out.println(turnover);

        }
    }

    public enum Headers {
        date,//日期
        open,//开盘价
        high,//最高价
        close,//收盘价
        low,//最低价
        volume,//成交量
        price_change,//价格变动
        p_change,//涨跌幅
        ma5,//5日均价
        ma10,//10日均价
        ma20,//20日均价
        v_ma5,//5日均量
        v_ma10,//10日均量
        v_ma20,//20日均量
        turnover,//换手率[注：指数无此项]
    }

}
