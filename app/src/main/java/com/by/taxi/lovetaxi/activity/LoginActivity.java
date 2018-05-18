package com.by.taxi.lovetaxi.activity;

import android.content.Intent;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.by.taxi.lovetaxi.R;
import com.by.taxi.lovetaxi.javabean.MyPermissions;
import com.by.taxi.lovetaxi.javabean.MyUser;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText etusername;
    private EditText etpassword;
    private Button login;
    private Button sign;
    private Button forpassword;
    private Button eye;
    private boolean flag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] str=new String[]{"Manifest.permission.READ_EXTERNAL_STORAGE"};
        setContentView(R.layout.activity_login);
        //这里的AppLication ID 写上自己创建项目得到的那个AppLication ID
        Bmob.initialize(this, "64a9582a1950cfc5eac1b65afb3b11e2");
        initialize();
        initView();
    }

    private void initView() {

    }
    //初始化
    private void initialize() {
        String []str =new String[]{"Manifest.permission.ACCESS_FINE_LOCATION","Manifest.permission.ACCESS_COARSE_LOCATION","Manifest.permission.READ_PHONE_STATE","Manifest.permission.WRITE_EXTERNAL_STORAGE","Manifest.permission.WRITE_EXTERNAL_STORAG","Manifest.permission.CHANGE_WIFI_STATE","Manifest.permission.READ_PROFILE"};
        MyPermissions permission= new MyPermissions(this,this);
        permission.requestPermission(str);
        etusername = (EditText) findViewById(R.id.username);
        etpassword = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        sign = (Button) findViewById(R.id.register);
        sign.setOnClickListener(this);
        forpassword =(Button) findViewById(R.id.login_error);
        forpassword.setOnClickListener(this);
        eye = (Button) findViewById(R.id.bt_pwd_eye);
        eye.setOnClickListener(this);
        boolean flag = false;
    }
    //监听事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_pwd_eye:
                if (!flag) {
                    etpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                flag = !flag;
                etpassword.postInvalidate();
                CharSequence text =etpassword.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
                break;
            case R.id.login:
                final String username = etusername.getText().toString();
                final String password = etpassword.getText().toString();
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                    BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                    query.addWhereEqualTo("username",username);
                    query.addWhereEqualTo("type",1);
                    query.findObjects(new FindListener<MyUser>() {
                        @Override
                        public void done(List<MyUser> list, BmobException e) {
                            if(e==null){
                                if(list.size()==1){
                                    BmobUser bu2 = new MyUser();
                                    bu2.setUsername(username);
                                    bu2.setPassword(password);
                                    bu2.login(new SaveListener<BmobUser>() {

                                        @Override
                                        public void done(BmobUser bmobUser, BmobException e) {
                                            if(e==null){
                                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent1);
                                            }else{
                                                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                                                e.getStackTrace();
                                            }
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                                    e.getStackTrace();
                                }

                            }else{
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }
                        }
                    });

                }
                break;
            case R.id.login_error:
                Intent intent2 = new Intent(this, SmsActivity.class);
                startActivity(intent2);
                break;
            case R.id.register:
                Intent intent3 = new Intent(this,registerActivity.class);
                startActivity(intent3);
                break;

        }
    }
}