package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

public abstract class CardWithHealth extends Card{
    public final static String HEALTH = "health";

    protected int health;

    public CardWithHealth(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull Integer health) {
        super(id, name, manaCost, imgUrl);
        this.health = health;
    }

    public int getHealth(){ return this.health; }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty(HEALTH, getHealth());
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardWithHealth)) return false;
        if (!super.equals(o)) return false;

        CardWithHealth that = (CardWithHealth) o;

        return getHealth() == that.getHealth();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getHealth();
        return result;
    }
}
