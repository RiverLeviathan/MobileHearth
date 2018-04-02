package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

/**
 * Created by Ludwig on 25/03/2018.
 */

public class Weapon extends Card {
    public final static String DAMAGE = "attack";
    public final static String DURABILITY = "durability";

    protected int damage, durability;

    public Weapon(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull Integer damage, @NonNull  Integer durability) {
        super(id, name, manaCost, imgUrl);

        this.damage = damage;
        this.durability = durability;
    }

    public int getDamage() { return damage; }
    public int getDurability() { return durability; }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty(DAMAGE, getDamage());
        obj.addProperty(DURABILITY, getDurability());
        obj.addProperty(Card.TYPE, CardFactory.CardType.WEAPON.toString());
        return obj;
    }

    @NonNull
    public static Weapon from(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull Integer damage, @NonNull  Integer durability){
        return new Weapon(id, name, manaCost, imgUrl, damage, durability);
    }
}
