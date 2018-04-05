package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ladysnake.mobile.EditView;
import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.activities.DisplayCardDetails;
import com.example.ladysnake.mobile.activities.DisplayDeckCards;
import com.example.ladysnake.mobile.activities.DisplayResultList;
import com.example.ladysnake.mobile.model.Card;
import com.example.ladysnake.mobile.model.Deck;
import com.example.ladysnake.mobile.model.DeckList;

import java.io.IOException;
import java.util.List;

/**
 * @author Ludwig GUERIN
 */
public class DeckCardsAdapter extends ArrayAdapter<Card> {
    public final static String FILE_PATH_EXTRA = DisplayResultList.FILE_PATH_EXTRA;
    public final static String FILE_PATH = DisplayResultList.FILE_PATH;

    public final static String TAG = "DeckCardsAdapter";

    public static class State{
        protected TextView cardNameTextView;
        protected ImageButton detailsBtn, deleteBtn;

        public State(TextView c, ImageButton det, ImageButton del){
            this.cardNameTextView = c;
            this.detailsBtn = det;
            this.deleteBtn = del;
        }

        public State(View c, View det, View del){
            this(
                (TextView)c,
                (ImageButton)det,
                (ImageButton)del
            );
        }

        public static State from(TextView c, ImageButton det, ImageButton del){ return new State(c, det, del); }
        public static State from(View c, View det, View del){ return new State(c, det, del); }

        public TextView getCardNameTextView() { return cardNameTextView; }
        public ImageButton getDetailsBtn() { return detailsBtn; }
        public ImageButton getDeleteBtn() { return deleteBtn; }
    }

    protected Deck deck;
    protected DisplayDeckCards activity;

    public DeckCardsAdapter setDisplayDeckCards(@NonNull DisplayDeckCards activity){
        this.activity = activity;
        return this;
    }

    public DisplayDeckCards getDisplayDeckCards(){ return activity; }


    public DeckCardsAdapter(@NonNull Context context, int resource) { super(context, resource); }
    public DeckCardsAdapter(@NonNull Context context, int resource, int textViewResourceId) { super(context, resource, textViewResourceId); }
    public DeckCardsAdapter(@NonNull Context context, int resource, @NonNull Card[] objects) { super(context, resource, objects); }
    public DeckCardsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Card[] objects) { super(context, resource, textViewResourceId, objects); }
    public DeckCardsAdapter(@NonNull Context context, int resource, @NonNull List<Card> objects) { super(context, resource, objects); }
    public DeckCardsAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Card> objects) { super(context, resource, textViewResourceId, objects); }


    public DeckCardsAdapter setDeck(Deck deck){
        this.deck = deck;
        return this;
    }

    public Deck getDeck() { return deck; }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        State state;

        if(row == null){
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_item_deck_edit, null);
            state = State.from(
                row.findViewById(R.id.card_name),
                row.findViewById(R.id.detailsBtn),
                row.findViewById(R.id.removeBtn)
            );
            row.setTag(state);
        }else
            state = (State)row.getTag();

        Card card = getItem(position);
        state.getCardNameTextView().setText(card.getName());

        state.getDetailsBtn().setOnClickListener(view -> this.goShowDetails(card));
        state.getDeleteBtn().setOnClickListener(view -> this.goDeleteCard(card));

        return row;
    }

    protected void goShowDetails(Card card){
        Log.v(TAG, "Showing details for " + card.getName() + "@" + card.getId());
        Intent intent = new Intent(getContext(), DisplayCardDetails.class);
        intent.setAction(Intent.ACTION_VIEW);

        try {
            FileWriter.from(getContext()).writeTo(FILE_PATH, card.toJson().toString());
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            Toast.makeText(getContext(), FileWriter.ERR_MSG, Toast.LENGTH_SHORT).show();
            return;
        }

        intent.putExtra(FILE_PATH_EXTRA, FILE_PATH);
        getContext().startActivity(intent);
    }

    protected void goDeleteCard(Card card){
        Log.v(TAG, "Deleting " + card.getName() + "@" + card.getId());
        DeckList deckList = null;
        deckList = DeckList.fromFile(EditView.DECKLIST_FILEPATH, getContext());

        deckList.removeDeck(this.deck);
        this.deck.removeCard(card);
        deckList.addDeck(this.deck);

        try {
            FileWriter.from(getContext()).writeTo(EditView.DECKLIST_FILEPATH, deckList.toJson().toString());
            getDisplayDeckCards().setupView(getDisplayDeckCards().getState(), this.deck);
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            Toast.makeText(getContext(), FileWriter.ERR_MSG, Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
