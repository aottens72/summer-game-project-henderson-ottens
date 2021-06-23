package com.hendersonottens.nordsolldeep.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hendersonottens.nordsolldeep.GameRoot;

public class TitleScreen implements Screen{
    private Stage stage;
    private Game game;

    public TitleScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        //game title label placed in the upper third of the screen
        Label title = new Label("Nordsoll Deep", GameRoot.gameSkin,"title");
        title.setAlignment(Align.center);
        title.setY(Gdx.graphics.getHeight()*2/3);
        title.setWidth(Gdx.graphics.getWidth());
        stage.addActor(title);

        //play button that will send player to the GameScreen when pressed
        TextButton playButton = new TextButton("Play!",GameRoot.gameSkin);
        playButton.setWidth(Gdx.graphics.getWidth()/2);
        playButton.setPosition(Gdx.graphics.getWidth()/2-playButton.getWidth()/2,Gdx.graphics.getHeight()/2-playButton.getHeight()/2);

        //Input listener disposes current screen and sets it to the appropriate one
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //maybe send to a screen where the player can select new game or continue, or have these buttons on the title screen instead of just play
                game.getScreen().dispose();
                game.setScreen(new GameScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);

        //option button with input listener
        TextButton optionsButton = new TextButton("Options",GameRoot.gameSkin);
        optionsButton.setWidth(Gdx.graphics.getWidth()/2);
        optionsButton.setPosition(Gdx.graphics.getWidth()/2-optionsButton.getWidth()/2,Gdx.graphics.getHeight()/4-optionsButton.getHeight()/2);

        //input listener disposes current screen and sets it to the appropriate one
        optionsButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.getScreen().dispose();
                game.setScreen(new OptionScreen(game));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(optionsButton);

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        //clear screen, teal color for improved aesthetic
        Gdx.gl.glClearColor(0, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw everything on stage
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
