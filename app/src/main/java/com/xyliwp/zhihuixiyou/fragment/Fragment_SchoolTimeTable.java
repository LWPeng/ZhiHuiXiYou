package com.xyliwp.zhihuixiyou.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopeer.cardstack.AllMoveDownAnimatorAdapter;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.UpDownAnimatorAdapter;
import com.loopeer.cardstack.UpDownStackAnimatorAdapter;
import com.xyliwp.zhihuixiyou.R;
import com.xyliwp.zhihuixiyou.ui.ShuXinPopWindow;
import com.xyliwp.zhihuixiyou.utils.KeBiao;
import com.xyliwp.zhihuixiyou.utils.KeBiaoStackAdapter;
import com.xyliwp.zhihuixiyou.utils.KeCheng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.xyliwp.zhihuixiyou.utils.Content.keBiaos;
import static com.xyliwp.zhihuixiyou.utils.Content.keChengs;
import static com.xyliwp.zhihuixiyou.utils.Content.kebiaoer;
import static com.xyliwp.zhihuixiyou.utils.Content.kebiaoliu;
import static com.xyliwp.zhihuixiyou.utils.Content.kebiaoqi;
import static com.xyliwp.zhihuixiyou.utils.Content.kebiaosi;
import static com.xyliwp.zhihuixiyou.utils.Content.kebiaowu;
import static com.xyliwp.zhihuixiyou.utils.Content.kebiaoyi;
import static com.xyliwp.zhihuixiyou.utils.Content.kebiasan;
import static com.xyliwp.zhihuixiyou.utils.Content.sessionId;
import static com.xyliwp.zhihuixiyou.utils.Content.set_Cookie;
import static com.xyliwp.zhihuixiyou.utils.Content.today;

/**
 * Created by lwp940118 on 2016/10/17.
 */
public class Fragment_SchoolTimeTable extends Fragment implements CardStackView.ItemExpendListener{

    private static final String tag = "Fragment_schooltimetable ------- ";
    private View rootView;
    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_4,
            R.color.color_7,
            R.color.color_13,
            R.color.color_16,
            R.color.color_19,
            R.color.color_2,
            R.color.color_9
    };
    private CardStackView mStackView;
    private LinearLayout mActionButtonContainer;
    private KeBiaoStackAdapter mTestStackAdapter;

    private ImageButton imageButton_kaoqinshuaxin;
    private LinearLayout linearLayout_classtanle;
    private ShuXinPopWindow shuXinPopWindow;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:     //获取信息失败
                    if (shuXinPopWindow != null) {
                        shuXinPopWindow.dismiss();
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
                    Toast.makeText(getContext(), "课表获取失败", Toast.LENGTH_SHORT).show();
                    linearLayout_classtanle.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    if (shuXinPopWindow != null){
                        shuXinPopWindow.dismiss();
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
                    linearLayout_classtanle.setVisibility(View.VISIBLE);
                    break;
                case 3:         //打开popwindows
                    linearLayout_classtanle.setVisibility(View.INVISIBLE);
                    shuXinPopWindow = new ShuXinPopWindow(getActivity(),"课表获取中...");
                    shuXinPopWindow.showPopupWindow(imageButton_kaoqinshuaxin);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View)inflater.inflate(R.layout.fragment_schooltimetable,container,false);
        //绑定id
        initFindById();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getKebiaoInfo(today);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return rootView;
    }

    /**
     * textview查找绑定id
     */
    private void initFindById(){
        mStackView = (CardStackView) rootView.findViewById(R.id.stackview_main);
        mActionButtonContainer = (LinearLayout) rootView.findViewById(R.id.button_container);
        linearLayout_classtanle = (LinearLayout)rootView.findViewById(R.id.linearlayout_classtable);
        imageButton_kaoqinshuaxin = (ImageButton)rootView.findViewById(R.id.imagebutton_class_shuaxin);
        mStackView.setItemExpendListener(this);
        mTestStackAdapter = new KeBiaoStackAdapter(getContext());
        mStackView.setAdapter(mTestStackAdapter);
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        mTestStackAdapter.updateData(Arrays.asList(TEST_DATAS));
                    }
                }
                , 200
        );
        imageButton_kaoqinshuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            Message message1 = new Message();
                            message1.what = 3;
                            handler.sendMessage(message1);
                            getKebiaoInfo(today);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    /**
    * 获取本学期的课表
    * @throws IOException
    */
    @SuppressLint("LongLogTag")
    private void getKebiaoInfo(String s) throws IOException {

        Log.e(tag,today);
        final OkHttpClient okHttpClient = new OkHttpClient();
        //创建请求参数
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("term_no",s);
        builder.add("json", "true");
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url("http://jwkq.xupt.edu.cn:8080/User/GetStuClass")
                .post(body)
                .addHeader("Cookie", sessionId + "; " + set_Cookie)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            try {
                jsonObject = new JSONObject(response.body().string());
                if (jsonObject.getBoolean("IsSucceed")){
                    jsonArray = jsonObject.getJSONArray("Obj");
                    jieXiShuJu(jsonArray);
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }else{
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
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
     * 获取课表信息
     * @param jsonArray
     * @throws JSONException
     */
    @SuppressLint("LongLogTag")
    private void jieXiShuJu(JSONArray jsonArray) throws JSONException{
        Log.e(tag,jsonArray.toString());
        keBiaos.removeAll(keBiaos);
        for (int i = 0 ;i < jsonArray.length();i++){
            String js = jsonArray.get(i).toString();
            JSONObject jsonObject1 = new JSONObject(js);
            KeBiao keBiao = new KeBiao();
            keBiao.setTeacher(jsonObject1.getString("Teach_Name"));
            keBiao.setKechengming(jsonObject1.getString("S_Name"));
            keBiao.setDidian(jsonObject1.getString("RoomNum"));
            keBiao.setJieci(jsonObject1.getString("JT_NO"));
            keBiao.setZhouji(jsonObject1.getInt("WEEKNUM"));
            keBiaos.add(keBiao);
        }
        Log.e(tag,""+jsonArray.length());

        kebiaoyi.removeAll(kebiaoyi);
        kebiaoer.removeAll(kebiaoer);
        kebiasan.removeAll(kebiasan);
        kebiaosi.removeAll(kebiaosi);
        kebiaowu.removeAll(kebiaowu);
        kebiaoliu.removeAll(kebiaoliu);
        kebiaoqi.removeAll(kebiaoqi);

        for (int i = 0;i < keBiaos.size();i++){

            switch (keBiaos.get(i).getZhouji()){
                case 1:
                    kebiaoyi.add(keBiaos.get(i));
                    break;
                case 2:
                    kebiaoer.add(keBiaos.get(i));
                    break;
                case 3:
                    kebiasan.add(keBiaos.get(i));
                    break;
                case 4:
                    kebiaosi.add(keBiaos.get(i));
                    break;
                case 5:
                    kebiaowu.add(keBiaos.get(i));
                    break;
                case 6:
                    kebiaoliu.add(keBiaos.get(i));
                    break;
                case 7:
                    kebiaoqi.add(keBiaos.get(i));
                    break;
            }
        }

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
