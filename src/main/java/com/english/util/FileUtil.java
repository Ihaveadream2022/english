package com.english.util;

import java.io.*;
import java.util.Base64;

public class FileUtil {

    // Write a file to output stream
    public static void writeFileToOutputStream(String filePath, OutputStream outputStream) {
        FileInputStream fileInputStream = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int length = 0;
            while ((length = fileInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Write a base64 String to output stream
    public static void writeBase64ToOutputStream(String base64String, OutputStream outputStream) {
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            if (base64String == null) {
                throw new IllegalArgumentException("base64String is null");
            }
            byte[] dataBytes = Base64.getDecoder().decode(base64String);
            byteArrayInputStream = new ByteArrayInputStream(dataBytes);
            byte[] bytes = new byte[1024];
            int readPos = 0;
            while ((readPos = byteArrayInputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, readPos);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Write a string to a file
    public static void writeStringToFile(String str, File file) {
        if (str == null) {
            throw new IllegalArgumentException("str is null");
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write(str);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            flag = file.delete();
        }
        return flag;
    }
}
