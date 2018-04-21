package com.by.taxi.lovetaxi.javabean;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by User on 2018/1/13.
 */

public class MyPermissions {
    private Context context;
    private Activity activity;

    public MyPermissions() {

    }

    public MyPermissions(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * 通过处理权限数组进行动态权限获取
     * @param permissions   Manifest.permission
     */
    public void requestPermission(String[] permissions) {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            //验证是否许可权限
            for (String str : permissions) {
                if (ContextCompat.checkSelfPermission(context, str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
    }
}
