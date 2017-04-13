package com.abecedarian.stock.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by tianle.li on 2017/3/22.
 */
public class DBConnect {

    private static Connection conn;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (File.separator.equals("/")) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/finance?useSSL=false&serverTimezone=UTC&charset=utf8", "abecedarian", "abecedarian.123");

        } else {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/finance?useSSL=false&serverTimezone=UTC&charset=utf8", "abecedarian", "115422");

        }
        return conn;

    }

    public static void close() throws SQLException {
        if (!conn.isClosed()) {
            conn.close();
        }
    }


}
