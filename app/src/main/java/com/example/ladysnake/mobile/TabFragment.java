package com.example.ladysnake.mobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TabFragment extends Fragment {
    protected TabLayout.Tab tab;

    public TabFragment withTab(TabLayout.Tab tab){
        this.tab = tab;
        return this;
    }

    public static TabFragment make(){ return new TabFragment(); }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(this.tab == null)
            return null;

        return this.tab.getCustomView();
    }
}
