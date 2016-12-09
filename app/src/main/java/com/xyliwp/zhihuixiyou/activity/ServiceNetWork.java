package com.xyliwp.zhihuixiyou.activity;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Service 实现对该app的网络监控，段网时跳转至 网络出错页面，
 * 不断网时 进行操作页面
 * Created by lwp940118 on 2016/11/7.
 */
public class ServiceNetWork extends Service{

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)){
                connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()){
                    String name = networkInfo.getTypeName();
                    ActivityError.activityError.finish();
                    intent.setClass(context,ActivityLogin.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else{
                    intent.setClass(context,ActivityError.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 注册BroadCastReceive
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //过滤掉系统无法响应的Intent 只将自己关心的Intent接受进来进行处理。
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //注册广播接收器
        registerReceiver(broadcastReceiver,intentFilter);
    }

    /**
     * 销毁时我们要把注册的广播销毁掉
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }
}
