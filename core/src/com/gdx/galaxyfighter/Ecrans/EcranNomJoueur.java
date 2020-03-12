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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdx.galaxyfighter.GalaxyFighter;
import com.gdx.galaxyfighter.Tools.Score;

public class EcranNomJoueur implements Screen {
    private GalaxyFighter parent;

    private Stage stage;
    private Table table;
    private Skin skin;

    private TextField textField;
    private TextButton continuer;
    private TextButton retour;

    private boolean continu;
    private Label error;

    public EcranNomJoueur(GalaxyFighter jeu){
        parent = jeu;

        stage = new Stage(new FitViewport(GalaxyFighter.V_WIDTH, GalaxyFighter.V_HEIGHT, new OrthographicCamera()), parent.batch);
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("skin/skin1/cloud-form-ui.json"));
        textField = new TextField("", skin);
        retour = new TextButton("Retour", skin);
        continuer = new TextButton("Continuer", skin);
        error = new Label("Entrez un pseudo !", new Label.LabelStyle(new BitmapFont(), Color.RED));

        table.top();
        table.add(textField).padTop(100).fillX();
        table.row();
        table.add(continuer).padTop(130).fillX();
        table.row();
        table.add(retour).padTop(40).fillX();

        parent.joueur = new Score();
    }

    @Override
    public void show() {
        continuer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (parent.bruitage)
                    parent.button.play();
                if (!continu){
                    stage.clear();
                    table.clear();
                    table.top();
                    table.add(textField).padTop(100).fillX();
                    table.row();
                    table.add(error).fillX().padTop(10).padLeft(10);
                    table.row();
                    table.add(continuer).padTop(100).fillX();
                    table.row();
                    table.add(retour).padTop(40).fillX();
                    stage.addActor(table);
                    stage.draw();
                }
                else {
                    parent.joueur.setScore(0);
                    parent.joueur.setPseudo(textField.getText());
                    parent.changeScreen(GalaxyFighter.CHOIXNIVEAU);
                }
            }
        });

        retour.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(parent.bruitage)
                    parent.button.play();
                parent.changeScreen(GalaxyFighter.MENU);
            }
        });
    }

    public void text(){
        parent.joueur.setPseudo(textField.getText());
        if (parent.joueur.getPseudo().length() < 1){
            continu = false;
        }
        else
            continu = true;
    }

    @Override
    public void render(float delta) {
        text();

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
