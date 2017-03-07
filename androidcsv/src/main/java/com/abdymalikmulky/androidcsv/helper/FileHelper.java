package com.abdymalikmulky.androidcsv.helper;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 3/7/17.
 */

public class FileHelper {
    String dirName;
    String fileName;

    public FileHelper() {
        this.dirName = "AbdyCSV";
        this.fileName = "AbdyCSV";
    }

    public FileHelper(String dirName, String fileName) {
        this.dirName = dirName;
        this.fileName = fileName;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File storeFile(String content){
        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
        if (root.canWrite()){
            File dir    =   new File (root.getAbsolutePath() + "/"+getDirName());
            dir.mkdirs();
            file   =   new File(dir, getFileName()+".csv");
            FileOutputStream out   =   null;
            Log.d("CSV-FILE",dir.getAbsolutePath());
            try {
                out = new FileOutputStream(file);
                out.write(content.getBytes());
                out.close();
            } catch (FileNotFoundException e) {
                Log.e("CSV-ERROR",e.toString());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("CSV-ERROR",e.toString());
                e.printStackTrace();
            }
        }
        return file;
    }
}
