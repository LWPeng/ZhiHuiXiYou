package com.xyliwp.zhihuixiyou;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xyliwp.zhihuixiyou.fragment.Fragment_My;
import com.xyliwp.zhihuixiyou.fragment.Fragment_Prod;
import com.xyliwp.zhihuixiyou.fragment.Fragment_SchoolTimeTable;
import com.xyliwp.zhihuixiyou.myviewpager.DepthPageTransformer;
import com.xyliwp.zhihuixiyou.myviewpager.MyViewPager;

import java.util.ArrayList;
import java.util.List;


/*
    主页面的显示 和设置
 */
public class MainActivity extends FragmentActivity {

    private final String tag = "MainActivity ----- ";

    //radoibutton的控件设置
    private RadioGroup radioGroup;
    private RadioButton radioButton0;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private TextView textView0;
    private TextView textView1;
    private TextView textView2;

    //myviewpager的定义
    private MyViewPager myViewPager;
    //fragment的定义
    private Fragment_My fragment_my;
    private Fragment_Prod fragment_prod;
    private Fragment_SchoolTimeTable fragment_schoolTimeTable;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //对id的查找
        initFindById();
        //fragment的初始化
        initFragment();
        //fragment适配器的初始化
        initFragmentPagrAdepter();
        //初始化viewpager的适配器
        initViewPagerAdepter();
        //点击事件的设置
        radioGroupOnCheckedChangeListener();
    }

    /**
     * id的获取
     */
    private void initFindById(){
        radioButton0 = (RadioButton)findViewById(R.id.radiobutton0);
        radioButton1 = (RadioButton)findViewById(R.id.radiobutton1);
        radioButton2 = (RadioButton)findViewById(R.id.radiobutton2);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        textView0 = (TextView)findViewById(R.id.textview_mian0);
        textView1 = (TextView)findViewById(R.id.textview_mian1);
        textView2 = (TextView)findViewById(R.id.textview_mian2);
        myViewPager = (MyViewPager)findViewById(R.id.myViewPager);
    }

    /**
     * 对3个碎片进行初始化
     */
    private void initFragment(){
        fragments = new ArrayList<Fragment>();

        fragment_my = new Fragment_My();
        fragment_prod = new Fragment_Prod();
        fragment_schoolTimeTable = new Fragment_SchoolTimeTable();

        fragments.add(fragment_prod);
        fragments.add(fragment_schoolTimeTable);
        fragments.add(fragment_my);
    }

    /**
     * fragment的适配器
     */
    private void initFragmentPagrAdepter(){
        FragmentManager manager = getSupportFragmentManager();
        fragmentPagerAdapter = new FragmentPagerAdapter(manager) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
    }

    /**
     * 初始化viewpager的适配器
     */
    private void initViewPagerAdepter(){
        //viewpager的滑动效果
        myViewPager.setPageTransformer(true,new DepthPageTransformer());
        myViewPager.setAdapter(fragmentPagerAdapter);
        myViewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        radioButton0.setChecked(true);
                        textView0.setTextColor(getResources().getColor(R.color.dianzhong));
                        textView1.setTextColor(getResources().getColor(R.color.meizhong));
                        textView2.setTextColor(getResources().getColor(R.color.meizhong));
                        break;
                    case 1:
                        radioButton1.setChecked(true);
                        textView0.setTextColor(getResources().getColor(R.color.meizhong));
                        textView1.setTextColor(getResources().getColor(R.color.dianzhong));
                        textView2.setTextColor(getResources().getColor(R.color.meizhong));
                        break;
                    case 2:
                        radioButton2.setChecked(true);
                        textView0.setTextColor(getResources().getColor(R.color.meizhong));
                        textView1.setTextColor(getResources().getColor(R.color.meizhong));
                        textView2.setTextColor(getResources().getColor(R.color.dianzhong));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * radiogroup的点击事件
     */
    private void radioGroupOnCheckedChangeListener(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radiobutton0:
                        myViewPager.setCurrentItem(0);
                        textView0.setTextColor(getResources().getColor(R.color.dianzhong));
                        textView1.setTextColor(getResources().getColor(R.color.meizhong));
                        textView2.setTextColor(getResources().getColor(R.color.meizhong));
                        break;
                    case R.id.radiobutton1:
                        myViewPager.setCurrentItem(1);
                        textView0.setTextColor(getResources().getColor(R.color.meizhong));
                        textView1.setTextColor(getResources().getColor(R.color.dianzhong));
                        textView2.setTextColor(getResources().getColor(R.color.meizhong));
                        break;
                    case R.id.radiobutton2:
                        myViewPager.setCurrentItem(2);
                        textView0.setTextColor(getResources().getColor(R.color.meizhong));
                        textView1.setTextColor(getResources().getColor(R.color.meizhong));
                        textView2.setTextColor(getResources().getColor(R.color.dianzhong));
                        break;
                }
            }
        });

        textView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewPager.setCurrentItem(0);
                textView0.setTextColor(getResources().getColor(R.color.dianzhong));
                textView1.setTextColor(getResources().getColor(R.color.meizhong));
                textView2.setTextColor(getResources().getColor(R.color.meizhong));
                radioButton0.setChecked(true);
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setChecked(true);
                textView0.setTextColor(getResources().getColor(R.color.meizhong));
                textView1.setTextColor(getResources().getColor(R.color.dianzhong));
                textView2.setTextColor(getResources().getColor(R.color.meizhong));
                myViewPager.setCurrentItem(1);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton2.setChecked(true);
                textView0.setTextColor(getResources().getColor(R.color.meizhong));
                textView1.setTextColor(getResources().getColor(R.color.meizhong));
                textView2.setTextColor(getResources().getColor(R.color.dianzhong));
                myViewPager.setCurrentItem(2);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
