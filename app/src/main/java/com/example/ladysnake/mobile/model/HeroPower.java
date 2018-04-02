package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

/**
 * A model that describes a hero power card
 * @author Ludwig GUERIN
 */
public class HeroPower extends DescribedCard {
    public HeroPower(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description) {
        super(id, name, manaCost, imgUrl, description);
    }

    public static HeroPower from(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description){
        return new HeroPower(id, name, manaCost, imgUrl, description);
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty(Card.TYPE, CardFactory.CardType.HERO_POWER.toString());
        return obj;
    }
}
