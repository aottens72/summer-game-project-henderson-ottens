package com.hendersonottens.nordsolldeep.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hendersonottens.nordsolldeep.GameRoot;
import com.hendersonottens.nordsolldeep.Inventory;


import static com.badlogic.gdx.scenes.scene2d.InputEvent.Type.exit;


public class PauseScreen implements Screen {

    private Game game;
    private GameScreen prevScreen;
    private Inventory inventory;
    private Stage stage;
    private ButtonGroup mainTabs = new ButtonGroup();
    protected TextButton systemButton;
    protected TextButton inventoryButton;
    protected TextButton partyButton;
    protected List systemList;

    public PauseScreen(GameScreen screen, Game aGame, Inventory theInventory){
        prevScreen = screen;
        game = aGame;
        inventory = theInventory;
        stage = new Stage(new ScreenViewport());
        Container container = new Container();
        container.fill();
        container.setBounds(0, 0, 800, 360);
        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.setDebug(true);
        horizontalGroup.setBounds(0, 370, 800, 100);
        GameRoot.gameSkin.getFont("subtitle").getData().setScale(0.5f);
        inventoryButton = new TextButton("Inventory", GameRoot.gameSkin);
        partyButton = new TextButton("Party", GameRoot.gameSkin);
        systemButton = new TextButton("System", GameRoot.gameSkin);
        systemList = new List(GameRoot.gameSkin);
        Array<String> systemListItems = new Array();
        systemListItems.add("Options", "Quit Game");
        systemList.setItems(systemListItems);
        container.setActor(systemList);
        systemList.setVisible(false);
        systemList.setFillParent(true); //i hope the parent is the container


        systemButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTabs.setChecked("System");
                systemButton.setDisabled(true);
            }
        });
        partyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTabs.setChecked("Party");
            }
        });
        inventoryButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTabs.setChecked("Inventory");
            }
        });

        mainTabs.setMaxCheckCount(1);
        mainTabs.add(inventoryButton, partyButton, systemButton);
        mainTabs.setChecked("Inventory");
        mainTabs.setMinCheckCount(1);
        mainTabs.setUncheckLast(true);
        horizontalGroup.addActor(inventoryButton);
        horizontalGroup.addActor(partyButton);
        horizontalGroup.addActor(systemButton);
        stage.addActor(horizontalGroup);
        stage.addActor(container);
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            game.setScreen(prevScreen);
        }
        if(mainTabs.getChecked() == systemButton){
            systemList.setVisible(true);
        }
        if(mainTabs.getChecked() == inventoryButton){
            systemList.setVisible(false);
        }
        if(mainTabs.getChecked() == partyButton) {
            systemList.setVisible(false);
        }
        if(systemList.getPressedItem() == "Quit Game"){
            System.exit(0);
        }
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
