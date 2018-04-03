package com.example.ladysnake.mobile.tools;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * A utility class that is used to write to a file
 */
public class FileWriter {
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
        FileOutputStream fs = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
        OutputStreamWriter os = new OutputStreamWriter(fs);
        os.write(content);

        os.flush();
        os.close();
        fs.close();

        return this;
    }

    public static FileWriter from(@NonNull Context context){ return new FileWriter(context); }
}
