package com.abecedarian.stock.utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by tianle.li on 2017/3/23.
 */
public class FileUtils {


    public static File createFile(String filePath) throws IOException {
        File file = new File(filePath);

        boolean status = false;
        if (!file.exists()) {
            status = file.createNewFile() && file.setExecutable(true, false) && file.setReadable(true, false) && file.setWritable(true, false);
        }
        return status ? file : null;
    }


    public static String getFileDir() {
        if (File.separator.equals("/")) {
            return "/home/abecedarian/workspace/tushare/resource";
        } else {
            return "D:\\dev";
        }
    }

    public static void main(String[] args) {
        System.out.println("dd");
    }

}
