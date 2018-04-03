package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * A utility class that is used to write to a file
 */
public class FileWriter {
    public final static String TAG = "FileWriter CUSTOM";
    public final static String ERR_MSG = "Erreur fatale pendant l'écriture d'un fichier";
    public final static String BASE_PATH = "";//Environment.getExternalStorageDirectory() + File.separator;

    protected Context context;

    public FileWriter(@NonNull Context context){
        this.context = context;
    }

    public Context getContext() { return context; }

    /**
     * Writes the given data to the given file
     * @param fileName being the path to the file to write into
     * @param content being the new content of the given file
     * @return this (for chaining purposes)
     * @throws IOException
     */
    public FileWriter writeTo(@NonNull String fileName, @NonNull String content) throws IOException {
        String path = BASE_PATH + fileName;
        FileOutputStream fs = this.getContext().openFileOutput(path, Context.MODE_PRIVATE);
        fs.write(content.getBytes(), 0, content.getBytes().length);
        Log.v(TAG, "Wrote to "+fileName);
        fs.close();

        return this;
    }

    public static FileWriter from(@NonNull Context context){ return new FileWriter(context); }
}
