package com.example.db.xiaoshiji.activity;

import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.db.xiaoshiji.R;

import fragment.FragmentDiningRoom;
import fragment.FragmentLost;
import utils.AppConstant;

public class ActivityDiningRoomInfo extends AppCompatActivity implements FragmentDiningRoom.OnFragmentInteractionListener{

    public FragmentTransaction fragmentTransaction;
    public Toolbar toolbar;

    public Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_dining_room_info);

        init();

        bundle = this.getIntent().getExtras();

        if (savedInstanceState==null){
            fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);
            FragmentDiningRoom fragmentLost = new FragmentDiningRoom();
            fragmentLost.setArguments(bundle);
            fragmentTransaction.replace(R.id.container_diningroom_info,fragmentLost).commit();

        }

    }

    public void init(){
        //toolBar init
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("发现食堂");
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.actionbar_title_color));
        if (Build.VERSION.SDK_INT>=21)
            toolbar.setElevation(24);
        setSupportActionBar(toolbar);
    }
    public Toolbar getToolbar(){
        return toolbar;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
