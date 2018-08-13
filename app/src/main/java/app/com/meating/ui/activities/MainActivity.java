package app.com.meating.ui.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import app.com.meating.R;
import app.com.meating.utilities.Utils;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth.AuthStateListener authStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        configereGoogleSignin();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, RegisteringActivity.class));

        }

        Toast.makeText(this, firebaseAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                mGoogleSignInClient.signOut();

                LoginManager.getInstance().logOut();


                startActivity(new Intent(MainActivity.this, RegisteringActivity.class));
                finish();
            }
        });
    }

    private void configereGoogleSignin(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }
}
