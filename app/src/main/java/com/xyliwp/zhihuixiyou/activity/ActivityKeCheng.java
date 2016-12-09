package com.xyliwp.zhihuixiyou.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.xyliwp.zhihuixiyou.R;
import com.xyliwp.zhihuixiyou.ui.ShuXinPopWindow;
import com.xyliwp.zhihuixiyou.utils.Content;
import com.xyliwp.zhihuixiyou.utils.KeCheng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.xyliwp.zhihuixiyou.utils.Content.YanSe;
import static com.xyliwp.zhihuixiyou.utils.Content.keChengs;
import static com.xyliwp.zhihuixiyou.utils.Content.keChengxinxis;
import static com.xyliwp.zhihuixiyou.utils.Content.sessionId;
import static com.xyliwp.zhihuixiyou.utils.Content.set_Cookie;

/**
 * Created by lwp940118 on 2016/10/30.
 */
public class ActivityKeCheng extends Activity{

    private static final String tag = "ActivityKeCheng ------ ";

    private ImageButton imageButton_kaoqinback;
    private ImageButton imageButton_kaoqinshuaxin;

    private ShuXinPopWindow shuXinPopWindow;
    private LinearLayout linearLayout_kaoqin;
    private ListView listView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:     //获取信息失败
                    shuXinPopWindow.dismiss();
                    listView.setAdapter(new MyAdapter(ActivityKeCheng.this,keChengxinxis));
                    Toast.makeText(ActivityKeCheng.this, "课程信息获取失败", Toast.LENGTH_SHORT).show();
                    linearLayout_kaoqin.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    shuXinPopWindow.dismiss();
                    listView.setAdapter(new MyAdapter(ActivityKeCheng.this,keChengxinxis));
                    linearLayout_kaoqin.setVisibility(View.VISIBLE);
                    break;
                case 3:         //打开popwindows
                    linearLayout_kaoqin.setVisibility(View.INVISIBLE);
                    shuXinPopWindow = new ShuXinPopWindow(ActivityKeCheng.this,"课程信息获取中...");
                    shuXinPopWindow.showPopupWindow(imageButton_kaoqinshuaxin);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kecheng);

        initFindById();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getKaoQinInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        setOnClickListener();
    }

    private void initFindById(){
        imageButton_kaoqinback = (ImageButton)findViewById(R.id.imagebutton_kaoqin_back);
        imageButton_kaoqinshuaxin = (ImageButton)findViewById(R.id.imagebutton_kaoqin_shuaxin);
        linearLayout_kaoqin = (LinearLayout)findViewById(R.id.linearlayout_kaoqin);
        listView = (ListView)findViewById(R.id.listview_activitykecheng);
    }

    private void setOnClickListener(){
        imageButton_kaoqinback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageButton_kaoqinshuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getKaoQinInfo();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 获取本学期的考勤记录
     * @throws IOException
     */
    private void getKaoQinInfo() throws IOException {

        Message message1 = new Message();
        message1.what = 3;
        handler.sendMessage(message1);
        final OkHttpClient okHttpClient = new OkHttpClient();
        //创建请求参数
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("json", "true");
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url("http://jwkq.xupt.edu.cn:8080/User/GetAttendRepList")
                .post(body)
                .addHeader("Cookie", sessionId + "; " + set_Cookie)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response.body().string());
                jieXiShuJu(jsonObject);
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            //获取信息失败
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    /**
     * 获取课程签到信息
     * @param jsonObject
     * @throws JSONException
     */
    private void jieXiShuJu(JSONObject jsonObject) throws JSONException{

        JSONArray jsonArray = jsonObject.getJSONArray("Obj");
        Log.e(tag,jsonArray.toString());
        keChengxinxis.removeAll(keChengxinxis);
        for (int i = 0 ;i < jsonArray.length();i++){
            String js = jsonArray.get(i).toString();
            JSONObject jsonObject1 = new JSONObject(js);
            KeCheng keCheng = new KeCheng();
            keCheng.setKechengming(jsonObject1.getString("CourseName"));
            keChengxinxis.add(keCheng);
        }
        Log.e(tag,"课程数量"+keChengxinxis.size());
    }


    /**
     * lietview的适配器
     */
    class MyAdapter extends BaseAdapter {
        private List<KeCheng> keChengliebioa;
        private LayoutInflater layoutInflater;
        private Context context;
        private Integer[] TEST_DATAS;
        public MyAdapter(Context content, List<KeCheng> keChengs){
            this.context = content;
            this.keChengliebioa = keChengs;
            this.layoutInflater = LayoutInflater.from(content);
            TEST_DATAS = new Integer[keChengs.size()];
            for (int i = 0 ;i < keChengs.size();i++){
                TEST_DATAS[i] = YanSe[i];
            }
        }

        @Override
        public int getCount() {
            return keChengliebioa.size();
        }

        @Override
        public Object getItem(int position) {
            return keChengliebioa.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHorld viewHorld = null;
            if (viewHorld == null){
                viewHorld = new ViewHorld();
                convertView = layoutInflater.inflate(R.layout.listview_item_activitykecheng,null);
                viewHorld.textView = (TextView)convertView.findViewById(R.id.textview_listvew_activitykecheng_item);
                convertView.setTag(viewHorld);
            }else{
                viewHorld = (ViewHorld)convertView.getTag();
            }
            viewHorld.textView.setText(keChengliebioa.get(position).getKechengming());
            viewHorld.textView.setBackgroundColor(context.getResources().getColor((int)TEST_DATAS[position]));

            return convertView;
        }

        public class ViewHorld{
            private TextView textView;
        }

    }

}
