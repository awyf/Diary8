package com.example.diary.utils;

import android.content.Context;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {


    public static boolean saveInfoByContext(Context context, String password) {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("info.txt", Context.MODE_PRIVATE);
            byte[] psw_bytes = password.getBytes();
            byte[] encode = Base64.encode(psw_bytes, Base64.DEFAULT);
            fileOutputStream.write(encode);
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static String readInfoByContext(Context context) {
        try {
            FileInputStream fis = context.openFileInput("info.txt");
            ByteArrayOutputStream psw_temp = new ByteArrayOutputStream();
            byte[] psw_bytes = new byte[1024];
            int len;
            while ((len = fis.read(psw_bytes)) != -1) {
                psw_temp.write(psw_bytes, 0 ,len);
            }
            byte[] decode = Base64.decode(psw_bytes, Base64.DEFAULT);
            fis.close();
            psw_temp.close();
            return new String(decode);
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
