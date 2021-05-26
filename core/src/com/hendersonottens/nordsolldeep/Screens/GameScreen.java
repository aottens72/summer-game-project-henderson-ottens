package com.hendersonottens.nordsolldeep.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hendersonottens.nordsolldeep.Player;

public class GameScreen implements Screen {
    private Stage stage;
    private Game game;
    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private boolean flag = true;
    private SpriteBatch batch;
    //private Sprite sprite;
    private Player player;
    private MapLayer collisionLayer;
    //private MapObjects objects;
    World world = new World(new Vector2(0, -10), true);

    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());


        map = new TmxMapLoader().load("maps/map1.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
        collisionLayer = map.getLayers().get("Collision Layer");
        //objects = collisionLayer.getObjects();
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        Sprite sprite = new Sprite(new Texture("player.png"));
        sprite.setBounds(64, 608, 32, 32);
        player = new Player(sprite);

        Array<Body> bodies = new Array<>();
        loadBodies(collisionLayer, bodies);
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,1,1);
    }
    public void loadBodies(MapLayer objects, Array<Body> bodies) {
        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();

        for (MapObject object : objects.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                def.position.x = (rect.x + rect.width / 2) / 32;
                def.position.y = (rect.y + rect.height / 2) / 32;
                def.type = BodyDef.BodyType.StaticBody;

                shape.setAsBox(rect.width / 2 / 32, rect.height / 2 / 32);

                bodies.add(world.createBody(def));
                bodies.get(bodies.size - 1).createFixture(shape, 0);
            }
        }
        Rectangle rect = player.rectangle;

        def.position.x = (rect.x + rect.width / 2) / 32;
        def.position.y = (rect.y + rect.height / 2) / 32;
        def.type = BodyDef.BodyType.DynamicBody;

        shape.setAsBox(rect.width / 2 / 32, rect.height / 2 / 32);

        bodies.add(world.createBody(def));
        bodies.get(bodies.size - 1).createFixture(shape, 0);
    }
    public class ListenerClass implements ContactListener {
        @Override
        public void endContact(Contact contact) {

        }

        @Override
        public void preSolve(Contact contact, Manifold oldManifold) {

        }

        @Override
        public void postSolve(Contact contact, ContactImpulse impulse) {

        }

        @Override
        public void beginContact(Contact contact) {
            player.rectangle.setPosition(player.sprite.getX(), player.sprite.getY());
        }
    };
    private void cameraController(Camera aCamera){

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player.rectangle.setY(player.rectangle.y+32);
            /*for(RectangleMapObject object : objects.getByType(RectangleMapObject.class)){
                if(player.rectangle.overlaps(object.getRectangle())){
                    player.rectangle.setPosition(player.sprite.getX(), player.sprite.getY());
                    return;
                }
            }*/
            aCamera.translate(0, 32, 0);
            player.moveUp();
            aCamera.update();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player.rectangle.setY(player.rectangle.y-32);
            /*for(RectangleMapObject object : objects.getByType(RectangleMapObject.class)){
                if(player.rectangle.overlaps(object.getRectangle())){
                    player.rectangle.setPosition(player.sprite.getX(), player.sprite.getY());
                    return;
                }
            }*/
            aCamera.translate(0, -32, 0);
            player.moveDown();
            aCamera.update();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player.rectangle.setX(player.rectangle.x-32);
            /*for(RectangleMapObject object : objects.getByType(RectangleMapObject.class)){
                if(player.rectangle.overlaps(object.getRectangle())){
                    player.rectangle.setPosition(player.sprite.getX(),player.sprite.getY());
                    return;
                }
            }*/
            aCamera.translate(-32, 0, 0);
            player.moveLeft();
            aCamera.update();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.rectangle.setX(player.rectangle.x+32);
            /*for(RectangleMapObject object : objects.getByType(RectangleMapObject.class)){
                if(player.rectangle.overlaps(object.getRectangle())){
                    player.rectangle.setPosition(player.sprite.getX(), player.sprite.getY());
                    return;
                }
            }*/
            aCamera.translate(32, 0, 0);
            player.moveRight();
            aCamera.update();
        }
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        if(flag){
            flag = false;
            camera.position.set(250, 500, 0);
            camera.update();
        }
        batch.begin();
        player.sprite.draw(batch);
        cameraController(camera);
        batch.end();

        //camera.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
        map.dispose();
        tiledMapRenderer.dispose();
    }
}
