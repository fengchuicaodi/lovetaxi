package com.by.taxi.lovetaxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.by.taxi.lovetaxi.javabean.MyUser;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SmsActivity extends Activity implements View.OnClickListener {
    private EditText mnumber, prove;
    private Button mgetsms, mreturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        //Bmob.initialize(this,"614b7327837cd173e62ebd23c50ec16b")
        initialize();
    }

    private void initialize() {
        Bmob.initialize(this, "64a9582a1950cfc5eac1b65afb3b11e2");
        BmobSMS.initialize(this, "64a9582a1950cfc5eac1b65afb3b11e2");
        mnumber = (EditText) findViewById(R.id.phone);
        prove = (EditText) findViewById(R.id.cord);
        mgetsms = (Button) findViewById(R.id.getcord);
        mgetsms.setOnClickListener(this);
        mreturn = (Button) findViewById(R.id.savecord);
        mreturn.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getcord:
                send();
                break;
            case R.id.savecord:
                proof();
                break;
        }
    }
    //点击获取验证码

    private void send() {
        String number = mnumber.getText().toString();
        if (number.length() == 0) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        if (number.length() != 11) {
            Toast.makeText(this, "请输入11位有效号码", Toast.LENGTH_LONG).show();
            return;
        }
        BmobSMS.requestSMSCode(this, number, "短信模板", new RequestSMSCodeListener() {

            @Override
            public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
                // TODO Auto-generated method stub
                if (e == null) {
                    //发送成功时，让获取验证码按钮不可点击，且为灰色
                    mgetsms.setEnabled(false);
                    //mgetsms.setBackgroundColor();
                    Toast.makeText(SmsActivity.this, "验证码发送成功，请尽快使用", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            //Message_btn.setBackgroundResource(R.drawable.button_shape02);
                            mgetsms.setText(millisUntilFinished / 1000 + "秒");
                        }

                        @Override
                        public void onFinish() {
                            mgetsms.setClickable(true);
                            //Message_btn.setBackgroundResource(R.drawable.button_shape);
                            mgetsms.setText("重新发送");
                        }
                    }.start();
                }
                else {
                    Toast.makeText(SmsActivity.this, "验证码发送失败，请检查网络连接", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    private void proof(){
        String number = mnumber.getText().toString();
        String pro = prove.getText().toString();
        BmobSMS.verifySmsCode(this, number, pro, new VerifySMSCodeListener() {
            @Override
            public void done(cn.bmob.sms.exception.BmobException e) {
                if(e==null){
                    Toast.makeText(SmsActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(SmsActivity.this, MainActivity.class);
                    startActivity(intent1);
                 }else{
                    Toast.makeText(SmsActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
             }
            }
        });
    }

}



