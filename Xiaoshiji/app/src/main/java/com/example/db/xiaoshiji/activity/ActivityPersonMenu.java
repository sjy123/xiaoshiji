package com.example.db.xiaoshiji.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.db.xiaoshiji.R;

import java.util.List;

import adapter.PersonalDishesListAdpter;
import beans.PersonalDishesInfo;
import utils.AppConstant;
import utils.LeanCloudService;

public class ActivityPersonMenu extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_person_menu);

        initToolBar();
        initView();

    }

    private void initView() {
        listView= (ListView) findViewById(R.id.listview_personalDishes);

        //leancloud 获取我喜爱的菜品
        LeanCloudService.findPersionalDishesInfo(new FindCallback<PersonalDishesInfo>() {
            @Override
            public void done(List<PersonalDishesInfo> list, AVException e) {
                if (e == null) {
                    PersonalDishesListAdpter personalDishesListAdpter=new PersonalDishesListAdpter(list);
                    personalDishesListAdpter.setMyOnItemClickedListener(new PersonalDishesListAdpter.MyOnItemClickedListener() {
                        @Override
                        public void onClick(String diningroomname,String mealname, String mealprice) {
                            Intent intent=new Intent(ActivityPersonMenu.this, DishesDetailActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("diningroomname",diningroomname);
                            bundle.putString("mealname",mealname);
                            bundle.putString("mealprice",mealprice);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    listView.setAdapter(personalDishesListAdpter);
                }
            }
        }, AVUser.getCurrentUser().getUsername());

    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("私人菜单");
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
