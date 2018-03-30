package com.example.ladysnake.mobile.model;

import com.google.gson.JsonObject;

public abstract class CardFactory {
    public final static String[] MINION_TYPES = new String[]{"Minion"};
    public final static String[] SPELL_TYPES = new String[]{"Enchantment", "Spell"};
    public final static String[] WEAPON_TYPES = new String[]{"Weapon", "Hero Power"};
    public final static String[] HERO_TYPES = new String[]{""};

    public static Card from(JsonObject json){
        final String type = json.get("type").getAsString();
        return null;
    }

    private static Minion fromMinion(JsonObject json){
        return null;
    }
}
