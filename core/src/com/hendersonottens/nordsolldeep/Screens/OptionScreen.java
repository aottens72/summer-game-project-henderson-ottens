package com.hendersonottens.nordsolldeep.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hendersonottens.nordsolldeep.GameRoot;

public class OptionScreen implements Screen {
    private Stage stage;
    private Game game;

    public OptionScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Label options = new Label("Options", GameRoot.gameSkin, "subtitle");
        options.setAlignment(Align.center);
        options.setY(Gdx.graphics.getHeight()*2/3);
        options.setWidth(Gdx.graphics.getWidth());
        stage.addActor(options);

        Label music = new Label("Music: ", GameRoot.gameSkin, "default");
        music.setX(50);
        music.setY((float) (Gdx.graphics.getHeight()/1.75));
        stage.addActor(music);

        Slider musicSlider = new Slider(0, 100, 1, false, GameRoot.gameSkin);
        musicSlider.setWidth(Gdx.graphics.getWidth()-50);
        musicSlider.setY((float) (Gdx.graphics.getHeight()/2.5));
        stage.addActor(musicSlider);

        TextButton backButton = new TextButton("BACK", GameRoot.gameSkin);
        backButton.setWidth(300);
        backButton.setHeight(100);
        backButton.setPosition(50, (float) Gdx.graphics.getHeight()/7);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button) {
                Screen curScreen = game.getScreen();
                curScreen.dispose();
                game.setScreen(new TitleScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent e, float x, float y, int pointer, int button){
                return true;
            }
        });
        stage.addActor(backButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
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
        stage.dispose();
    }
}
