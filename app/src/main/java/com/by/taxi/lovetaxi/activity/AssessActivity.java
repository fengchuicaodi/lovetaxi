package com.by.taxi.lovetaxi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.by.taxi.lovetaxi.R;

import cn.bmob.v3.Bmob;

public class AssessActivity extends AppCompatActivity implements View.OnClickListener {

    private Button back;
    private Button assesssubmit;
    private EditText assesstext;
    private RatingBar ratingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);
        Bmob.initialize(this, "64a9582a1950cfc5eac1b65afb3b11e2");
        initialize();
    }
    private void initialize(){
        assesstext=(EditText)findViewById(R.id.add_content);
        assesssubmit=(Button)findViewById(R.id.assess);
        assesssubmit.setOnClickListener(this);
        back=(Button)findViewById(R.id.back);
        back.setOnClickListener(this);
        ratingBar=(RatingBar)findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.i("fenshu", "当前分数="+v);
            }
        });
    }


    private void assess(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.assess:
                assess();
                break;

        }
    }
}
