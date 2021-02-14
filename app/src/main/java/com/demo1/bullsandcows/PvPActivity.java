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

import java.util.ArrayList;

public class PvPActivity extends AppCompatActivity {

    private int position;
    private int turn;

    private Player player1;
    private Player player2;

    private ArrayList<String> history1;
    private ListView listView1;
    private TextView textView1;
    private ArrayList<String> history2;
    private ListView listView2;
    private TextView textView2;

    private TextView textView;
    private TextView textTurn;

    private StringBuilder builderTry;
    private boolean error;
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    private boolean gameOver;
    private int firstPlayer;
    private boolean lastTry;


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
            position = 0;
            turn = (int) (Math.random() * 2 + 1);
            history1 = new ArrayList<>();
            history2 = new ArrayList<>();
            Intent intent = getIntent();
            player1 = new Player(intent.getStringExtra("name1"), intent.getIntArrayExtra("target1"));
            player2 = new Player(intent.getStringExtra("name2"), intent.getIntArrayExtra("target2"));
            gameOver = false;
            firstPlayer = turn;
            lastTry = false;
        } else {
            position = savedInstanceState.getInt("position");
            turn = savedInstanceState.getInt("turn");
            history1 = savedInstanceState.getStringArrayList("history1");
            history2 = savedInstanceState.getStringArrayList("history2");
            player1 = (Player) savedInstanceState.getSerializable("player1");
            player2 = (Player) savedInstanceState.getSerializable("player2");
            gameOver = savedInstanceState.getBoolean("gameOver");
            firstPlayer = savedInstanceState.getInt("firstPlayer");
            lastTry = savedInstanceState.getBoolean("lastTry");
            textView.setText(savedInstanceState.getString("text"));
        }
        if(turn == 1) {
            textTurn.setText(player1.getWhoseTurn());
        } else {
            textTurn.setText(player2.getWhoseTurn());
        }
        builderTry = new StringBuilder();
        textView1.setText(player1.getName());
        textView2.setText(player2.getName());
        adapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, history1);
        listView1.setAdapter(adapter1);
        adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, history2);
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
        error = false;
        if(!gameOver) {
            if(position >= 4){
                Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
            } else {
                int x = Integer.parseInt(view.getTag().toString());
                if(turn == 1) {
                    for (int i = 0; i < position; i++) {
                        if(x == player1.getTrying()[i]) {
                            error = true;
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < position; i++) {
                        if(x == player2.getTrying()[i]) {
                            error = true;
                            break;
                        }
                    }
                }
                if(error) {
                    Toast.makeText(this, R.string.differentNumbers, Toast.LENGTH_SHORT).show();
                } else {
                    if(turn == 1) {
                        player1.setPartOfTrying(x, position);
                    } else {
                        player2.setPartOfTrying(x, position);
                    }
                    builderTry.setLength(0);
                    builderTry.append(textView.getText());
                    builderTry.append(x).append(" ");
                    textView.setText(builderTry.toString());
                    position++;
                }
            }
        }
    }

    public void onClickOk(View view) {
        if(position != 4) {
            Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
        } else {
            textView.setText("");
            if(turn == 1) {
                player1.setBulls(0);
                player1.setCows(0);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if(player1.getTrying()[i] == player2.getNumber()[j]) {
                            if(i == j) {
                                player1.setBulls(player1.getBulls() + 1);
                            } else {
                                player1.setCows(player1.getCows() + 1);
                            }
                        }
                    }
                }
                history1.add(player1.getStep() + ") " + player1.getTrying()[0] + "" + player1.getTrying()[1] + "" + player1.getTrying()[2] + "" + player1.getTrying()[3] + " - " + player1.getBulls() + "б. " + player1.getCows() + "к.");
                adapter1.notifyDataSetChanged();
                player1.setStep(player1.getStep() + 1);
                textView.setText("");
                position = 0;
                for (int i = 0; i < 4; i++) {
                    player1.setPartOfTrying(-1, i);;
                }
                turn = 2;
                textTurn.setText(player2.getWhoseTurn());
                if(player1.getBulls() == 4) {
                    textView2.setText(player2.getNameWithNumber());
                    if (firstPlayer == 2) {
                        gameOver = true;
                        textView1.setText(player1.getNameWithNumber());
                        if(!lastTry) {
                            textView.setText(player1.getWinner());
                        } else {
                            textView.setText(R.string.draw);
                        }
                    } else {
                        Toast.makeText(this, R.string.lastTurn, Toast.LENGTH_SHORT).show();
                        lastTry = true;
                    }
                } else {
                    if(lastTry) {
                        gameOver = true;
                        textView2.setText(player2.getNameWithNumber());
                        textView.setText(player2.getWinner());
                    }
                }
            } else {
                player2.setBulls(0);
                player2.setCows(0);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if(player2.getTrying()[i] == player1.getNumber()[j]) {
                            if(i == j) {
                                player2.setBulls(player2.getBulls() + 1);;
                            } else {
                                player2.setCows(player2.getCows() + 1);
                            }
                        }
                    }
                }
                history2.add(player2.getStep() + ") " + player2.getTrying()[0] + "" + player2.getTrying()[1] + "" + player2.getTrying()[2] + "" + player2.getTrying()[3] + " - " + player2.getBulls() + "б. " + player2.getCows() + "к.");
                adapter2.notifyDataSetChanged();
                player2.setStep(player2.getStep() + 1);
                textView.setText("");
                position = 0;
                for (int i = 0; i < 4; i++) {
                    player2.setPartOfTrying(-1, i);;
                }
                turn = 1;
                textTurn.setText(player1.getWhoseTurn());
                if(player2.getBulls() == 4) {
                    textView1.setText(player1.getNameWithNumber());
                    if (firstPlayer == 1) {
                        gameOver = true;
                        textView2.setText(player2.getNameWithNumber());
                        if(!lastTry) {
                            textView.setText(player2.getWinner());
                        } else {
                            textView.setText(R.string.draw);
                        }
                    } else {
                        Toast.makeText(this, R.string.lastTurn, Toast.LENGTH_SHORT).show();
                        lastTry = true;
                    }
                } else {
                    if(lastTry) {
                        gameOver = true;
                        textView1.setText(player1.getNameWithNumber());
                        textView.setText(player1.getWinner());
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
            if(turn == 1) {
                player1.setPartOfTrying(-1, position);
            } else {
                player2.setPartOfTrying(-1, position);
            }
        }
    }

    public void onClickFinish(View view) {
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("history1", history1);
        outState.putStringArrayList("history2", history2);
        outState.putInt("position", position);
        outState.putInt("turn", turn);
        outState.putSerializable("player1", player1);
        outState.putSerializable("player2", player2);
        outState.putString("text", textView.getText().toString());
        outState.putInt("firstPlayer", firstPlayer);
        outState.putBoolean("gameOver", gameOver);
        outState.putBoolean("lastTry", lastTry);
    }
}