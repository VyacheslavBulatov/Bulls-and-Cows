package com.demo1.bullsandcows;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LobbiActivity extends AppCompatActivity {
    private int[] number1;
    private int[] number2;
    private int position;
    private int turn;
    private TextView textView;
    private TextView hint;
    private EditText name1;
    private EditText name2;
    private StringBuilder builderTry;
    private boolean error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobbi);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.hide();
        }
        textView = findViewById(R.id.textViewLTryingL);
        hint = findViewById(R.id.textViewHint);
        name1 = findViewById(R.id.editTextPersonName1);
        name2 = findViewById(R.id.editTextPersonName2);
        if(savedInstanceState == null) {
            number1 = new int[] {-1, -1, -1, -1};
            number2 = new int[] {-1, -1, -1, -1};
            position = 0;
            turn = 1;
        } else {
            number1 = savedInstanceState.getIntArray("number1");
            number2 = savedInstanceState.getIntArray("number2");
            position = savedInstanceState.getInt("position");
            turn = savedInstanceState.getInt("turn");
            textView.setText(savedInstanceState.getString("text"));
        }
        builderTry = new StringBuilder();
        if(turn == 1) {
            name1.setVisibility(View.VISIBLE);
            name2.setVisibility(View.INVISIBLE);
            hint.setText(R.string.message1);
        } else {
            name1.setVisibility(View.INVISIBLE);
            name2.setVisibility(View.VISIBLE);
            hint.setText(R.string.message2);
        }
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
        if(position >= 4){
            Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
        } else {
            int x = Integer.parseInt(view.getTag().toString());
            if (turn ==1) {
                for (int i = 0; i < position; i++) {
                    if (x == number1[i]) {
                        error = true;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < position; i++) {
                    if (x == number2[i]) {
                        error = true;
                        break;
                    }
                }
            }
            if(error) {
                Toast.makeText(this, R.string.differentNumbers, Toast.LENGTH_SHORT).show();
            } else {
                if(turn == 1) {
                    number1[position] = x;
                } else {
                    number2[position] = x;
                }
                builderTry.setLength(0);
                builderTry.append(textView.getText());
                builderTry.append(x).append(" ");
                textView.setText(builderTry.toString());
                position++;
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
                number1[position] = -1;
            } else {
                number2[position] = -1;
            }
        }
    }

    public void onClickOk(View view) {
        String n1 = name1.getText().toString().trim();
        String n2 = name2.getText().toString().trim();
        if(position != 4) {
            Toast.makeText(this, R.string.warningHighPosition, Toast.LENGTH_SHORT).show();
        } else {
            if(turn == 1) {
                if(n1.isEmpty()) {
                    Toast.makeText(this, R.string.name1, Toast.LENGTH_SHORT).show();
                } else {
                    turn = 2;
                    name1.setVisibility(View.INVISIBLE);
                    name2.setVisibility(View.VISIBLE);
                    position = 0;
                    hint.setText(R.string.message2);
                    textView.setText("");
                }
            } else {
                if(n2.isEmpty()) {
                    Toast.makeText(this, R.string.name2, Toast.LENGTH_SHORT).show();
                } else if(n2.equals(n1)) {
                    Toast.makeText(this, R.string.difNames, Toast.LENGTH_SHORT).show();
                } else {
                    int[] target1 = number1;
                    int[] target2 = number2;
                    Intent intent = new Intent(this, PvPActivity.class);
                    intent.putExtra("name1", n1);
                    intent.putExtra("name2", n2);
                    intent.putExtra("target1", target1);
                    intent.putExtra("target2", target2);
                    turn = 1;
                    position = 0;
                    number1 = new int[] {-1, -1, -1, -1};
                    number2 = new int[] {-1, -1, -1, -1};
                    hint.setText(R.string.message1);
                    textView.setText("");
                    name1.setVisibility(View.VISIBLE);
                    name2.setVisibility(View.INVISIBLE);
                    startActivity(intent);
                }
            }
        }
    }

    public void onClickClear(View view) {
        turn = 1;
        position = 0;
        number1 = new int[] {-1, -1, -1, -1};
        number2 = new int[] {-1, -1, -1, -1};
        hint.setText(R.string.message1);
        textView.setText("");
        name1.setVisibility(View.VISIBLE);
        name2.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
        outState.putInt("turn", turn);
        outState.putIntArray("number1", number1);
        outState.putIntArray("number2", number2);
        outState.putString("text", textView.getText().toString());
    }
}