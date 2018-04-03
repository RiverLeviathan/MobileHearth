package com.example.ladysnake.mobile.tools;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

/**
 * A class that allows you to read a JSON array from a file
 * @author Ludwig GUERIN
 */
public class JsonArrayReader extends FileReader{
    protected Context context;

    public JsonArrayReader(@NonNull Context context) { super(context); }

    public Context getContext(){ return context; }

    /**
     * Reads a file containing a JSON array
     * @param filePath being the path to read from
     * @return the parsed {@link JsonArray}
     * @throws IOException
     */
    public JsonArray readToJson(@NonNull String filePath) throws IOException {
        String content = super.readFrom(filePath);
        return (new Gson()).fromJson(content, JsonArray.class);
    }

    public static JsonArrayReader from (@NonNull Context context){ return new JsonArrayReader(context); }
}
