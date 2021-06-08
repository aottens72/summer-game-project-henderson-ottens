package com.hendersonottens.nordsolldeep;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;

public class Player {

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



}
