package com.gdx.galaxyfighter.Tools;

public class Score {
    private int score;
    private String pseudo;

    public Score(int score, String pseudo){
        this.score = score;
        this.pseudo = pseudo;
    }

    public Score(){
        new Score(0, "");
    }

    public void addScore(int ajout){
        score += ajout;
    }

    public int getScore(){
        return score;
    }

    public String getPseudo(){
        return pseudo;
    }

    public void setScore(int score){
        this.score = score;
    }

    public void setPseudo(String pseudo){
        this.pseudo = pseudo;
    }
}
