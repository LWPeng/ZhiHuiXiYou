package com.xyliwp.zhihuixiyou.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;
import com.xyliwp.zhihuixiyou.R;
import com.xyliwp.zhihuixiyou.ui.ShuXinPopWindow;
import com.xyliwp.zhihuixiyou.utils.KaoQinStackAdapter;
import com.xyliwp.zhihuixiyou.utils.KeCheng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.xyliwp.zhihuixiyou.utils.Content.*;

/**
 * Created by lwp940118 on 2016/10/30.
 */
public class ActivityKaoQin extends Activity implements CardStackView.ItemExpendListener {

    private static final String tag = "ActivityKaoQin ------ ";
    private CardStackView mStackView;
    private LinearLayout mActionButtonContainer;
    private KaoQinStackAdapter mTestStackAdapter;

    private ImageButton imageButton_kaoqinback;
    private ImageButton imageButton_kaoqinshuaxin;

    private ShuXinPopWindow shuXinPopWindow;
    private LinearLayout linearLayout_kaoqin;

    public static Integer[] TEST_DATAS;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:     //获取信息失败
                    shuXinPopWindow.dismiss();
                    Toast.makeText(ActivityKaoQin.this, "考勤信息获取失败", Toast.LENGTH_SHORT).show();
                    adddata();
                    linearLayout_kaoqin.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    shuXinPopWindow.dismiss();
                    adddata();
                    linearLayout_kaoqin.setVisibility(View.VISIBLE);
                    break;
                case 3:         //打开popwindows
                    linearLayout_kaoqin.setVisibility(View.INVISIBLE);
                    shuXinPopWindow = new ShuXinPopWindow(ActivityKaoQin.this,"考勤信息获取中...");
                    shuXinPopWindow.showPopupWindow(imageButton_kaoqinshuaxin);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaoqin);
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

        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new KaoQinStackAdapter(this);
        mStackView.setAdapter(mTestStackAdapter);
        setOnClickListener();
    }

    private void adddata(){
        TEST_DATAS = new Integer[keChengs.size()];
        for (int i = 0 ;i < keChengs.size();i++){
            TEST_DATAS[i] = YanSe[i];
        }
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );
    }

    private void initFindById(){
        mStackView = (CardStackView) findViewById(R.id.stackview_kaoqin);
        mActionButtonContainer = (LinearLayout) findViewById(R.id.button_container);
        imageButton_kaoqinback = (ImageButton)findViewById(R.id.imagebutton_kaoqin_back);
        imageButton_kaoqinshuaxin = (ImageButton)findViewById(R.id.imagebutton_kaoqin_shuaxin);
        linearLayout_kaoqin = (LinearLayout)findViewById(R.id.linearlayout_kaoqin);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actions, menu);
        return super.onCreateOptionsMenu(menu);
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
        keChengs.removeAll(keChengs);
        for (int i = 0 ;i < jsonArray.length();i++){
            String js = jsonArray.get(i).toString();
            JSONObject jsonObject1 = new JSONObject(js);
            KeCheng keCheng = new KeCheng();
            keCheng.setKechengming(jsonObject1.getString("CourseName"));
            keCheng.setYingdao(jsonObject1.getInt("ShouldAttend"));
            keCheng.setZongji(jsonObject1.getInt("Total"));
            keCheng.setZhegnchang(jsonObject1.getInt("Attend"));
            keCheng.setChidao(jsonObject1.getInt("Late"));
            keCheng.setQueqin(jsonObject1.getInt("Absence"));
            keChengs.add(keCheng);
        }
        Log.e(tag,"课程数量"+keChengs.size());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_all_down:
                mStackView.setAnimatorAdapter(new AllMoveDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down:
                mStackView.setAnimatorAdapter(new UpDownAnimatorAdapter(mStackView));
                break;
            case R.id.menu_up_down_stack:
                mStackView.setAnimatorAdapter(new UpDownStackAnimatorAdapter(mStackView));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPreClick(View view) {
        mStackView.pre();
    }

    public void onNextClick(View view) {
        mStackView.next();
    }

    @Override
    public void onItemExpend(boolean expend) {
        mActionButtonContainer.setVisibility(expend ? View.VISIBLE : View.GONE);
    }

}
