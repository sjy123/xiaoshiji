package com.example.db.xiaoshiji;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import beans.DishMenuInfo;
import database.MyDataBaseHelper;
import fragment.DiningRoomInfo_fragmentPos0;
import fragment.DiningRoomInfo_fragmentPos1;
import fragment.FragmentAccountInfo;
import fragment.FragmentAll;
import fragment.FragmentBringMeal;
import fragment.FragmentDiningRoom;
import fragment.FragmentFind;
import fragment.FragmentFound;
import fragment.FragmentHelpDetails;
import fragment.FragmentLost;
import fragment.FragmentLostDetails;
import fragment.FragmentPutLost;
import fragment.FragmentSignIn;
import fragment.FragmentSignUp;
import fragment.FragmentMy;
import fragment.FragmentPutForward;
import utils.AppConstant;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
                                                                ,FragmentAll.OnFragmentInteractionListener
                                                                ,FragmentFind.OnFragmentInteractionListener
                                                                ,FragmentMy.OnFragmentInteractionListener
                                                                , FragmentDiningRoom.OnFragmentInteractionListener
                                                                , FragmentPutForward.OnFragmentInteractionListener
                                                                , FragmentHelpDetails.OnFragmentInteractionListener
                                                                , FragmentSignUp.OnFragmentInteractionListener
                                                                , FragmentAccountInfo.OnFragmentInteractionListener
                                                                , DiningRoomInfo_fragmentPos0.OnFragmentInteractionListener
                                                                , DiningRoomInfo_fragmentPos1.OnFragmentInteractionListener
                                                                , FragmentSignIn.OnFragmentInteractionListener
                                                                , FragmentBringMeal.OnFragmentInteractionListener
                                                                , FragmentLost.OnFragmentInteractionListener
                                                                , FragmentFound.OnFragmentInteractionListener
                                                                , FragmentPutLost.OnFragmentInteractionListener
                                                                , FragmentLostDetails.OnFragmentInteractionListener{

    public TextView mTextViewAll,mTextViewFind,mTextViewMy;
    public Toolbar toolbar;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        存储本地账户的相关信息
        包括姓名、登陆状态
        在初始的时候显示
         */
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.uniBoys.Xiaoshiji", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("NAME","未登录惹~");
        editor.putBoolean("STATUS",false);
        editor.commit();

        init();

        if (savedInstanceState==null){
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);
            fragmentTransaction.replace(R.id.container,new FragmentAll()).commit();
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.v("sjy", "selected" + tab.getPosition());

                if (tab.getPosition()==0)
                {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out);
                    fragmentTransaction.replace(R.id.container, new FragmentAll());
                    fragmentTransaction.commit();
                }else if (tab.getPosition()==1){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);
                    fragmentTransaction.replace(R.id.container, new FragmentFind());
                    fragmentTransaction.commit();
                }else {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);
                    fragmentTransaction.replace(R.id.container, new FragmentMy());
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
        AppConstant.readTxt(getApplicationContext());
//        Log.v("jbjb0",String.valueOf(readTxt().size()));
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

        //设置tabLayout
        tabLayout = (TabLayout)findViewById(R.id.tablayout);

        tabLayout.addTab(tabLayout.newTab().setCustomView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_item0,null)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_item1, null)));
        tabLayout.addTab(tabLayout.newTab().setCustomView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_item2, null)));

//        mTextViewAll = (TextView)findViewById(R.id.textview_all);
//        mTextViewFind = (TextView)findViewById(R.id.textview_find);
//        mTextViewMy = (TextView)findViewById(R.id.textview_my);
//
//        mTextViewAll.setTextColor(this.getResources().getColor(R.color.actionbar));
//
//        mTextViewAll.setOnClickListener(this);
//        mTextViewFind.setOnClickListener(this);
//        mTextViewMy.setOnClickListener(this);
    }
    public Toolbar getToolbar(){
        return toolbar;
    }
    @Override
    public void onClick(View view) {

//        switch (view.getId()){
//            case R.id.textview_all:
//
//                mTextViewAll.setTextColor(this.getResources().getColor(R.color.actionbar));
//                mTextViewFind.setTextColor(this.getResources().getColor(R.color.indicator));
//                mTextViewMy.setTextColor(this.getResources().getColor(R.color.indicator));
//
//                fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container,new FragmentAll()).commit();
//
//                break;
//            case R.id.textview_find:
//
//                mTextViewFind.setTextColor(this.getResources().getColor(R.color.actionbar));
//                mTextViewAll.setTextColor(this.getResources().getColor(R.color.indicator));
//                mTextViewMy.setTextColor(this.getResources().getColor(R.color.indicator));
//
//                fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container, new FragmentFind()).commit();
//
//                break;
//            case R.id.textview_my:
//
//                mTextViewMy.setTextColor(this.getResources().getColor(R.color.actionbar));
//                mTextViewAll.setTextColor(this.getResources().getColor(R.color.indicator));
//                mTextViewFind.setTextColor(this.getResources().getColor(R.color.indicator));
//
//                fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.container,new FragmentMy()).commit();
//
//                break;
//        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        toolbar.setSubtitle(null);
        super.onBackPressed();
    }



}
