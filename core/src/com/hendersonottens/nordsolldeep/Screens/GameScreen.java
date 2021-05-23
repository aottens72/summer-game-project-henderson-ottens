package com.hendersonottens.nordsolldeep.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
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

    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());




        map = new TmxMapLoader().load("map1.tmx");

        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,1,1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
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

    }
}
