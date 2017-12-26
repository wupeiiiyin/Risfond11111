package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTheTransactionActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_time_display)
    TextView tvTimeDisplay;
    @BindView(R.id.tv_time_displaytime)
    TextView tvTimeDisplaytime;

    private TransactiondatabaseSQL ttdbsqlite;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edit_addthetransaction_content)
    EditText editAddthetransactionContent;
    @BindView(R.id.ll_addthetransaction_time)
    LinearLayout llAddthetransactionTime;
    @BindView(R.id.ll_addthetransaction_reminding)
    LinearLayout llAddthetransactionReminding;
    @BindView(R.id.tv_addthetransaction_commit)
    TextView tvAddthetransactionCommit;

    @BindView(R.id.ll_adddate)
    LinearLayout lladddate;
    @BindView(R.id.ll_addtime)
    LinearLayout lladdtime;

    private Button btn;
    private ListView llllll;
    private Cursor c;

    private MediaPlayer mediaPlayer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private String time,date;
    private GoogleApiClient client;
    private String beginTime;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_the_transaction;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        ttdbsqlite = new TransactiondatabaseSQL(this.getApplication());
        tvTitle.setText("添加事务");
        ButterKnife.bind(this);
        c = ttdbsqlite.checktransaction();
        btn = (Button) findViewById(R.id.xxx);
        llllll = (ListView) findViewById(R.id.llllll);
        btn.setOnClickListener(this);

//        Intent intent = getIntent();
//        String selectedtime = intent.getStringExtra("selectedtime");
//        tvTimeDisplay.setText(selectedtime);

        /*
        * 集合数据
        * */
//        String trim = "abcdiefjiijij";
//        ContentValues cv = new ContentValues();
//        cv.put("name", trim);
//        ttdbsqlite.Addtransaction(cv);
    }

    @OnClick({R.id.ll_adddate,R.id.ll_addtime,R.id.ll_addthetransaction_time,R.id.tv_addthetransaction_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //日期
            case R.id.ll_adddate:
                DatePickerDialog dialog1 = new DatePickerDialog(AddTheTransactionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //当前选择的日期
                        date = year+"-"+Integer.parseInt(monthOfYear+1+"")+"-"+dayOfMonth;
                        tvTimeDisplay.setText(date);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                //通过Calendar获得当前年、月、日
                //显示
                dialog1.show();
                break;
            //时间
            case R.id.ll_addtime:
                TimePickerDialog dialog = new TimePickerDialog(AddTheTransactionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time = hourOfDay+":"+minute;
                        tvTimeDisplaytime.setText(time);
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                //显示
                dialog.show();
                //startActivity(TimeTransactionActivity.class, false);
                break;

            case R.id.ll_addthetransaction_time:
                Intent intent = new Intent();
                intent.putExtra("time",time);
                intent.putExtra("date",date);
                intent.setClass(AddTheTransactionActivity.this, RemindingTimeActivity.class);
                AddTheTransactionActivity.this.startActivity(intent);
                finish();
                break;
            //添加
            case R.id.tv_addthetransaction_commit:
//                String trim = "abcdiefjiijij";
//                ContentValues cv = new ContentValues();
//                cv.put("name", trim);
//                ttdbsqlite.Addtransaction(cv);
                String arr_list = editAddthetransactionContent.getText().toString();
                if (arr_list == null || arr_list.equals("")) {
                    Toast.makeText(getApplicationContext(), "添加的内容不能为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(RemindingActivity.class, false);
                    break;
                }
        }
    }

    //查
    @Override
    public void onClick(View v) {
        ArrayList<String> list = new ArrayList();
        View view2 = LayoutInflater.from(AddTheTransactionActivity.this).inflate(R.layout.activity_text, null);
        llllll = (ListView) view2.findViewById(R.id.llllll);
        c.moveToFirst();
        while (c.moveToNext()) {
            String cursorString1 = c.getString(c.getColumnIndex("name"));
            list.add("内容:" + cursorString1);

        }
        ArrayAdapter Adapter = new ArrayAdapter(AddTheTransactionActivity.this, android.R.layout.simple_expandable_list_item_1, list);
        llllll.setAdapter(Adapter);


        AlertDialog.Builder builder2 = new AlertDialog.Builder(AddTheTransactionActivity.this);
        builder2.setView(view2);
        builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder2.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//                //时间一到跳转Activity,在这个Activity中播放音乐
//                mediaPlayer = MediaPlayer.create(this, R.raw.duan);
//                mediaPlayer.start();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddTheTransaction Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
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
//                mediaPlayer.stop();
//                finish();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
