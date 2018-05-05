package com.by.taxi.lovetaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.by.taxi.lovetaxi.javabean.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText upassword;
    private EditText npassword;
    private Button back;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Bmob.initialize(this, "64a9582a1950cfc5eac1b65afb3b11e2");
        initialize();
    }
    //初始化
    private void initialize() {

        upassword = (EditText) findViewById(R.id.usedpassword);
        npassword = (EditText) findViewById(R.id.newpassword);
        back = (Button) findViewById(R.id.backward);
        back.setOnClickListener(this);
        save = (Button) findViewById(R.id.keep);
        save.setOnClickListener(this);
        ;
    }
    //修改密码
    private void hold()
    {
        String uspassword =upassword.getText().toString();
        String newpassword =npassword.getText().toString();
        BmobUser.updateCurrentUserPassword(uspassword, newpassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Toast.makeText(PasswordActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                }else{
                    Log.e("ps",  e.toString());
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backward:
                Intent intent4 = new Intent(this, MainActivity.class);
                startActivity(intent4);
                break;
            case R.id.keep:
                hold();
                break;

        }
    }
}
