package com.by.taxi.lovetaxi;

        import android.app.Activity;
        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.view.KeyEvent;
        import android.view.Window;


public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 3000;
     private Handler handler;
    @Override
    //在SplashActivity中禁用返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return  true;
            }
        return  super.onKeyDown(keyCode, event);

        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
                 super.onCreate(savedInstanceState);
                 getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                 setContentView(R.layout.activity_splash);

                 handler = new Handler();
                 // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到LoginActivity
                 handler.postDelayed(new Runnable() {

                     @Override
             public void run() {
                                 Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                 startActivity(intent);
                                 SplashActivity.this.finish();
                             }
        }, SPLASH_DISPLAY_LENGHT);

            }
 }