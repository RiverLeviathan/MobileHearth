package com.example.ladysnake.mobile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Ludwig on 24/03/2018.
 */

public class SearchView extends ResourceAwareFragment{

    public static SearchView make(){ return new SearchView(); }

    public static class State{
        protected Button btn;

        public State(Button b){
            this.btn = b;
        }
        public State(View b){
            this(
                (Button)(b)
            );
        }
        public static State from(Button b){ return new State(b); }
        public static State from(View b){ return new State(b); }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_view, null);
        State state = State.from(
            view.findViewById(R.id.searchBtn)
        );
        view.setTag(state);
        return view;
    }
}
