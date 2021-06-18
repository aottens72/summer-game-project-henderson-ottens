package com.hendersonottens.nordsolldeep;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.physics.box2d.Body;

public class Player {
    //texture atlas allows processing of a sprite sheet
    public TextureAtlas playerMovement = new TextureAtlas("player_animations.atlas");
    //atlas for combat animation
    public TextureAtlas combatMovement = new TextureAtlas("player_combat_animations.atlas");
    //an animation for idle player
    public Animation<TextureRegion> idleAnimation;
    //animation played during combat
    public Animation<TextureRegion> combatAnimation;
    //array of which parts of sprite sheet are a part of the idle animation
    private String[] IDLE = new String[] {"01", "02", "03", "04"};
    private TextureRegion movementFrame;
    //player sprite
    public Sprite sprite;
    //player body for collisions and movement
    public Body playerBody;
    //player health
    public int currHP;
    //player maximum health
    public int MAX_HP;
    //player level
    public int currLevel;
    //player experience points
    public int currXP;


    public Player(Sprite aSprite){
        movementFrame = playerMovement.findRegion("01");

        //makes a texture region for the frames of the idle animation
        TextureRegion[] idleFrames = new TextureRegion[IDLE.length];
        TextureRegion[] idleCombatFrames = new TextureRegion[IDLE.length];
        //iterates through the images that are part of idle animation
        for (int i = 0; i < IDLE.length ; i++){
            String pathIdle = IDLE[i];
            String pathCombatIdle = IDLE[i];
            idleFrames[i] = playerMovement.findRegion(pathIdle);
            idleCombatFrames[i] = combatMovement.findRegion(pathCombatIdle);
        }
        //creates the animation with timing for how long each frame lasts
        idleAnimation = new Animation<TextureRegion>(1/2f,idleFrames);
        combatAnimation = new Animation<TextureRegion>(1/2f, idleCombatFrames);
        //set sprite to passed in sprite
        sprite = aSprite;
        //body is set in GameScreen
        //starting HP currently 50
        currHP = 50;
        MAX_HP = 50;
        //start at level 1 with no experience
        currLevel = 1;
        currXP = 0;
    }

    public void idleAnimation(Batch batch, float time){
        batch.draw(idleAnimation.getKeyFrame(time, true), playerBody.getPosition().x - 20, playerBody.getPosition().y - 15, sprite.getWidth(), sprite.getHeight());
    }

    public void movementAnimation(Batch batch){
        batch.draw(movementFrame, playerBody.getPosition().x -20, playerBody.getPosition().y - 15, sprite.getWidth(), sprite.getHeight());
    }

    public void combatIdleAnimation(Batch batch, float time){
        batch.draw(combatAnimation.getKeyFrame(time, true), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }
}
