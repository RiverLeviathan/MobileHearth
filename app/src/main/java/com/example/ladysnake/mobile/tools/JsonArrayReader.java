package com.example.ladysnake.mobile.tools;


import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.JsonArray;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class JsonArrayReader {
    protected Context context;

    public JsonArrayReader(@NonNull Context context){
        this.context = context;
    }

    public Context getContext(){ return context; }

    public JsonArray readFrom(@NonNull String filePath) throws FileNotFoundException {
        FileInputStream fs = getContext().openFileInput(filePath);
        InputStreamReader reader = new InputStreamReader(fs);

        //TODO: Read from file, cast and return
        return null;
    }
}
