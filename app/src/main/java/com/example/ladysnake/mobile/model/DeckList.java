package com.example.ladysnake.mobile.model;

import android.content.Context;
import android.util.Log;

import com.example.ladysnake.mobile.tools.JsonArraySerializable;
import com.example.ladysnake.mobile.tools.JsonObjectReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DeckList implements JsonArraySerializable {
    protected List<Deck> decks;

    // Constructor
    public DeckList() {
        this.decks = new ArrayList<>();
    }

    @Override
    public JsonArray toJson() {
        JsonArray deckListJson = new JsonArray();
        for (Deck deck : decks) {
            deckListJson.add(deck.toJson());
        }
        return deckListJson;
    }

    // Getter
    public List<Deck> getDecks() {
        return decks;
    }

    // Adders
    public void addDeck(Deck deck) {
        decks.add(deck);

    }
    public void addDecks(ArrayList<Deck> decks) {
        decks.addAll(decks);
    }

    // Removers
    public void removeDeck(Deck deck) {
        decks.remove(deck);
    }
    public void removeDecks(ArrayList<Deck> decks) {
        decks.removeAll(decks);
    }

    public static DeckList from(JsonArray jsonArray) {
        DeckList deckList = new DeckList();
        for (JsonElement deck : jsonArray) {
            deckList.addDeck(Deck.from(deck.getAsJsonObject()));
        }
        return deckList;
    }

    public static DeckList fromFile(String file, Context context) {
        try {
            JsonObjectReader reader = new JsonObjectReader(context);
            JsonObject jsonObject = reader.readAsJsonObject(file);
            return DeckList.from(jsonObject.getAsJsonArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
