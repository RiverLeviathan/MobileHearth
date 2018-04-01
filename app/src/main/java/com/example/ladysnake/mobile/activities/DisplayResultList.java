package com.example.ladysnake.mobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.SearchView;
import com.example.ladysnake.mobile.model.Card;
import com.example.ladysnake.mobile.model.CardFactory;
import com.example.ladysnake.mobile.tools.ResultListAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayResultList extends AppCompatActivity {
    public final static String TAG = "DisplayResultList";
    public final static String CARD_EXTRA = "card";

    public static class State{
        protected ListView listView;

        public State(ListView l){
            this.listView = l;
        }
        public State(View l){
            this(
                (ListView)l
            );
        }

        public static State from(ListView l){ return new State(l); }
        public static State from(View l){ return new State(l); }

        public ListView getListView() { return listView; }
    }

    protected State state;
    protected JsonArray resultData;
    protected ResultListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result_list);

        Intent intent = super.getIntent();
        if(intent == null){
            Log.e(TAG, "Error, started activity without intent");
            Toast.makeText(this, "No intent", Toast.LENGTH_SHORT).show();
            super.finish();
            return;
        }

        String resultString = intent.getStringExtra(SearchView.JSON_ARRAY_EXTRA);
        this.resultData = new Gson().fromJson(resultString, JsonArray.class);

        this.state = State.from(
            findViewById(R.id.searchResultListView)
        );

        setupView(this.state, this.resultData);
    }

    protected void setupView(State state, JsonArray resultData){
        ListView listView = state.getListView();
        List<Card> cards = new ArrayList<>();

        for(JsonElement elem : resultData){
            JsonObject object = elem.getAsJsonObject();
            Card card = CardFactory.from(object);
            if(card != null)
                cards.add(card);
        }

        this.adapter = new ResultListAdapter(this, R.layout.list_item_result, cards);
        listView.setAdapter(this.adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> { //TODO: See why this fucker doesn't work
            //TODO: Go activity that will display the card's details
            goShowDetails(this.adapter.getItem(position));
        });
    }

    protected void goShowDetails(Card card){
        Log.v(TAG, "Going to show details of card: " + card.toJson().toString());
        Intent intent = new Intent(this, DisplayCardDetails.class);
//        intent.setAction(Intent.ACTION_VIEW);
        Gson gson = new Gson();
        intent.putExtra(CARD_EXTRA, gson.toJson(card.toJson()));
        startActivity(intent);
    }
}
