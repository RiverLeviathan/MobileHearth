package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

//import com.google.common.collect.BiMap;
//import com.google.common.collect.HashBiMap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.text.WordUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * A "static" class used to easily instantiate a {@link Card} object from its JSON representation
 */
public abstract class CardFactory {
    public final static String TAG = "CardFactory";

    /**
     * An enum listing the different types of cards
     */
    public static enum CardType{
        MINION,
        SPELL,
        WEAPON,
        HERO,
        HERO_POWER,
        ENCHANTMENT;

        protected final static Map<CardType, String> STRINGS = new HashMap<CardType, String>(){{
            put(CardType.MINION, "Minion");
            put(CardType.SPELL, "Spell");
            put(CardType.WEAPON, "Weapon");
            put(CardType.HERO, "Hero");
            put(CardType.HERO_POWER, "Hero Power");
            put(CardType.ENCHANTMENT, "Enchantment");
        }};

        protected final static BiMap<String, CardType> TYPES = HashBiMap.create(STRINGS).inverse();

        public String toString(){
            return STRINGS.get(this);
        }

        /**
         * Creates a {@link CardType} from its string representation
         * @param str being the string to construct a {@link CardType} from
         * @return the constructed {@link CardType}
         */
        public static CardType from(String str){
            return TYPES.get(/*WordUtils.capitalizeFully(*/str/*)*/);
        }
    }

    /**
     * Instantiate a {@link Card} from its JSON represenation (as a {@link String})
     * @param json being the JSON card to construct from
     * @return the instantiated {@link Card}, or NULL if invalid
     */
    @Nullable
    public static Card from(@NonNull String json){
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        return from(jsonObject);
    }

    /**
     * Instantiate a {@link Card} from a {@link JsonObject}
     * @param json being the {@link JsonObject} card to construct from
     * @return the instantiated {@link Card}, or NULL if invalid
     */
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
            case HERO_POWER:
                return fromHeroPower(json);
            case ENCHANTMENT:
                return fromEnchantment(json);
            default:
                Log.e(TAG, "Couldn't instantiate a Card for " + json.toString());
                return null;
        }
    }

    @Nullable
    protected static Minion fromMinion(@NonNull JsonObject json){
        try{
            String id = json.get(Minion.ID).getAsString();
            String name = json.get(Minion.NAME).getAsString();
            Integer manaCost = json.get(Minion.MANA_COST).getAsInt();
            String description = json.get(Minion.DESCRIPTION).getAsString();
            String imgUrl = json.get(Minion.IMG_URL).getAsString();
            Integer damage = json.get(Minion.DAMAGE).getAsInt();
            Integer health = json.get(Minion.HEALTH).getAsInt();

            return Minion.from(id, name, manaCost, description, imgUrl, damage, health);
        }catch(Throwable t){
            return null;
        }
    }

    @Nullable
    protected static Spell fromSpell(@NonNull JsonObject json){
        try{
            String id = json.get(Spell.ID).getAsString();
            String name = json.get(Spell.NAME).getAsString();
            Integer manaCost = json.get(Spell.MANA_COST).getAsInt();
            String description = json.get(Spell.DESCRIPTION).getAsString();
            String imgUrl = json.get(Spell.IMG_URL).getAsString();

            return Spell.from(id, name, manaCost, description, imgUrl);
        }catch(Throwable t){
            return null;
        }
    }

    @Nullable
    protected static Weapon fromWeapon(@NonNull JsonObject json){
        try{
            String id = json.get(Weapon.ID).getAsString();
            String name = json.get(Weapon.NAME).getAsString();
            Integer manaCost = json.get(Weapon.MANA_COST).getAsInt();
            String imgUrl = json.get(Weapon.IMG_URL).getAsString();
            Integer damage = json.get(Weapon.DAMAGE).getAsInt();
            Integer durability = json.get(Weapon.DURABILITY).getAsInt();

            return Weapon.from(id, name, manaCost, imgUrl, damage, durability);
        }catch(Throwable t){
            return null;
        }
    }

    @Nullable
    protected static Hero fromHero(@NonNull JsonObject json){
        try{
            String id = json.get(Hero.ID).getAsString();
            String name = json.get(Hero.NAME).getAsString();
            Integer manaCost = json.get(Hero.MANA_COST).getAsInt();
            String imgUrl = json.get(Hero.IMG_URL).getAsString();
            Integer health = json.get(Hero.HEALTH).getAsInt();

            return Hero.from(id, name, manaCost, imgUrl, health);
        }catch(Throwable t){
            return null;
        }
    }

    @Nullable
    protected static HeroPower fromHeroPower(@NonNull JsonObject json){
        try{
            String id = json.get(HeroPower.ID).getAsString();
            String name = json.get(HeroPower.NAME).getAsString();
            Integer manaCost = json.get(HeroPower.MANA_COST).getAsInt();
            String imgUrl = json.get(HeroPower.IMG_URL).getAsString();
            String description = json.get(HeroPower.DESCRIPTION).getAsString();

            return HeroPower.from(id, name, manaCost, imgUrl, description);
        }catch(Throwable t){
            return null;
        }
    }

    @Nullable
    protected static Enchantment fromEnchantment(@NonNull JsonObject json){//TODO: Modify Enchantment so that it doesn't require an image
        try{
            String id = json.get(Enchantment.ID).getAsString();
            String name = json.get(Enchantment.NAME).getAsString();
            Integer manaCost = json.get(Enchantment.MANA_COST).getAsInt();
            String imgUrl = json.get(Enchantment.IMG_URL).getAsString();
            String description = json.get(Enchantment.DESCRIPTION).getAsString();

            return Enchantment.from(id, name, manaCost, imgUrl, description);
        }catch(Throwable t){
            return null;
        }
    }
}
