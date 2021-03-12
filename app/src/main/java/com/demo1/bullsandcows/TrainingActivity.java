package com.demo1.bullsandcows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo1.logic.GameLogic;
import com.demo1.logic.Player;

import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {

    private GameLogic logic;
    private ListView listView;
    private TextView textView;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        listView = findViewById(R.id.listViewTrainHistory);
        textView = findViewById(R.id.textViewTrying);
        if(savedInstanceState == null) {
            logic = new GameLogic(new Player(), new Player(), true);
        } else {
            logic = (GameLogic) savedInstanceState.getSerializable("logic");
        }
        textView.setText(logic.getText());
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, logic.getPlayer1().getHistory());
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onClickNumber(View view) {
        if(logic.isPositionError()){
            Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
        } else {
            logic.clickedNumber(Integer.parseInt(view.getTag().toString()));
            textView.setText(logic.getText());
            if (logic.isError()) {
                Toast.makeText(this, R.string.differentNumbers, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onClickBack(View view) {
            logic.clearNumber();
            textView.setText(logic.getText());
    }

    public void onClickOk(View view) {
        if(logic.isPositionError()) {
            Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
        } else {
            logic.checking();
            adapter.notifyDataSetChanged();
            textView.setText(logic.getText());
        }
    }

    public void onClickGenerate(View view) {
        logic.generateNewNumber();
        adapter.notifyDataSetChanged();
        textView.setText(logic.getText());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("logic", logic);
    }
}