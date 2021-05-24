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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private Stage stage;
    private Game game;
    private TiledMap map;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private boolean flag = true;
    private SpriteBatch batch;
    private Sprite sprite;

    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());


        map = new TmxMapLoader().load("maps/map1.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("player.png"));
        sprite.setBounds(50, 610, 32, 32);
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,1,1);
    }
    private void cameraController(Camera aCamera, Sprite playerSprite){

        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            aCamera.translate(0, 10, 0);
            playerSprite.setPosition(playerSprite.getX(), playerSprite.getY()+20);
            aCamera.update();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            aCamera.translate(0, -10, 0);
            playerSprite.setPosition(playerSprite.getX(), playerSprite.getY()-20);
            aCamera.update();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            aCamera.translate(-10, 0, 0);
            playerSprite.setPosition(playerSprite.getX() -20, playerSprite.getY());
            aCamera.update();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            aCamera.translate(10, 0, 0);
            playerSprite.setPosition(playerSprite.getX()+20, playerSprite.getY());
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
        sprite.draw(batch);
        cameraController(camera, sprite);
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
