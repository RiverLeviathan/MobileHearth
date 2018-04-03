package com.example.ladysnake.mobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ladysnake.mobile.EditView;
import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.SearchView;
import com.example.ladysnake.mobile.model.Deck;
import com.example.ladysnake.mobile.tools.JsonObjectReader;
import com.google.gson.JsonObject;

import java.io.IOException;

public class DisplayDeckCards extends AppCompatActivity {
    State state = new State();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_deck_cards);

        Intent intent = getIntent();
        String extra = intent.getStringExtra(EditView.EXTRA_FILEPATH);
        try {
            JsonObject jsonObject = JsonObjectReader.from(this).readAsJsonObject(extra);
            Deck deck = Deck.from(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public static class State {
        protected TextView textView;
        protected ListView listView;

        public State(ListView l){
            this.listView = l;
        }
        public State(TextView t){
            this.textView = t;
        }
        public State(View l){
            this.listView = (ListView) l;
        }
        public State(View t){
            this.textView = (TextView) t;
        }

        public static State from(ListView l){ return new State(l); }
        public static State from(TextView t){ return new State(t); }
        public static State from(View l){ return new State(l); }
        public static State from(View t){ return new State(t); }

        public ListView getListView() { return listView; }
    }
}
