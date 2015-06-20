package com.example.db.xiaoshiji.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.db.xiaoshiji.R;

import org.w3c.dom.Text;

import beans.CommitInfo;
import beans.DiningRoomCommentInfo;
import beans.DiningRoomInfo;
import utils.AppConstant;

public class DiningRoomCommentActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView iv_logo;
    TextView tv_name,tv_location;
    RatingBar ratingBar;
    EditText et_comment;
    Button bt_submit;
    public String diningroomname;
    public String diningroomaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_room_comment);
        Bundle bundle=getIntent().getExtras();
        diningroomname=bundle.getString("diningroomname");
        diningroomaddress=bundle.getString("diningroomaddress");
        initToolBar();
        initView();
    }

    private void initView() {
        iv_logo = (ImageView) findViewById(R.id.iv_diningRoomLogo);

        tv_name = (TextView) findViewById(R.id.tv_diningRoomName);
        tv_name.setText(diningroomname);

        tv_location = (TextView) findViewById(R.id.tv_diningRoomLocation);
        tv_location.setText(diningroomaddress);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars= (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        et_comment = (EditText) findViewById(R.id.et_diningRoomComment);

        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_comment.getText().toString().trim();
                if (AVUser.getCurrentUser() != null) {
                    if (content != null) {
                        if (content.length() < 12) {
                            Toast.makeText(DiningRoomCommentActivity.this,"至少12个字",Toast.LENGTH_SHORT).show();
                            return ;
                        }
                        DiningRoomCommentInfo commitInfo = new DiningRoomCommentInfo();
                        commitInfo.setContent(content);
                        commitInfo.setDate(AppConstant.getCurrentTime());
                        commitInfo.setUser(AVUser.getCurrentUser().getUsername());
                        /*
                        db 评价的食堂的名字，由于菜谱暂时没搞好，这个先只填东一吧
                         */
                        commitInfo.setDiningRoomName(diningroomname);
                        commitInfo.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "评论成功惹~", Toast.LENGTH_SHORT).show();
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
                finish();
            }
        });
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("评价");
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
