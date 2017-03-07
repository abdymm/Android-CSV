package com.abdymalikmulky.androidcsv;

import android.net.Uri;
import android.util.Log;

import com.abdymalikmulky.androidcsv.helper.FileHelper;

import java.io.File;
import java.util.ArrayList;

import static com.abdymalikmulky.androidcsv.CSVProperties.APOSTROPHE;
import static com.abdymalikmulky.androidcsv.CSVProperties.BRACKET_CLOSE;
import static com.abdymalikmulky.androidcsv.CSVProperties.COMMA;
import static com.abdymalikmulky.androidcsv.CSVProperties.NEW_LINE;

/**
 * Bismillahirrahmanirrahim
 * Created by abdymalikmulky on 3/6/17.
 */

public class CSVGenerator extends CSVContent {

    FileHelper fileHelper;
    String content = "";

    public CSVGenerator() {
        fileHelper = new FileHelper();
    }
    public CSVGenerator(String dirName,String fileName) {
        fileHelper = new FileHelper(dirName,fileName);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
        String titleString =   "\""+getTitle()+"\"";
        appendContent(titleString);
    }

    /**
     * Split a toString content
     * @param content
     * @return
     */
    private String splitContent(String content){
        String output = "";

        String[] bracketSplit = content.split("\\{");
        output = bracketSplit[1];
        output = output.replace(BRACKET_CLOSE,"");
        output = output.replaceAll("\\s+","");
        output = output.replaceAll(APOSTROPHE,"");


        return output;
    }



    /**
     * Header table like <th> parse by string content
     * @param content
     * @return
     */
    private String getHeaderTable(String content){
        String headerContent = getContentTable(content,true);

        return headerContent;
    }

    /**
     * Header table like <td> parse by string content
     * @param content
     * @return
     */
    private String getDataTable(String content){
        String dataContent = getContentTable(content,false);

        return dataContent;
    }

    /**
     * Content genartor for getHeaderTable() and getDataTable
     * @param content
     * @return
     */
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

    /**
     * Setup Table yang akan di generate
     * @param tableTitle
     * @param datas
     * @param <T>
     * @return
     */
    public <T> String addTable(String tableTitle, ArrayList<T> datas){
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
        File file = fileHelper.storeFile(content);
        Uri uri  =   Uri.fromFile(file);
        return uri;
    }
    private String appendContent(String addedContent){
        content += addedContent+NEW_LINE;
        return content;
    }
    private String appendQuote(String content){
        return "\""+content+"\"";
    }
}
