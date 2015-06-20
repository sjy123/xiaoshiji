package com.example.db.xiaoshiji.activity;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

import fragment.DiningRoomInfo_fragmentPos0;
import fragment.DiningRoomInfo_fragmentPos1;
import utils.AppConstant;

public class DiningRoomInfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ImageButton imageButton;
    /*
    db 对应食堂名字和地点
     */
    public Bundle bundle;
    public String diningroomname,diningroomaddress;
    public TextView mName,mAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_room_info);
        initToolBar();

        bundle = this.getIntent().getExtras();
        if (bundle!=null){
            diningroomname = bundle.getString("diningroomname");
            diningroomaddress = bundle.getString("diningroomaddress");
        }

        init();
    }

    private void init() {

        mName = (TextView)findViewById(R.id.textview_name);
        mAddress = (TextView)findViewById(R.id.textview_location);

        mName.setText(diningroomname);
        mAddress.setText(diningroomaddress);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("食堂信息"));
        tabLayout.addTab(tabLayout.newTab().setText("食堂评价"));
        //初始化pos0
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
        DiningRoomInfo_fragmentPos0 diningRoomInfo_fragmentPos0 = new DiningRoomInfo_fragmentPos0();
        Bundle bundle = new Bundle();
        bundle.putString("diningroomname",mName.getText().toString());
        diningRoomInfo_fragmentPos0.setArguments(bundle);
        fragmentTransaction.replace(R.id.diningroom_container, diningRoomInfo_fragmentPos0);
        fragmentTransaction.commit();
        //tabLayout设置监听
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.v("sjy", "selected" + tab.getPosition());
                android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (tab.getPosition() == 0) {

                    fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    DiningRoomInfo_fragmentPos0 diningRoomInfo_fragmentPos0 = new DiningRoomInfo_fragmentPos0();
                    Bundle bundle = new Bundle();
                    bundle.putString("diningroomname",mName.getText().toString());
                    diningRoomInfo_fragmentPos0.setArguments(bundle);
                    fragmentTransaction.replace(R.id.diningroom_container, diningRoomInfo_fragmentPos0);
                    fragmentTransaction.commit();
                } else {

                    fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    fragmentTransaction.replace(R.id.diningroom_container, new DiningRoomInfo_fragmentPos1());
                    fragmentTransaction.commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.v("sjy", "unselected" + tab.getPosition());

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.v("sjy", "reselected" + tab.getPosition());

            }
        });
        //初始化评价按钮
        imageButton = (ImageButton) findViewById(R.id.tb_comment);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DiningRoomInfoActivity.this,DiningRoomCommentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("食堂详情");
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));

        if (Build.VERSION.SDK_INT >= 21)
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
