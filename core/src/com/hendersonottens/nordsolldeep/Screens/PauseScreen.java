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
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    private ButtonGroup inventoryTabs = new ButtonGroup();
    protected TextButton systemButton;
    protected TextButton inventoryButton;
    protected TextButton partyButton;
    protected List systemList;
    protected Table inventoryTable;
    protected List itemsList = new List(GameRoot.gameSkin);
    protected List weaponsList = new List(GameRoot.gameSkin);
    protected List tomesList = new List(GameRoot.gameSkin);
    protected List armorList = new List(GameRoot.gameSkin);
    protected List consumablesList = new List(GameRoot.gameSkin);
    protected List keyItemsList = new List(GameRoot.gameSkin);
    protected List partyList = new List(GameRoot.gameSkin);
    //protected Container container;
    protected Container inventoryContainer;

    //inventory tab buttons;
    protected TextButton items;
    protected TextButton weapons;
    protected TextButton armor;
    protected TextButton consumables;
    protected TextButton tomes;
    protected TextButton keyItems;

    public PauseScreen(GameScreen screen, Game aGame, Inventory theInventory){
        //keep references to previous screen, the game, and the inventory taken from player
        prevScreen = screen;
        game = aGame;
        inventory = theInventory;

        //create stage on which all the UI will be
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        //inventory container should allow us to switch between our sub-inventory lists
        inventoryContainer = new Container();
        inventoryContainer.fill();


        //horizontalGroup holds main tabs, meaning Inventory, party, and system
        HorizontalGroup horizontalGroup = new HorizontalGroup();
        //horizontalGroup.setDebug(true);
        horizontalGroup.setBounds(0, Gdx.graphics.getHeight()-100, 800, 100);
        GameRoot.gameSkin.getFont("subtitle").getData().setScale(0.5f);
        inventoryButton = new TextButton("Inventory", GameRoot.gameSkin);
        partyButton = new TextButton("Party", GameRoot.gameSkin);
        systemButton = new TextButton("System", GameRoot.gameSkin);

        //GameRoot.gameSkin.getFont("subtitle").getData().setScale(0.25f);
        items = new TextButton("Items", GameRoot.gameSkin);
        weapons = new TextButton("Weapons", GameRoot.gameSkin);
        armor = new TextButton("Armor", GameRoot.gameSkin);
        consumables = new TextButton("Consumables", GameRoot.gameSkin);
        tomes = new TextButton("Tomes", GameRoot.gameSkin);
        keyItems = new TextButton("Key Items", GameRoot.gameSkin);

        systemList = new List(GameRoot.gameSkin);
        systemList.setBounds(0, -100, 800, 260);
        inventoryTable = new Table(GameRoot.gameSkin);
        inventoryTable.setBounds(0, -100, stage.getViewport().getScreenWidth(), stage.getViewport().getScreenHeight());
        Array<String> systemListItems = new Array();
        systemListItems.add("Options", "Quit Game");

        systemList.setItems(systemListItems);
        itemsList.setItems(inventory.items);

        weaponsList.setItems(inventory.weapons);

        armorList.setItems(inventory.armor);

        tomesList.setItems(inventory.tomes);

        consumablesList.setItems(inventory.consumables);

        keyItemsList.setItems(inventory.keyItems);

        systemList.setFillParent(true); //i hope the parent is the container

        HorizontalGroup horizontalGroup2 = new HorizontalGroup();
        horizontalGroup2.setDebug(true);
        inventoryTable.setVisible(false);
        inventoryTable.setFillParent(true);
        inventoryTable.top();

        systemButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mainTabs.setChecked("System");
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

        items.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                inventoryTabs.setChecked("Items");
            }
        });

        weapons.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                inventoryTabs.setChecked("Weapons");
            }
        });

        armor.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                inventoryTabs.setChecked("Armor");
            }
        });

        consumables.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                inventoryTabs.setChecked("Consumables");
            }
        });

        tomes.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               inventoryTabs.setChecked("Tomes");
           }
        });

        keyItems.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                inventoryTabs.setChecked("Key Items");
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
        inventoryTabs.add(items, weapons, armor, consumables, tomes, keyItems);
        inventoryTabs.setChecked("Items");
        inventoryTabs.setMinCheckCount(1);
        inventoryTabs.setMaxCheckCount(1);
        inventoryTabs.setUncheckLast(true);
//        horizontalGroup2.addActor(items);
//        horizontalGroup2.addActor(weapons);
//        horizontalGroup2.addActor(armor);
//        horizontalGroup2.addActor(consumables);
//        horizontalGroup2.addActor(tomes);
//        horizontalGroup2.addActor(keyItems);
        inventoryTable.setDebug(true);
        inventoryTable.add(items).minWidth(stage.getViewport().getScreenWidth()/6f);
        inventoryTable.add(weapons).minWidth(stage.getViewport().getScreenWidth()/6f);
        inventoryTable.add(consumables).minWidth(stage.getViewport().getScreenWidth()/6f);
        inventoryTable.add(tomes).minWidth(stage.getViewport().getScreenWidth()/6f);
        inventoryTable.add(keyItems).minWidth(stage.getViewport().getScreenWidth()/6f);
        inventoryTable.add(armor).minWidth(stage.getViewport().getScreenWidth()/6f);
        inventoryTable.row();
        inventoryTable.add(inventoryContainer).fill().colspan(6).minHeight(stage.getViewport().getScreenHeight()/2f);
        stage.addActor(horizontalGroup);
        stage.addActor(systemList);
        stage.addActor(inventoryTable);
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
            inventoryTable.setVisible(false);
        }
        if(mainTabs.getChecked() == inventoryButton){
            systemList.setVisible(false);
            inventoryTable.setVisible(true);
            if(inventoryTabs.getChecked() == items){
                inventoryContainer.setActor(itemsList);
            }
            else if(inventoryTabs.getChecked() == weapons){
                inventoryContainer.setActor(weaponsList);
            }
            else if(inventoryTabs.getChecked() == armor){
                inventoryContainer.setActor(armorList);
            }
            else if(inventoryTabs.getChecked() == consumables){
                inventoryContainer.setActor(consumablesList);
            }
            else if(inventoryTabs.getChecked() == tomes){
                inventoryContainer.setActor(tomesList);
            }
            else if(inventoryTabs.getChecked() == keyItems){
                inventoryContainer.setActor(keyItemsList);
            }
        }
        if(mainTabs.getChecked() == partyButton) {
            systemList.setVisible(false);
            inventoryTable.setVisible(false);
        }
        if(systemList.getPressedItem() == "Quit Game"){
            System.exit(0);
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
