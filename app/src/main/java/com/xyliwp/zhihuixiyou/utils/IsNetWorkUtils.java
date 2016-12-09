package com.xyliwp.zhihuixiyou.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * 判断 网络状态
 * Created by lwp940118 on 2016/11/9.
 */
public class IsNetWorkUtils {
    public Context context = null;
    public IsNetWorkUtils(Context context){
        this.context = context;
    }

    /**
     * 检查网络连接
     * @param context
     * @return
     */
    public static boolean isConnectNET(final Context context){
        final ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()){
            return  true;
        }else{
            Toast.makeText(context,"网络连接不正常",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
