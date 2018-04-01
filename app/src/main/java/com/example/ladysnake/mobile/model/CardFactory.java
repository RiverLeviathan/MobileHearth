package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

//import com.google.common.collect.BiMap;
//import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.text.WordUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class CardFactory {
    public final static String TAG = "CardFactory";

//    public final static String MINION_TYPE = "Minion";
//    public final static String SPELL_TYPE = "Spell";
//    public final static String WEAPON_TYPE = "Weapon";
//    public final static String HERO_TYPE = "Hero";

    public static enum CardType{
        MINION,
        SPELL,
        WEAPON,
        HERO;

        protected final static Map<CardType, String> STRINGS = new HashMap<CardType, String>(){{
            put(CardType.MINION, "Minion");
            put(CardType.SPELL, "Spell");
            put(CardType.WEAPON, "Weapon");
            put(CardType.HERO, "Hero");
        }};

        protected final static Map<String, CardType> TYPES = new HashMap<String, CardType>(){{
            put("Minion", CardType.MINION);
            put("Spell", CardType.SPELL);
            put("Weapon", CardType.WEAPON);
            put("Hero", CardType.HERO);
        }};

//        protected final static BiMap<CardType, String> STRINGS = HashBiMap.create(new HashMap<CardType, String>(){{
//            put(CardType.MINION, "Minion");
//            put(CardType.SPELL, "Spell");
//            put(CardType.WEAPON, "Weapon");
//            put(CardType.HERO, "Hero");
//        }});
//
//        protected final static BiMap<String, CardType> TYPES = STRINGS.inverse();

        public String toString(){
            return STRINGS.get(this);
        }

        public static CardType from(String str){
            return TYPES.get(WordUtils.capitalizeFully(str));
        }
    }

    @Nullable
    public static Card from(@NonNull String json){
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        return from(jsonObject);
    }

    @Nullable
    public static Card from(@NonNull JsonObject json){
//        Log.v(TAG, "Json: " + json.get("type").getAsString());
        final CardType type = CardType.from(json.get("type").getAsString());
        if(type == null)
            return null;

        switch (type){
            case MINION:
                return fromMinion(json);
            case SPELL:
                return fromSpell(json);
            case WEAPON:
                return fromWeapon(json);
            case HERO:
                return fromHero(json);
            default:
                return null;
        }
    }

    @Nullable
    protected static Minion fromMinion(@NonNull JsonObject json){
        try{
            String name = json.get(Card.NAME).getAsString();
            Integer manaCost = json.get(Card.MANA_COST).getAsInt();
            String description = json.get(DescribedCard.DESCRIPTION).getAsString();
            String imgUrl = json.get(Card.IMG_URL).getAsString();
            Integer damage = json.get(Minion.DAMAGE).getAsInt();
            Integer health = json.get(Minion.HEALTH).getAsInt();

            return Minion.from(name, manaCost, description, imgUrl, damage, health);
        }catch(Exception e){
            return null;
        }
    }

    @Nullable
    protected static Spell fromSpell(@NonNull JsonObject json){
        try{
            String name = json.get(Card.NAME).getAsString();
            Integer manaCost = json.get(Card.MANA_COST).getAsInt();
            String description = json.get(DescribedCard.DESCRIPTION).getAsString();
            String imgUrl = json.get(Card.IMG_URL).getAsString();

            return Spell.from(name, manaCost, description, imgUrl);
        }catch(Exception e){
            return null;
        }
    }

    @Nullable
    protected static Weapon fromWeapon(@NonNull JsonObject json){
        try{
            String name = json.get(Card.NAME).getAsString();
            Integer manaCost = json.get(Card.MANA_COST).getAsInt();
            String imgUrl = json.get(Card.IMG_URL).getAsString();
            Integer damage = json.get(Weapon.DAMAGE).getAsInt();
            Integer durability = json.get(Weapon.DURABILITY).getAsInt();

            return Weapon.from(name, manaCost, imgUrl, damage, durability);
        }catch(Exception e){
            return null;
        }
    }

    @Nullable
    protected static Hero fromHero(@NonNull JsonObject json){
        try{
            String name = json.get(Card.NAME).getAsString();
            Integer manaCost = json.get(Card.MANA_COST).getAsInt();
            String imgUrl = json.get(Card.IMG_URL).getAsString();
            Integer health = json.get(CardWithHealth.HEALTH).getAsInt();

            return Hero.from(name, manaCost, imgUrl, health);
        }catch(Exception e){
            return null;
        }
    }
}
