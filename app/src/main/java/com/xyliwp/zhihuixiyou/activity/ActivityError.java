package com.xyliwp.zhihuixiyou.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.xyliwp.zhihuixiyou.R;

/**
 * Created by lwp940118 on 2016/11/7.
 */
public class ActivityError extends Activity{
    public static ActivityError activityError;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);
        activityError = this;
        Intent intent = new Intent(this,ServiceNetWork.class);
        startService(intent);
    }
}
