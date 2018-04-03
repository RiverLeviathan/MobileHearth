package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileReader {
    protected Context context;

    public FileReader(Context context){
        this.context = context;
    }

    public Context getContext() { return context; }

    public String readFrom(@NonNull String filePath) throws IOException {
        FileInputStream fs = getContext().openFileInput(filePath);
        InputStreamReader reader = new InputStreamReader(fs);

        String content = "";
        int read;
        while((read = reader.read()) != -1)
            content.concat("" + ((char) read));

        return content;
    }
}
