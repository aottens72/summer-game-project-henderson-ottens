package com.hendersonottens.nordsolldeep;

import com.badlogic.gdx.graphics.g2d.Batch;

public class GenericEnemy implements Enemy{

    public int curr_hp;
    public int MAX_HP;
    public int attackStat;
    public int defenseStat;
    public int speedStat;

    public GenericEnemy(){
        curr_hp = 50;
        MAX_HP = 50;
        attackStat = 10;
        defenseStat = 10;
        speedStat = 10;
    }

    @Override
    public void idleAnimation(Batch batch, float time) {

    }

    @Override
    public void combatAnimation(Batch batch, float time) {

    }

    @Override
    public void movementAnimation(Batch batch, float time) {

    }
}
