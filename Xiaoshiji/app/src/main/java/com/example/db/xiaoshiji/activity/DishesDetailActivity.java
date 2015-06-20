package com.example.db.xiaoshiji.activity;

import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.db.xiaoshiji.R;
import com.github.adnansm.timelytextview.TimelyView;

import java.util.ArrayList;
import java.util.List;

import adapter.DishCommentAdapter;
import beans.CommitInfo;
import beans.PersonalDishesInfo;
import utils.AppConstant;
import utils.LeanCloudService;

public class DishesDetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Toolbar toolbar;
    int[] imageLib = {R.drawable._1, R.drawable._2, R.drawable._3, R.drawable._4, R.drawable._5, R.drawable._6, R.drawable._7, R.drawable._8};
    private TimelyView timelyView;
    private TextView tv_imageNumber,tv_mealname,tv_mealprice,tv_diningroomname;
    private ListView listView;
    private LinearLayout dishesComment;
    private LinearLayout ll_dishesLike;
    private ImageView iv_dishesLike;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    public String mealname;
    public String mealprice;
    public String diningroomname;
    public boolean LIKE_THIS_DISHES=false;

    /*
    db 写的对对话框的控件和监听
     */
    public EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        mealname = bundle.getString("mealname");
        mealprice = bundle.getString("mealprice");
        diningroomname = bundle.getString("diningroomname");
        setContentView(R.layout.activity_dishes_detail);
        initToolBar();
        initView();
        initImage(); //初始like图片是全红心还是变红心
    }

    private void initImage() {
        //通过leancloud 判断是否喜爱这个菜
        if (AVUser.getCurrentUser()!=null)
            LeanCloudService.findIfLikeThisDish(new FindCallback<PersonalDishesInfo>() {
                @Override
                public void done(List<PersonalDishesInfo> list, AVException e) {
                   if (e==null && list.size()!=0){
                        LIKE_THIS_DISHES =true;
                        iv_dishesLike.setImageResource(R.drawable.dishes_like_selected);
                    }
                }
            },AVUser.getCurrentUser().getUsername(),mealname,diningroomname);
        ll_dishesLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AVUser.getCurrentUser()==null)
                {
                    Toast.makeText(DishesDetailActivity.this,"请先登录",Toast.LENGTH_SHORT).show();
                    return;
                }
                //之前不喜欢
                if (!LIKE_THIS_DISHES){
                    //加入leancloud
                    PersonalDishesInfo personalDishesInfo=new PersonalDishesInfo();
                    personalDishesInfo.setDiningRoomName(diningroomname);
                    personalDishesInfo.setMEALNAME(mealname);
                    personalDishesInfo.setMEALPRICE(mealprice);
                    personalDishesInfo.setUser(AVUser.getCurrentUser().getUsername());
                    personalDishesInfo.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                LIKE_THIS_DISHES = true;
                                iv_dishesLike.setImageResource(R.drawable.dishes_like_selected);
                                Toast.makeText(DishesDetailActivity.this, "加入我的私人菜单", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    AVQuery<PersonalDishesInfo> personalDishesInfoAVQuery = AVQuery.getQuery(PersonalDishesInfo.class);
                    personalDishesInfoAVQuery.whereEqualTo("user", AVUser.getCurrentUser().getUsername());
                    personalDishesInfoAVQuery.whereContains("diningroomname", diningroomname);
                    personalDishesInfoAVQuery.whereContains("mealname", mealname);
                    personalDishesInfoAVQuery.deleteAllInBackground(new DeleteCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                LIKE_THIS_DISHES = false;
                                iv_dishesLike.setImageResource(R.drawable.dishes_like_default);
                                Toast.makeText(DishesDetailActivity.this, "取消喜欢成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });
    }


    private void initView() {
        //心like
        iv_dishesLike= (ImageView) findViewById(R.id.iv_dishesLike);

        ll_dishesLike = (LinearLayout) findViewById(R.id.ll_dishLike);

        tv_mealname = (TextView) findViewById(R.id.tv_mealname);
        tv_mealname.setText(mealname);

        tv_diningroomname = (TextView) findViewById(R.id.tv_diningRoomName);
        tv_diningroomname.setText(diningroomname);

        tv_mealprice = (TextView) findViewById(R.id.tv_mealprice);
        tv_mealprice.setText("￥"+mealprice);

        timelyView = (TimelyView) findViewById(R.id.timelyview);
        timelyView.animate(0).start();

        viewPager = (ViewPager) findViewById(R.id.dishes_viewpager);
        viewPager.setAdapter(new MyViewPagerAdpater());
        viewPager.addOnPageChangeListener(new MyViewPagerListener());

        tv_imageNumber = (TextView) findViewById(R.id.dishes_imageNumber);
        tv_imageNumber.setText("/" + (imageLib.length + 1));

        //leancloude获取评论
        listView = (ListView) findViewById(R.id.lv_dish);
        LeanCloudService.findCommitInfos(new FindCallback<CommitInfo>() {
            @Override
            public void done(List<CommitInfo> commit, AVException e) {
                if (e == null) {
                    listView.setAdapter(new DishCommentAdapter(getApplicationContext(), commit));
                } else Toast.makeText(getApplicationContext(), "没有评论惹~", Toast.LENGTH_SHORT).show();
            }
        },diningroomname,mealname);

        builder = new AlertDialog.Builder(this);
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.comment_edit, null);
        editText = (EditText) mCustomView.findViewById(R.id.et_diningRoomComment);
        builder.setTitle("撰写评价");

        builder.setCancelable(false);
        builder.setView(mCustomView);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("提交", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String content = editText.getText().toString().trim();
                if (AVUser.getCurrentUser() != null) {
                    if (content != null) {
                        if (content.length() < 12) {
                            Toast.makeText(DishesDetailActivity.this,"至少12个字",Toast.LENGTH_SHORT).show();
                                    return ;
                        }
                        CommitInfo commitInfo = new CommitInfo();
                        commitInfo.setContent(content);
                        commitInfo.setDate(AppConstant.getCurrentTime());
                        commitInfo.setUser(AVUser.getCurrentUser().getUsername());
                        commitInfo.setDiningRoomName(diningroomname);
                        commitInfo.setMEALNAME(mealname);

                        commitInfo.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "评论成功惹~", Toast.LENGTH_SHORT).show();
                                    LeanCloudService.findCommitInfos(new FindCallback<CommitInfo>() {
                                        @Override
                                        public void done(List<CommitInfo> commit, AVException e) {
                                            if (e == null) {
                                                listView.setAdapter(new DishCommentAdapter(getApplicationContext(), commit));
                                            } else
                                                Toast.makeText(getApplicationContext(), "没有评论惹~", Toast.LENGTH_SHORT).show();
                                        }
                                    }, diningroomname, mealname);
                                } else {
                                    Log.v("error1", e.getMessage());
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "评论不能为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "请先注册惹~", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });

        alertDialog = builder.create();


        dishesComment = (LinearLayout) findViewById(R.id.ll_dishShare);
        dishesComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("菜品详情");
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

    public class MyViewPagerAdpater extends PagerAdapter {

        @Override
        public int getCount() {
            return imageLib.length + 1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(getApplicationContext());
            if (position != getCount() - 1) {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(imageLib[position]);
            } else {
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setImageResource(R.drawable.ic_launcher);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private class MyViewPagerListener implements ViewPager.OnPageChangeListener {
        com.nineoldandroids.animation.ObjectAnimator objectAnimator = timelyView.animate(0, 1);
        int duration = 100;
        int postionNow = 0;
        int postionNext = 1;

        MyViewPagerListener() {
            objectAnimator = timelyView.animate(0, 1);
            objectAnimator.setDuration(duration);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //向右滑动

//            objectAnimator.setCurrentPlayTime((long) (positionOffset*duration));
            Log.v("sjy", "onscrool pos is " + position + "  posoffset is" + positionOffset);

        }

        @Override
        public void onPageSelected(int position) {
            Log.v("sjy", "select pos is" + position);


        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    Log.v("s", "drag");
                    if (objectAnimator.isRunning()) {

                        objectAnimator.setCurrentPlayTime(duration);
                        objectAnimator.cancel();
                    }
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    Log.v("s", "idle");
                    postionNext = viewPager.getCurrentItem();
                    if (postionNow == postionNext)
                        objectAnimator = timelyView.animate(postionNext);
                    else objectAnimator = timelyView.animate(postionNow, postionNext);
                    objectAnimator.setDuration(duration);
                    objectAnimator.start();
                    postionNow = postionNext;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    Log.v("s", "settling");
                    break;
            }
        }
    }
}