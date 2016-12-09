package com.xyliwp.zhihuixiyou.utils;

import com.xyliwp.zhihuixiyou.R;

import org.apache.http.client.HttpClient;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 智慧西邮的常量的放置.
 * Created by lwp940118 on 2016/10/22.
 */
public class Content {
    //登录之后 返回的数据以json格式进行保存
    public static JSONObject jsonObjectMain;
    //网络连接 的请求
    public static HttpClient httpClient;
    //登录页面返回的coolie
    public static String set_Cookie;
    public static String sessionId;
    public static HashMap<Integer,String> hashMap = new HashMap<>();
    public static int tagData;
    public static List<KeCheng> keChengs = new ArrayList<KeCheng>();
    public static List<KeCheng> keChengxinxis = new ArrayList<KeCheng>();
    public static GeRenInfo geRenInfo = new GeRenInfo();
    public static List<KeBiao> keBiaos = new ArrayList<KeBiao>();
    public static List<KeBiao> kebiaoyi = new ArrayList<KeBiao>();
    public static List<KeBiao> kebiaoer = new ArrayList<KeBiao>();
    public static List<KeBiao> kebiasan = new ArrayList<KeBiao>();
    public static List<KeBiao> kebiaosi = new ArrayList<KeBiao>();
    public static List<KeBiao> kebiaowu = new ArrayList<KeBiao>();
    public static List<KeBiao> kebiaoliu = new ArrayList<KeBiao>();
    public static List<KeBiao> kebiaoqi = new ArrayList<KeBiao>();
    public static List<QianDao> daka = new ArrayList<QianDao>();

    public static Integer[] YanSe = new Integer[]{
            R.color.color_4,
            R.color.color_7,
            R.color.color_13,
            R.color.color_16,
            R.color.color_19,
            R.color.color_2,
            R.color.color_9,
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_4,
            R.color.color_7,
            R.color.color_13,
            R.color.color_16,
            R.color.color_19,
            R.color.color_2,
            R.color.color_9,
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
    };
    public static String today ;
    static {
        Calendar cal = Calendar.getInstance();
        tagData = cal.get(Calendar.DAY_OF_WEEK)-1;
        for(int i = 0 ; i < 7 ; i++){
            int week_index = cal.get(Calendar.DAY_OF_WEEK);
            Date date=cal.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            String dateString = formatter.format(date);
            hashMap.put(week_index-1,dateString);
            cal.add(cal.DATE,1);
        }
        Calendar calendar = Calendar.getInstance();
        int tag = calendar.getTime().getMonth()+1;
        if (tag>=3 &&tag <=7){
            today = (calendar.getTime().getYear()+1900)+"-"+(calendar.getTime().getYear()+1900+1)+"-2";
        }else{
            today = (calendar.getTime().getYear()+1900)+"-"+(calendar.getTime().getYear()+1900+1)+"-1";
        }
    }
}
