package com.hendersonottens.nordsolldeep.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hendersonottens.nordsolldeep.Combat;
import com.hendersonottens.nordsolldeep.GameRoot;
import com.hendersonottens.nordsolldeep.GenericEnemy;
import com.hendersonottens.nordsolldeep.Player;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;

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

        final Combat combat = new Combat(player, new GenericEnemy());

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
                if(attackList.getSelected() == "Sword Slash"){
                    combat.attackFlag = true;
                }
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
        attackListItems.add("Sword Slash", "Shield Bash");
        attackList.setItems(attackListItems);
        attackList.setVisible(false);
        attackList.getStyle().font = GameRoot.gameSkin.getFont("font");
        bagList = new List(GameRoot.gameSkin);
        bagList.getStyle().font = GameRoot.gameSkin.getFont("font");

        Array<String> partyListItems = new Array<>();
        partyListItems.add("Player\t\t" + player.currHP + "/" + player.MAX_HP);
        List partyList = new List(GameRoot.gameSkin);
        partyList.setItems(partyListItems);


//        (GameRoot.gameSkin.get("health", ProgressBarStyle.class)).knobBefore = GameRoot.gameSkin.getTiledDrawable("progress-bar-health-knob");
//        ProgressBar playerHealthBar = new ProgressBar(0.0f, player.MAX_HP, 1.0f, false, GameRoot.gameSkin, "health");
//        playerHealthBar.setValue(0);
//        playerHealthBar.updateVisualValue();
        //playerHealthBar.setFillParent(true);

        Array<String> bagListItems = new Array();
        bagList.setItems(player.inventory.consumables);
        bagList.setVisible(false);
        Table table = new Table();
        //table.setFillParent(true);
        //table.setDebug(true);
        table.add(attackList).colspan(2).minWidth(600/2f);
        table.add(bagList).colspan(2).minWidth(600/2f);
        table.row();
        table.add(attackButton).minWidth(600/4f).prefHeight(25);
        table.add(defendButton).minWidth(600/4f).prefHeight(25);
        table.add(bagButton).minWidth(600/4f).prefHeight(25);
        table.add(fleeButton).minWidth(600/4f).prefHeight(25);
        table.bottom().left();
        stage.addActor(table);

        partyList.setBounds(Gdx.graphics.getWidth()/1.7f,0, Gdx.graphics.getWidth()/2.5f, Gdx.graphics.getHeight()/3f);
        stage.addActor(partyList);
    }

    @Override
    public void show() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player.sprite.setBounds(Gdx.graphics.getWidth()*.9f, Gdx.graphics.getHeight()/2f, 50, 50);
        Texture background = new Texture("background/battle-background-sunny-hillsx1.png");
        stage.act();
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
