package com.example.ladysnake.mobile;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ladysnake.mobile.tools.ApiAware;
import com.example.ladysnake.mobile.tools.ResourceAwareFragment;
import com.koushikdutta.ion.Ion;

import java.nio.charset.Charset;

/**
 * Created by Ludwig on 24/03/2018.
 */

public class SearchView extends ResourceAwareFragment implements ApiAware{
    public static String TAG = "SearchView";

    String api(String uri){
        return getContext().getString(R.string.api) + uri;
    }

    public static SearchView make(){ return new SearchView(); }

    public static class State{
        protected TextInputEditText nameInput;
        protected ImageButton nameSubmit;
        protected Spinner classeSpinner, typeSpinner, factionSpinner, raceSpinner;

        public State(TextInputEditText i, ImageButton b, Spinner c, Spinner t, Spinner f, Spinner r){
            this.nameInput = i;
            this.nameSubmit = b;
            this.classeSpinner = c;
            this.typeSpinner = t;
            this.factionSpinner = f;
            this.raceSpinner = r;
        }
        public State(View i, View b, View c, View t, View f, View r){
            this(
                ((TextInputEditText) i),
                ((ImageButton) b),
                ((Spinner) c),
                ((Spinner) t),
                ((Spinner) f),
                ((Spinner) r)
            );
        }
        public static State from(TextInputEditText i, ImageButton b, Spinner c, Spinner t, Spinner f, Spinner r){ return new State(i, b, c, t, f, r); }
        public static State from(View i, View b, View c, View t, View f, View r){ return new State(i, b, c, t, f, r); }

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

    protected void setupView(State state) {
        setupNameSearch(state);
        setupClassSearch(state);
        setupTypeSearch(state);
        setupFactionSearch(state);
        setupRaceSearch(state);
    }

    protected void setupNameSearch(State state) {
        TextInputEditText nameInput = state.getNameInput();
        ImageButton submit = state.getNameSubmitButton();

        nameInput.setOnEditorActionListener((v, actionId, event) -> {
            if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
                return submit.performClick();

            return false;
        });

        submit.setOnClickListener(v -> {
            String value = this.state.getNameInput().getText().toString().toLowerCase();
            if(value.isEmpty())
                return;

            String endPoint = api("/cards/search/%name%");
            String url = endPoint.replaceFirst("%name%", Uri.encode(value));

            Ion.with(getContext()).load(url)
            .addHeader(getString(R.string.apiCredentialHeader), getString(R.string.apiCredential))
            .addQuery("locale", getString(R.string.apiLocale))
            .asJsonArray(Charset.forName("utf-8"))
            .then((e, result) -> {
                if(e != null) {
                    Log.e(TAG, e.toString());
                    Toast.makeText(getContext(), getString(R.string.api_fail), Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage(result.toString())
                .setTitle("Result");

                AlertDialog dialog = builder.create();
                dialog.show();
            });
        });
    }

    protected void setupClassSearch(State state){
        Spinner classeSpinner = state.getClasseSpinner();
        classeSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, CLASSES));
    }

    protected void setupTypeSearch(State state) {
        Spinner typeSpinner = state.getTypeSpinner();
        typeSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, TYPES));
    }

    private void setupFactionSearch(State state) {
        Spinner factionSpinner = state.getFactionSpinner();
        factionSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, FACTIONS));
    }

    private void setupRaceSearch(State state) {
        Spinner raceSpinner = state.getRaceSpinner();
        raceSpinner.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, RACES));
    }


    public final static String[] CLASSES = new String[]{
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

    public final static String[] TYPES = new String[]{
            "Minion",
            "Spell",
            "Weapon",
            "Hero"
    };

    public final static String[] FACTIONS = new String[]{};

    public final static String[] RACES = new String[]{
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
