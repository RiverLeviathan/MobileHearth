package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

/**
 * Created by Ludwig on 25/03/2018.
 */

public class Spell extends Card {
    public Spell(@NonNull String name, @NonNull Integer manaCost, @NonNull String description, @NonNull String imgUrl) {
        super(name, manaCost, description, imgUrl);
    }

    @NonNull
    public static Spell from(@NonNull String name, @NonNull Integer manaCost, @NonNull String description, @NonNull String imgUrl){
        return new Spell(name, manaCost, description, imgUrl);
    }
}
