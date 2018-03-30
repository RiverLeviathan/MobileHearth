package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ladysnake.mobile.EditView;
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
        protected ImageButton deckedEditButton;

        public State(ImageButton deckedEditButton){
            this.deckedEditButton = deckedEditButton;
        }
        public State(View deckedEditButton){
            this((ImageButton) (deckedEditButton));
        }
        public static EditView.State from(ImageButton deckedEditButton){ return new EditView.State(deckedEditButton); }
        public static EditView.State from(View deckedEditButton){ return new EditView.State(deckedEditButton); }
    }

}
