package app.com.meating;

import android.app.Application;

import com.firebase.client.Firebase;

import static java.security.AccessController.getContext;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(getApplicationContext());
    }
}
