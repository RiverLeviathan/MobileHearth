package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

/**
 * A model that describes an enchantment card
 * @author Ludwig GUERIN
 */
public class Enchantment extends ImageLessDecribedCard {
    public Enchantment(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String description) {
        super(id, name, manaCost, description);
    }

    public static Enchantment from(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String description){
        return new Enchantment(id, name, manaCost, description);
    }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty(Card.TYPE, CardFactory.CardType.ENCHANTMENT.toString());
        return obj;
    }
}
