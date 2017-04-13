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
        String filePath = getFileDir() + File.separator + "000625.csv";

        Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(new FileReader(filePath));
        for (CSVRecord record : records) {
            String one = record.get(0);
            String two = record.get(1);
            System.out.println(one + "\t" + two);
        }
    }

}
