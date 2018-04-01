package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

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
}
