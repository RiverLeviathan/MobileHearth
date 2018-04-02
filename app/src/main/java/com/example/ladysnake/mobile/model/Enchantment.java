package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

public class Enchantment extends DescribedCard {
    public Enchantment(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description) {
        super(id, name, manaCost, imgUrl, description);
    }

    public static Enchantment from(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description){
        return new Enchantment(id, name, manaCost, imgUrl, description);
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty(Card.TYPE, CardFactory.CardType.ENCHANTMENT.toString());
        return obj;
    }
}
