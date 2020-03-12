package com.gdx.galaxyfighter.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.gdx.galaxyfighter.GalaxyFighter;

import java.util.Vector;

public class TopScore {
    private int highscore;

    private Score topScore1;
    private Score topScore2;
    private Score topScore3;
    private Score topScore4;
    private Score topScore5;
    private Score topScore6;
    private Score topScore7;
    private Score topScore8;
    private Score topScore9;

    private Preferences prefs;

    private Vector<Score> scoreVec;

    public TopScore(GalaxyFighter jeu){
        //On recupere le fichier de sauvegarde
        prefs = Gdx.app.getPreferences("sauvegarde_highscore");
        //On charge le fichier sauvegarde_highscore, pour obtenir son highscore pour le modifier par la suite
        this.highscore = prefs.getInteger("highscore",0);

        topScore1 = new Score(prefs.getInteger("score1", 0), prefs.getString("j1", "default"));
        topScore2 = new Score(prefs.getInteger("score2", 0), prefs.getString("j2", "default"));
        topScore3 = new Score(prefs.getInteger("score3", 0), prefs.getString("j3", "default"));
        topScore4 = new Score(prefs.getInteger("score4", 0), prefs.getString("j4", "default"));
        topScore5 = new Score(prefs.getInteger("score5", 0), prefs.getString("j5", "default"));
        topScore6 = new Score(prefs.getInteger("score6", 0), prefs.getString("j6", "default"));
        topScore7 = new Score(prefs.getInteger("score7", 0), prefs.getString("j7", "default"));
        topScore8 = new Score(prefs.getInteger("score8", 0), prefs.getString("j8", "default"));
        topScore9 = new Score(prefs.getInteger("score9", 0), prefs.getString("j9", "default"));

        scoreVec = new Vector<Score>();

        scoreVec.add(topScore1);
        scoreVec.add(topScore2);
        scoreVec.add(topScore3);
        scoreVec.add(topScore4);
        scoreVec.add(topScore5);
        scoreVec.add(topScore6);
        scoreVec.add(topScore7);
        scoreVec.add(topScore8);
        scoreVec.add(topScore9);
    }

    public void addScore(Score newscore){
        for(int i = 0; i < scoreVec.size() && i < 10; i++){
            if(newscore.getScore() > scoreVec.get(i).getScore()) {
                scoreVec.add(i, newscore);
                break;
            }
        }
    }

    public Score getiScore(int i){
        return scoreVec.get(i);
    }

    public void save_prefs(){
        for (int i = 1; i < 10; i++) {
            prefs.putInteger(String.format("score%d",i), getiScore(i - 1).getScore());
            prefs.putString(String.format("j%d",i), getiScore(i - 1).getPseudo());
        }
        prefs.flush();
    }
}