package com.hendersonottens.nordsolldeep.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hendersonottens.nordsolldeep.GameRoot;

public class CombatScreen implements Screen {

    private Game game;
    private GameScreen prevScreen;
    private Stage stage;

    public CombatScreen(GameScreen screen, Game aGame){
        prevScreen = screen;
        game = aGame;
        stage = new Stage(new ScreenViewport());
        Image image = new Image(new Texture("menu-font-raw/pixthulhu.png"));
        Image image2 = new Image(new Texture("menu-font-raw/portrait.png"));
        SplitPane pane = new SplitPane(image, image2, true, GameRoot.gameSkin);

        stage.addActor(pane);
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
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
