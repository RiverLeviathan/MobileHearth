package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

/**
 * A model that describes a spell card
 * @author Ludwig GUERIN
 */
public class Spell extends DescribedCard {

    public Spell(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description) {
        super(id, name, manaCost, imgUrl, description);
    }

    @NonNull
    public static Spell from(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description){
        return new Spell(id, name, manaCost, imgUrl, description);
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty(Card.TYPE, CardFactory.CardType.SPELL.toString());
        return obj;
    }
}
