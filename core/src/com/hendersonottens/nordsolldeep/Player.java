package com.hendersonottens.nordsolldeep;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Player {
    public Sprite sprite;
    public Rectangle rectangle;

    public Player(Sprite aSprite){
        sprite = aSprite;
        rectangle = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

    public void moveUp(){
        sprite.setPosition(sprite.getX(), sprite.getY()+32);
    }

    public void moveDown(){
        sprite.setPosition(sprite.getX(), sprite.getY()-32);
    }

    public void moveLeft(){
        sprite.setPosition(sprite.getX()-32,sprite.getY());
    }

    public void moveRight(){
        sprite.setPosition(sprite.getX()+32, sprite.getY());
    }
}
