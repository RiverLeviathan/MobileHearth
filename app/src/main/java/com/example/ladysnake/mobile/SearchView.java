package com.example.ladysnake.mobile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ladysnake.mobile.activities.DisplayResultList;
import com.example.ladysnake.mobile.tools.ApiAware;
import com.example.ladysnake.mobile.tools.FileWriter;
import com.example.ladysnake.mobile.tools.ResourceAwareFragment;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.builder.Builders;
import com.koushikdutta.ion.builder.LoadBuilder;
import com.koushikdutta.ion.future.ResponseFuture;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A class that handle's the search view's logic
 * @author Ludwig GUERIN
 */
public class SearchView extends ResourceAwareFragment implements ApiAware{
    public final static String TAG = "SearchView";
    public final static String JSON_ARRAY_EXTRA = "JsonObject";
    public final static String FILE_PATH_EXTRA = "filePath";
    public final static String FILE_PATH = "SearchView.JsonArray.json";

    /**
     * An helper method that crafts a complete URL to the API based on a URI
     * @param uri being the URI to craft the URL from
     * @return the complete URL to the correct API endpoint
     */
    String api(String uri){
        return getContext().getString(R.string.api) + uri;
    }

    /**
     * A class that gives access to the relevant UI components
     */
    public static SearchView make(){ return new SearchView(); }

    public static class State{
        protected LoadBuilder<Builders.Any.B> httpClient;
        protected TextInputEditText nameInput;
        protected ImageButton nameSubmit;
        protected Spinner classeSpinner, typeSpinner, factionSpinner, raceSpinner;

        public State(LoadBuilder<Builders.Any.B> http, TextInputEditText i, ImageButton b, Spinner c, Spinner t, Spinner f, Spinner r){
            this.httpClient = http;
            this.nameInput = i;
            this.nameSubmit = b;
            this.classeSpinner = c;
            this.typeSpinner = t;
            this.factionSpinner = f;
            this.raceSpinner = r;
        }
        public State(LoadBuilder<Builders.Any.B> http, View i, View b, View c, View t, View f, View r){
            this(
                http,
                ((TextInputEditText) i),
                ((ImageButton) b),
                ((Spinner) c),
                ((Spinner) t),
                ((Spinner) f),
                ((Spinner) r)
            );
        }
        public static State from(LoadBuilder<Builders.Any.B> http, TextInputEditText i, ImageButton b, Spinner c, Spinner t, Spinner f, Spinner r){ return new State(http, i, b, c, t, f, r); }
        public static State from(LoadBuilder<Builders.Any.B> http, View i, View b, View c, View t, View f, View r){ return new State(http, i, b, c, t, f, r); }

        public LoadBuilder<Builders.Any.B> getHttpClient() { return httpClient; }
        public LoadBuilder<Builders.Any.B> http() { return this.getHttpClient(); }
        public TextInputEditText getNameInput() { return nameInput; }
        public ImageButton getNameSubmitButton() { return nameSubmit; }
        public Spinner getClasseSpinner() { return classeSpinner; }
        public Spinner getTypeSpinner() { return typeSpinner; }
        public Spinner getFactionSpinner() { return factionSpinner; }
        public Spinner getRaceSpinner() { return raceSpinner; }
    }

    protected State state;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_view, null);

        this.state = State.from(
            Ion.with(getContext()),
            view.findViewById(R.id.nameInput),
            view.findViewById(R.id.nameSubmit),
            view.findViewById(R.id.classeSpinner),
            view.findViewById(R.id.typeSpinner),
            view.findViewById(R.id.factionSpinner),
            view.findViewById(R.id.raceSpinner)
        );
        view.setTag(this.state);

        setupView(this.state);
        return view;
    }

//    @Override
//    public void onResume() {
//        setupView(this.state);
//        super.onResume();
//    }

    /**
     * Retrieves the (correctly formatted) text input's value
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     * @return the text input's value
     */
    protected String getTextInputValue(State state){
        return state.getNameInput().getText().toString().toLowerCase().trim();
    }

    /**
     * Determines whether or not the text input has a (correct) value
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     * @return TRUE if it does, FALSE if it doesn't
     */
    protected boolean textInputHasValue(State state){
        return !Pattern.compile("^\\s*$")
        .matcher(this.getTextInputValue(state))
        .matches();
    }


    /**
     * Dispatches a GET request to the specified URL
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     * @param url being the URL to GET from
     * @param queryParams being the additional query string params
     * @param queryArrayParams being the additional query string array params
     * @return a Future that resolves to the desired {@link JsonArray}
     */
    @NonNull
    protected ResponseFuture<JsonArray> dispatchRequest(@NonNull State state, @NonNull String url, @Nullable Map<String, String> queryParams, @Nullable Map<String, List<String>> queryArrayParams){
        Log.v(TAG, "GET@" + url + " w/ " + queryParams + " & " + queryArrayParams);

        Builders.Any.B request = state.http().load(url)
        .addHeader(getString(R.string.apiCredentialHeader), getString(R.string.apiCredential))
        .addQuery(getString(R.string.apiLocaleQueryStringName), getString(R.string.apiLocale));

        if(queryParams != null){
            if(!queryParams.isEmpty()){
                for (Map.Entry<String, String> entry : queryParams.entrySet())
                    request.addQuery(entry.getKey(), entry.getValue());
            }
        }

        if(queryArrayParams != null){
            if(!queryArrayParams.isEmpty())
                request.addQueries(queryArrayParams);
        }

        return request.asJsonArray(Charset.forName("utf-8"));
    }

    /**
     * Dispatches a GET request to the specified URL
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     * @param url being the URL to GET from
     * @return a Future that resolves to the desired {@link JsonArray}
     */
    @NonNull
    protected ResponseFuture<JsonArray> dispatchRequest(@NonNull State state, @NonNull String url){
        return this.dispatchRequest(state, url, null, null);
    }

    /**
     * Dispatches a GET request to the info endpoint
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     * @return a Future that resolves to the desired {@link JsonObject}
     */
    protected ResponseFuture<JsonObject> dispatchInfoRequest(@NonNull State state){
        String url = api("/info");
        Log.v(TAG, "GET@" + url);

        Builders.Any.B request = state.http().load(url)
        .addHeader(getString(R.string.apiCredentialHeader), getString(R.string.apiCredential))
        .addHeader("Accept", "application/json");
//        .addQuery(getString(R.string.apiLocaleQueryStringName), getString(R.string.apiLocale));

        return request.asJsonObject(Charset.forName("utf-8"));
    }

    /**
     * Handles the switching between the {@link MainActivity} and {@link DisplayResultList}
     * @param json being the {@link JsonArray} of cards that will be used by {@link DisplayResultList}
     */
    protected void goShowResults(JsonArray json){
        Intent intent = new Intent(getContext(), DisplayResultList.class);
        intent.setAction(Intent.ACTION_VIEW);
//        intent.putExtra(JSON_ARRAY_EXTRA, json.toString());
        try {
            FileWriter.from(getContext()).writeTo(FILE_PATH, (new Gson()).toJson(json));
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            Toast.makeText(getContext(), FileWriter.ERR_MSG, Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(FILE_PATH_EXTRA, FILE_PATH);

        Log.v(TAG, "Starting activity : DisplayResultList");
        getContext().startActivity(intent);
    }

    /**
     * Configures the view components based on the given {@link State} object
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     */
    protected void setupView(State state) {
//        setupNameSearch(state);
//        setupClassSearch(state);
//        setupTypeSearch(state);
//        setupFactionSearch(state);
//        setupRaceSearch(state);

        this.dispatchInfoRequest(state)
        .then((e, json)->{
            if(e != null) {
                Log.e(TAG, e.toString());
                Toast.makeText(getContext(), getString(R.string.api_fail), Toast.LENGTH_SHORT).show();
                return;
            }

            JsonArray classes = json.getAsJsonArray("classes");
            JsonArray types = json.getAsJsonArray("types");
            JsonArray factions = json.getAsJsonArray("factions");
            JsonArray races = json.getAsJsonArray("races");

            List<String> classesList = new ArrayList<>();
            for(JsonElement classe : classes)
                classesList.add(classe.getAsString());
            CLASSES = classesList.toArray(new String[0]);

            List<String> typesList = new ArrayList<>();
            for(JsonElement type : types)
                typesList.add(type.getAsString());
            TYPES = typesList.toArray(new String[0]);

            List<String> factionsList = new ArrayList<>();
            for(JsonElement faction : factions)
                factionsList.add(faction.getAsString());
            FACTIONS = factionsList.toArray(new String[0]);

            List<String> racesList = new ArrayList<>();
            for(JsonElement race : races)
                racesList.add(race.getAsString());
            RACES = racesList.toArray(new String[0]);


            setupNameSearch(state);
            setupClassSearch(state);
            setupTypeSearch(state);
            setupFactionSearch(state);
            setupRaceSearch(state);
        });
    }

    /**
     * Configures the name criteria
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     */
    protected void setupNameSearch(State state) {
        Log.v(TAG, "Setting up name search");
        TextInputEditText nameInput = state.getNameInput();
        ImageButton submit = state.getNameSubmitButton();

        nameInput.setOnEditorActionListener((v, actionId, event) -> {
            if(event != null){
                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                    return submit.performClick();
            }

            return false;
        });

        submit.setOnClickListener(v -> {
            if(!this.textInputHasValue(state))
                return;

            String value = this.getTextInputValue(state);
            String url = api("/cards/search/%name%").replaceFirst("%name%", Uri.encode(value));

            this.dispatchRequest(state, url)
            .then((e, json) -> {
                if(e != null) {
                    Log.e(TAG, e.getMessage());
                    Log.e(TAG, "" + json);
                    Toast.makeText(getContext(), getString(R.string.api_fail), Toast.LENGTH_SHORT).show();
                    return;
                }

                this.goShowResults(json);
            });
        });
    }

    /**
     * Configures the class criteria
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     */
    protected void setupClassSearch(State state){
        Log.v(TAG, "Setting up class search");
        Spinner classeSpinner = state.getClasseSpinner();
        classeSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, CLASSES));
        classeSpinner.setPrompt("Sélectionnez une classe");

        classeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirst = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isFirst){ //Ne pas déclencher la requête à l'initialisation du composant graphique
                    isFirst = false;
                    return;
                }

                String value = classeSpinner.getSelectedItem().toString();
                String url = api("/cards/classes/%class%").replaceFirst("%class%", Uri.encode(value));
                SearchView.this.dispatchRequest(state, url)
                .then((e, json) -> {
                    if(e != null) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(getContext(), getString(R.string.api_fail), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //TODO: (optional) search by name inside of result
                    SearchView.this.goShowResults(json);
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Configures the type criteria
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     */
    protected void setupTypeSearch(State state) {
        Log.v(TAG, "Setting up type search");
        Spinner typeSpinner = state.getTypeSpinner();
        typeSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, TYPES));
        typeSpinner.setPrompt("Sélectionnez un type");

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirst = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isFirst){
                    isFirst = false;
                    return;
                }

                String value = typeSpinner.getSelectedItem().toString();
                String url = api("/cards/types/%type%").replaceFirst("%type%", Uri.encode(value));
                SearchView.this.dispatchRequest(state, url)
                .then((e, json) -> {
                    if(e != null) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(getContext(), getString(R.string.api_fail), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //TODO: (optional) search by name inside of result
                    SearchView.this.goShowResults(json);
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Configures the faction criteria
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     */
    private void setupFactionSearch(State state) {
        Log.v(TAG, "Setting up faction search");
        Spinner factionSpinner = state.getFactionSpinner();
        factionSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, FACTIONS));
        factionSpinner.setPrompt("Sélectionnez une faction");

        factionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirst = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isFirst){
                    isFirst = false;
                    return;
                }


                String value = factionSpinner.getSelectedItem().toString();
                String url = api("/cards/factions/%faction%").replaceFirst("%faction%", Uri.encode(value));
                SearchView.this.dispatchRequest(state, url)
                .then((e, json) -> {
                    if(e != null) {
                        Log.e(TAG, e.getMessage());
                        Toast.makeText(getContext(), getString(R.string.api_fail), Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //TODO: (optional) search by name inside of result
                    SearchView.this.goShowResults(json);
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    /**
     * Configures the race criteria
     * @param state being the {@link State} to use (as if it was this {@link SearchView}'s {@link State})
     */
    private void setupRaceSearch(State state) {
        Log.v(TAG, "Setting up race search");
        Spinner raceSpinner = state.getRaceSpinner();
        raceSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, RACES));
        raceSpinner.setPrompt("Sélectionnez une race");

        raceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean isFirst = true;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(isFirst){
                    isFirst = false;
                    return;
                }


                String value = raceSpinner.getSelectedItem().toString();
                String url = api("/cards/races/%race%").replaceFirst("%race%", Uri.encode(value));
                SearchView.this.dispatchRequest(state, url)
                        .then((e, json) -> {
                            if(e != null) {
                                Log.e(TAG, e.getMessage());
                                Toast.makeText(getContext(), getString(R.string.api_fail), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //TODO: (optional) search by name inside of result
                            SearchView.this.goShowResults(json);
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    public /*final*/ static String[] CLASSES = new String[]{
            "Druid",
            "Hunter",
            "Mage",
            "Paladin",
            "Priest",
            "Rogue",
            "Shaman",
            "Warlock",
            "Warrior",
            "Death Knight"
    };

    public /*final*/ static String[] TYPES = new String[]{
            "Minion",
            "Spell",
            "Weapon",
            "Hero"
    };

    public /*final*/ static String[] FACTIONS = new String[]{};

    public /*final*/ static String[] RACES = new String[]{
            "Beast",
            "Demon",
            "Dragon",
            "Elemental",
            "Mech",
            "Murloc",
            "Pirate",
            "Totem",
            "General"
    };
}
