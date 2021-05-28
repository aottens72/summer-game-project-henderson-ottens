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


//GameScreen contains all player movement and interactions
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
    private Body playerBody;
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    //private MapObjects objects;
    World world = new World(new Vector2(0, 0), true);

    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        //load in map from .tmx file created from Tiled
        map = new TmxMapLoader().load("maps/map1.tmx");

        //create renderer with reference to the map
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        //the map has a collision layer that contains objects indicating walls
        collisionLayer = map.getLayers().get("Collision Layer");
        //objects = collisionLayer.getObjects();

        //camera gives user vision
        //batch allows sprites to be drawn on the map
        camera = new OrthographicCamera();
        batch = new SpriteBatch();

        //create player sprite and player object
        Sprite sprite = new Sprite(new Texture("player.png"));
        sprite.setBounds(64, 608, 32, 32);
        player = new Player(sprite);

        //ArrayList of bodies on the collision layer
        Array<Body> bodies = new Array<>();
        loadBodies(collisionLayer, bodies);
        CollisionListener listener = new CollisionListener();
        world.setContactListener(listener);
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,1,1);
    }

    //loadBodies function takes in the object layer that contains the bodies needed and the list to place them in
    public void loadBodies(MapLayer objects, Array<Body> bodies) {

        //def and shape will be used and adjusted to create boxes for all objects on the layer
        BodyDef def = new BodyDef();
        PolygonShape shape = new PolygonShape();

        //for each loop gets all objects from layer and creates their collision bodies
        for (MapObject object : objects.getObjects()) {
            //check if the object is a rectangle object, as these are the walls
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();

                def.position.x = (rect.x + rect.width / 2);
                def.position.y = (rect.y + rect.height / 2);
                def.type = BodyDef.BodyType.StaticBody;

                shape.setAsBox(rect.width / 2, rect.height / 2);

                bodies.add(world.createBody(def));
                bodies.get(bodies.size - 1).createFixture(shape, 0);
            }
        }
        //player object contains a rectangle that encapsulates the player
        Rectangle rect = player.rectangle;

        def.position.x = (rect.x + rect.width / 2);
        def.position.y = (rect.y + rect.height / 2);
        def.type = BodyDef.BodyType.DynamicBody;

        shape.setAsBox(rect.width / 2 , rect.height / 2 );

        playerBody = world.createBody(def);
        playerBody.setUserData(player.rectangle);
        bodies.add(playerBody);
        bodies.get(bodies.size - 1).createFixture(shape, 0);
    }
    public class CollisionListener implements ContactListener {
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
            //playerBody.setTransform(player.rectangle.x, player.rectangle.y, 0);
            //playerBody.setAwake(true);
            //if player body comes in contact with another body we want it to stop it's movement
            playerBody.setLinearVelocity(0f, 0f);
            System.out.print("collision");
        }
    };

    //takes in reference to camera, moves spirte and camera upon button presses
    //called in the render function, the main loop
    private void cameraController(Camera aCamera){
        Vector2 pos = playerBody.getPosition();

        // apply left-down impulse
        if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)){
            playerBody.applyLinearImpulse(-0.80f, -0.80f, pos.x, pos.y, true);
        }
        //apply left-up impulse
        else if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)){
            playerBody.applyLinearImpulse(-0.80f, 0.80f, pos.x, pos.y, true);
        }
        //apply right-down impulse
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)){
            playerBody.applyLinearImpulse(0.80f, -0.80f, pos.x, pos.y, true);
        }
        //apply right-up impulse
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)){
            playerBody.applyLinearImpulse(0.80f, 0.80f, pos.x, pos.y, true);
        }
        //apply left impulse
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerBody.applyLinearImpulse(-0.80f, 0, pos.x, pos.y, true);
        }
        // apply right impulse
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerBody.applyLinearImpulse(0.80f, 0, pos.x, pos.y, true);
        }
        // apply up impulse
        else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerBody.applyLinearImpulse(0, 0.80f, pos.x, pos.y, true);
        }
        // apply down impulse
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerBody.applyLinearImpulse(0, -0.80f, pos.x, pos.y, true);
        }
        //stop movement if nothing pressed
        else{
            playerBody.setLinearVelocity(0f, 0f);
        }

        //set camera location to where the playerBody aka the collision box is and update
        aCamera.position.set(playerBody.getPosition().x, playerBody.getPosition().y, 0);
        aCamera.update();
    }

    @Override
    public void render(float delta) {
        //clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //debug renderer is used to see where collision boxes are
        //debugRenderer.render(world, camera.combined);

        //update world
        world.step(1/60f, 6, 2);
        //give player vision
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
        camera.update();
        //flag is initialized to true so that this if statement only runs the first time render is called
        if(flag){
            flag = false;
            camera.position.set(250, 500, 0);
            camera.update();
        }

        //move player sprite and rectangle based on the position of its body
        player.rectangle.setX(playerBody.getPosition().x);
        player.rectangle.setY(playerBody.getPosition().y);
        player.sprite.setPosition(playerBody.getPosition().x - 20, playerBody.getPosition().y - 15);
        //draw sprites
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
