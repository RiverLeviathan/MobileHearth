package com.example.ladysnake.mobile.tools;

import com.google.gson.JsonArray;

import java.io.Serializable;

/**
 * An interface that describes objects that can be serialized to a {@link JsonArray}
 */
public interface JsonArraySerializable extends Serializable {
    public JsonArray toJson();
}
