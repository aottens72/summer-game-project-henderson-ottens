package com.hendersonottens.nordsolldeep;

import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;

public class Inventory {

    public Array<String> items;
    public Array<String> weapons;
    public Array<String> armor;
    public Array<String> consumables;
    public Array<String> tomes;
    public Array<String> keyItems;

    public Inventory(){
        items = new Array<>(false, 99);
        weapons = new Array<>(false, 99);
        armor = new Array<>(false, 99);
        consumables = new Array<>(false, 99);
        tomes = new Array<>(false, 99);
        keyItems = new Array<>(false, 99);
    }

    public void add(String item, String type){
        if(type == "item"){
            items.add(item);
        }
        else if(type == "weapon"){
            weapons.add(item);
        }
        else if(type == "armor"){
            armor.add(item);
        }
        else if(type == "consumable"){
            consumables.add(item);
        }
        else if(type == "tome"){
            tomes.add(item);
        }
        else
            keyItems.add(item);
    }


}
