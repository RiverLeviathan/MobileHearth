package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

public abstract class Card{
    protected String name;
    protected int manaCost;
    protected String imgUrl;

    public Card(@NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl){
        this.name = name;
        this.manaCost = manaCost;
        this.imgUrl = imgUrl;
    }

    public String getName() { return name; }
    public int getManaCost() { return manaCost; }
    public String getImgUrl() { return imgUrl; }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (getManaCost() != card.getManaCost()) return false;
        if (!getName().equals(card.getName())) return false;
        return getImgUrl().equals(card.getImgUrl());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getManaCost();
        result = 31 * result + getImgUrl().hashCode();
        return result;
    }
}
