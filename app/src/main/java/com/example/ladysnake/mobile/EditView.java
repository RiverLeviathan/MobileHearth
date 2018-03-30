package com.example.ladysnake.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ladysnake.mobile.tools.ResourceAwareFragment;

/**
 * Created by Ludwig on 24/03/2018.
 */

public class EditView extends ResourceAwareFragment {

    public static EditView make(){ return new EditView(); }

    public static class State{
        protected ListView decklist;

        public State(ListView decklist){
            this.decklist = decklist;
        }
        public State(View decklist){
            this((ListView) (decklist));
        }
        public static State from(ListView decklist){ return new State(decklist); }
        public static State from(View decklist){ return new State(decklist); }
    }

    protected State state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_view, null);
        this.state = State.from(view.findViewById(R.id.decklist));
        view.setTag(state);
        return view;
    }

    public void setupView(State state) {

    }
}
