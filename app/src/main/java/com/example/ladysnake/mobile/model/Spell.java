package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

/**
 * Created by Ludwig on 25/03/2018.
 */

public class Spell extends DescribedCard {

    public Spell(@NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description) {
        super(name, manaCost, imgUrl, description);
    }

    @NonNull
    public static Spell from(@NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description){
        return new Spell(name, manaCost, imgUrl, description);
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty(Card.TYPE, CardFactory.CardType.SPELL.toString());
        return obj;
    }
}
