package com.abdymalikmulky.androidcsvexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abdymalikmulky.androidcsv.CSVGenerator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CSVGenerator csvGenerator;
    ArrayList<ToDo> toDos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toDos = generateDummUser();

        csvGenerator = new CSVGenerator(getString(R.string.app_name),getString(R.string.app_name));
        csvGenerator.setTitle(getString(R.string.app_name));

        csvGenerator.setSubtitle("PROFILE");
        csvGenerator.addKeyValue("Name","Abdy Malik Mulky");
        csvGenerator.addKeyValue("Email","me@abdymalikmulky.com");
        csvGenerator.addNewLine();


        String[] exceptionUser = {"status"};
        csvGenerator.addTable("Data ToDo", toDos,exceptionUser);

        Uri uri = csvGenerator.generate();

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT,R.string.app_name);
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("text/html");
        startActivity(sendIntent);
    }
    private ArrayList<ToDo> generateDummUser(){
        ArrayList<ToDo> toDos = new ArrayList<>();
        for (int i=0;i<2;i++){
            toDos.add(new ToDo(i,"Eat","Eat everything","-"));
        }
        return toDos;
    }


}
