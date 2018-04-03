package com.example.ladysnake.mobile.model;

import android.content.Context;
import android.util.Log;

import com.example.ladysnake.mobile.tools.JsonArrayReader;
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


    public Deck getDeck(String deckName){
        for(Deck deck : this.decks){
            if(deck.getName().equals(deckName))
                return deck;
        }

        return null;
    }

    public static DeckList from(JsonArray jsonArray) {
        DeckList deckList = new DeckList();
        for (JsonElement deck : jsonArray) {
            deckList.addDeck(Deck.from(deck.getAsJsonObject()));
        }
        return deckList;
    }

    public static DeckList fromFile(String file, Context context)/* throws IOException*/ {
        JsonArray res = null;
        try {
            res = JsonArrayReader.from(context).readToJson(file);
        } catch (IOException e) {
            return new DeckList();
        }
        return DeckList.from(res);
    }

}
