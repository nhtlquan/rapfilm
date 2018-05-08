package com.example.rapfilm;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rapfilm.model.Collection;
import com.example.rapfilm.model.CollectionSimple;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HotActivity extends AppCompatActivity {
    private LinkedHashMap<String, Fragment> mapFragments;
    private SmartTabLayout viewPagerTab;
    private ViewGroup tab;
    private ViewPager pager;
    private String item_type;
    private TextView title;
    private ArrayList<CollectionSimple> collections;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        title = findViewById(R.id.title);
        pager = (ViewPager) findViewById(R.id.viewpager1);
        tab = (ViewGroup) findViewById(R.id.tab);
        View view = LayoutInflater.from(this).inflate(R.layout.demo_custom_tab_icon_and_notification_mark_bottom, tab, false);
        tab.addView(view);
        viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        mapFragments = new LinkedHashMap<String, Fragment>();
        getData();
    }

    private void getData() {
        title.setText(getIntent().getStringExtra("title"));
        this.item_type = getIntent().getStringExtra("itemType");
        this.collections = (ArrayList<CollectionSimple>) getIntent().getSerializableExtra("collections");
        for (CollectionSimple collection : collections) {
            mapFragments.put(collection.getName(), CategoryItemFragment.newInstance(collection.getId(), item_type));
        }
        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(this, getSupportFragmentManager(),
                mapFragments);
        pager.setOffscreenPageLimit(1);
        pager.setAdapter(tabFragmentAdapter);
        viewPagerTab.setViewPager(pager);
    }

    @Override
    public void onBackPressed() {
        for (int i = 0; i < viewPagerTab.getChildCount(); i++) {
            if (getCurrentFocus() == viewPagerTab.getTabAt(i))
                super.onBackPressed();
            else
                viewPagerTab.getTabAt(i).requestFocus();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
