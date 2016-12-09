package com.xyliwp.zhihuixiyou.myviewpager;

/**
 * Created by lwp940118 on 2016/10/17.
 */
import com.nineoldandroids.view.ViewHelper;
import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

public class RotateDownPageTransformer implements ViewPager.PageTransformer
{
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    private static final float ROT_MAX = 20.0f;
    private float mRot;
    private float mTrans;

    @SuppressLint("NewApi")
    public void transformPage(View view, float position)
    {

        Log.e("TAG", view + " , " + position + "");

        if (position < -1)
        { // [-Infinity,-1)
            // This page is way off-screen to the left.
            ViewHelper.setRotation(view, 0);

        } else if (position <= 1) // a椤垫粦鍔ㄨ嚦b椤�锛�a椤典粠 0.0 ~ -1 锛沚椤典粠1 ~ 0.0
        { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0)
            {

                mRot = (ROT_MAX * position);
                ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(view, view.getMeasuredHeight());
                ViewHelper.setRotation(view, mRot);
            } else
            {

                mRot = (ROT_MAX * position);
                ViewHelper.setPivotX(view, view.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(view, view.getMeasuredHeight());
                ViewHelper.setRotation(view, mRot);
            }

            // Scale the page down (between MIN_SCALE and 1)

            // Fade the page relative to its size.

        } else
        { // (1,+Infinity]
            // This page is way off-screen to the right.
            ViewHelper.setRotation(view, 0);
        }
    }
}