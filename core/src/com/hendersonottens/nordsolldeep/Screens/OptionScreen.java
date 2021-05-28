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


//options menu screen
//mostly empty and not used currently, to be finished later
public class OptionScreen implements Screen {
    private Stage stage;
    private Game game;

    public OptionScreen(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        //Options label in upper third of screen
        Label options = new Label("Options", GameRoot.gameSkin, "subtitle");
        options.setAlignment(Align.center);
        options.setY(Gdx.graphics.getHeight()*2/3);
        options.setWidth(Gdx.graphics.getWidth());
        stage.addActor(options);

        //music label above slider
        Label music = new Label("Music: ", GameRoot.gameSkin, "default");
        music.setX(50);
        music.setY((float) (Gdx.graphics.getHeight()/1.75));
        stage.addActor(music);

        //music volume slider from 0 to 100
        //should add a visual component to see where the volume is numerically
        Slider musicSlider = new Slider(0, 100, 1, false, GameRoot.gameSkin);
        musicSlider.setWidth(Gdx.graphics.getWidth()-50);
        musicSlider.setY((float) (Gdx.graphics.getHeight()/2.5));
        stage.addActor(musicSlider);

        //back button that sends you back to title screen via input listener
        TextButton backButton = new TextButton("BACK", GameRoot.gameSkin);
        backButton.setWidth(300);
        backButton.setHeight(100);
        backButton.setPosition(50, (float) Gdx.graphics.getHeight()/7);
        //input listener disposes current screen and sets it to the title screen
        //might be changed to previous screen, for now this is the only previous screen
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent e, float x, float y, int pointer, int button) {
                game.getScreen().dispose();
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
        //clear screen
        Gdx.gl.glClearColor(1,1,1,1);
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
