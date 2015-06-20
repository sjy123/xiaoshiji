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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.db.xiaoshiji.R;
import com.github.adnansm.timelytextview.TimelyView;

import adapter.DishCommentAdapter;
import beans.CommitInfo;
import utils.AppConstant;
import utils.LeanCloudService;

public class DishesDetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Toolbar toolbar;
    int[] imageLib={R.drawable._1,R.drawable._2,R.drawable._3,R.drawable._4,R.drawable._5,R.drawable._6,R.drawable._7,R.drawable._8};
    private int currentPos=0;
    private TimelyView timelyView;
    private TextView tv_imageNumber;
    private ListView listView;
    private ImageButton dishesComment;
    AlertDialog.Builder builder;
    AlertDialog alertDialog;

    /*
    db 写的对对话框的控件和监听
     */
    public EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true,this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes_detail);
        initToolBar();
        initView();
    }



    private void initView() {
        timelyView = (TimelyView) findViewById(R.id.timelyview);
        timelyView.animate(0).start();

        viewPager = (ViewPager) findViewById(R.id.dishes_viewpager);
        viewPager.setAdapter(new MyViewPagerAdpater());
        viewPager.addOnPageChangeListener(new MyViewPagerListener());

        tv_imageNumber= (TextView) findViewById(R.id.dishes_imageNumber);
        tv_imageNumber.setText("/" + (imageLib.length + 1));

        listView= (ListView) findViewById(R.id.lv_dish);
        if (LeanCloudService.findCommitInfos()!=null){
            listView.setAdapter(new DishCommentAdapter(getApplicationContext(),LeanCloudService.findCommitInfos()));
        }else {
            Toast.makeText(getApplicationContext(),"没有评论惹~",Toast.LENGTH_SHORT).show();
        }

        builder = new AlertDialog.Builder(this);
        View mCustomView = LayoutInflater.from(this).inflate(R.layout.comment_edit,null);
        editText = (EditText)mCustomView.findViewById(R.id.et_diningRoomComment);
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
                if (AVUser.getCurrentUser()!=null){
                    if (content!=null){
                        CommitInfo commitInfo = new CommitInfo();
                        commitInfo.setContent(content);
                        commitInfo.setDate(AppConstant.getCurrentTime());
                        commitInfo.setUser(AVUser.getCurrentUser().getUsername());
                        /*
                        db 评价的食堂的名字，由于菜谱暂时没搞好，这个先只填东一吧
                         */
                        commitInfo.setDiningRoomName("东一");
                        commitInfo.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e==null){
                                    Toast.makeText(getApplicationContext(),"评论成功惹~",Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.v("error1",e.getMessage());
                                }
                            }
                        });
                    }else {
                        Toast.makeText(getApplicationContext(),"评论不能为空",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"请先注册惹~",Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });
        alertDialog = builder.create();


        dishesComment = (ImageButton) findViewById(R.id.ib_dishesComment);
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
    public class MyViewPagerAdpater extends PagerAdapter{

        @Override
        public int getCount() {
            return imageLib.length+1;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView=new ImageView(getApplicationContext());
            if (position!=getCount()-1)
            {
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(imageLib[position]);}
            else {
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
    private class MyViewPagerListener implements ViewPager.OnPageChangeListener{
        com.nineoldandroids.animation.ObjectAnimator objectAnimator=timelyView.animate(0,1);
        int duration=100;
        int postionNow=0;
        int postionNext=1;
        MyViewPagerListener(){
            objectAnimator = timelyView.animate(0,1);
            objectAnimator.setDuration(duration);
        }
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //向右滑动

//            objectAnimator.setCurrentPlayTime((long) (positionOffset*duration));
            Log.v("sjy","onscrool pos is "+position+"  posoffset is"+positionOffset);

        }

        @Override
        public void onPageSelected(int position) {
            Log.v("sjy", "select pos is" + position);


        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state)
            {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    Log.v("s","drag");
                    if (objectAnimator.isRunning()){

                        objectAnimator.setCurrentPlayTime(duration);
                        objectAnimator.cancel();
                    }
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    Log.v("s","idle");
                    postionNext=viewPager.getCurrentItem();
                    if (postionNow==postionNext)
                        objectAnimator = timelyView.animate(postionNext);
                    else objectAnimator= timelyView.animate(postionNow,postionNext);
                    objectAnimator.setDuration(duration);
                    objectAnimator.start();
                    postionNow=postionNext;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    Log.v("s","settling");
                    break;
            }
        }
    }
}