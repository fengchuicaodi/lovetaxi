package com.by.taxi.lovetaxi;

import android.app.Activity;
import android.content.Intent;
import android.os.UserHandle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.by.taxi.lovetaxi.javabean.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener {
  ;
  private EditText username;
  private EditText phonenumber;
  private EditText idcard;
  ;
  private EditText sex;
  private EditText type;
  private Button back;
  private Button revise;
  private Button save;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_person);
    Bmob.initialize(this, "64a9582a1950cfc5eac1b65afb3b11e2");
    initialize();
  }

  private void initialize() {

    username = (EditText) findViewById(R.id.name);
    phonenumber = (EditText) findViewById(R.id.lyphone);
    idcard = (EditText) findViewById(R.id.lyidcard);
    sex = (EditText) findViewById(R.id.lysex);
    type = (EditText) findViewById(R.id.type);
    revise = (Button) findViewById(R.id.button_revise);
    revise.setOnClickListener(this);
    back = (Button) findViewById(R.id.button_backward);
    back.setOnClickListener(this);
    save = (Button) findViewById(R.id.save);
    save.setOnClickListener(this);
    MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
    username.setText(userInfo.getUsername());
    phonenumber.setText(userInfo.getMobilePhoneNumber());
    idcard.setText(userInfo.getIdcard());
    sex.setText(userInfo.getSex());
    type.setText("乘客");
    username.setCursorVisible(false);
    username.setFocusable(false);
    username.setFocusableInTouchMode(false);
    phonenumber.setCursorVisible(false);
    phonenumber.setFocusable(false);
    phonenumber.setFocusableInTouchMode(false);
    idcard.setCursorVisible(false);
    idcard.setFocusable(false);
    idcard.setFocusableInTouchMode(false);
    sex.setCursorVisible(false);
    sex.setFocusable(false);
    sex.setFocusableInTouchMode(false);
    type.setCursorVisible(false);
    type.setFocusable(false);
    type.setFocusableInTouchMode(false);
  }
  private void hold()
  {
    String name =username.getText().toString();
    String number =phonenumber.getText().toString();
    String s = sex.getText().toString();
    String typ=type.getText().toString();
    String cid =idcard.getText().toString();
    MyUser newUser = new MyUser();
    newUser.setUsername(name);
    newUser.setMobilePhoneNumber(number);
    newUser.setSex(s);
    newUser.setIdcard(cid);
    BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
    newUser.update(bmobUser.getObjectId(),new UpdateListener() {
      @Override
      public void done(BmobException e) {
        if(e==null){
          Toast.makeText(PersonActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
        }else{
          Log.e("ps",  e.toString());
        }
      }
    });

    username.setText(name);
    phonenumber.setText(number);
    idcard.setText(cid);
    sex.setText(s);
    type.setText(typ);
    username.setCursorVisible(false);
    username.setFocusable(false);
    username.setFocusableInTouchMode(false);
    phonenumber.setCursorVisible(false);
    phonenumber.setFocusable(false);
    phonenumber.setFocusableInTouchMode(false);
    idcard.setCursorVisible(false);
    idcard.setFocusable(false);
    idcard.setFocusableInTouchMode(false);
    sex.setCursorVisible(false);
    sex.setFocusable(false);
    sex.setFocusableInTouchMode(false);
    type.setCursorVisible(false);
    type.setFocusable(false);
    type.setFocusableInTouchMode(false);
  }
  private void modify(){
    username.setCursorVisible(true);
    username.setFocusable(true);
    username.setFocusableInTouchMode(true);
    phonenumber.setCursorVisible(true);
    phonenumber.setFocusable(true);
    phonenumber.setFocusableInTouchMode(true);
    idcard.setCursorVisible(true);
    idcard.setFocusable(true);
    idcard.setFocusableInTouchMode(true);
    sex.setCursorVisible(true);
    sex.setFocusable(true);
    sex.setFocusableInTouchMode(true);
    username.requestFocus();
    phonenumber.requestFocus();
    idcard.requestFocus();
    sex.requestFocus();
  }
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.button_backward:
        Intent intent4 = new Intent(this, MainActivity.class);
        startActivity(intent4);
        break;
      case R.id.button_revise:
      modify();
        break;
      case R.id.save:
        hold();
        break;

    }
  }
}
