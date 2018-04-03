package com.example.ladysnake.mobile.model;

import android.support.annotation.NonNull;

public class ImageLessDecribedCard extends DescribedCard{
    public final static String DEFAULT_URL = ImageLessCard.DEFAULT_URL;

    public ImageLessDecribedCard(@NonNull String id, @NonNull String name, @NonNull Integer manaCost, @NonNull String description) {
        super(id, name, manaCost, DEFAULT_URL, description);
    }
}
