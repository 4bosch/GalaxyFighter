package com.gdx.galaxyfighter.Ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdx.galaxyfighter.GalaxyFighter;


public class EcranOption implements Screen {
    //On definit les attribut dont on aura besoin
    private GalaxyFighter parent;

    private Stage stage;
    private Table table;
    private Skin skin;
    private Label optionTitre; //L'ecriture Option en titre
    private Label musiqueTXT; //L'ecriture Musique
    private Label vibrationTXT; //L'ecriture Vibration

    private CheckBox musique; //Checkbox qui va servir à activer/desactiver la musique
    private CheckBox vibration; //Checkbox qui va servir à activer/desactiver les vibrations
    private TextButton retour; // le boutton retour

    public EcranOption(GalaxyFighter jeu){
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
        musiqueTXT = new Label("Musique", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        vibrationTXT = new Label("Vibration", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        musique = new CheckBox("" ,skin, "default");
        vibration = new CheckBox("", skin, "default");
        optionTitre = new Label("Options", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        retour = new TextButton("Retour", skin);

        // Position des differents elements sur l'ecran
        table.top();
        table.padTop(10);
        table.add(optionTitre).padLeft(60);
        table.row().padTop(100);
        table.add(musiqueTXT);
        table.add(musique).height(25).width(25);
        table.row().padTop(50);
        table.add(vibrationTXT);
        table.add(vibration).height(25).width(25);
        table.row().padTop(100);
        table.add(retour).padLeft(60);
    }

    @Override
    public void show() {
        if (parent.musiqueB)
            musique.setChecked(true); 
        if (parent.vibrationB)
            vibration.setChecked(true);
        retour.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(parent.bruitage)
                    parent.button.play();
                parent.changeScreen(GalaxyFighter.MENU);
            }
        });
        musique.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (musique.isChecked()){
                    parent.musiqueB = true;
                    parent.musiqueMenu.play();
                    System.out.println("Musique on");
                }
                else {
                    parent.musiqueB = false;
                    parent.musiqueMenu.stop();
                    System.out.println("Musique off");
                }
            }
        });
        vibration.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (vibration.isChecked()){
                    parent.vibrationB = true;
                }
                else {
                    parent.vibrationB = false;
                }
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
