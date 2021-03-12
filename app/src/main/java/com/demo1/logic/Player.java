package com.demo1.logic;



import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String name;
    private int[] number;
    private int step;
    private int[] trying;
    private int bulls;
    private int cows;
    private ArrayList<String> history;
    private boolean isSolved;

    public Player(String name, int[] number) {
        this.name = name;
        this.number = number;
        this.step = 1;
        this.trying = new int[] {-1, -1, -1, -1};
        this.bulls = 0;
        this.cows = 0;
        this.history = new ArrayList<>();
        isSolved = false;
    }

    public Player(){
        trying = new int[] {-1, -1, -1, -1};
        number = new int[] {-1, -1, -1, -1};
        this.bulls = 0;
        this.cows = 0;
        this.history = new ArrayList<>();
        isSolved = false;
        this.step = 1;
        this.name = "";
    }


    public String getName() {
        return name;
    }

    public int[] getNumber() {
        return number;
    }

    public String getNameWithNumber(){
        return name + "(" + number[0] + number[1] + number[2] + number[3] + ")";
    }

    public String getWhoseTurn() {
        return "Ход игрока - " + this.name;
    }

    public String getWinner() {
        return "Победил игрок - " + this.name;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int[] getTrying() {
        return trying;
    }

    public void setPartOfTrying(int number, int position) {
        this.trying[position] = number;
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void addToHistory(String part){
        this.history.add(part);
    }

    public void clearHistory(){
        this.history.clear();
    }

    public void setPartOfNumber(int number, int position) {
        this.number[position] = number;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }
}
