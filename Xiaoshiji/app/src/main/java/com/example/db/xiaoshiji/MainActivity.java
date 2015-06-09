package com.example.db.xiaoshiji;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import fragment.DiningRoomInfoFragment;
import fragment.DiningRoomInfo_fragmentPos0;
import fragment.DiningRoomInfo_fragmentPos1;
import fragment.DishesDetailFragment;
import fragment.DishesListFragment;
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
                                                                , DiningRoomInfoFragment.OnFragmentInteractionListener
                                                                , DishesListFragment.OnFragmentInteractionListener
                                                                , FragmentPutForward.OnFragmentInteractionListener
                                                                , FragmentHelpDetails.OnFragmentInteractionListener
                                                                , FragmentSignUp.OnFragmentInteractionListener
                                                                , FragmentAccountInfo.OnFragmentInteractionListener
                                                                , DiningRoomInfo_fragmentPos0.OnFragmentInteractionListener
                                                                , DiningRoomInfo_fragmentPos1.OnFragmentInteractionListener
                                                                , DishesDetailFragment.OnFragmentInteractionListener
                                                                , FragmentSignIn.OnFragmentInteractionListener
                                                                , FragmentBringMeal.OnFragmentInteractionListener
                                                                , FragmentLost.OnFragmentInteractionListener
                                                                , FragmentFound.OnFragmentInteractionListener
                                                                , FragmentPutLost.OnFragmentInteractionListener
                                                                , FragmentLostDetails.OnFragmentInteractionListener{

    public TextView mTextViewAll,mTextViewFind,mTextViewMy;
    public FragmentTransaction fragmentTransaction;
    public Toolbar toolbar;

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
            fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);

            fragmentTransaction.replace(R.id.container,new FragmentAll()).commit();
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
        mTextViewAll = (TextView)findViewById(R.id.textview_all);
        mTextViewFind = (TextView)findViewById(R.id.textview_find);
        mTextViewMy = (TextView)findViewById(R.id.textview_my);

        mTextViewAll.setOnClickListener(this);
        mTextViewFind.setOnClickListener(this);
        mTextViewMy.setOnClickListener(this);
    }
    public Toolbar getToolbar(){
        return toolbar;
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.textview_all:
                fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,new FragmentAll()).commit();
                break;
            case R.id.textview_find:
                fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new FragmentFind()).commit();
                break;
            case R.id.textview_my:
                fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container,new FragmentMy()).commit();
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        toolbar.setSubtitle(null);
        super.onBackPressed();
    }
}
