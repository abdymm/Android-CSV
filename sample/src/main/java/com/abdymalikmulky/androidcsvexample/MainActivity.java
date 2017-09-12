package com.abdymalikmulky.androidcsvexample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.abdymalikmulky.androidcsv.CSVGenerator;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    CSVGenerator csvGenerator;
    ArrayList<ToDo> toDos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialPermission();
    }
    private ArrayList<ToDo> generateDummUser(){
        ArrayList<ToDo> toDos = new ArrayList<>();
        for (int i=0;i<2;i++){
            toDos.add(new ToDo(i,"Eat","Eat everything","-"));
        }
        return toDos;
    }

    public void generateCsv(View view) {
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


    private void initialPermission() {
        if (!checkPermissions()) {
            requestPermissions();
        }
    }

    public boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
    public void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);


        if (shouldProvideRationale) {
            showSnackbar(this, R.string.allow_location_permission, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Toast.makeText(this, "Failed interaction was cancelled", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Success granted permission", Toast.LENGTH_SHORT).show();
            } else {
                showSnackbar(this, R.string.setting_permission, R.string.label_setting, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",
                                BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public static void showSnackbar(Activity activity, final int mainTextStringId, final int actionStringId,
                                    View.OnClickListener listener) {
        Snackbar.make(activity.findViewById(android.R.id.content),
                activity.getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(activity.getString(actionStringId), listener).show();
    }

}
