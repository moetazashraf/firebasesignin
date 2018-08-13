package app.com.meating.utilities;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public class Utils {



    public static void hideAndroidUI(Activity context){
        if (context != null) {
            View decorView = context.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
