package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

/**
 * Created by Ludwig on 25/03/2018.
 */

public class Minion extends DescribedCard {
    protected int damage, health;

    public Minion(@NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description, @NonNull Integer damage, @NonNull Integer health) {
        super(name, manaCost, imgUrl, description);

        this.damage = damage;
        this.health = health;
    }

    public int getDamage() { return damage; }
    public int getHealth() { return health; }

    @NonNull
    public static Minion from(@NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull String description, @NonNull Integer damage, @NonNull Integer health){
        return new Minion(name, manaCost, imgUrl, description, damage, health);
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
