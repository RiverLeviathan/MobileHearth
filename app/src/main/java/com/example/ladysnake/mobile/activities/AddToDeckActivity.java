package com.example.ladysnake.mobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ladysnake.mobile.EditView;
import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.model.Card;
import com.example.ladysnake.mobile.model.CardFactory;
import com.example.ladysnake.mobile.model.Deck;
import com.example.ladysnake.mobile.model.DeckList;
import com.example.ladysnake.mobile.tools.AddToDeckAdapter;
import com.example.ladysnake.mobile.tools.FileReader;
import com.example.ladysnake.mobile.tools.FileWriter;
import com.example.ladysnake.mobile.tools.JsonObjectReader;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ludwig GUERIN
 */
public class AddToDeckActivity extends AppCompatActivity {
    public final static String TAG = "AddToDeckActivity";

    public static class State{
        protected ListView deckListView;
        protected Button submitButton;

        public State(ListView d, Button s){
            this.deckListView = d;
            this.submitButton = s;
        }

        public State(View d, View s){
            this(
                (ListView)d,
                (Button)s
            );
        }

        public static State from(ListView d, Button s){ return new State(d, s); }
        public static State from(View d, View s){ return new State(d, s); }

        public ListView getDeckListView() { return deckListView; }
        public Button getSubmitButton() { return submitButton; }
    }

    protected State state;
    protected JsonObject data;
    protected Card card;
    protected AddToDeckAdapter adapter;
    protected List<Deck> decks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_deck);
        Log.v(TAG, "Succesfully landed on activity : AddToDeckActivity");

        Intent intent = super.getIntent();
        if(intent == null){
            Log.e(TAG, "Error, started activity without intent");
            Toast.makeText(this, "No intent", Toast.LENGTH_SHORT).show();
            super.finish();
            return;
        }

//        String data = intent.getStringExtra(DisplayResultList.CARD_EXTRA);
        String filePath = intent.getStringExtra(DisplayCardDetails.FILE_PATH_EXTRA);
        try {
            this.data = JsonObjectReader.from(this).readAsJsonObject(filePath);
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, FileReader.ERR_MSG, Toast.LENGTH_SHORT).show();
            return;
        }

        this.card = CardFactory.from(this.data);

        this.state = State.from(
            findViewById(R.id.decks),
            findViewById(R.id.submit)
        );

        setupView(this.state, this.card);
    }

    protected void setupView(State state, Card card){
        setupList(state, card);
    }

    protected void setupList(State state, Card card){
        ListView list = state.getDeckListView();
        DeckList deckList;

        deckList = DeckList.fromFile(EditView.DECKLIST_FILEPATH, this);

        this.decks = deckList.getDecks();
        this.adapter = new AddToDeckAdapter(this, R.layout.list_item_add_to_deck, this.decks);
        list.setAdapter(this.adapter);
    }

    /*
     * cf. https://stackoverflow.com/questions/24811536/android-listview-get-item-view-by-position
     */
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    /*state.submitButton@onClick*/
    public void addToDecks(View view) {
        List<Deck> selectedDecks = new ArrayList<>();
        for(int i = 0 ; i < decks.size() ; i+=1){
            View deckItem = getViewByPosition(i, this.state.getDeckListView());
            AddToDeckAdapter.State viewState = ((AddToDeckAdapter.State) deckItem.getTag());
            CheckBox checkBox = viewState.getCheckBox();

            if(checkBox.isChecked())
                selectedDecks.add(decks.get(i));
        }

        if(selectedDecks.size() == 0)
            return;

        DeckList deckList;
        deckList = DeckList.fromFile(EditView.DECKLIST_FILEPATH, this);

        for(Deck deck : selectedDecks){
            deckList.removeDeck(deck);
            deck.addCard(this.card);
            deckList.addDeck(deck);
        }

        try {
            FileWriter.from(this).writeTo(EditView.DECKLIST_FILEPATH, deckList.toJson().toString());
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, FileWriter.ERR_MSG, Toast.LENGTH_SHORT).show();
            return;
        }

        this.finish();
    }
}
