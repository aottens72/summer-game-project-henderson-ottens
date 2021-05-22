package com.hendersonottens.nordsolldeep.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hendersonottens.nordsolldeep.Map;

public class GameScreen implements Screen {
    private Stage stage;
    private Game game;
    private Map map;
    public GameScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());
        //stage.addActor(map);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

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
