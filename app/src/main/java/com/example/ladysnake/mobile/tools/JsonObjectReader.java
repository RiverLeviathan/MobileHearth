package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

/**
 * @author Ludwig GUERIN
 */
public class JsonObjectReader extends FileReader {
    public JsonObjectReader(@NonNull Context context) { super(context); }

    /**
     * Reads a file that contains a JSON object
     * @param filePath being the path to read from
     * @return the parsed {@link JsonObject}
     * @throws IOException
     */
    public JsonObject readAsJsonObject(@NonNull String filePath) throws IOException {
        String content = super.readFrom(filePath);
        return (new Gson()).fromJson(content, JsonObject.class);
    }

    public static JsonObjectReader from(@NonNull Context context){ return new JsonObjectReader(context); }
}
