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
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.hendersonottens.nordsolldeep.Player;


//GameScreen contains all player movement and interactions
public class GameScreen implements Screen {
    //our GameRoot
    //allows for changing of screens, essentially the core of the game
    private Game game;

    //map is the current map being displayed
    private TiledMap map;

    //camera and tiledMapRenderer gives user vision
    private OrthographicCamera camera = new OrthographicCamera();
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private boolean flag = true;

    //batch allows sprites to be drawn on the map
    private SpriteBatch batch = new SpriteBatch();


    protected Player player;
    private MapLayer collisionLayer;
    //body should probably be part of player object
    //private Body playerBody;

    //enemy sprite, temp
    //will make an Enemy class and likely have a list of them
    //each enemy will have health, damage, sprite, etc.
    //Player objects will be similar
    private Sprite enemySprite;
    //protected Enemy enemy;
    //temp, needs to eventually allow for more enemies
    private Body enemyBody;

    //debug renderer shows bodies, helps with tuning
    private Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
    World world = new World(new Vector2(0, 0), true);

    public GameScreen(Game aGame) {
        game = aGame;

        //load in map from .tmx file created from Tiled
        map = new TmxMapLoader().load("maps/map1.tmx");

        //create renderer with reference to the map
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        //the map has a collision layer that contains objects indicating walls
        collisionLayer = map.getLayers().get("Collision Layer");

        //create player sprite and player object
        enemySprite = new Sprite(new Texture("enemy.png"));
        enemySprite.setBounds(500, 608, 32, 32);
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
    //bodies are what allow for our collisions
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

        //create a body rectangle around sprite
        def.position.x = (player.sprite.getX() + player.sprite.getWidth() / 2);
        def.position.y = (player.sprite.getY() + player.sprite.getHeight() / 2);
        def.type = BodyDef.BodyType.DynamicBody;

        shape.setAsBox(player.sprite.getWidth() / 2 , player.sprite.getHeight() / 2 );

        player.playerBody = world.createBody(def);
        bodies.add(player.playerBody);
        bodies.get(bodies.size - 1).createFixture(shape, 0);

        //enemy now needs to have a collision box
        def.position.x = (enemySprite.getX() + enemySprite.getWidth() / 2);
        def.position.y = (enemySprite.getY() + enemySprite.getHeight() /2);
        def.type = BodyDef.BodyType.KinematicBody;


        shape.setAsBox(enemySprite.getWidth()/2, enemySprite.getHeight()/2);

        enemyBody = world.createBody(def);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        enemyBody.createFixture(fixtureDef);
        bodies.add(enemyBody);


        shape.dispose();

    }

    //CollisionListener allows us to check for wall or enemy collisions, soon to be more
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
            //if player body comes in contact with another body we want it to stop it's movement
            player.playerBody.setLinearVelocity(0f, 0f);
            System.out.print("collision");

            //once player stops moving we want to check what the collision was with
            //this is done using sensors
            //unsure which fixture is which, so both are checked
            if(contact.getFixtureA().isSensor() || contact.getFixtureB().isSensor()){
                //collision with a sensor, which is only enemy right now so switch to combat screen
                //would like to create and run an animation once this occurs

                //eventually there may be more sensors than just enemies, requiring different interactions
                //think about how this may be done (thinking specific info tied to fixtures, an identifier)

                //store screen for return post-combat
                Screen currScreen = game.getScreen();
                //dispose
                //crashes when this line is uncommented, unsure why
                //we could use this to our advantage however
                //not disposing the underlying game screen when switching to combat means we may not have to pass in the "currScreen" reference
                //to the CombatScreen constructor
                //game.getScreen().dispose();
                //transition to new screen
                game.setScreen(new CombatScreen((GameScreen) currScreen, game));
            }
        }
    };

    //takes in reference to camera, moves sprite and camera upon button presses
    //called in the render function, the main loop
    private void cameraController(Camera aCamera){
        Vector2 pos = player.playerBody.getPosition();

        // apply left-down impulse
        if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)){
            player.playerBody.applyLinearImpulse(-0.80f, -0.80f, pos.x, pos.y, true);
        }
        //apply left-up impulse
        else if(Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)){
            player.playerBody.applyLinearImpulse(-0.80f, 0.80f, pos.x, pos.y, true);
        }
        //apply right-down impulse
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)){
            player.playerBody.applyLinearImpulse(0.80f, -0.80f, pos.x, pos.y, true);
        }
        //apply right-up impulse
        else if(Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)){
            player.playerBody.applyLinearImpulse(0.80f, 0.80f, pos.x, pos.y, true);
        }
        //apply left impulse
        else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.playerBody.applyLinearImpulse(-0.80f, 0, pos.x, pos.y, true);
        }
        // apply right impulse
        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.playerBody.applyLinearImpulse(0.80f, 0, pos.x, pos.y, true);
        }
        // apply up impulse
        else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.playerBody.applyLinearImpulse(0, 0.80f, pos.x, pos.y, true);
        }
        // apply down impulse
        else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.playerBody.applyLinearImpulse(0, -0.80f, pos.x, pos.y, true);
        }
        //stop movement if nothing pressed
        else{
            player.playerBody.setLinearVelocity(0f, 0f);
        }

        //set camera location to where the playerBody aka the collision box is and update
        aCamera.position.set(player.playerBody.getPosition().x, player.playerBody.getPosition().y, 0);
        aCamera.update();
    }

    @Override
    public void render(float delta) {
        //clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //debug renderer is used to see where collision boxes are
        //comment out unless in use
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
        player.sprite.setPosition(player.playerBody.getPosition().x - 20, player.playerBody.getPosition().y - 15);

        //draw sprites
        batch.begin();
        enemySprite.draw(batch);
        player.sprite.draw(batch);
        cameraController(camera);
        batch.end();

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
