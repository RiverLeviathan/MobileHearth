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
        protected ImageView deckedEditButton;

        public State(ImageView deckedEditButton){
            this.deckedEditButton = deckedEditButton;
        }
        public State(View deckedEditButton){
            this((ImageView) (deckedEditButton));
        }
        public static EditView.State from(ImageView deckedEditButton){ return new EditView.State(deckedEditButton); }
        public static EditView.State from(View deckedEditButton){ return new EditView.State(deckedEditButton); }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ResultListAdapter.State state;

        if(row == null){
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_item_deck, null);
            state = ResultListAdapter.State.from(
                    row.findViewById(R.id.textView),
                    row.findViewById(R.id.imageButton)
            );
            row.setTag(state);
        }else
            state = (ResultListAdapter.State)row.getTag();

        Deck deck = getItem(position);
        state.getTextView().setText(deck.getName());

        state.getIcon().setOnClickListener();

        return row;
    }

}
