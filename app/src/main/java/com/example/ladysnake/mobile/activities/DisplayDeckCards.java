package com.example.ladysnake.mobile.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ladysnake.mobile.EditView;
import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.SearchView;
import com.example.ladysnake.mobile.model.Deck;
import com.example.ladysnake.mobile.tools.DeckCardsAdapter;
import com.example.ladysnake.mobile.tools.FileReader;
import com.example.ladysnake.mobile.tools.FileWriter;
import com.example.ladysnake.mobile.tools.JsonObjectReader;
import com.google.gson.JsonObject;

import java.io.IOException;

public class DisplayDeckCards extends AppCompatActivity {
    public final static String TAG = "DisplayDeckCards";

    public static class State {
        protected TextView textView;
        protected ListView listView;

        public State(TextView t, ListView l){
            this.textView = t;
            this.listView = l;
        }

        public State(View t, View l){
            this(
                    (TextView)t,
                    (ListView)l
            );
        }

        public static State from(TextView t, ListView l){ return new State(t, l); }
        public static State from(View t, View l){ return new State(t, l); }

        public TextView getTextView() { return textView; }
        public ListView getListView() { return listView; }
    }

    protected State state;
    protected Deck deck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_deck_cards);

        Intent intent = getIntent();
        String extra = intent.getStringExtra(EditView.EXTRA_FILEPATH);
        try {
            JsonObject jsonObject = JsonObjectReader.from(this).readAsJsonObject(extra);
            this.deck = Deck.from(jsonObject);
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, FileReader.ERR_MSG, Toast.LENGTH_SHORT).show();
            return;
        }

        this.state = State.from(
            findViewById(R.id.deckName),
            findViewById(R.id.cards)
        );


        setupView(this.state, this.deck);
    }

    protected void setupView(@NonNull State state, @NonNull Deck deck){
        state.getTextView().setText(deck.getName());

        DeckCardsAdapter adapter = new DeckCardsAdapter(this, R.layout.list_item_deck_edit, deck.getCards());
        adapter.setDeck(deck);
        state.getListView().setAdapter(adapter);
    }
}
