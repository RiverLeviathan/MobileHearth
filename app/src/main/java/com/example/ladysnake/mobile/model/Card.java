package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

public abstract class Card{
    protected String name;
    protected int manaCost;
    protected String description;
    protected String imgUrl;

    public Card(@NonNull String name, @NonNull Integer manaCost, @NonNull String description, @NonNull String imgUrl){
        this.name = name;
        this.manaCost = manaCost;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public String getName() { return name; }
    public int getManaCost() { return manaCost; }
    public String getDescription() { return description; }
    public String getImgUrl() { return imgUrl; }
}
