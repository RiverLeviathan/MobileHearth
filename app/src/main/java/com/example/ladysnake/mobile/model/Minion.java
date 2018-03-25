package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

/**
 * Created by Ludwig on 25/03/2018.
 */

public class Minion extends Card {
    protected int damage, health;

    public Minion(@NonNull String name, @NonNull Integer manaCost, @NonNull String description, @NonNull String imgUrl, @NonNull Integer damage, @NonNull Integer health) {
        super(name, manaCost, description, imgUrl);

        this.damage = damage;
        this.health = health;
    }

    public int getDamage() { return damage; }
    public int getHealth() { return health; }

    @NonNull
    public static Minion from(@NonNull String name, @NonNull Integer manaCost, @NonNull String description, @NonNull String imgUrl, @NonNull Integer damage, @NonNull Integer health){
        return new Minion(name, manaCost, description, imgUrl, damage, health);
    }
}
