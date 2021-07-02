package com.hendersonottens.nordsolldeep;

import com.hendersonottens.nordsolldeep.Player;
import com.hendersonottens.nordsolldeep.GenericEnemy;

import java.util.Random;

public class Combat{

    public boolean attackFlag = false;
    public boolean defendFlag = false;
    public boolean bagFlag = false;

    private Player player;
    private GenericEnemy enemy;

    public Combat(Player thePlayer, GenericEnemy theEnemy){
        player = thePlayer;
        enemy = theEnemy;
        combatLoop();
    }

    public void playerTurn(){
        while(!attackFlag && !defendFlag && !bagFlag){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("stuck here");
        }
        if(attackFlag){
            System.out.println("Entered attack, things are going well");
        } else if (defendFlag){

        } else if(bagFlag){

        }
    }
    public void enemyTurn(){
        System.out.println("in enemyTurn");
    }
    public void combatLoop() {
        while (player.currHP != 0 || enemy.curr_hp != 0) {
            if (enemy.speedStat > player.speedStat) {

            } else if (player.speedStat > enemy.speedStat) {

            } else {
                if (Math.random() % 2 == 0) {
                    playerTurn();
                    enemyTurn();
                } else {
                    enemyTurn();
                    playerTurn();
                }
            }
        }
    }
}
