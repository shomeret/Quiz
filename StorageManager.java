package com.example.quizapp;

import android.content.Context;
import android.icu.util.Output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StorageManager {
    String fileName = "results.txt";
    int attempt = 0;
    int total = 0;
    public void saveNewPrivateExternal(Context context, String str) {
        OutputStream os = null;
        try {
            File folder = context.getExternalFilesDir("resultExternalData");
            File myfile = new File(folder, fileName);
            OutputStream outStream = new FileOutputStream(myfile);
            outStream.write(str.getBytes(StandardCharsets.UTF_8));

        } catch(IOException ex){
            ex.printStackTrace();
        }



    }


    public String getStringFromPrivateExternal(Context context) {
        File folder = context.getExternalFilesDir("resultExternalData");
        File myfile = new File(folder, fileName);
        String text = getdata(myfile);
        if (text != null) {
            return text;
        } else {
            return "no data";
        }

    }



    private String getdata(File myfile) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(myfile);
            int i = -1;
            StringBuffer buffer = new StringBuffer();
            while ((i = fileInputStream.read()) != -1) {
                buffer.append((char) i);
            }
            return buffer.toString();
        }   catch (Exception ex) {
                ex.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return null;
    }


}


