package com.demo1.logic;


import android.widget.Toast;

import com.demo1.bullsandcows.R;

import java.io.Serializable;

public class GameLogic implements Serializable {
    private Player player1;
    private Player player2;
    private String text;
    private int position;
    private StringBuilder builderTry;
    private boolean gameOver;
    private int firstPlayer;
    private boolean lastTry;

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isLastTry() {
        return lastTry;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isError() {
        return error;
    }

    public boolean isPositionError() {
        return positionError;
    }

    private int turn;
    private boolean error;
    private boolean positionError;
    private boolean training;

    public GameLogic(Player player1, Player player2, boolean training) {
        this.player1 = player1;
        this.player2 = player2;
        position = 0;
        builderTry = new StringBuilder();
        lastTry = false;
        this.training = training;
        if(training) {
            turn = 1;
            gameOver = true;
            text = "Сгенерируйте первую комбинацию";
        } else {
            turn = (int) (Math.random() * 2 + 1);
            firstPlayer = turn;
            gameOver = false;
            text = "";
        }
    }

    public void clickedNumber(int tag){
        error = false;
        positionError = false;
        if(!gameOver) {
            if (position >= 4) {
                positionError = true;
            } else {
                if(turn == 1) {
                    for (int i = 0; i < position; i++) {
                        if(tag == player1.getTrying()[i]) {
                            error = true;
                            break;
                        }
                    }
                } else {
                    for (int i = 0; i < position; i++) {
                        if(tag == player2.getTrying()[i]) {
                            error = true;
                            break;
                        }
                    }
                }
                if(!error) {
                    if(turn == 1) {
                        player1.setPartOfTrying(tag, position);
                    } else {
                        player2.setPartOfTrying(tag, position);
                    }
                    builderTry.setLength(0);
                    builderTry.append(text);
                    builderTry.append(tag).append(" ");
                    this.text = builderTry.toString();
                    position++;
                }
            }
        }
    }

    public void checking() {
        positionError = false;
        if (position != 4) {
            positionError = true;
        } else {
            text = "";
            if (turn == 1) {
                player1.setBulls(0);
                player1.setCows(0);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (player1.getTrying()[i] == player2.getNumber()[j]) {
                            if (i == j) {
                                player1.setBulls(player1.getBulls() + 1);
                            } else {
                                player1.setCows(player1.getCows() + 1);
                            }
                        }
                    }
                }
                player1.addToHistory(player1.getStep() + ") " + player1.getTrying()[0] + "" + player1.getTrying()[1] + "" + player1.getTrying()[2] + "" + player1.getTrying()[3] + " - " + player1.getBulls() + "б. " + player1.getCows() + "к.");
                player1.setStep(player1.getStep() + 1);
                position = 0;
                for (int i = 0; i < 4; i++) {
                    player1.setPartOfTrying(-1, i);;
                }
                if(training) {
                    if(player1.getBulls() == 4) {
                        gameOver = true;
                        text = "Поздравляем! Вы угадали число: \n" + player2.getNumber()[0] + "" + player2.getNumber()[1] + "" + player2.getNumber()[2] + "" + player2.getNumber()[3];
                    }
                } else {
                    turn = 2;
                    if(player1.getBulls() == 4) {
                        player2.setSolved(true);
                        if (firstPlayer == 2) {
                            gameOver = true;
                            player1.setSolved(true);
                            if(!lastTry) {
                                text = player1.getWinner();
                            } else {
                                text = "Ничья";
                            }
                        } else {
                            lastTry = true;
                        }
                    } else {
                        if(lastTry) {
                            gameOver = true;
                            player2.setSolved(true);
                            text = player2.getWinner();
                        }
                    }
                }
            } else {
                player2.setBulls(0);
                player2.setCows(0);
                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        if (player2.getTrying()[i] == player1.getNumber()[j]) {
                            if (i == j) {
                                player2.setBulls(player2.getBulls() + 1);
                            } else {
                                player2.setCows(player2.getCows() + 1);
                            }
                        }
                    }
                }
                player2.addToHistory(player2.getStep() + ") " + player2.getTrying()[0] + "" + player2.getTrying()[1] + "" + player2.getTrying()[2] + "" + player2.getTrying()[3] + " - " + player2.getBulls() + "б. " + player2.getCows() + "к.");
                player2.setStep(player2.getStep() + 1);
                position = 0;
                for (int i = 0; i < 4; i++) {
                    player2.setPartOfTrying(-1, i);;
                }
                turn = 1;
                if(player2.getBulls() == 4) {
                    player1.setSolved(true);
                    if (firstPlayer == 1) {
                        gameOver = true;
                        player2.setSolved(true);
                        if(!lastTry) {
                            text = player2.getWinner();
                        } else {
                            text = "Ничья";
                        }
                    } else {
                        lastTry = true;
                    }
                } else {
                    if(lastTry) {
                        gameOver = true;
                        player1.setSolved(true);
                        text = player1.getWinner();
                    }
                }
            }
            }
        }

        public void clearNumber(){
            if(position > 0) {
                builderTry.setLength(0);
                builderTry.append(text);
                builderTry.setLength(builderTry.length() - 2);
                this.text = builderTry.toString();
                position--;
                if(turn == 1) {
                    player1.setPartOfTrying(-1, position);
                } else {
                    player2.setPartOfTrying(-1, position);
                }
            }
        }

        public void generateNewNumber(){
            gameOver = false;
            player1.clearHistory();
            text = "";
            position = 0;
            player1.setCows(0);
            player1.setBulls(0);
            for (int i = 0; i < 4; i++) {
                player1.setPartOfTrying(-1,i);
            }
            int x = 0;
            for (int i = 0; i < 4; i++) {
                do {
                    x = (int) (Math.random() * 10);
                } while (x == player2.getNumber()[0] || x == player2.getNumber()[1] || x == player2.getNumber()[2]);
                player2.setPartOfNumber(x, i);
            }
        }

        public Player getPlayer2() {
            return player2;
        }

        public Player getPlayer1(){
            return player1;
        }

        public String getText() {
            return text;
        }
    }
