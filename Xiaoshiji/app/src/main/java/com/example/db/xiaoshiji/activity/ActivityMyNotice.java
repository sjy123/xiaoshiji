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

import fragment.FragmentBringMealNotice;
import fragment.FragmentFoundNotice;
import fragment.FragmentLostNotice;
import fragment.FragmentMyNotice;
import utils.AppConstant;

public class ActivityMyNotice extends AppCompatActivity implements FragmentMyNotice.OnFragmentInteractionListener
                                                                 , FragmentLostNotice.OnFragmentInteractionListener
                                                                 , FragmentFoundNotice.OnFragmentInteractionListener
                                                                 , FragmentBringMealNotice.OnFragmentInteractionListener{

    public FragmentTransaction fragmentTransaction;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_my_notice);

        init();

        if (savedInstanceState==null){
            fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);

            fragmentTransaction.replace(R.id.container_my_notice,new FragmentMyNotice()).commit();
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
