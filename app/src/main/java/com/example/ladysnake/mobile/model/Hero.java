package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;


public class Hero extends Card {
    public Hero(@NonNull String name, @NonNull Integer manaCost, @NonNull String description, @NonNull String imgUrl) {
        super(name, manaCost, description, imgUrl);
    }
}
