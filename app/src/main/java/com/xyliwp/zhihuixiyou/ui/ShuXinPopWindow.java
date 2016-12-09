package com.xyliwp.zhihuixiyou.ui;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xyliwp.zhihuixiyou.R;

/**
 * Created by lwp940118 on 2016/10/30.
 */
public class ShuXinPopWindow extends PopupWindow{
    private View popWindow;
    private String s;
    public ShuXinPopWindow(final Activity activity,String s){
        LayoutInflater layoutInflater = (LayoutInflater)activity
                .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        popWindow = layoutInflater.inflate(R.layout.popwindow_shuaxin,null);
        //设置弹出的popview视图
        this.s = s;
        this.setContentView(popWindow);
        //设置弹出窗口的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置高度  为layout的自适应高度
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置  pop可点击可见
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        TextView textView = (TextView)popWindow.findViewById(R.id.textview_pop);
        textView.setText(s);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener
        this.setBackgroundDrawable(colorDrawable);
        // 设置pop弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

    }

    /**
     * 设置pop弹出窗口
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, Gravity.CENTER,0,0);
        } else {
            this.dismiss();
        }
    }

}
