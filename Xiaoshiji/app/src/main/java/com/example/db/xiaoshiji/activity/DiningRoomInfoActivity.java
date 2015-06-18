package com.example.db.xiaoshiji.activity;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.db.xiaoshiji.R;

import fragment.DiningRoomInfo_fragmentPos0;
import fragment.DiningRoomInfo_fragmentPos1;
import utils.AppConstant;

public class DiningRoomInfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_room_info);
        initToolBar();
        init();
    }

    private void init() {
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("食堂信息"));
        tabLayout.addTab(tabLayout.newTab().setText("食堂评价"));
        //初始化pos0
        android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
        fragmentTransaction.replace(R.id.diningroom_container, new DiningRoomInfo_fragmentPos0());
        fragmentTransaction.commit();
        //tabLayout设置监听
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.v("sjy", "selected" + tab.getPosition());
                android.support.v4.app.FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                if (tab.getPosition()==0)
                {

                    fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    fragmentTransaction.replace(R.id.diningroom_container, new DiningRoomInfo_fragmentPos0());
                    fragmentTransaction.commit();
                }else{

                    fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);
                    fragmentTransaction.replace(R.id.diningroom_container, new DiningRoomInfo_fragmentPos1());
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.v("sjy","unselected"+tab.getPosition());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.v("sjy","reselected"+tab.getPosition());

            }
        });
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("食堂详情");
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));

        if (Build.VERSION.SDK_INT>=21)
            toolbar.setElevation(24);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
