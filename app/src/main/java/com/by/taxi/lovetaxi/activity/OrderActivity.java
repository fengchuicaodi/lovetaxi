package com.by.taxi.lovetaxi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.by.taxi.lovetaxi.R;
import com.by.taxi.lovetaxi.javabean.MyUser;
import com.by.taxi.lovetaxi.javabean.ordercar;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText starttime;
    private EditText startlocation;
    private EditText endlocation;
    private Button canalButton;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Bmob.initialize(this, "64a9582a1950cfc5eac1b65afb3b11e2");
        initialize();
    }
    private void initialize(){
        starttime=(EditText)findViewById(R.id.starttime);
        starttime.setOnClickListener(this);
        startlocation=(EditText) findViewById(R.id.startlocationEdit);
        startlocation.setOnClickListener(this);
        endlocation=(EditText) findViewById(R.id.endlocationEdit);
        endlocation.setOnClickListener(this);
        canalButton=(Button)findViewById(R.id.cancelButton);
        canalButton.setOnClickListener(this);
        submitButton=(Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
    }
    private void showDataPickDialog(DateType type){
        DatePickDialog dialog = new DatePickDialog(this);
        //设置上下年分限制
        dialog.setYearLimt(5);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(DateType.TYPE_YMDHM);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm");
        //设置选择回调
        dialog.setOnChangeLisener(null);

        dialog.show();
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                starttime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
            }
        });
    }
    private void submitOrder(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String sdate=starttime.getText().toString()+":00";
        try{
            java.util.Date date=sdf.parse(sdate);
            BmobDate bmobDate=new BmobDate(date);
            Log.e("sfsfafsf",date.toString() );
            final MyUser user = BmobUser.getCurrentUser(MyUser.class);
            ordercar order = new ordercar();
            order.setOrder_time(bmobDate);
            order.setStart_location(startlocation.getText().toString());
            order.setEnd_location(endlocation.getText().toString());
            order.setStatus(0);
            order.setPassengername(user);
            order.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        Log.i("bmob","关联成功");
                    }else{
                        Log.e("bmob","失败："+e.getMessage());
                    }
                }
            });
        }catch (Exception e){
            Log.e("fsdff",e.toString());
        }



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    String returnedData=data.getStringExtra("data");
                    Log.e("fanhui",returnedData );
                    startlocation.setText(returnedData);
                }
                break;
            case 2:
                if(resultCode==RESULT_OK){
                    String returnedData=data.getStringExtra("data");
                    Log.e("fanhui",returnedData );
                    endlocation.setText(returnedData);
                }
                break;
            default:
        }
    }
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.starttime:
                showDataPickDialog(DateType.TYPE_YMDHM);
                break;
            case R.id.startlocationEdit:
                Intent intent = new Intent(OrderActivity.this, PoiKeywordSearchActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.endlocationEdit:
                Intent intent1 = new Intent(OrderActivity.this, PoiKeywordSearchActivity.class);
                startActivityForResult(intent1,2);
                break;
            case R.id.cancelButton:
                Intent intent2 = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
            case R.id.submitButton:
                submitOrder();

        }
    }

}
