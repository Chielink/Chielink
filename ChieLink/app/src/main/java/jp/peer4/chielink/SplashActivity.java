package jp.peer4.chielink;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler hd1 = new Handler();
        //2秒後に画面遷移
        hd1.postDelayed(new splashHandler(), 2000);
    }

    class splashHandler implements Runnable{
        public void run(){
            //別のActivityに遷移
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
