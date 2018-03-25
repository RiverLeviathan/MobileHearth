package com.example.ladysnake.mobile;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ludwig on 25/03/2018.
 */

public class TabPagerAdapter extends FragmentPagerAdapter {
    List<ResourceAwareFragment> fragments = new ArrayList<ResourceAwareFragment>(){{
        add(0, SearchView.make().withRes(R.drawable.search));
        add(1, EditView.make().withRes(R.drawable.edit));
    }};

    public TabPagerAdapter(FragmentManager fm) { super(fm); }

    @Override
    public ResourceAwareFragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
