package com.gdx.galaxyfighter;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.gdx.galaxyfighter.Ecrans.EcranChoixNiveau;
import com.gdx.galaxyfighter.Ecrans.EcranFin;
import com.gdx.galaxyfighter.Ecrans.EcranGameOver;
import com.gdx.galaxyfighter.Ecrans.EcranJeu;
import com.gdx.galaxyfighter.Ecrans.EcranMenuPrincipal;
import com.gdx.galaxyfighter.Ecrans.EcranNomJoueur;
import com.gdx.galaxyfighter.Ecrans.EcranOption;
import com.gdx.galaxyfighter.Ecrans.EcranScore;
import com.gdx.galaxyfighter.Tools.Score;
import com.gdx.galaxyfighter.Tools.TopScore;

public class GalaxyFighter extends Game {
    public SpriteBatch batch;

    public int niveau;
    public boolean vibrationB;
    public boolean musiqueB;
    public boolean bruitage;

    public TopScore topScore;
    public Score joueur;

    public Music musiqueMenu;
    public Sound button;

    public static final int V_WIDTH = 240;
    public static final int V_HEIGHT = 400;

    public Texture background;

    private EcranJeu jeu;
    private EcranOption option;
    private EcranMenuPrincipal menuPrincipal;
    private EcranScore score;
    private EcranNomJoueur nomJoueur;
    private EcranChoixNiveau choixNiveau;
    private EcranGameOver gameOver;
    private EcranFin fin;

    public final static int MENU = 0;
    public final static int OPTION = 1;
    public final static int SCORE = 2;
    public final static int JEU = 3;
    public final static int NOMJOUEUR = 4;
    public final static int CHOIXNIVEAU = 5;
    public final static int GAMEOVER = 6;
    public final static int GAGNE = 7;

    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if (menuPrincipal != null)
                    menuPrincipal.dispose();
                menuPrincipal = new EcranMenuPrincipal(this);
                this.setScreen(menuPrincipal);
                break;
            case OPTION:
                if (option != null)
                    option.dispose();
                option = new EcranOption(this);
                this.setScreen(option);
                break;
            case SCORE:
                if (score != null)
                    score.dispose();
                score = new EcranScore(this);
                this.setScreen(score);
                break;
            case JEU:
                if (jeu != null)
                    jeu.dispose();
                jeu = new EcranJeu(this, new Vector3(10, 25, 45), new Vector3(2, 5, 10), niveau);
                this.setScreen(jeu);
                break;
            case NOMJOUEUR:
                if (nomJoueur != null)
                    nomJoueur.dispose();
                nomJoueur = new EcranNomJoueur(this);
                this.setScreen(nomJoueur);
                break;
            case CHOIXNIVEAU:
                if (choixNiveau != null)
                    choixNiveau.dispose();
                choixNiveau = new EcranChoixNiveau(this);
                this.setScreen(choixNiveau);
                break;
            case GAMEOVER:
                if (gameOver != null)
                    gameOver.dispose();
                gameOver = new EcranGameOver(this);
                this.setScreen(gameOver);
                break;
            case GAGNE:
                if (fin != null)
                    fin.dispose();
                fin = new EcranFin(this);
                this.setScreen(fin);
                break;
        }
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        topScore = new TopScore(this);
        vibrationB = true;
        musiqueB = true;
        bruitage = true;
        musiqueMenu = Gdx.audio.newMusic(Gdx.files.internal("musics_n_sounds/menu_music.mp3"));
        musiqueMenu.setLooping(true);
        musiqueMenu.setVolume(0.5f);
        background = new Texture(Gdx.files.internal("sprites/background.jpeg"));
        button = Gdx.audio.newSound(Gdx.files.internal("musics_n_sounds/sounds/button.mp3"));
        setScreen(new EcranMenuPrincipal(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose(){
        musiqueMenu.dispose();
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }
}
