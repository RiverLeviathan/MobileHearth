package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

/**
 * A model that describes a hero card
 * @author Ludwig GUERIN
 */
public class Hero extends CardWithHealth {
    public Hero(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull Integer health) {
        super(id, name, manaCost, imgUrl, health);
    }

    public static Hero from(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull Integer health){
        return new Hero(id, name, manaCost, imgUrl, health);
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty(Card.TYPE, CardFactory.CardType.HERO.toString());
        return obj;
    }
}
