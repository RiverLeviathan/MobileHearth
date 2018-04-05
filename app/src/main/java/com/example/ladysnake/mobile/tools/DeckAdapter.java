package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ladysnake.mobile.EditView;
import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.model.Card;
import com.example.ladysnake.mobile.model.Deck;

import java.util.List;

public class DeckAdapter extends ArrayAdapter<Deck>{
    public DeckAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public DeckAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public DeckAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull Deck[] objects) {
        super(context, resource, objects);
    }

    public DeckAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull Deck[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public DeckAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Deck> objects) {
        super(context, resource, objects);
    }

    public DeckAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<Deck> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public static class State{
        protected TextView cardName;
        protected ImageButton edit, delete;

        public State(TextView t, ImageButton e, ImageButton d){
            this.cardName = t;
            this.edit = e;
            this.delete = d;
        }
        public State(View t, View e, View d){
            this(
                (TextView)t,
                (ImageButton)e,
                (ImageButton)d
            );
        }
        public static State from(TextView t, ImageButton e, ImageButton d){ return new State(t, e, d); }
        public static State from(View t, View e, View d){ return new State(t, e, d); }

        public TextView getCardName() { return cardName; }
        public ImageButton getEdit() { return edit; }
        public ImageButton getDelete() { return delete; }
    }

    protected EditView editView;

    public DeckAdapter setEditView(@NonNull EditView editView){
        this.editView = editView;
        return this;
    }

    public EditView getEditView() { return editView; }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        State state;

        if(row == null){
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_item_deck, null);
            state = State.from(
                    row.findViewById(R.id.textView),
                    row.findViewById(R.id.imageButton),
                    row.findViewById(R.id.imageButton2)
            );
            row.setTag(state);
        }else
            state = (State)row.getTag();

        Deck deck = getItem(position);
        state.getCardName().setText(deck.getName());

        state.getEdit().setOnClickListener(view -> getEditView().goEditDeck(deck.toJson()));

        state.getDelete().setOnClickListener(view ->  getEditView().deleteDeck(deck));

        return row;
    }

}
