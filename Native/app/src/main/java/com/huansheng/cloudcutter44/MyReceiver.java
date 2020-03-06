package com.huansheng.cloudcutter44;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "UpdateReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
        String packageName = intent.getDataString();
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {//接收升级广播
            Log.e(TAG, "onReceive:升级了一个安装包，重新启动此程序");
            if (packageName.equals("package:" + MainActivity.Instance.getPackageName())) {
                MyReceiver.restartAPP(context);//升级完自身app,重启自身
            }
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {//接收安装广播
            Log.e(TAG, "onReceive:安装了" + packageName);
            if (packageName.equals("package:" + MainActivity.Instance.getPackageName())) {
                /*SystemUtil.reBootDevice();*/
            }
        } else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) { //接收卸载广播
            Log.e(TAG, "onReceive:卸载了" + packageName);
        }
    }
    public static void restartAPP(Context context){
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(MainActivity.Instance.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        //ActManager.getAppManager().finishAllActivity();
    }
}