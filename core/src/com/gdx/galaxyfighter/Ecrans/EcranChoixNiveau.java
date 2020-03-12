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

public class EcranChoixNiveau implements Screen {
    
    //Definition des differents attributs (Bouttons, titres, skin ...)

    private GalaxyFighter parent;

    private Stage stage;                      
    private Skin skin;
    private Label choix1;
    private Label choix2;
    private Label choix3;

    private Table table1;
    private TextButton level1;
    private TextButton next1;

    private Table table2;
    private TextButton level2;
    private TextButton previous2;
    private TextButton next2;

    private Table table3;
    private TextButton level3;
    private TextButton previous3;

    public EcranChoixNiveau(GalaxyFighter jeu){
        parent = jeu;

        stage = new Stage(new FitViewport(GalaxyFighter.V_WIDTH, GalaxyFighter.V_HEIGHT, new OrthographicCamera()), parent.batch);
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1/30f));
        stage.draw();

        skin = new Skin(Gdx.files.internal("skin/skin1/cloud-form-ui.json"));
        table1 = new Table();
        table1.setFillParent(true);
        //table1.setDebug(true);
        level1 = new TextButton("Niveau 1", skin);
        next1 = new TextButton("Suivant", skin);
        choix1 = new Label("Choix du Niveau", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table1.top();
        table1.add(choix1).padTop(10);
        table1.row();
        table1.add(level1).padTop(140);
        table1.row();
        table1.add(next1).padTop(140).padLeft(140);

        table2 = new Table();
        table2.setFillParent(true);
        //table2.setDebug(true);
        level2 = new TextButton("Niveau 2", skin);
        previous2 = new TextButton("Precedent", skin);
        next2 = new TextButton("Suivant", skin);
        choix2 = new Label("Choix du Niveau", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table2.top();
        table2.add(choix2).padTop(10).fillX().padLeft(60);
        table2.row();
        table2.add(level2).padTop(140).fillX().padLeft(60);
        table2.row().padTop(140);
        table2.add(previous2).fillX().padRight(60);
        table2.add(next2).fillX();

        table3 = new Table();
        table3.setFillParent(true);
        //table3.setDebug(true);
        level3 = new TextButton("Niveau 3", skin);
        previous3 = new TextButton("Precedent", skin);
        choix3 = new Label("Choix du Niveau", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table3.top();
        table3.add(choix3).padTop(10);
        table3.row();
        table3.add(level3).padTop(140);
        table3.row();
        table3.add(previous3).padTop(150).padRight(100);

        stage.addActor(table1);
    }

    @Override
    public void show() {
       next1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.clear();
                stage.addActor(table2);
                stage.draw();
                if(parent.bruitage)
                    parent.button.play();
            }
        });

       previous2.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               stage.clear();
               stage.addActor(table1);
               stage.draw();
               if(parent.bruitage)
                   parent.button.play();
           }
       });
       next2.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               stage.clear();
               stage.addActor(table3);
               stage.draw();
               if(parent.bruitage)
                   parent.button.play();
           }
       });

       previous3.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               stage.clear();
               stage.addActor(table2);
               stage.draw();
               if(parent.bruitage)
                   parent.button.play();
           }
       });

       level1.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               parent.niveau = 1;
               if(parent.bruitage)
                   parent.button.play();
               if (parent.musiqueB)
                   parent.musiqueMenu.stop();
               parent.changeScreen(GalaxyFighter.JEU);
           }
       });

       level2.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
                parent.niveau = 2;
               if(parent.bruitage)
                   parent.button.play();
               if (parent.musiqueB)
                   parent.musiqueMenu.stop();
                parent.changeScreen(GalaxyFighter.JEU);
           }
       });

       level3.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               parent.niveau = 3;
               if(parent.bruitage)
                   parent.button.play();
               if (parent.musiqueB)
                   parent.musiqueMenu.stop();
                parent.changeScreen(GalaxyFighter.JEU);
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
