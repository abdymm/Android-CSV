package com.abdymalikmulky.androidcsv;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.abdymalikmulky.androidcsv.CSVProperties.COMMA;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 3/6/17.
 */

public class CSVGenerator extends CSVContent {

    String content = "";

    public CSVGenerator() {

    }
    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        String titleString =   "\""+getTitle()+"\"";
        appendContent(titleString);
    }

    private String splitContent(String content){
        String output = "";

        String[] bracketSplit = content.split("\\{");
        output = bracketSplit[1];
        output = output.replace("}","");
        output = output.replaceAll("\\s+","");
        output = output.replaceAll("'","");


        return output;
    }


    private String getHeaderTable(String content){
        String headerContent = getContentTable(content,true);

        return headerContent;
    }
    private String getDataTable(String content){
        String dataContent = getContentTable(content,false);

        return dataContent;
    }
    private String getContentTable(String content,boolean isHeader){
        String contentTable = "";
        String contentStr = "";
        int contentIndex = 0;

        if(!isHeader){
            contentIndex=1;
        }

        String[] commSplit = content.split(COMMA);
        for (int i=0;i<commSplit.length;i++){
            String[] equalSplit = commSplit[i].split("=");
            contentStr = equalSplit[contentIndex];
            if(isHeader) {
                contentStr = contentStr.toUpperCase();
            }

            if(i>0){
                contentTable += COMMA;
            }
            contentTable += appendQuote(contentStr);
        }

        return contentTable;
    }
    public <T> String setTable(String tableTitle, ArrayList<T> datas){
        String titleString =   "\""+tableTitle+"\"";
        appendContent(titleString);

        String header = "";
        String data = "";

        for (int i=0;i<datas.size();i++){
            String dataObjtoString = datas.get(i).toString();
            dataObjtoString = splitContent(dataObjtoString);
            if(i==0){
                header = getHeaderTable(dataObjtoString);
                appendContent(header);
            }else{
                data = getDataTable(dataObjtoString);
                appendContent(data);
            }
        }
        Log.d("DATA-",content);
        return content;
    }

    public Uri generate(){
        String dirName = getTitle();
        String fileName = getTitle();

        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
        if (root.canWrite()){
            File dir    =   new File (root.getAbsolutePath() + "/"+dirName);
            dir.mkdirs();
            file   =   new File(dir, fileName+".csv");
            FileOutputStream out   =   null;
            Log.d("CSV-FILE",dir.getAbsolutePath());
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                Log.e("CSV-ERROR",e.toString());
                e.printStackTrace();
            }
            try {
                out.write(content.getBytes());
            } catch (IOException e) {
                Log.e("CSV-ERROR",e.toString());
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                Log.e("CSV-ERROR",e.toString());
                e.printStackTrace();
            }
        }
        Uri uri  =   Uri.fromFile(file);
        return uri;
    }
    private String appendContent(String addedContent){
        content += addedContent+"\n";
        return content;
    }
    private String appendQuote(String content){
        return "\""+content+"\"";
    }
}
