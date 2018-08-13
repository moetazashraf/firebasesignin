package app.com.meating.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.com.meating.R;
import app.com.meating.utilities.Utils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Utils.hideAndroidUI(this);

        Thread timer = new Thread() {
            public void run() {

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    startActivity(new Intent(getApplication(), RegisteringActivity.class));
                }
            }

        };
        timer.start();
    }
}
