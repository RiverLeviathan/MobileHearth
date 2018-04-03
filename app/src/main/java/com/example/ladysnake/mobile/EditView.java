package com.example.ladysnake.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ladysnake.mobile.activities.DisplayDeckCards;
import com.example.ladysnake.mobile.activities.DisplayResultList;
import com.example.ladysnake.mobile.model.DeckList;
import com.example.ladysnake.mobile.tools.DeckAdapter;
import com.example.ladysnake.mobile.tools.FileWriter;
import com.example.ladysnake.mobile.tools.JsonObjectReader;
import com.example.ladysnake.mobile.tools.ResourceAwareFragment;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

/**
 * Created by Ludwig on 24/03/2018.
 */

public class EditView extends ResourceAwareFragment {
    public static final String DECKLIST_FILEPATH = "decklist.json";
    public static final String DECK_FILEPATH = "deck.json";
    public static final String TAG = "EditView";
    public final static String EXTRA_FILEPATH = "filePath";
    public static EditView make(){ return new EditView(); }

    public static class State{
        protected ListView decklist;

        public State(ListView decklist){
            this.decklist = decklist;
        }
        public State(View decklist){
            this((ListView) (decklist));
        }
        public static State from(ListView decklist){ return new State(decklist); }
        public static State from(View decklist){ return new State(decklist); }
    }

    protected State state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_view, null);
        this.state = State.from(view.findViewById(R.id.decklist));
        view.setTag(state);
        return view;
    }

    public void setupView(State state) {
        JsonObjectReader reader = new JsonObjectReader(getContext());
        try {
            JsonObject jsonObject = reader.readAsJsonObject(DECKLIST_FILEPATH);
            DeckList.from(jsonObject.getAsJsonArray());
        } catch (IOException e) {
            DeckList deckList = new DeckList();
        }
        state.decklist.setAdapter(new DeckAdapter(getContext(), R.layout.list_item_deck));
        state.decklist.setOnItemClickListener((parent, view, position, id) -> {

        });
    }

    protected void goEditDeck(JsonObject json){
        Intent intent = new Intent(getContext(), DisplayDeckCards.class);
        intent.setAction(Intent.ACTION_VIEW);
        try {
            FileWriter.from(getContext()).writeTo(DECK_FILEPATH, json.toString());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(getContext(), "Erreur fatale pendant l'écriture dans un fichier", Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(EXTRA_FILEPATH, DECK_FILEPATH);

        Log.v(TAG, "Starting activity : DisplayResultList");
        getContext().startActivity(intent);
    }
}
