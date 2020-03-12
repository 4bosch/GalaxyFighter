package com.gdx.galaxyfighter.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.galaxyfighter.GalaxyFighter;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private GalaxyFighter parent;

    private Integer life;
    private Integer niveau;

    private Label lifeLabel;
    private static Label scoreLabel;
    private Label niveauLabel;
    private Label lifeStrLabel;
    private Label scoreStrLabel;
    private Label niveauStrLabel;

    public Hud(GalaxyFighter jeu){
        parent = jeu;
        life = 3;
        niveau = 1;

        viewport = new FitViewport(GalaxyFighter.V_WIDTH, GalaxyFighter.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, jeu.batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        //table.setDebug(true);

        lifeLabel = new Label(String.format("%02d", life), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%04d", jeu.joueur.getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        niveauStrLabel = new Label("NIVEAU", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lifeStrLabel = new Label("VIE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreStrLabel = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        niveauLabel = new Label(String.format("%02d", niveau), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(lifeStrLabel).expandX().padTop(10);
        table.add(niveauStrLabel).expandX().padTop(10);
        table.add(scoreStrLabel).expandX().padTop(10);
        table.row();
        table.add(lifeLabel).expandX();
        table.add(niveauLabel).expandX();
        table.add(scoreLabel).expandX();

        stage.addActor(table);
    }

    public static void updateScore(int score){
        scoreLabel.setText(String.format("%04d",score));

    }

    public void perdVie(int life){
        this.life -= life;
        lifeLabel.setText(String.format("%02d", this.life));
    }

    public void setVie(int life){
        this.life = life;
        lifeLabel.setText(String.format("%02d", this.life));
    }
    public int getVie(){
        return life;
    }

    public void setNiveau(int niveau){
        this.niveau = niveau;
        niveauLabel.setText(String.format("%02d", niveau));
    }
    @Override
    public void dispose(){
        stage.dispose();
    }
}
