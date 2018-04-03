package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

/**
 * A class that describes cards w/o image
 * @author Ludwig GUERIN
 */
public class ImageLessCard extends Card {
    public final static String DEFAULT_URL = "https://www.google.fr";

    public ImageLessCard(@NonNull String id, @NonNull String name, @NonNull Integer manaCost) {
        super(id, name, manaCost, DEFAULT_URL);
    }
}
