package com.abdymalikmulky.androidcsvexample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.abdymalikmulky.androidcsv.CSVGenerator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CSVGenerator csvGenerator;
    ArrayList<Feeder> feeders;
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feeders = generateDummFeeder();
        users = generateDummUser();

        csvGenerator = new CSVGenerator();
        csvGenerator.setTitle("TMan");

        csvGenerator.setTable("Data Feeder",feeders);
        csvGenerator.setTable("Data User",users);


        Uri uri = csvGenerator.generate();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType("text/html");
        startActivity(sendIntent);
    }
    private ArrayList<Feeder> generateDummFeeder(){
        ArrayList<Feeder> feeders = new ArrayList<>();
        for (int i=0;i<10;i++){
            feeders.add(new Feeder(i+"Id",i+"Feeder",i+"Mini"));
        }
        return feeders;
    }
    private ArrayList<User> generateDummUser(){
        ArrayList<User> users = new ArrayList<>();
        for (int i=0;i<10;i++){
            users.add(new User(i+"Abdy",i+"Abdy",i+"Abdy",i*10));
        }
        return users;
    }


}
