package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A utility class used to read files
 * @author Ludwig GUERIN
 */
public class FileReader {
    public final static String TAG = "FileReader CUSTOM";
    public final static String ERR_MSG = "Erreur fatale pendant la lecture d'un fichier";
    public final static String BASE_PATH = FileWriter.BASE_PATH;

    protected Context context;

    public FileReader(@NonNull Context context){
        this.context = context;
    }

    public Context getContext() { return this.context; }

    /**
     * Reads data from the given file
     * @param fileName being the path to the file to read from
     * @return this (for chaining purposes)
     * @throws IOException
     */
    public String readFrom(@NonNull String fileName) throws IOException {
        String path = BASE_PATH + fileName;
        FileInputStream fs = context.openFileInput(path);

        final int size = fs.available();
        byte[] buffer = new byte[size];
        fs.read(buffer, 0, size);

        String content = new String(buffer);

        fs.close();
        return content;
    }

    public static FileReader from(@NonNull Context context){ return new FileReader(context); }
}
