package com.hendersonottens.nordsolldeep;

import com.badlogic.gdx.graphics.g2d.Batch;


public interface Enemy {
    public void idleAnimation(Batch batch, float time);
    public void combatAnimation(Batch batch, float time);
    public void movementAnimation(Batch batch, float time);
}
