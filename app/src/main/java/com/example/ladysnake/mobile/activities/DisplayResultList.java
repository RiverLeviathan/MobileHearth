package com.example.ladysnake.mobile.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ladysnake.mobile.R;

public class DisplayResultList extends AppCompatActivity {
    public static class State{}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result_list);
    }
}
