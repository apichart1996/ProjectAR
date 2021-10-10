package com.example.projectar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    Button BTcamera;
    Button BThelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        BTcamera = (Button)findViewById(R.id.bt_pagecamera);
        BTcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MenuActivity.this,CameraActivity.class);
                startActivity(camera);
//                startActivityForResult(camera, 1);
            }
        });

        BThelp = (Button) findViewById(R.id.bt_pagehelp);
        BThelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(MenuActivity.this,HelpActivity.class);
                startActivity(create);
            }
        });
    }
//    @Override
//    public void onResume()
//    {  // After a pause OR at startup
//        super.onResume();
//        //Refresh your stuff here
//    }

//    @Override
//    public void onRestart()
//    {
//        super.onRestart();
//        finish();
//        startActivity(getIntent());
//    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == 1) {
//
//            if(resultCode == RESULT_OK){
//                //Update List
//            }
//            if (resultCode == RESULT_CANCELED) {
//                //Do nothing?
//            }
//        }
//    }//onActivityResult

}
