package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class CardStatHolder {
    protected final static Map<String, String> MAPPING = new HashMap<String, String>(){{
        put(Card.MANA_COST, "Coût en mana");
        put(CardWithHealth.HEALTH, "PV");
        put(Hero.HEALTH, "PV");
        put(Minion.HEALTH, "PV");
        put(Minion.DAMAGE, "ATK");
        put(Weapon.DAMAGE, "ATK");
        put(Weapon.DURABILITY, "Durabilité");
    }};

    @NonNull
    protected static String getMappedName(String key){
        return MAPPING.get(key);
    }

    protected final static String NAME = Card.NAME;
    protected final static String IMG_URL = Card.IMG_URL;
    protected final static String ID = Card.ID;
    protected final static String DESCRIPTION = DescribedCard.DESCRIPTION;

    @NonNull protected Map<String, String> stats;
    @NonNull protected String name, imgUrl, id;
    @Nullable protected String description;

    protected CardStatHolder(){
        this.stats = new HashMap<>();
        this.setName("").setImgUrl("").setId("");
        this.description = null;
    }
    protected CardStatHolder setName(@NonNull String name){
        this.name = name;
        return this;
    }
    protected CardStatHolder setImgUrl(@NonNull String url){
        this.imgUrl = url;
        return this;
    }
    protected CardStatHolder setDescription(@NonNull String desc){
        this.description = desc;
        return this;
    }
    protected CardStatHolder setId(@NonNull String id){
        this.id = id;
        return this;
    }
    protected CardStatHolder addStat(@NonNull String key, @NonNull String value){
//        if(this.stats.containsKey(key))
//            this.stats.replace(key, value);
//        else
            this.stats.put(key, value);

        return this;
    }
    protected CardStatHolder(@NonNull String name, @NonNull String url){
        this();
        this.setName(name).setImgUrl(url);
    }
    protected CardStatHolder(@NonNull String name, @NonNull String url, @NonNull String id){
        this(name, url);
        this.setId(id);
    }
    protected CardStatHolder(@NonNull String name, @NonNull String url, @NonNull String id, @NonNull String description){
        this(name, url, id);
        this.setDescription(description);
    }


    @Nullable public String getStat(String key){
        return this.stats.get(key);
    }
    @NonNull public String getName(){ return this.name; }
    @NonNull public String getImgUrl(){ return this.imgUrl; }
    @NonNull public String getId(){ return this.id; }
    @Nullable public String getDescription(){ return this.description; }
    public boolean hasDescription(){ return this.description != null; }
    public Map<String, String> getStats(){ return new HashMap<>(this.stats); }

    @Nullable
    public static CardStatHolder from(Card card){
        if(card instanceof Hero)
            return from((Hero)card);
        else if(card instanceof Minion)
            return from((Minion)card);
        else if(card instanceof Spell)
            return from((Spell)card);
        else if(card instanceof Weapon)
            return from((Weapon)card);
        else if(card instanceof HeroPower)
            return from((HeroPower)card);
        else if(card instanceof Enchantment)
            return from((Enchantment)card);
        else
            return null;
    }

    @NonNull
    protected static CardStatHolder from(Hero hero){
        JsonObject json = hero.toJson();
        CardStatHolder ret = new CardStatHolder(
            json.get(NAME).getAsString(),
            json.get(IMG_URL).getAsString(),
            json.get(ID).getAsString()
        );

        ret.addStat(getMappedName(Hero.MANA_COST), json.get(Hero.MANA_COST).getAsString())
        .addStat(getMappedName(Hero.HEALTH), json.get(Hero.HEALTH).getAsString());

        return ret;
    }

    @NonNull
    protected static CardStatHolder from(Minion minion){
        JsonObject json = minion.toJson();
        CardStatHolder ret = new CardStatHolder(
            json.get(NAME).getAsString(),
            json.get(IMG_URL).getAsString(),
            json.get(ID).getAsString(),
            json.get(DESCRIPTION).getAsString()
        );

        ret.addStat(getMappedName(Minion.MANA_COST), json.get(Minion.MANA_COST).getAsString())
        .addStat(getMappedName(Minion.DAMAGE), json.get(Minion.DAMAGE).getAsString())
        .addStat(getMappedName(Minion.HEALTH), json.get(Minion.HEALTH).getAsString());

        return ret;
    }

    @NonNull
    protected static CardStatHolder from(Spell spell){
        JsonObject json = spell.toJson();
        CardStatHolder ret = new CardStatHolder(
            json.get(NAME).getAsString(),
            json.get(IMG_URL).getAsString(),
            json.get(ID).getAsString(),
            json.get(DESCRIPTION).getAsString()
        );

        ret.addStat(getMappedName(Spell.MANA_COST), json.get(Spell.MANA_COST).getAsString());

        return ret;
    }

    @NonNull
    protected static CardStatHolder from(Weapon weapon){
        JsonObject json = weapon.toJson();
        CardStatHolder ret = new CardStatHolder(
            json.get(NAME).getAsString(),
            json.get(IMG_URL).getAsString(),
            json.get(ID).getAsString()
        );

        ret.addStat(getMappedName(Weapon.MANA_COST), json.get(Weapon.MANA_COST).getAsString())
        .addStat(getMappedName(Weapon.DAMAGE), json.get(Weapon.DAMAGE).getAsString())
        .addStat(getMappedName(Weapon.DURABILITY), json.get(Weapon.DURABILITY).getAsString());

        return ret;
    }

    @NonNull
    protected static CardStatHolder from(HeroPower spell){
        JsonObject json = spell.toJson();
        CardStatHolder ret = new CardStatHolder(
            json.get(NAME).getAsString(),
            json.get(IMG_URL).getAsString(),
            json.get(ID).getAsString(),
            json.get(DESCRIPTION).getAsString()
        );

        ret.addStat(getMappedName(HeroPower.MANA_COST), json.get(HeroPower.MANA_COST).getAsString());

        return ret;
    }

    @NonNull
    protected static CardStatHolder from(Enchantment spell){
        JsonObject json = spell.toJson();
        CardStatHolder ret = new CardStatHolder(
            json.get(NAME).getAsString(),
            json.get(IMG_URL).getAsString(),
            json.get(ID).getAsString(),
            json.get(DESCRIPTION).getAsString()
        );

        ret.addStat(getMappedName(HeroPower.MANA_COST), json.get(Enchantment.MANA_COST).getAsString());

        return ret;
    }
}
