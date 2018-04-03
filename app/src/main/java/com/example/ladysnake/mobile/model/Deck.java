package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.example.ladysnake.mobile.tools.JsonObjectSerializable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Deck implements JsonObjectSerializable {
    public final static String NAME = "name";
    public final static String CARDS = "cards";

    @NonNull protected String name;
    @NonNull protected List<Card> cards;

    // Constructors
    public Deck() {
        this("deck");
    }

    public Deck(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    // To String
    @Override
    public String toString() {
        return "Deck{" +
                "name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck deck = (Deck) o;

        if (!name.equals(deck.name)) return false;
        return cards.equals(deck.cards);

    }

    // Hash
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + cards.hashCode();
        return result;
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards;
    }

    // Adders
    public boolean addCard(Card card) {
        return cards.add(card);
    }

    public boolean addCards(List<Card> cards) {
        return cards.addAll(cards);
    }

    // Removers
    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    public boolean removeCards(List<Card> cards) {
        return cards.removeAll(cards);
    }

    // Setters (rename = setName)
    public void rename(String name) {
        this.name = name;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public JsonObject toJson() {
        JsonObject deckJson = new JsonObject();
        JsonArray cardsJson = new JsonArray();
        for (Card card : cards) {
            cardsJson.add(card.toJson());
        }
        deckJson.addProperty(NAME, name);
        deckJson.add(CARDS, cardsJson);
        return deckJson;
    }

    public static Deck from(JsonObject jsonObject) {
        Deck deck = new Deck(jsonObject.get(NAME).getAsString());
        for (JsonElement card : jsonObject.getAsJsonArray(CARDS)) {
            deck.cards.add(CardFactory.from(card.getAsJsonObject()));
        }
        return deck;
    }
}
