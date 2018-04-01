package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;


public class Hero extends CardWithHealth {
    public Hero(@NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull Integer health) {
        super(name, manaCost, imgUrl, health);
    }

    public static Hero from(@NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl, @NonNull Integer health){
        return new Hero(name, manaCost, imgUrl, health);
    }
}
