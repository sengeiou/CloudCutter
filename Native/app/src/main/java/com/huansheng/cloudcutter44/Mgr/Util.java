package com.huansheng.cloudcutter44.Mgr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.huansheng.cloudcutter44.R;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class Util {

    public static void hideBottomMenu(Activity act) {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
        View v = act.getWindow().getDecorView();
        v.setSystemUiVisibility(View.GONE);
       } else if (Build.VERSION.SDK_INT >= 19) {
            Window _window = act.getWindow();
            WindowManager.LayoutParams params = _window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            _window.setAttributes(params);
        }
    }
    public static boolean isWifiConnected(Activity act){
        ConnectivityManager con=(ConnectivityManager)act.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            //执行相关操作
            return true;
        }else{
            return false;
        }
    }


    public static AlertDialog getAlertDialog(Activity act){
        return new AlertDialog.Builder(act, R.style.DIalogTheme).create();
    }
    public static AlertDialog.Builder getAlertDialogBuilder(Activity act){
        return new AlertDialog.Builder(act, R.style.DIalogTheme);
    }

    public static AlertDialog.Builder getAlertDialogBuilder(Context context) {
        return new AlertDialog.Builder(context, R.style.DIalogTheme);
    }
}
