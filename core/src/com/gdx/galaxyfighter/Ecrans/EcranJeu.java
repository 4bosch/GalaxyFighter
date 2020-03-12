package com.gdx.galaxyfighter.Ecrans;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gdx.galaxyfighter.GalaxyFighter;
import com.badlogic.gdx.math.Rectangle;
import com.gdx.galaxyfighter.Scenes.Hud;

import java.util.Iterator;

public class EcranJeu implements Screen {
    private GalaxyFighter jeu;

    private Texture spaceshipTex;
    private Texture big_asteroidTex;
    private Texture medium_asteroidTex;
    private Texture mini_asteroidTex;
    private Texture big_bonus_scoreTex;
    private Texture medium_bonus_scoreTex;
    private Texture mini_bonus_scoreTex;
    private Texture piste_finTex;

    private Music musiqueJeu;
    private Sound bonusSound;

    private static final float BASTEW = 14.0f;
    private static final float BASTEH = 14.0f;
    private static final float MASTEW = 12.0f;
    private static final float MASTEH = 12.0f;
    private static final float MIASTEW = 9.0f;
    private static final float MIASTEH = 8.0f;
    private static final float FINH = 56f;

    private static final float BSBONUSW = 13.0f;
    private static final float BSBONUSH = 13.0f;
    private static final float MSBONUSW = 9.0f;
    private static final float MSBONUSH = 9.0f;
    private static final float MISBONUSW = 5.0f;
    private static final float MISBONUSH = 5.0f;
    private static final float FINW = 56f;

    private static final float SHIPW = 19;
    private static final float SHIPH = 20;

    private Vector3 probaAsteroid;
    private Vector3 probaScorebonus;

    private OrthographicCamera camera;
    private FitViewport gamePort;
    private Hud hud;

    private Array<Rectangle> drops;
    private long lastDrop;
    public Rectangle spaceship;
    private Rectangle piste_fin;

    private Skin skin;
    private Touchpad touchpad;
    private Stage stage;
    private Table table;

    private float distance;
    private int niveau;

    public EcranJeu(GalaxyFighter jeu, Vector3 probaAsteroid, Vector3 probaScorebonus, int niveau) {
        //On conserve un copie du jeu afin de pouvoir enchainer apres la fin du niveau sans recreer
        //tous les ecrans deja existants
        this.jeu = jeu;

        //Creation des differentes textures requises
        big_asteroidTex = new Texture(Gdx.files.internal("sprites/big_asteroid.png"));
        medium_asteroidTex = new Texture(Gdx.files.internal("sprites/medium_asteroid.png"));
        mini_asteroidTex = new Texture(Gdx.files.internal("sprites/mini_asteroid.png"));
        big_bonus_scoreTex = new Texture(Gdx.files.internal("sprites/big_bonus_score.png"));
        medium_bonus_scoreTex = new Texture(Gdx.files.internal("sprites/medium_bonus_score.png"));
        mini_bonus_scoreTex = new Texture(Gdx.files.internal("sprites/mini_bonus_score.png"));
        spaceshipTex = new Texture(Gdx.files.internal("sprites/redship1.png"));
        piste_finTex = new Texture(Gdx.files.internal("sprites/piste_fin.png"));

        //Creation de la camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GalaxyFighter.V_WIDTH, GalaxyFighter.V_HEIGHT);

        //Creation d'un fitviewport permettant de garder la meme zone affiche peut importe la resolution
        //de l'appareil utilise
        gamePort = new FitViewport(GalaxyFighter.V_WIDTH, GalaxyFighter.V_HEIGHT, camera);

        //Creation du hud
        hud = new Hud(jeu);
        hud.setNiveau(niveau);

        //Creation du Rectangle gerant le vaisseau
        spaceship = new Rectangle();

        //Position de depart du vaisseau
        spaceship.x = GalaxyFighter.V_WIDTH / 2;
        spaceship.y = 20;

        //Taille du sprite du vaisseau 14x15 pixel
        spaceship.height = SHIPW;
        spaceship.width = SHIPH;

        //Creation du Rectangle du la piste final
        piste_fin = new Rectangle();
        piste_fin.height = FINH;
        piste_fin.width = FINW;
        piste_fin.x = GalaxyFighter.V_WIDTH / 2 - 28;
        piste_fin.y = GalaxyFighter.V_HEIGHT + 64;

        //Creation d'un tableau contenant les objets tombants
        drops = new Array<Rectangle>();

        //On ajoute un gros asteroide par default
        spawnObject(BASTEW, BASTEH);

        //On recupere les differentes probabilites d'apparition des objets
        this.probaAsteroid = probaAsteroid;
        this.probaScorebonus = probaScorebonus;
        this.probaScorebonus.x += probaAsteroid.z;
        this.probaScorebonus.y += probaAsteroid.z;
        this.probaScorebonus.z += probaAsteroid.z;

        //On cree un stage pour les controles du vaisseau
        stage = new Stage(new FitViewport(GalaxyFighter.V_WIDTH, GalaxyFighter.V_HEIGHT, new OrthographicCamera()), jeu.batch);
        //On indique que le stage sera interactif
        Gdx.input.setInputProcessor(stage);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        //On initialise le skin pour les boutons et les differents boutons de controles
        skin = new Skin(Gdx.files.internal("skin/skin2/tracer-ui.json"));

        touchpad = new Touchpad(0, skin);

        //On cree une table qui contiendra tout nos boutons afin de les ordenancer facilement
        table = new Table();
        table.bottom();
        table.setFillParent(true);
        //table.setDebug(true);

        //On ajoute les boutons a la table
        table.add(touchpad).padBottom(10).width(80).height(80).padRight(GalaxyFighter.V_WIDTH / 2 + 20);

        //On ajoute la table au stage
        stage.addActor(table);

        //On initialise la distance a 0
        distance = 0;

        //On recupere le niveau afin d'ajuster la difficulte par la suite
        this.niveau = niveau;

        //On change le nombre de vies selon la difficulte
        switch (niveau) {
            case 1:
                hud.setVie(12);
                break;
            case 2:
                hud.setVie(6);
                break;
        }
        musiqueJeu = Gdx.audio.newMusic(Gdx.files.internal("musics_n_sounds/game_music.mp3"));
        if (jeu.musiqueB) {
            musiqueJeu.setLooping(true);
            musiqueJeu.setVolume(0.5f);
            musiqueJeu.play();
        }
        bonusSound = Gdx.audio.newSound(Gdx.files.internal("musics_n_sounds/sounds/bonus.mp3"));
    }

    @Override
    public void show() {
        //On gere les mouvements avec un joystick qui bouge sur un axe -1:1 en x et y
        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float deltaX = ((Touchpad) actor).getKnobPercentX();
                float deltaY = ((Touchpad) actor).getKnobPercentY();
                if (deltaX > 0)
                    spaceship.x += 400 * Gdx.graphics.getDeltaTime() * Math.abs(deltaX);
                if (deltaX < 0)
                    spaceship.x -= 400 * Gdx.graphics.getDeltaTime() * Math.abs(deltaX);
                if (deltaY > 0)
                    spaceship.y += 400 * Gdx.graphics.getDeltaTime() * Math.abs(deltaY);
                if (deltaY < 0)
                    spaceship.y -= 400 * Gdx.graphics.getDeltaTime() * Math.abs(deltaY);
            }
        });
    }

    //Cette methode gere les input du joueur
    private void handleInput(float dt) {
        int velocity = 400;

        //Gere les deplacements du vaisseau au clavier
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) spaceship.x -= velocity * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) spaceship.x += velocity * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) spaceship.y += velocity * dt;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) spaceship.y -= velocity * dt;
    }

    //Cette methode permet de limiter le joueur a ce que voit la camera afin qu'il ne soit pas hors champ
    private void limitPlayer() {
        if (spaceship.x > GalaxyFighter.V_WIDTH - spaceship.width)
            spaceship.x = GalaxyFighter.V_WIDTH - spaceship.width;
        if (spaceship.x < 0)
            spaceship.x = 0;
        if (spaceship.y > GalaxyFighter.V_HEIGHT - spaceship.height)
            spaceship.y = GalaxyFighter.V_HEIGHT - spaceship.height;
        if (spaceship.y < 0)
            spaceship.y = 0;
    }

    //Cette methode permet de faire apparaitre un objet dans le tableau d'objet
    private void spawnObject(float width, float height) {
        Rectangle obj = new Rectangle();
        obj.width = width;
        obj.height = height;
        obj.x = MathUtils.random(0, GalaxyFighter.V_WIDTH - obj.width);
        obj.y = GalaxyFighter.V_HEIGHT;
        drops.add(obj);
        lastDrop = TimeUtils.nanoTime();
    }

    //Cette methode permet de faire tomber les objets et de les supprimer une fois arriver en bas de l'ecran
    private void objectFall(float dt, boolean end) {
        if (end == false) {
            for (Iterator<Rectangle> iter = drops.iterator(); iter.hasNext(); ) {
                Rectangle obj = iter.next();
                //On utilise un vitesse differente selon le niveau de difficulte
                switch (niveau) {
                    case 1:
                        obj.y -= 200 * dt;
                        break;
                    case 2:
                        obj.y -= 300 * dt;
                        break;
                    case 3:
                        obj.y -= 400 * dt;
                        break;
                }

                if (obj.y + 64 < 0)
                    iter.remove();
                collision(obj, iter);
            }
        }
        else {
            if (piste_fin.y > 250) {
                piste_fin.y -= 100 * dt;
            }
            for (Iterator<Rectangle> iter = drops.iterator(); iter.hasNext();){
                Rectangle obj = iter.next();
                obj.y -= 100 * dt;
                collision(obj, iter);
            }
            if (piste_fin.overlaps(spaceship)) {
                if (jeu.musiqueB)
                    musiqueJeu.stop();
                this.dispose();
                jeu.changeScreen(GalaxyFighter.GAGNE);
            }
        }
    }

    //Cette methode genere de facon aleatoire les differents objets en tenant compte de leurs differentes probabilites
    private void randomDrop() {
        int random = (int) (Math.random() * (probaScorebonus.z + probaAsteroid.z) + 1);
        if (0 <= random && random <= probaAsteroid.x)
            spawnObject(BASTEW, BASTEH);
        if (probaAsteroid.x < random && random <= probaAsteroid.y)
            spawnObject(MASTEW, MASTEH);
        if (probaAsteroid.y < random && random <= probaAsteroid.z)
            spawnObject(MIASTEW, MIASTEH);
        if (probaAsteroid.z < random && random <= probaScorebonus.x)
            spawnObject(BSBONUSW, BSBONUSH);
        if (probaScorebonus.x < random && random <= probaScorebonus.y)
            spawnObject(MSBONUSW, MSBONUSH);
        if (probaScorebonus.y < random && random <= probaScorebonus.z)
            spawnObject(MISBONUSW, MISBONUSH);
    }

    //Cette methode permet d'identifer chaque objet grace a leur dimension et renvoie la texture adequate
    private Texture selectObject(Rectangle rect) {
        if (rect.height == BASTEH && rect.width == BASTEW)
            return big_asteroidTex;
        if (rect.height == MASTEH && rect.width == MASTEW)
            return medium_asteroidTex;
        if (rect.height == MIASTEH && rect.width == MIASTEW)
            return mini_asteroidTex;
        if (rect.height == BSBONUSH && rect.width == BSBONUSW)
            return big_bonus_scoreTex;
        if (rect.height == MSBONUSH && rect.width == MSBONUSW)
            return medium_bonus_scoreTex;
        if (rect.height == MISBONUSH && rect.width == MISBONUSW)
            return mini_bonus_scoreTex;
        return null;
    }

    //Cette methode gere le rendu de tous les objets contenus dans drops
    private void renderObject() {
        for (int i = 0; i < drops.size; i++) {
            jeu.batch.draw(selectObject(drops.get(i)), drops.get(i).x, drops.get(i).y);
        }
        jeu.batch.draw(piste_finTex, piste_fin.x, piste_fin.y);
     }

    //Cette methode gere les effets des differentes collisions entre les objets et le vaisseau
    private void effectOfCollision(Rectangle obj){
        if ((obj.width == BASTEW) && (obj.height == BASTEH)){
            hud.perdVie(3);
            if (jeu.vibrationB)
                Gdx.input.vibrate(500);
        }
        if ((obj.width == MASTEW) && (obj.height == MASTEH)){
            hud.perdVie(2);
            if (jeu.vibrationB)
                Gdx.input.vibrate(500);
        }
        if ((obj.width == MIASTEW) && (obj.height == MIASTEH)){
            hud.perdVie(1);
            if (jeu.vibrationB)
                Gdx.input.vibrate(500);
        }
        if ((obj.width == BSBONUSW) && (obj.height == BSBONUSH)){
            jeu.joueur.addScore(10000);
            hud.updateScore(jeu.joueur.getScore());
            if(jeu.bruitage)
                bonusSound.play();
            if (jeu.vibrationB)
                Gdx.input.vibrate(200);
        }
        if ((obj.width == MSBONUSW) && (obj.height == MSBONUSH)){
            jeu.joueur.addScore(1000);
            hud.updateScore(jeu.joueur.getScore());
            if(jeu.bruitage)
                bonusSound.play();
            if (jeu.vibrationB)
                Gdx.input.vibrate(200);
        }
        if ((obj.width == MISBONUSW) && (obj.height == MISBONUSH)){
            jeu.joueur.addScore(100);
            hud.updateScore(jeu.joueur.getScore());
            if(jeu.bruitage)
                bonusSound.play();
            if (jeu.vibrationB)
                Gdx.input.vibrate(200);
        }
    }

    //Cette methode detecte la collision entre un objet et le vaisseau et applique les effets qui en decoulent
    private void collision(Rectangle obj, Iterator<Rectangle> iter){
        if(obj.overlaps(spaceship)){
            effectOfCollision(obj);
            iter.remove();
        }
    }

    private void spawn(float dt){
        switch (niveau) {
            case 1:
                if (TimeUtils.nanoTime() - lastDrop > 500000000) {
                    randomDrop();
                    jeu.joueur.addScore(2);
                    hud.updateScore(jeu.joueur.getScore());
                }
                break;
            case 2:
                if (TimeUtils.nanoTime() - lastDrop > 300000000){
                    randomDrop();
                    jeu.joueur.addScore(2);
                    hud.updateScore(jeu.joueur.getScore());
                }
                break;
            case 3:
                if (TimeUtils.nanoTime() - lastDrop > 100000000) {
                    randomDrop();
                    jeu.joueur.addScore(2);
                    hud.updateScore(jeu.joueur.getScore());
                }
                break;
        }
        distance += dt;
    }

    //Cette methode regroupe les methodes d'update
    private void update(float dt){
        limitPlayer();
        handleInput(dt);
        camera.update();
    }

    //Cette methode provient de la LIBGDX et s'execute toutes les 0.015s. Elle se charge de tout ce qui est rendu
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        if ((int)distance < 30){
            spawn(delta);
            objectFall(delta, false);
        }
        else {
            objectFall(delta, true);
        }

        //On arrete la partie si l'on a plus de vie
        if (hud.getVie() <= 0) {
            if (jeu.musiqueB)
                musiqueJeu.stop();
            jeu.changeScreen(GalaxyFighter.GAMEOVER);
        }
        update(delta);
        jeu.batch.setProjectionMatrix(camera.combined);
        //On ajoute toutes les textures qui doivent etre rendu
        jeu.batch.begin();
        jeu.batch.draw(jeu.background, 0, 0);
        renderObject();
        jeu.batch.draw(spaceshipTex, spaceship.x, spaceship.y);
        jeu.batch.end();

        jeu.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        //On fait le rendu du hud
        hud.stage.draw();
        //On fait le rendu des controles du vaisseau
        stage.draw();
    }

    //Cette methode provient de la LIBGDX et est execute lorsque la fenetre change de resolution
    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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

    //Cette methode provient de LIBGDX et est execute a la fin du programme, elle permet de supprimer les elements de la memoire
    @Override
    public void dispose() {
        hud.dispose();
        big_asteroidTex.dispose();
        medium_asteroidTex.dispose();
        mini_asteroidTex.dispose();
        big_bonus_scoreTex.dispose();
        medium_bonus_scoreTex.dispose();
        mini_bonus_scoreTex.dispose();
        musiqueJeu.dispose();
        stage.dispose();
    }
}
