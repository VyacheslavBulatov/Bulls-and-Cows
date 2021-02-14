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

import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {
    private int[] target;
    private int[] trying;
    private int position;
    private int turn;

    private ArrayList<String> history;
    private ListView listView;
    private TextView textView;

    private int bulls;
    private int cows;
    private boolean error;

    private StringBuilder builderTry;
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
            history = new ArrayList<>();
            bulls = 0;
            cows = 0;
            position = 0;
            trying = new int[]{-1, -1, -1, -1};
            target = new int[]{-1, 2, 3, 4};
            textView.setText(R.string.trainingWelcome);
            turn = 1;
        } else {
            history = savedInstanceState.getStringArrayList("history");
            bulls = savedInstanceState.getInt("bulls");
            cows = savedInstanceState.getInt("cows");
            position = savedInstanceState.getInt("position");
            trying = savedInstanceState.getIntArray("trying");
            target = savedInstanceState.getIntArray("target");
            textView.setText(savedInstanceState.getString("text"));
            turn = savedInstanceState.getInt("turn");
        }
        builderTry = new StringBuilder();
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, history);
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
        error = false;
        if( target[0] != -1) {
            if(bulls < 4) {
                if(position >= 4){
                    Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
                } else {
                    int x = Integer.parseInt(view.getTag().toString());
                    for (int i = 0; i < position; i++) {
                        if(x == trying[i]) {
                            error = true;
                            break;
                        }
                    }
                    if(error) {
                        Toast.makeText(this, R.string.differentNumbers, Toast.LENGTH_SHORT).show();
                    } else {
                        trying[position] = x;
                        builderTry.setLength(0);
                        builderTry.append(textView.getText());
                        builderTry.append(x).append(" ");
                        textView.setText(builderTry.toString());
                        position++;
                    }
                }
            }
        }
    }

    public void onClickBack(View view) {
        if(position > 0) {
            builderTry.setLength(0);
            builderTry.append(textView.getText());
            builderTry.setLength(builderTry.length() - 2);
            textView.setText(builderTry.toString());
            position--;
            trying[position] = -1;
        }
    }

    public void onClickOk(View view) {
        if(position != 4) {
            Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
        } else {
            bulls = 0;
            cows = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if(trying[i] == target[j]) {
                        if(i == j) {
                            bulls += 1;
                        } else {
                            cows += 1;
                        }
                    }
                }
            }
            history.add(turn + ") " + trying[0] + "" + trying[1] + "" + trying[2] + "" + trying[3] + " - " + bulls + "б. " + cows + "к.");
            adapter.notifyDataSetChanged();
            textView.setText("");
            position = 0;
            turn++;
            for (int i = 0; i < 4; i++) {
                trying[i] = -1;
            }
            if (bulls == 4) {
                String result = "Поздравляем! Вы угадали число: \n" + target[0] + "" + target[1] + "" + target[2] + "" + target[3];
                textView.setText(result);
            }
        }
    }

    public void onClickGenerate(View view) {
        history.clear();
        adapter.notifyDataSetChanged();
        textView.setText("");
        position = 0;
        bulls = 0;
        cows = 0;
        turn = 1;
        for (int i = 0; i < 4; i++) {
            trying[i] = -1;
        }
        int x = 0;
        for (int i = 0; i < 4; i++) {
            do {
                x = (int) (Math.random() * 10);
            } while (x == target[0] || x == target[1] || x == target[2]);
            target[i] = x;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("history", history);
        outState.putInt("bulls", bulls);
        outState.putInt("cows", cows);
        outState.putInt("position", position);
        outState.putIntArray("trying", trying);
        outState.putIntArray("target", target);
        outState.putString("text", textView.getText().toString());
        outState.putInt("turn", turn);
    }
}