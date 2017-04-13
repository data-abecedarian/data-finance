package com.abecedarian.stock.utils;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by tianle.li on 2017/3/22.
 */
public class DBUtils {

    private static final String Delimiter = "\t";

    public static void loadFileToDB(Statement stat, String filePath, String tableName) {
        String insertSql = "load data local infile'" + filePath.replace("\\", "\\\\") + "' into table " + tableName + " fields terminated by '" + Delimiter + "'";

        try {
            System.out.println(insertSql);
            stat.executeUpdate(insertSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
