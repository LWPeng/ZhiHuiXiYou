package com.xyliwp.zhihuixiyou.ui;

/**
 * Created by lwp940118 on 2016/10/26.
 */
import android.content.Context;

public class DensityUtil {

    public static float dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dpValue * scale;
    }
}