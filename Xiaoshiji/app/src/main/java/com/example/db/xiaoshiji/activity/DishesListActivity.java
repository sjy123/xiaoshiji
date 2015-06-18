package com.example.db.xiaoshiji.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.db.xiaoshiji.R;

import adapter.DishesListAdpter;
import utils.AppConstant;

public class DishesListActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    int[] imageLib={R.drawable._1,R.drawable._2,R.drawable._3,R.drawable._4,R.drawable._5,R.drawable._6,R.drawable._7,R.drawable._8};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_list);
        initToolBar();
        init();
    }

    private void init() {
        recyclerView= (RecyclerView)findViewById(R.id.recycler_disheslist);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
//        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        DishesListAdpter dishesListAdpter=new DishesListAdpter(this,imageLib);
        dishesListAdpter.setMyOnItemClickedListener(new DishesListAdpter.MyOnItemClickedListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(DishesListActivity.this,DishesDetailActivity.class));
            }
        });
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(dishesListAdpter);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("食堂菜品");
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
