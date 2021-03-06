package com.example.ladysnake.mobile.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
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
import com.example.ladysnake.mobile.tools.FileReader;
import com.example.ladysnake.mobile.tools.FileWriter;
import com.example.ladysnake.mobile.tools.JsonObjectReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;

/**
 * @author Ludwig GUERIN
 */
public class DisplayCardDetails extends AppCompatActivity {
    public final static String TAG = "DisplayCardDetails";

    public final static String FILE_PATH_EXTRA = "filePath";
    public final static String FILE_PATH = "DisplayCardDetails.JsonObject.json";

    public static class State{
        protected TextView nameTextView, factionTextView, typeTextView, raceTextView, loreTextView, descriptionTextView;
        protected ImageView imageView;

        public State(TextView n, ImageView i, TextView f, TextView t, TextView r, TextView l, TextView d){
            this.nameTextView = n;
            this.imageView = i;
            this.factionTextView = f;
            this.typeTextView = t;
            this.raceTextView = r;
            this.loreTextView = l;
            this.descriptionTextView = d;
        }
        public State(View n, View i, View f, View t, View r, View l, View d){
            this(
                (TextView)n,
                (ImageView)i,
                (TextView)f,
                (TextView)t,
                (TextView)r,
                (TextView)l,
                (TextView)d
            );
        }
        public static State from(TextView n, ImageView i, TextView f, TextView t, TextView r, TextView l, TextView d){ return new State(n, i, f, t, r, l, d); }
        public static State from(View n, View i, View f, View t, View r, View l, View d){ return new State(n, i, f, t, r, l, d); }

        public TextView getNameTextView() { return nameTextView; }
        public ImageView getImageView() { return imageView; }
        public TextView getFactionTextView() { return factionTextView; }
        public TextView getTypeTextView() { return typeTextView; }
        public TextView getRaceTextView() { return raceTextView; }
        public TextView getLoreTextView() { return loreTextView; }
        public TextView getDescriptionTextView() { return descriptionTextView; }
    }

    protected State state;
    protected CardStatHolder statHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_card_details);//TODO: (optional) Remove this and change the view if it has a description or not

        Log.v(TAG, "Succesfully landed on activity : DisplayCardDetails");

        Intent intent = super.getIntent();
        if(intent == null){
            Log.e(TAG, "Error, started activity without intent");
            Toast.makeText(this, "No intent", Toast.LENGTH_SHORT).show();
            super.finish();
            return;
        }

//        String data = intent.getStringExtra(DisplayResultList.CARD_EXTRA);
        String filePath = intent.getStringExtra(DisplayResultList.FILE_PATH_EXTRA);
        JsonObject data;
        try {
            data = JsonObjectReader.from(this).readAsJsonObject(filePath);
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, FileReader.ERR_MSG, Toast.LENGTH_SHORT).show();
            return;
        }

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
            findViewById(R.id.factionValue),
            findViewById(R.id.typeValue),
            findViewById(R.id.raceValue),
            findViewById(R.id.loreValue),
            findViewById(R.id.descriptionValue)
        );

        this.setupView(this.state, this.statHolder);
    }

    String api(String uri){
        return this.getString(R.string.api) + uri;
    }

    protected ResponseFuture<JsonArray> dispatchInfoRequest(CardStatHolder statHolder){
        String id = Uri.encode(statHolder.getId());
        String url = api("/cards/%id%").replaceFirst("%id%", id);
        return Ion.with(this).load(url)
        .addHeader(getString(R.string.apiCredentialHeader), getString(R.string.apiCredential))
        .addHeader(getString(R.string.acceptHeader), getString(R.string.acceptHeaderValue))
        .addQuery(getString(R.string.apiLocaleQueryStringName), getString(R.string.apiLocale))
        .asJsonArray(Charset.forName("utf-8"));

    }



    public void addToDeck(View view) {
        Intent intent = new Intent(this, AddToDeckActivity.class);
        intent.setAction(Intent.ACTION_VIEW);

        try {
            FileWriter.from(this).writeTo(FILE_PATH, this.statHolder.getCard().toJson().toString());
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, FileReader.ERR_MSG, Toast.LENGTH_SHORT).show();
            return;
        }

        intent.putExtra(FILE_PATH_EXTRA, FILE_PATH);
        this.startActivity(intent);
    }


    protected void setupView(State state, CardStatHolder statHolder){
        state.getNameTextView().setText(statHolder.getName());
        Picasso.get().load(statHolder.getImgUrl())
        .error(R.drawable.error)
        .placeholder(R.drawable.progress_animation)
        .into(state.getImageView());

        this.setupValues(state, statHolder);
    }

    protected void setupValues(State state, CardStatHolder statHolder) {
        this.dispatchInfoRequest(statHolder)
        .then((e, jsonArray)->{
            if(e != null) {
                Log.e(TAG, e.getMessage());
                Toast.makeText(this, getString(R.string.api_fail), Toast.LENGTH_SHORT).show();
                return;
            }

            JsonObject json = jsonArray.get(0).getAsJsonObject();

            state.getFactionTextView().setText(convertStat(json.get(FACTION)));
            state.getTypeTextView().setText(convertStat(json.get(TYPE)));
            state.getRaceTextView().setText(convertStat(json.get(RACE)));
            state.getLoreTextView().setText(convertStat(json.get(LORE)));
            state.getDescriptionTextView().setText(
                Html.fromHtml(
                    convertStat(statHolder.getDescription()).replace("\n", "<br/>")
                )
            );
        });
    }

    private String convertStat(JsonElement element){
        return convertStat(String.valueOf(element));
    }

    private String convertStat(String value){
        if(value == null || value == "null")
            return "∅";

        return value.replaceFirst("^\"(.*)\"$", "$1");
    }

    public final static String FACTION = "playerClass";//"faction";
    public final static String TYPE = "type";
    public final static String RACE = "race";
    public final static String LORE = "flavor";
}
