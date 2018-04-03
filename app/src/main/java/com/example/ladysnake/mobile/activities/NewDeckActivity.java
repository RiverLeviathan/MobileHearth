package com.example.ladysnake.mobile.activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ladysnake.mobile.EditView;
import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.model.Deck;
import com.example.ladysnake.mobile.model.DeckList;
import com.example.ladysnake.mobile.tools.FileReader;
import com.example.ladysnake.mobile.tools.FileWriter;

import java.io.IOException;

/**
 * @author Ludwig GUERIN
 */
public class NewDeckActivity extends AppCompatActivity {
    public final static String TAG = "NewDeckActivity";

    public static class State{
        protected TextInputEditText deckNameInput;
        protected Button submitButton;

        public State(TextInputEditText d, Button s){
            this.deckNameInput = d;
            this.submitButton = s;
        }

        public State(View d, View s){
            this(
                (TextInputEditText)d,
                (Button)s
            );
        }

        public static State from(TextInputEditText d, Button s){ return new State(d, s); }
        public static State from(View d, View s){ return new State(d, s); }

        public TextInputEditText getDeckNameInput() { return deckNameInput; }
        public Button getSubmitButton() { return submitButton; }
    }

    protected State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_deck);

        this.state = State.from(
            findViewById(R.id.deckName),
            findViewById(R.id.submit)
        );

        setupView(this.state);
    }

    protected String getInputValue(State state){
        return state.getDeckNameInput().getText().toString().trim();
    }

    protected void setupView(State state){
        state.getSubmitButton().setOnClickListener(view -> {
            String value = getInputValue(state);
            if(value.isEmpty())
                return;

            Deck deck = new Deck(value);
            DeckList deckList;

            deckList = DeckList.fromFile(EditView.DECKLIST_FILEPATH, this);

            if(deckList.getDeck(deck.getName()) != null){
                Toast.makeText(this, "Nom déjà utilisé, veuillez en choisir un autre", Toast.LENGTH_SHORT).show();
                return;
            }

            deckList.addDeck(deck);

            try {
                FileWriter.from(this).writeTo(EditView.DECKLIST_FILEPATH, deckList.toJson().toString());
            } catch (IOException e) {
//            e.printStackTrace();
                Log.e(TAG, e.getMessage());
                Toast.makeText(this, FileWriter.ERR_MSG, Toast.LENGTH_SHORT).show();
                return;
            }

            this.finish();
        });
    }
}
