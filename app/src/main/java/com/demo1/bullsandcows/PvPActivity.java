package com.demo1.bullsandcows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo1.logic.GameLogic;
import com.demo1.logic.Player;

import java.util.ArrayList;

public class PvPActivity extends AppCompatActivity {

    private ListView listView1;
    private TextView textView1;
    private ListView listView2;
    private TextView textView2;
    private TextView textView;
    private TextView textTurn;

    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;

    private GameLogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pv_p);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        listView1 = findViewById(R.id.listViewP1);
        listView2 = findViewById(R.id.listViewP2);
        textView = findViewById(R.id.textViewLTryingP);
        textTurn = findViewById(R.id.textViewTurn);
        textView1 = findViewById(R.id.textViewP1);
        textView2 = findViewById(R.id.textViewP2);
        if(savedInstanceState == null) {
            Intent intent = getIntent();
            logic = new GameLogic(new Player(intent.getStringExtra("name1"), intent.getIntArrayExtra("target1")), new Player(intent.getStringExtra("name2"), intent.getIntArrayExtra("target2")), false );
        } else {
            logic = (GameLogic) savedInstanceState.getSerializable("logic");
        }
        if(logic.getTurn() == 1) {
            textTurn.setText(logic.getPlayer1().getWhoseTurn());
        } else {
            textTurn.setText(logic.getPlayer2().getWhoseTurn());
        }
        textView.setText(logic.getText());
        textView1.setText(logic.getPlayer1().getName());
        textView2.setText(logic.getPlayer2().getName());
        adapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, logic.getPlayer1().getHistory());
        listView1.setAdapter(adapter1);
        adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, logic.getPlayer2().getHistory());
        listView2.setAdapter(adapter2);
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
        logic.clickedNumber(Integer.parseInt(view.getTag().toString()));
        if(!logic.isGameOver()) {
            if (logic.isPositionError()) {
                Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
            } else {
                textView.setText(logic.getText());
                if (logic.isError()) {
                    Toast.makeText(this, R.string.differentNumbers, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onClickOk(View view) {
        logic.checking();
        if(logic.isPositionError()) {
            Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
        } else {
            textView.setText(logic.getText());
            adapter1.notifyDataSetChanged();
            adapter2.notifyDataSetChanged();
            if(logic.getTurn() == 1) {
                textTurn.setText(logic.getPlayer1().getWhoseTurn());
            } else {
                textTurn.setText(logic.getPlayer2().getWhoseTurn());
            }
            if(logic.getPlayer1().isSolved()) {
                textView1.setText(logic.getPlayer1().getNameWithNumber());
            }
            if(logic.getPlayer2().isSolved()) {
                textView2.setText(logic.getPlayer2().getNameWithNumber());
            }
            if(logic.isLastTry() && !logic.isGameOver()) {
                Toast.makeText(this, R.string.lastTurn, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void onClickBack(View view) {
        logic.clearNumber();
        textView.setText(logic.getText());
    }

    public void onClickFinish(View view) {
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("logic", logic);
    }
}