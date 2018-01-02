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
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.WheelMain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

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
    private long millionSeconds;
    //响铃
    private WheelMain wheelMainDate;
    private String beginTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //private String message;
    private SharedPreferences remind;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
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
        //EventBus.getDefault().register(this);
        //        Intent intent = getIntent();
        //        String year_month_day = intent.getStringExtra("year_month_day");
        //        tvTimeTq0.setText(year_month_day);
        remind = getSharedPreferences("remind", MODE_PRIVATE);
        String time_tp = remind.getString("time_tp", "");
        if (time_tp.length() > 0) {
            if (time_tp.equals("不提醒")) {
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
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Intent intent = getIntent();
        time = intent.getStringExtra("mTvTimeDisplay");
        Log.e("ccccc","add传来的:"+time);
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void getMsg(MessageEvent messageEvent){
//        //这个msg就是传过来的值  ok
//        tvTimeTq0.setText(messageEvent.getMessage().toString());
//        message = messageEvent.getMessage();
//        Log.e("CQQQQQ",message+"-------------");
//    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.ll_time_tq0, R.id.ll_time_tq5, R.id.ll_time_tq15, R.id.ll_time_tq30, R.id.ll_time_tq60, R.id.ll_time_tq240})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_time_tq0://不提醒
                imgTimeTq0.setVisibility(View.VISIBLE);
                imgTimeTq5.setVisibility(View.GONE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
                String tvtiemqt0 = tvTimeTq0.getText().toString();
                remind.edit().putString("time_tp", tvtiemqt0).commit();
                break;
            case R.id.ll_time_tq5://提前5分钟
                imgTimeTq0.setVisibility(View.GONE);
                imgTimeTq5.setVisibility(View.VISIBLE);
                imgTimeTq15.setVisibility(View.GONE);
                imgTimeTq30.setVisibility(View.GONE);
                imgTimeTq60.setVisibility(View.GONE);
                imgTimeTq240.setVisibility(View.GONE);
                String tvtiemqt5 = tvTimeTq5.getText().toString();
                //这里接收传来的时间数据

                remind.edit().putString("time_tp", tvtiemqt5).commit();
                //String tokenizer123

                setTimeStart(300000);
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
                setTimeStart(900000);
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
                setTimeStart(1800000);
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
                setTimeStart(3600000);
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
                setTimeStart(86400000);
                break;
        }
    }

    public void setTimeStart(long million){
        String[] split = time.split(" ");
        String[] split1 = split[1].split(":");
        mHour = Integer.parseInt(split1[0]);
        mMinute = Integer.parseInt(split1[1]);
        //String s = time;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long millionSeconds = 0;//毫秒
        try {
            millionSeconds = sdf.parse(time).getTime();
            Log.e("ccccc","执行的hao miao zhi:"+millionSeconds);
            long l = millionSeconds - System.currentTimeMillis();
            Log.e("ccccc","l:"+l);

            SimpleDateFormat sss = new SimpleDateFormat("hh:mm");
            long millionSeconds2 = sss.parse(time.split(" ")[1]).getTime();
            sss.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
            String hms = sss.format(million);
            Log.e("ccccc","测试时间:"+hms);
            day = (int) ((l-million) / 1000 / 60 / 60 / 24);
            mHour = (int) ((millionSeconds2-million) / 1000 / 60 / 60);
            mMinute = (int) ((millionSeconds2-million*60) / 1000 / 60 );
          //mMinute = (int) ((millionSeconds2-million*60) / 1000 / 60 / 60);


//            Log.e("ccccc","测试时间2:"+mHour+":"+mMinute);
            Log.e("ccccc","时:"+mHour);
            Log.e("ccccc","分:"+mMinute);
            Log.e("ccccc","5分钟的是时间"+day);

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
        Log.e("ccccc","back:"+day+mHour+mMinute);
        Intent intent = new Intent();
        intent.putExtra("mDay",day);
        intent.putExtra("mHour",mHour);
        intent.putExtra("mMinute",mMinute);
        setResult(2020,intent);
        super.back(v);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
}