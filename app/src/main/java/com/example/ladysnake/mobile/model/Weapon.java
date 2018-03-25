package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

/**
 * Created by Ludwig on 25/03/2018.
 */

public class Weapon extends Card {
    protected int damage, durability;

    public Weapon(@NonNull String name, @NonNull Integer manaCost, @NonNull String description, @NonNull String imgUrl, @NonNull Integer damage, @NonNull  Integer durability) {
        super(name, manaCost, description, imgUrl);

        this.damage = damage;
        this.durability = durability;
    }

    public int getDamage() { return damage; }
    public int getDurability() { return durability; }

    @NonNull
    public static Weapon from(@NonNull String name, @NonNull Integer manaCost, @NonNull String description, @NonNull String imgUrl, @NonNull Integer damage, @NonNull  Integer durability){
        return new Weapon(name, manaCost, description, imgUrl, damage, durability);
    }
}
