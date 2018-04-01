package com.example.ladysnake.mobile.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ladysnake.mobile.R;
import com.example.ladysnake.mobile.model.Card;
import com.example.ladysnake.mobile.model.CardFactory;
import com.example.ladysnake.mobile.model.CardStatHolder;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.Map;
import java.util.Set;

public class DisplayCardDetails extends AppCompatActivity {
    public final static String TAG = "DisplayCardDetails";

    public static class State{
        protected TextView nameTextView, descriptionTextView;
        protected ImageView imageView;
        protected TableLayout statsTable;

        public State(TextView n, ImageView i, TableLayout t, TextView d){
            this.nameTextView = n;
            this.imageView = i;
            this.statsTable = t;
            this.descriptionTextView = d;
        }
        public State(View n, View i, View t, View d){
            this(
                (TextView)n,
                (ImageView)i,
                (TableLayout)t,
                (TextView)d
            );
        }
        public static State from(TextView n, ImageView i, TableLayout t, TextView d){ return new State(n, i, t, d); }
        public static State from(View n, View i, View t, View d){ return new State(n, i, t, d); }

        public TextView getNameTextView() { return nameTextView; }
        public TextView getDescriptionTextView() { return descriptionTextView; }
        public ImageView getImageView() { return imageView; }
        public TableLayout getStatsTable() { return statsTable; }
    }

    protected State state;
    protected CardStatHolder statHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_card_details);//TODO: (optional) Remove this and change the view if it has a description or not

        Intent intent = super.getIntent();
        if(intent == null){
            Log.e(TAG, "Error, started activity without intent");
            Toast.makeText(this, "No intent", Toast.LENGTH_SHORT).show();
            super.finish();
            return;
        }

        String data = intent.getStringExtra(DisplayResultList.CARD_EXTRA);
        Card card = CardFactory.from(data);
        if(card == null){
            Log.v(TAG, "Attempted to create a card from an invalid String");
            super.finish();
            return;
        }

        Log.v(TAG, "Successfully used a Card : " + card.toJson().toString());

        this.statHolder = CardStatHolder.from(card);
        this.state = State.from(
            findViewById(R.id.cardName),
            findViewById(R.id.cardImage),
            findViewById(R.id.statsTable),
            findViewById(R.id.cardDescription)
        );

        this.setupView(this.state, this.statHolder);
    }

    protected void setupView(State state, CardStatHolder statHolder){
        state.getNameTextView().setText(statHolder.getName());
        Picasso.get().load(statHolder.getImgUrl()).into(state.getImageView());
        if(statHolder.hasDescription())
            state.getDescriptionTextView().setText(statHolder.getName());

        this.setupTable(state, statHolder);
    }

    @SuppressLint("ResourceAsColor")
    protected void setupTable(State state, CardStatHolder statHolder){
        Map<String, String> stats = statHolder.getStats();
        Set<String> tableHeaders = stats.keySet();
        TableLayout table = state.getStatsTable();

        TableRow headersRow = new TableRow(this);
        TableRow dataRow = new TableRow(this);
        for(String header : tableHeaders) {
            String data = stats.get(header);

            TextView headerView = new TextView(this);
            headerView.setText(header);
//            headerView.setTextAppearance(R.style.TextAppearance_AppCompat_Headline);
            headerView.setTextColor(R.color.text);
            headersRow.addView(headerView);


            TextView dataView = new TextView(this);
            dataView.setText(data);
            dataView.setTextColor(R.color.text);
            dataRow.addView(dataView);
        }
        table.addView(headersRow);
        table.addView(dataRow);
    }
}
