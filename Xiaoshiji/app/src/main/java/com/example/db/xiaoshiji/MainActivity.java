package com.example.db.xiaoshiji;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fragment.DiningRoomInfoFragment;
import fragment.DishesListFragment;
import fragment.FragmentAll;
import fragment.FragmentDiningRoom;
import fragment.FragmentFind;
import fragment.FragmentHelpDetails;
import fragment.FragmentMy;
import fragment.FragmentPutForward;
import utils.AppConstant;
import view.RippleBackground;


public class MainActivity extends ActionBarActivity implements View.OnClickListener
                                                                ,FragmentAll.OnFragmentInteractionListener
                                                                ,FragmentFind.OnFragmentInteractionListener
                                                                ,FragmentMy.OnFragmentInteractionListener
                                                                , FragmentDiningRoom.OnFragmentInteractionListener,
        DiningRoomInfoFragment.OnFragmentInteractionListener, DishesListFragment.OnFragmentInteractionListener
                                                                , FragmentPutForward.OnFragmentInteractionListener
                                                                , FragmentHelpDetails.OnFragmentInteractionListener{

    public TextView mTextViewAll,mTextViewFind,mTextViewMy;
    public FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        init();

        if (savedInstanceState==null){
            fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.abc_fade_in,R.anim.abc_fade_out);

            fragmentTransaction.replace(R.id.container,new FragmentAll()).commit();
        }


    }


    public void init(){

        mTextViewAll = (TextView)findViewById(R.id.textview_all);
        mTextViewFind = (TextView)findViewById(R.id.textview_find);
        mTextViewMy = (TextView)findViewById(R.id.textview_my);

        mTextViewAll.setOnClickListener(this);
        mTextViewFind.setOnClickListener(this);
        mTextViewMy.setOnClickListener(this);
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
}
