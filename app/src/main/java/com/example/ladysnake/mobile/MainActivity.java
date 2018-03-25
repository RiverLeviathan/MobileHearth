package com.example.ladysnake.mobile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static class State{
        ViewPager pager;
        TabLayout tabLayout;

        public State(ViewPager v, TabLayout t){
            this.pager = v;
            this.tabLayout = t;
        }
        public State(View v, View t){
            this(
                (ViewPager)(v),
                (TabLayout)(t)
            );
        }
        public static State from(ViewPager v, TabLayout t){ return new State(v, t); }
        public static State from(View v, View t){ return new State(v, t); }

        public ViewPager getPager() { return pager; }
        public TabLayout getTabLayout() { return tabLayout; }
    }

    protected State state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.state = State.from(
            findViewById(R.id.pager),
            findViewById(R.id.tabs)
        );

        setupView(this.state);
    }

    protected void setupView(State state){
        ViewPager pager = state.getPager();
        TabLayout tabs = state.getTabLayout();

        setupTabs(tabs);
        setupAdapter(state, tabs, pager);
    }

    protected void setupTabs(TabLayout tabs){
        TabLayout.Tab searchTab = tabs.newTab().setIcon(R.drawable.search);//.setCustomView(R.layout.search_view);
        TabLayout.Tab editTab = tabs.newTab().setIcon(R.drawable.edit);//.setCustomView(R.layout.edit_view);
        tabs.addTab(searchTab,0, true);
        tabs.addTab(editTab, 1);
    }

    protected void setupAdapter(State state, TabLayout tabs, ViewPager pager){
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
//        tabs.setupWithViewPager(pager, true);
        setupIcons(state, adapter);
        setupListeners(state, tabs, pager);
        adapter.notifyDataSetChanged();
    }

    protected void setupIcons(State state, TabPagerAdapter adapter){
        TabLayout tabLayout = state.getTabLayout();
        tabLayout.getTabAt(0).setIcon(adapter.getItem(0).getRes());
        tabLayout.getTabAt(1).setIcon(adapter.getItem(1).getRes());
    }

    protected void setupListeners(State state, TabLayout tabs, ViewPager pager){
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            TabLayout.Tab current = tabs.getTabAt(0);

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.equals(current))
                    return;

                current = tab;
                pager.setCurrentItem(tabs.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                tabs.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }
}
