package com.ml.thousandsdraw.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 高岩 on 2017/11/21.
 */

public class mfileUtil {

    public static boolean deleteFile(String path){
        File file = new File(path);
        if (checkIsFile(file)){
            return false;
        }
        file.delete();
        return true;
    }
    public static boolean checkIsFile(File file){
        if (file == null || !file.exists() || file.isDirectory()){
            return false;
        }
        return true;
    }
    public static void copyDirectory(String oldPaTH,String newPAth){
        File oldF = new File(oldPaTH);
        String[] list = oldF.list();
        File newF;
        for(String temp:list){

            if(oldPaTH.endsWith(File.separator)){
                newF = new File(oldPaTH + temp);
            }else {
                newF = new File(oldPaTH + File.separator + temp);
            }
            if (newF.isFile()){
                copyFile(newF.toString(),newPAth + "/" + newF.getName());
            }else {
                copyDirectory(newF + "/" + temp,newPAth + "/" + temp);
            }
        }
    }
    public static void copyFile(String oldPath,String newPath){
        try {
            FileInputStream inputStream = new FileInputStream(oldPath);
            FileOutputStream outputStream = new FileOutputStream(newPath);
            byte[] buff = new byte[1024];
            int byteRead;
            while ((byteRead = inputStream.read(buff)) > 0){
                outputStream.write(buff,0,byteRead);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            Log.e("tag",e+"");
        }
    }
}
