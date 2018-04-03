package com.example.ladysnake.mobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ladysnake.mobile.activities.DisplayDeckCards;
import com.example.ladysnake.mobile.activities.DisplayResultList;
import com.example.ladysnake.mobile.activities.NewDeckActivity;
import com.example.ladysnake.mobile.model.Deck;
import com.example.ladysnake.mobile.model.DeckList;
import com.example.ladysnake.mobile.tools.DeckAdapter;
import com.example.ladysnake.mobile.tools.FileWriter;
import com.example.ladysnake.mobile.tools.JsonArrayReader;
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
        protected ImageButton newDeckButton;

        public State(ListView decklist, ImageButton b){
            this.decklist = decklist;
            this.newDeckButton = b;
        }
        public State(View decklist, View b){
            this(
                (ListView) (decklist),
                (ImageButton) b
            );
        }
        public static State from(ListView decklist, ImageButton b){ return new State(decklist, b); }
        public static State from(View decklist, View b){ return new State(decklist, b); }

        public ListView getDecklist() { return decklist; }
        public ImageButton getNewDeckButton() { return newDeckButton; }
    }

    protected State state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_view, null);
        this.state = State.from(
            view.findViewById(R.id.decklist),
            view.findViewById(R.id.newDeck)
        );
        view.setTag(state);

        setupView(state);
        return view;
    }

    public void setupView(State state) {
        DeckList deckList;
        try {
            JsonArray jsonArray = JsonArrayReader.from(getContext()).readToJson(DECKLIST_FILEPATH);
            deckList = DeckList.from(jsonArray);
        } catch (IOException e) {
            deckList = new DeckList();
        }

        DeckAdapter adapter = new DeckAdapter(getContext(), R.layout.list_item_deck, deckList.getDecks());
        state.getDecklist().setAdapter(adapter);
        state.getDecklist().setOnItemClickListener((parent, view, position, id) -> {
            Deck deck = adapter.getItem(position);
            goEditDeck(deck.toJson());
        });

        setupButton(state);
    }

    protected void setupButton(State state){
        ImageButton btn = state.getNewDeckButton();
        btn.setOnClickListener(view -> {
           //TODO: Go to new activity that creates deck
            Intent intent = new Intent(getContext(), NewDeckActivity.class);
            intent.setAction(Intent.ACTION_VIEW);
            getContext().startActivity(intent);
        });
    }

    protected void goEditDeck(JsonObject json){
        Intent intent = new Intent(getContext(), DisplayDeckCards.class);
        intent.setAction(Intent.ACTION_VIEW);
        try {
            FileWriter.from(getContext()).writeTo(DECK_FILEPATH, json.toString());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(getContext(), FileWriter.ERR_MSG, Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(EXTRA_FILEPATH, DECK_FILEPATH);

        Log.v(TAG, "Starting activity : DisplayDeckCards");
        getContext().startActivity(intent);
    }
}
