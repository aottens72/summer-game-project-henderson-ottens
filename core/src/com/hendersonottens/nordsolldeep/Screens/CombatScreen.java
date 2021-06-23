package com.hendersonottens.nordsolldeep.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hendersonottens.nordsolldeep.GameRoot;
import com.hendersonottens.nordsolldeep.Player;

import static com.badlogic.gdx.utils.Align.left;

public class CombatScreen implements Screen {

    private Game game;
    private GameScreen prevScreen;
    private Stage stage;
    private List attackList;
    private List bagList;
    protected Player player;
    private float deltaTime = 0f;

    public CombatScreen(GameScreen screen, Game aGame, Player thePlayer){
        prevScreen = screen;
        game = aGame;
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        player = thePlayer;
        TextButton attackButton = new TextButton("Attack", GameRoot.gameSkin);
        TextButton defendButton = new TextButton("Defend", GameRoot.gameSkin);
        TextButton bagButton = new TextButton("Bag", GameRoot.gameSkin);
        TextButton fleeButton = new TextButton("Run", GameRoot.gameSkin);

        attackButton.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                attackList.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(toActor != attackList)
                    attackList.setVisible(false);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        defendButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                System.out.println("clicked");
            }
        });

        bagButton.addListener(new ClickListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                bagList.setVisible(true);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                if(toActor != bagList)
                    bagList.setVisible(false);
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        fleeButton.addListener(new ClickListener(){

            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(prevScreen);
            }
        });
        GameRoot.gameSkin.getFont("subtitle").getData().setScale(0.5f, 0.5f);
        attackList = new List(GameRoot.gameSkin);
        Array<String> attackListItems = new Array();
        attackListItems.add("Attack", "Defend");
        attackList.setItems(attackListItems);
        attackList.setVisible(false);
        attackList.getStyle().font = GameRoot.gameSkin.getFont("font");
        bagList = new List(GameRoot.gameSkin);
        bagList.getStyle().font = GameRoot.gameSkin.getFont("font");

        Array<String> bagListItems = new Array();
        bagList.setItems(player.inventory.consumables);
        bagList.setVisible(false);
        Table table = new Table();
        //table.setFillParent(true);
        table.setDebug(true);
        table.add(attackList).colspan(2).minWidth(600/2f).padLeft(100f);
        table.add(bagList).colspan(2).minWidth(600/2f).padRight(100f);
        table.row();
        table.add(attackButton).minWidth(600/4f).prefHeight(25).padLeft(100f);
        table.add(defendButton).minWidth(600/4f).prefHeight(25);
        table.add(bagButton).minWidth(600/4f).prefHeight(25);
        table.add(fleeButton).minWidth(600/4f).prefHeight(25).padRight(100f);
        table.bottom().left();
        stage.addActor(table);

    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player.sprite.setPosition(650, 240);
        Texture background = new Texture("background/battle-background-sunny-hillsx1.png");
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, 800, 480);
        player.combatIdleAnimation(stage.getBatch(), deltaTime);
        deltaTime += Gdx.graphics.getDeltaTime();
        stage.getBatch().end();
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
