package com.example.db.xiaoshiji.activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.db.xiaoshiji.R;

import org.w3c.dom.Text;

import utils.AppConstant;

public class DiningRoomCommentActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView iv_logo;
    TextView tv_name,tv_location;
    RatingBar ratingBar;
    EditText et_comment;
    Button bt_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppConstant.setStatus(true, this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_room_comment);
        initToolBar();
        initView();
    }

    private void initView() {
        iv_logo = (ImageView) findViewById(R.id.iv_diningRoomLogo);

        tv_name = (TextView) findViewById(R.id.tv_diningRoomName);
        tv_location = (TextView) findViewById(R.id.tv_diningRoomLocation);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        LayerDrawable stars= (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        et_comment = (EditText) findViewById(R.id.et_diningRoomComment);

        bt_submit = (Button) findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
