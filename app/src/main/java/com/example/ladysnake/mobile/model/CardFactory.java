package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

public abstract class CardFactory {
    public final static String MINION_TYPE = "Minion";
    public final static String SPELL_TYPE = "Spell";
    public final static String WEAPON_TYPE = "Weapon";
    public final static String HERO_TYPE = "Hero";

    @Nullable
    public static Card from(@NonNull JsonObject json){
        final String type = json.get("type").getAsString();

        switch (type){
            case MINION_TYPE:
                return fromMinion(json);
            case SPELL_TYPE:
                return fromSpell(json);
            case WEAPON_TYPE:
                return fromWeapon(json);
            case HERO_TYPE:
                return fromHero(json);
            default:
                return null;
        }
    }

    @Nullable
    protected static Minion fromMinion(@NonNull JsonObject json){
        try{
            String name = json.get("name").getAsString();
            Integer manaCost = json.get("cost").getAsInt();
            String description = "";//TODO: Search in JSON
            String imgUrl = json.get("img").getAsString();
            Integer damage = json.get("attack").getAsInt();
            Integer health = json.get("health").getAsInt();

            return new Minion(name, manaCost, description, imgUrl, damage, health);
        }catch(Exception e){
            return null;
        }
    }

    @Nullable
    protected static Spell fromSpell(@NonNull JsonObject json){
        try{
            String name = json.get("name").getAsString();
            Integer manaCost = json.get("cost").getAsInt();
            String description = "";//TODO: Search in JSON
            String imgUrl = json.get("img").getAsString();

            return new Spell(name, manaCost, description, imgUrl);
        }catch(Exception e){
            return null;
        }
    }

    @Nullable
    protected static Weapon fromWeapon(@NonNull JsonObject json){
        try{
            String name = json.get("name").getAsString();
            Integer manaCost = json.get("cost").getAsInt();
            String description = "";//TODO: Search in JSON
            String imgUrl = json.get("img").getAsString();

            //return new Weapon(name, manaCost, description, imgUrl);
            return null;
        }catch(Exception e){
            return null;
        }
    }

    @Nullable
    protected static Hero fromHero(@NonNull JsonObject json){
        return null;
    }
}
