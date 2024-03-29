package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemindingTimeActivity extends BaseActivity {
    @BindView(R.id.img_time_tq0)
    ImageView imgTimeTq0;
    @BindView(R.id.img_time_tq5)
    ImageView imgTimeTq5;
    @BindView(R.id.img_time_tq15)
    ImageView imgTimeTq15;
    @BindView(R.id.img_time_tq30)
    ImageView imgTimeTq30;
    @BindView(R.id.img_time_tq60)
    ImageView imgTimeTq60;
    @BindView(R.id.img_time_tq240)
    ImageView imgTimeTq240;

    @BindView(R.id.ll_time_tq0)
    LinearLayout llTimeTq0;
    @BindView(R.id.ll_time_tq5)
    LinearLayout llTimeTq5;
    @BindView(R.id.ll_time_tq15)
    LinearLayout llTimeTq15;
    @BindView(R.id.ll_time_tq30)
    LinearLayout llTimeTq30;
    @BindView(R.id.ll_time_tq60)
    LinearLayout llTimeTq60;
    @BindView(R.id.ll_time_tq240)
    LinearLayout llTimeTq240;
    private AlarmManager alarmManager;

    @BindView(R.id.tv_time_tq0)
    TextView tvTimeTq0;
    @BindView(R.id.tv_time_tq5)
    TextView tvTimeTq5;
    @BindView(R.id.tv_time_tq15)
    TextView tvTimeTq15;
    @BindView(R.id.tv_time_tq30)
    TextView tvTimeTq30;
    @BindView(R.id.tv_time_tq60)
    TextView tvTimeTq60;
    @BindView(R.id.tv_time_tq240)
    TextView tvTimeTq240;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private SharedPreferences remind;
    public String advance;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private String time;
    private int day;
    private int mMinute;
    private int mHour;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reminding_time;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("提醒时间");
        remind = getSharedPreferences("remind", MODE_PRIVATE);
        String time_tp = remind.getString("time_tp", "");
        if (time_tp.length() > 0) {
            if (time_tp.equals("事务开始时")) {
                imgTimeTq0.setVisibility(View.VISIBLE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
            } else if (time_tp.equals("提前5分钟")) {
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.VISIBLE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
            } else if (time_tp.equals("提前15分钟")) {
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.VISIBLE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
            } else if (time_tp.equals("提前30分钟")) {
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.VISIBLE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
            } else if (time_tp.equals("提前1小时")) {
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.VISIBLE);
                imgTimeTq240.setVisibility(View.GONE);
            } else if (time_tp.equals("提前1天")) {
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.VISIBLE);
            }
        } else {
            imgTimeTq0.setVisibility(View.VISIBLE);
            imgTimeTq5.setVisibility(View.GONE);
            imgTimeTq15.setVisibility(View.GONE);
            imgTimeTq30.setVisibility(View.GONE);
            imgTimeTq60.setVisibility(View.GONE);
            imgTimeTq240.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.


        Intent intent = getIntent();
        time = intent.getStringExtra("mTvTimeDisplay");
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.ll_time_tq0, R.id.ll_time_tq5, R.id.ll_time_tq15, R.id.ll_time_tq30, R.id.ll_time_tq60, R.id.ll_time_tq240})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time_tq0://(不提醒)事务开始时
                imgTimeTq0.setVisibility(View.VISIBLE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
                String tvtiemqt0 = tvTimeTq0.getText().toString();
                remind.edit().putString("time_tp", tvtiemqt0).commit();
                advance = tvtiemqt0;
                break;
            case R.id.ll_time_tq5://提前5分钟
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.VISIBLE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
                String tvtiemqt5 = tvTimeTq5.getText().toString();
                remind.edit().putString("time_tp", tvtiemqt5).commit();
          //      setTimeStart(300000);
                advance = tvtiemqt5;
                break;
            case R.id.ll_time_tq15:
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.VISIBLE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
                String tvtiemqt15 = tvTimeTq15.getText().toString();
                remind.edit().putString("time_tp", tvtiemqt15).commit();
            //    setTimeStart(900000);
                advance = tvtiemqt15;
                break;
            case R.id.ll_time_tq30:
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.VISIBLE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
                String tvtiemqt30 = tvTimeTq30.getText().toString();
                remind.edit().putString("time_tp", tvtiemqt30).commit();
            //    setTimeStart(1800000);
                advance = tvtiemqt30;
                break;
            case R.id.ll_time_tq60:
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.VISIBLE);
                imgTimeTq240.setVisibility(View.GONE);
                String tvtiemqt60 = tvTimeTq60.getText().toString();
                remind.edit().putString("time_tp", tvtiemqt60).commit();
               // setTimeStart(3600000);
                advance = tvtiemqt60;
                break;
            case R.id.ll_time_tq240:
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.VISIBLE);
                String tvtiemqt240 = tvTimeTq240.getText().toString();
                remind.edit().putString("time_tp", tvtiemqt240).commit();
               // setTimeStart(86400000);
                advance = tvtiemqt240;
                break;
        }
    }

    public void setTimeStart(long million){
        String[] split = time.split(" ");
        String[] split1 = split[1].split(":");
        mHour = Integer.parseInt(split1[0]);
        mMinute = Integer.parseInt(split1[1]);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(time).getTime();
            long l = millionSeconds - System.currentTimeMillis();

            int ss = 1000;
            int mi = ss * 60;
            int hh = mi * 60;
            int dd = hh * 24;


            day = (int) (l / dd);
            day = Integer.parseInt(day < 10 ? "0" + day : "" + day); //天
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date date = new Date(millionSeconds - million);
            String format = simpleDateFormat.format(date);
            mHour=Integer.parseInt(Integer.parseInt(format.split(":")[0])< 10 ? "0" + format.split(":")[0] : "" + format.split(":")[0]);
            mMinute=Integer.parseInt(Integer.parseInt(format.split(":")[1])< 10 ? "0" + format.split(":")[1] : "" + format.split(":")[1]);
            Log.e("ccccc","format:     "+format);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("RemindingTime Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
    @Override
    public void back(View v) {
        //返回操作
        Intent intent = new Intent();
        intent.putExtra("mDay",day);
        intent.putExtra("advance",advance);
        intent.putExtra("mHour",mHour);
        intent.putExtra("mMinute",mMinute);
        setResult(2020,intent);
        super.back(v);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }
}
