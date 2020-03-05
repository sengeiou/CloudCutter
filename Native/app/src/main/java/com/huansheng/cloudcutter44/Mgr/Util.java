package com.huansheng.cloudcutter44.Mgr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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

    public static void getFullScreen(final Dialog dialog){
        dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        dialog.getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        //布局位于状态栏下方
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        //全屏
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        //隐藏导航栏
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
                uiOptions |= 0x00001000;
                dialog.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
            }
        });
    }
}
