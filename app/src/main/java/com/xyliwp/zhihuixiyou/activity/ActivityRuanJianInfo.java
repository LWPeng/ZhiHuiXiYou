package com.xyliwp.zhihuixiyou.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.xyliwp.zhihuixiyou.R;

/**
 * Created by lwp940118 on 2016/10/31.
 */
public class ActivityRuanJianInfo extends Activity{
    private static final String tag = "ActivityRuanJianInfo ------ ";

    private ImageButton imageButton_kaoqinback;
    private String info = "        该软件是为了方便西安邮电大学在校学生考勤查询而开发的安卓手机客户端,集本周所需要打卡的课表、该学期所有课程的考勤记录和一些简单的个人信息"+
            "于一体的软件,省流量且方便同学打卡后进行查询。\n        若在使用过程中该软件突然停止运行或者对其有建议的同学可以私聊我,我将第一时间做出修改。\n"+
            "\n联系方式如下：\nQQ:847764002\n微信:fy847764002\n邮箱：847764002@qq.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ruanjianinfo);
        initFindById();
        setOnClickListener();
    }

    private void initFindById(){
        imageButton_kaoqinback = (ImageButton)findViewById(R.id.imagebutton_kaoqin_back);
        TextView textView_raunjian = (TextView)findViewById(R.id.textview_info_ruanjian);
        textView_raunjian.setText(info);
    }

    private void setOnClickListener() {
        imageButton_kaoqinback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
