package com.gdx.galaxyfighter.Ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdx.galaxyfighter.GalaxyFighter;

public class EcranScore implements Screen {
	//Définition des attributs
    private GalaxyFighter parent;

    private Stage stage;
    private Table table;
    private Skin skin;
    private TextButton retour;

    private Label topscore;
    private Label score1;
    private Label score2;
    private Label score3;
    private Label score4;
    private Label score5;
    private Label score6;
    private Label score7;
    private Label score8;
    private Label score9;

    public EcranScore(GalaxyFighter jeu){
        parent = jeu;
        //On définit la taille de la caméra 
        stage = new Stage(new FitViewport(GalaxyFighter.V_WIDTH, GalaxyFighter.V_HEIGHT, new OrthographicCamera()), parent.batch);
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);
        //On recupere le pseudo ainsi que le score  et on l'attribut à une variable 

        skin = new Skin(Gdx.files.internal("skin/skin1/cloud-form-ui.json"));
        topscore = new Label("Top Scores", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score1 = new Label(String.format("1. %10s : %d",parent.topScore.getiScore(0).getPseudo(), parent.topScore.getiScore(0).getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score2 = new Label(String.format("2. %10s : %d",parent.topScore.getiScore(1).getPseudo(), parent.topScore.getiScore(1).getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score3 = new Label(String.format("3. %10s : %d",parent.topScore.getiScore(2).getPseudo(), parent.topScore.getiScore(2).getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score4 = new Label(String.format("4. %10s : %d",parent.topScore.getiScore(3).getPseudo(), parent.topScore.getiScore(3).getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score5 = new Label(String.format("5. %10s : %d",parent.topScore.getiScore(4).getPseudo(), parent.topScore.getiScore(4).getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score6 = new Label(String.format("6. %10s : %d",parent.topScore.getiScore(5).getPseudo(), parent.topScore.getiScore(5).getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score7 = new Label(String.format("7. %10s : %d",parent.topScore.getiScore(6).getPseudo(), parent.topScore.getiScore(6).getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score8 = new Label(String.format("8. %10s : %d",parent.topScore.getiScore(7).getPseudo(), parent.topScore.getiScore(7).getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        score9 = new Label(String.format("9. %10s : %d",parent.topScore.getiScore(8).getPseudo(), parent.topScore.getiScore(8).getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        retour = new TextButton("Retour", skin);
        //On definit la position des differents éléments sur l'ecran
        table.top().padTop(10);
        table.add(topscore);
        table.row().padTop(30);
        table.add(score1);
        table.row().padTop(10);
        table.add(score2);
        table.row().padTop(10);
        table.add(score3);
        table.row().padTop(10);
        table.add(score4);
        table.row().padTop(10);
        table.add(score5);
        table.row().padTop(10);
        table.add(score6);
        table.row().padTop(10);
        table.add(score7);
        table.row().padTop(10);
        table.add(score8);
        table.row().padTop(10);
        table.add(score9);
        table.row().padTop(20);
        table.add(retour).fillX();
    }

    @Override
    public void show() {
        retour.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	//Changement d'ecran
                if(parent.bruitage)
                    parent.button.play();
                parent.changeScreen(GalaxyFighter.MENU);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        parent.batch.setProjectionMatrix(stage.getCamera().combined);
        parent.batch.begin();
        parent.batch.draw(parent.background, 0, 0);
        parent.batch.end();
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
