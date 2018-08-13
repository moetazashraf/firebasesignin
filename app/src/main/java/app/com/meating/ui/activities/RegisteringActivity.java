package app.com.meating.ui.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.com.meating.R;
import app.com.meating.ui.fragments.LoginFragment;
import app.com.meating.ui.fragments.OptionFragment;

public class RegisteringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fregister, new OptionFragment())
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {

            if (fragment instanceof LoginFragment) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }
}
