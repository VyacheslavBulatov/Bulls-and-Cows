package com.demo1.bullsandcows;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
    }

    public void onClickRules(View view) {
        Intent intent = new Intent(this, ActivityRules.class);
        startActivity(intent);
    }

    public void onClickTraining(View view) {
        Intent intent = new Intent(this, TrainingActivity.class);
        startActivity(intent);
    }

    public void onClick2p(View view) {
        Intent intent = new Intent(this, LobbiActivity.class);
        startActivity(intent);
    }
}