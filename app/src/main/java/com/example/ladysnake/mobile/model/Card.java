package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class Card{
    public final static String ID = "cardId";
    public final static String TYPE = "type";
    public final static String NAME = "name";
    public final static String MANA_COST = "cost";
    public final static String IMG_URL = "img";

    protected String id;
    protected String name;
    protected int manaCost;
    protected String imgUrl;

    public Card(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String imgUrl){
        this.id = id;
        this.name = name;
        this.manaCost = manaCost;
        this.imgUrl = imgUrl;
    }

    public String getId(){ return id; }
    public String getName() { return name; }
    public int getManaCost() { return manaCost; }
    public String getImgUrl() { return imgUrl; }


    public JsonObject toJson(){
        JsonObject obj = new JsonObject();
        obj.addProperty(ID, getId());
        obj.addProperty(NAME, getName());
        obj.addProperty(MANA_COST, getManaCost());
        obj.addProperty(IMG_URL, getImgUrl());
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        if (getManaCost() != card.getManaCost()) return false;
        if (!getId().equals(card.getId())) return false;
        if (!getName().equals(card.getName())) return false;
        return getImgUrl().equals(card.getImgUrl());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getManaCost();
        result = 31 * result + getImgUrl().hashCode();
        return result;
    }
}
