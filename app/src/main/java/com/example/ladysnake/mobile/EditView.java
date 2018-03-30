package com.example.ladysnake.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ladysnake.mobile.tools.ResourceAwareFragment;

/**
 * Created by Ludwig on 24/03/2018.
 */

public class EditView extends ResourceAwareFragment {

    public static EditView make(){ return new EditView(); }

    public static class State{
//        protected Button btn;
//
//        public State(Button b){
//            this.btn = b;
//        }
//        public State(View b){
//            this(
//                    (Button)(b)
//            );
//        }
//        public static State from(Button b){ return new State(b); }
//        public static State from(View b){ return new State(b); }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_view, null);
//        State state = State.from(
//            view.findViewById(R.id.editBtn)
//        );
//        view.setTag(state);
        return view;
    }
}
