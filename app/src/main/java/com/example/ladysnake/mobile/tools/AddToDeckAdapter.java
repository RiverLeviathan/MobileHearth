package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.model.Deck;

import java.util.List;

/**
 * @author Ludwig GUERIN
 */
public class AddToDeckAdapter extends ArrayAdapter<Deck> {
    public AddToDeckAdapter(@NonNull Context context, int resource) { super(context, resource); }
    public AddToDeckAdapter(@NonNull Context context, int resource, int textViewResourceId) { super(context, resource, textViewResourceId); }
    public AddToDeckAdapter(@NonNull Context context, int resource, @NonNull Deck[] objects) { super(context, resource, objects); }
    public AddToDeckAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Deck[] objects) { super(context, resource, textViewResourceId, objects); }
    public AddToDeckAdapter(@NonNull Context context, int resource, @NonNull List<Deck> objects) { super(context, resource, objects); }
    public AddToDeckAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Deck> objects) { super(context, resource, textViewResourceId, objects); }

    public static class State{
        protected CheckBox checkBox;

        public State(CheckBox c){
            this.checkBox = c;
        }

        public State(View c){
            this(
                ((CheckBox) c)
            );
        }

        public static State from(CheckBox c){  return new State(c); }
        public static State from(View c){  return new State(c); }

        public CheckBox getCheckBox() { return checkBox; }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        State state;

        if(row == null){
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_item_add_to_deck, null);
            state = State.from(
                row.findViewById(R.id.checkBox)
            );
            row.setTag(state);
        }else
            state = (State)row.getTag();

        Deck deck = getItem(position);
        state.getCheckBox().setText(deck.getName());
//        row.setClickable(true);

        return row;
    }
}
