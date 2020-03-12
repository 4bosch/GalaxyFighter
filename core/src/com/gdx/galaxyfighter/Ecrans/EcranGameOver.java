package com.gdx.galaxyfighter.Ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
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

public class EcranGameOver implements Screen {
    private GalaxyFighter parent;

    private Stage stage;
    private Table table;
    private Skin skin;
    private Label gameover;
    private Label score;
    private TextButton retourMenu;
    private Sound gameOverSound;

    public EcranGameOver(GalaxyFighter jeu){
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
        gameover = new Label("Game Over !", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        this.score = new Label(String.format("Score = %06d", jeu.joueur.getScore()), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        retourMenu = new TextButton("Retour au Menu ", skin);

        table.top();
        table.add(gameover).padTop(10).padLeft(15).fillX();
        table.row();
        table.add(this.score).padTop(90);
        table.row();
        table.add(retourMenu).padTop(200);

        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("musics_n_sounds/sounds/gameover.mp3"));
        if (parent.musiqueB)
            gameOverSound.play();
        parent.topScore.addScore(parent.joueur);
    }

    @Override
    public void show() {
        if (parent.musiqueB)
            gameOverSound.play();
        retourMenu.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
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
        gameOverSound.dispose();
    }
}
