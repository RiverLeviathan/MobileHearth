package com.example.ladysnake.mobile.tools;

import android.support.annotation.NonNull;

import com.google.gson.JsonObject;

import java.io.Serializable;

/**
 * An interface that describes objects that can be serialized to a {@link JsonObject}
 */
public interface JsonObjectSerializable extends Serializable {
    @NonNull
    public JsonObject toJson();
}
