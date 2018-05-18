package com.by.taxi.lovetaxi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.by.taxi.lovetaxi.R;
import com.by.taxi.lovetaxi.javabean.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class registerActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etusername;
    private EditText etpassword;
    private EditText etphonenumber;
    private EditText etidcard;
    private Button uppic;
    private Button register;
    private Button cancel;
    private RadioButton male ;
    private RadioButton female ;
    private String sex="男";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //这里的AppLication ID 写上自己创建项目得到的那个AppLication ID
        Bmob.initialize(this, "64a9582a1950cfc5eac1b65afb3b11e2");
        initialize();
    }

    private void initialize() {

        etusername = (EditText) findViewById(R.id.et_username);
        etpassword = (EditText) findViewById(R.id.et_password);
        etphonenumber = (EditText) findViewById(R.id.et_phonenumber);
        etidcard = (EditText) findViewById(R.id.idcard);
        uppic = (Button) findViewById(R.id.pic);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        male = (RadioButton) findViewById(R.id.male);
        male.setOnClickListener(this);
        female = (RadioButton) findViewById(R.id.female);
        female.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.male:
                sex="男";
                break;
            case R.id.female:
                sex="女";
                break;
            case R.id.register:
                registerData();
                break;
            case R.id.cancel:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 注册
     */
    private void registerData() {
        String name = etusername.getText().toString();
        String password = etpassword.getText().toString();
        String phone = etphonenumber.getText().toString();
        String idcard =etidcard.getText().toString();
        if(password.length()<8)
        {
            Toast.makeText(registerActivity.this,"注册失败，密码不得少于8位",Toast.LENGTH_SHORT).show();
        }
        else if(phone.length()!=11)
        {
            Toast.makeText(registerActivity.this,"注册失败，手机号输入错误",Toast.LENGTH_SHORT).show();
        }
        else{
            MyUser bu = new MyUser();
            bu.setUsername(name);
            bu.setPassword(password);
            bu.setMobilePhoneNumber(phone);
            bu.setIdcard(idcard);
            bu.setType(2);
            bu.setSex(sex);
            //注意：不能用save方法进行注册
        /*         *用户注册的提交
         */
            bu.signUp(new SaveListener<MyUser>() {
                @Override
                public void done(MyUser s, BmobException e) {
                    if(e==null){
                        Toast.makeText(registerActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(registerActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                        Log.e("registerActivity", e.toString());
                    }
                }
            });
        }

    }
}