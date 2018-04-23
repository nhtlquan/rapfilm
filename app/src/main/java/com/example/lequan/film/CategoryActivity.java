package com.example.lequan.film;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.LinkedHashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CategoryActivity extends AppCompatActivity {
    private LinkedHashMap<String, Fragment> mapFragments;
    private SmartTabLayout viewPagerTab;
    private ViewGroup tab;
    private ViewPager pager;
    private String item_type;
    private TextView title;

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
        mapFragments.put(getString(R.string.topVote), CategoryItemFragment.newInstance("topVote", item_type));
        mapFragments.put(getString(R.string.topDownload), CategoryItemFragment.newInstance("topDownload", item_type));
        mapFragments.put(getString(R.string.topIMDb), CategoryItemFragment.newInstance("topIMDb", item_type));
        TabFragmentAdapter tabFragmentAdapter = new TabFragmentAdapter(this, getSupportFragmentManager(),
                mapFragments);
        pager.setAdapter(tabFragmentAdapter);
        viewPagerTab.setViewPager(pager);
    }

    @Override
    public void onBackPressed() {
        for (int i = 0; i < viewPagerTab.getChildCount(); i++) {
            if (getCurrentFocus() != viewPagerTab.getTabAt(i)) {
                tab.requestFocus();
                return;
            }
        }
        super.onBackPressed();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
