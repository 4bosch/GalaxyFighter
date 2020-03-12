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

public class EcranMenuPrincipal implements Screen {

    private GalaxyFighter parent;

    private Stage stage;
    private Table table;
    private Skin skin;

    private Label titre;
    private TextButton jouer; //On définit le boutton Jouer
    private TextButton options; //On définit le boutton Options
    private TextButton score; //On définit le boutton Score
    private TextButton quitter; //On définit le boutton Quitter

    public EcranMenuPrincipal(GalaxyFighter jeu) {
        this.parent = jeu;

        stage = new Stage(new FitViewport(GalaxyFighter.V_WIDTH, GalaxyFighter.V_HEIGHT, new OrthographicCamera()), parent.batch);
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        skin = new Skin(Gdx.files.internal("skin/skin1/cloud-form-ui.json"));                           //On donne la forme du boutton
        titre = new Label("Galaxy Fighter", new Label.LabelStyle(new BitmapFont(), Color.WHITE));       //On donne la couleur, la police du titre du jeu
        jouer = new TextButton("Jouer", skin);                                                          //Le bouton Jouer aura la forme d'un boutton skin
        options = new TextButton("Options", skin);                                                      //Le bouton Options aura la forme d'un boutton skin
        score = new TextButton("Score", skin);                                                          //Le bouton Score aura la forme d'un boutton skin
        quitter = new TextButton("Quitter", skin);                                                     //Le boutton Quitter aura la forme d'un boutton skin

        table.top();
        table.add(titre).center().padTop(20);                                    //Definit la place du widget Titre
        table.row();                                                             //On descend d'une ligne
        table.add(jouer).fillX().uniformX().width(150).padTop(60);               //Definit la place du widget(le boutton) Jouer
        table.row();                                                             //On descend d'une ligne
        table.add(score).fillX().uniformX().padTop(30);                          //Definit la place du widget(le boutton) Score
        table.row();                                                             //On descend d'une ligne
        table.add(options).fillX().uniformX().padTop(30);                        //Definit la place du widget(le boutton) Options
        table.row();                                                             //On descend d'une ligne
        table.add(quitter).fillX().uniformX().padTop(80);                        //Definit la place du widget(le boutton) Quitter
        if (parent.musiqueB)
            parent.musiqueMenu.play();                                           //Permet de faire jouer la musique destiné au menu
    }


    @Override
    public void show(){
        jouer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(parent.bruitage)
                    parent.button.play();
                parent.changeScreen(GalaxyFighter.NOMJOUEUR);
            }
        });

        options.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(parent.bruitage)
                    parent.button.play();
                parent.changeScreen(GalaxyFighter.OPTION);
            }
        });

        score.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(parent.bruitage)
                    parent.button.play();
                parent.changeScreen(GalaxyFighter.SCORE);
            }
        });

        quitter.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.topScore.save_prefs();
                if(parent.bruitage)
                    parent.button.play();
                Gdx.app.exit();
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
    public void resize(int width, int height){
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
