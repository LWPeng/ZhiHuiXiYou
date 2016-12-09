package com.xyliwp.zhihuixiyou.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xyliwp.zhihuixiyou.R;
import com.xyliwp.zhihuixiyou.activity.ActivityKaoQin;
import com.xyliwp.zhihuixiyou.activity.ActivityKeCheng;
import com.xyliwp.zhihuixiyou.activity.ActivityRuanJianInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.xyliwp.zhihuixiyou.utils.Content.*;

/**
 * Created by lwp940118 on 2016/10/17.
 */
public class Fragment_My extends Fragment{

    private View rootView;
    private static final String tag = "Fragment_my --------- ";
    //对fragment_my的xml文件里面需要更新的ui进行设置
    private ImageView imageView_my_photo;
    private TextView textView_my_name;
    private TextView textView_my_sex;
    private TextView textView_my_xuehao;
    private TextView textView_my_xueyuan;
    private TextView textView_my_zhuanye;
    private TextView textView_my_class;
    private TextView textView_my_shenfen;
    private LinearLayout linearLayout_my_kechengxinxi;
    private LinearLayout linearLayout_my_kaoqintongji;
    private LinearLayout linearLayout_my_banbenxinxi;
    private Bitmap bitmap_myphoto;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    imageView_my_photo.setImageBitmap(bitmap_myphoto);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //获取 fragment的加载页面
        rootView = (View)inflater.inflate(R.layout.fragment_my,container,false);
        //绑定id
        initFindById();
        //点击事件的设置
        initOnClickListene();
        //初始化ui信息
        initMyUI();
        return rootView;
    }

    /**
     * 给定义的以上控件绑定id
     */
    private void initFindById(){
        imageView_my_photo = (ImageView)rootView.findViewById(R.id.imageview_my_photo);
        textView_my_class = (TextView)rootView.findViewById(R.id.textview_my_class);
        textView_my_name = (TextView)rootView.findViewById(R.id.textview_my_name);
        textView_my_sex = (TextView)rootView.findViewById(R.id.textview_my_sex);
        textView_my_shenfen = (TextView)rootView.findViewById(R.id.textview_my_shenfen);
        textView_my_xuehao = (TextView)rootView.findViewById(R.id.textview_my_xuehao);
        textView_my_xueyuan = (TextView)rootView.findViewById(R.id.textview_my_xueyuan);
        textView_my_zhuanye = (TextView)rootView.findViewById(R.id.textview_my_zhuanye);
        linearLayout_my_banbenxinxi = (LinearLayout)rootView.findViewById(R.id.linearlayout_my_ruanjianxinxi);
        linearLayout_my_kaoqintongji = (LinearLayout)rootView.findViewById(R.id.linearlayout_my_kaoqintongji);
        linearLayout_my_kechengxinxi = (LinearLayout)rootView.findViewById(R.id.linearlayout_my_kechengxinxi);
    }

    /**
     * 控件的点击事件的绑定
     */
    private void initOnClickListene(){
        linearLayout_my_banbenxinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag,"软件信息");
                Intent intent = new Intent(getActivity(), ActivityRuanJianInfo.class);
                startActivity(intent);
            }
        });
        linearLayout_my_kechengxinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag,"课程信息");
                Intent intent = new Intent(getActivity(), ActivityKeCheng.class);
                startActivity(intent);
            }
        });
        linearLayout_my_kaoqintongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(tag,"考勤统计");
                Intent intent = new Intent(getActivity(), ActivityKaoQin.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化myUI界面 通过 登录之后获取到的数据进行解析操作
     */
    private void initMyUI(){
        try {
            geRenInfo.setName(jsonObjectMain.getString("NAME"));
            geRenInfo.setSex(jsonObjectMain.getInt("SEX"));
            geRenInfo.setXuehao(jsonObjectMain.getString("SNO"));
            geRenInfo.setXueyuan(jsonObjectMain.getString("XYName"));
            geRenInfo.setZhuanye(jsonObjectMain.getString("ZYName"));
            geRenInfo.setBanji(jsonObjectMain.getString("BJ"));
            geRenInfo.setShenfen(jsonObjectMain.getString("SFLXMC"));
            textView_my_name.setText(geRenInfo.getName());
            if (geRenInfo.getSex() == 1) {
                textView_my_sex.setText("男");
            }else{
                textView_my_sex.setText("女");
            }
            textView_my_xuehao.setText(geRenInfo.getXuehao());
            textView_my_xueyuan.setText(geRenInfo.getXueyuan());
            textView_my_zhuanye.setText(geRenInfo.getZhuanye());
            textView_my_class.setText(geRenInfo.getBanji());
            textView_my_shenfen.setText(geRenInfo.getShenfen());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //开启子线程 下载图片 发送消息 更新ui
                    try {
                        bitmap_myphoto = getMyPhoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }).start();
            //jsonObjectMain.getString("NAME");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取其照片
     * @return
     */
    private Bitmap getMyPhoto() throws IOException{
        try {
            String photoString = "http://jwkq.xupt.edu.cn:8080/Common/GetPhotoByBH?xh="
                    +jsonObjectMain.getString("SNO");
            HttpGet httpGet = new HttpGet(photoString);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200){
                //获取 图片留
                byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
                Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                return bitmap;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 网络获取user网页信息
     */
    private void getUser() throws IOException{
        final OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("http://jwkq.xupt.edu.cn:8080/User/Schedule")
                .get()
//                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
//                .addHeader("Accept-Encoding","gzip,deflate,sdch")
//                .addHeader("Accept-Language","zh-CN,zh;q=0.8")
//                .addHeader("Connection","keep-alive")
                .addHeader("Cookie",sessionId+"; "+set_Cookie)
//                .addHeader("Host","jwkq.xupt.edu.cn:8080")
//                .addHeader("Referer","http://jwkq.xupt.edu.cn:8080/User/Query")
//                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36 SE 2.X MetaSr 1.0")
                .build();
        Response response = okHttpClient.newCall(request).execute();
        Log.e(tag,sessionId+"; "+set_Cookie);
        if (response.isSuccessful()){
            Log.e(tag,response.body().string());
        }else {
            Log.e(tag,"okhttp请求出错");
        }
    }

}
