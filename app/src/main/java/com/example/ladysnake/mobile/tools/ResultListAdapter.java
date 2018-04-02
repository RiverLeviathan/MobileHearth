package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.model.Card;

import java.util.List;

/**
 * An {@link ArrayAdapter} used to display a list of cards (destined to be detailed afterwards)
 * @author Ludwig GUERIN
 */
public class ResultListAdapter extends ArrayAdapter<Card> {
    /**
     * A class that gives access to the relevant UI components
     */
    public static class State{
        protected TextView textView;
        protected ImageView icon;

        public State(TextView t, ImageView i){
            this.textView = t;
            this.icon = i;
        }

        public State(View t, View i){
            this(
                (TextView) t,
                (ImageView)i
            );
        }

        public static State from(TextView t, ImageView i){ return new State(t, i); }
        public static State from(View t, View i){ return new State(t, i); }

        public ImageView getIcon() { return icon; }
        public TextView getTextView() { return textView; }
    }

    public ResultListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    public ResultListAdapter(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }
    public ResultListAdapter(@NonNull Context context, int resource, @NonNull Card[] objects) {
        super(context, resource, objects);
    }
    public ResultListAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull Card[] objects) {
        super(context, resource, textViewResourceId, objects);
    }
    public ResultListAdapter(@NonNull Context context, int resource, @NonNull List<Card> objects) {
        super(context, resource, objects);
    }
    public ResultListAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Card> objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        State state;

        if(row == null){
            row = LayoutInflater.from(getContext()).inflate(R.layout.list_item_result, null);
            state = State.from(
                row.findViewById(R.id.card_name),
                row.findViewById(R.id.show_card_details)
            );
            row.setTag(state);
        }else
            state = (State)row.getTag();

        Card card = getItem(position);
        state.getTextView().setText(card.getName());
//        row.setClickable(true);

        return row;
    }
}
