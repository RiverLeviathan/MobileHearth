package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

/**
 * A model that describes a minion card
 * @author Ludwig GUERIN
 */
public class Minion extends DescribedCard {
    public final static String DAMAGE = "attack";
    public final static String HEALTH = "health";

    protected int damage, health;

    public Minion(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description, @NonNull Integer damage, @NonNull Integer health) {
        super(id, name, manaCost, imgUrl, description);

        this.damage = damage;
        this.health = health;
    }

    public int getDamage() { return damage; }
    public int getHealth() { return health; }

    @Override
    public JsonObject toJson() {
        JsonObject obj = super.toJson();
        obj.addProperty(DAMAGE, getDamage());
        obj.addProperty(HEALTH, getHealth());
        obj.addProperty(Card.TYPE, CardFactory.CardType.MINION.toString());
        return obj;
    }

    @NonNull
    public static Minion from(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description, @NonNull Integer damage, @NonNull Integer health){
        return new Minion(id, name, manaCost, imgUrl, description, damage, health);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Minion)) return false;
        if (!super.equals(o)) return false;

        Minion minion = (Minion) o;

        if (getDamage() != minion.getDamage()) return false;
        return getHealth() == minion.getHealth();
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getDamage();
        result = 31 * result + getHealth();
        return result;
    }
}
