package com.xyliwp.zhihuixiyou.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xyliwp.zhihuixiyou.R;
import com.xyliwp.zhihuixiyou.utils.QianDao;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.xyliwp.zhihuixiyou.utils.Content.daka;
import static com.xyliwp.zhihuixiyou.utils.Content.httpClient;
import static com.xyliwp.zhihuixiyou.utils.Content.sessionId;
import static com.xyliwp.zhihuixiyou.utils.Content.set_Cookie;

/**
 * Created by lwp940118 on 2016/11/3.
 */
public class PopWindowShenSu extends PopupWindow{
    private View popWindow;
    private int posion;
    private static final String tag = "PopWindowShenSu ------- ";
    private EditText editText_popshensu_kecheng;
    private EditText editText_popshensu_riqi;
    private EditText editText_popshensu_beizhu;
    private RadioButton radioButton_popshensu_zhengchang;
    private RadioButton radioButton_popshensu_chidao;
    private RadioButton radioButton_popshensu_queqin;
    private Button button_popshensu_tijiao;
    private Button button_popshensu_quxioa;
    private QianDao qianDao;
    private JSONObject jsonObject1;
    private Context context;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    Toast.makeText(context,"服务器获取数据异常",Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    try {
                        Toast.makeText(context,jsonObject1.getString("Msg"),Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public PopWindowShenSu(final Context activity, int posion){
        LayoutInflater layoutInflater = (LayoutInflater)activity
                .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        popWindow = layoutInflater.inflate(R.layout.popwindowshensu,null);
        //设置弹出的popview视图
        this.setContentView(popWindow);
        //设置弹出窗口的宽
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置高度  为layout的自适应高度
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置  pop可点击可见
        this.setFocusable(true);
        this.context = activity;
        this.posion = posion;
        this.setOutsideTouchable(true);
        this.update();
        init();

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable colorDrawable = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener
        this.setBackgroundDrawable(colorDrawable);
        // 设置pop弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

    }

    /**
     * 初始化 popwindow
     */
    private void init(){
        editText_popshensu_beizhu = (EditText)popWindow.findViewById(R.id.edittext_shensu_beizhu);
        editText_popshensu_kecheng = (EditText)popWindow.findViewById(R.id.edittext_shensu_kecheng);
        editText_popshensu_riqi = (EditText)popWindow.findViewById(R.id.edittext_shensu_riqi);
        radioButton_popshensu_chidao = (RadioButton)popWindow.findViewById(R.id.radiobutton_chidao);
        radioButton_popshensu_queqin = (RadioButton)popWindow.findViewById(R.id.radiobutton_queqin);
        radioButton_popshensu_zhengchang = (RadioButton)popWindow.findViewById(R.id.radiobutton_zhengchang);
        button_popshensu_quxioa = (Button)popWindow.findViewById(R.id.button_popshensu_quxiao);
        button_popshensu_tijiao = (Button)popWindow.findViewById(R.id.button_popshensu_tijaio);
        qianDao = daka.get(posion);
        editText_popshensu_kecheng.setText(qianDao.getKechengminghceng());
        editText_popshensu_riqi.setText(qianDao.getRiqi());
        int stu = qianDao.getKaoqinzhuangtai();
        if (stu == 1){
            radioButton_popshensu_zhengchang.setChecked(true);
        }else if (stu == 2){
            radioButton_popshensu_chidao.setChecked(true);
        }else {
            radioButton_popshensu_queqin.setChecked(true);
        }

        button_popshensu_quxioa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        button_popshensu_tijiao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String ztahg = "";
                        if (radioButton_popshensu_queqin.isChecked()){
                            ztahg = "3";
                        }
                        if (radioButton_popshensu_zhengchang.isChecked()){
                            ztahg = "1";
                        }
                        if (radioButton_popshensu_chidao.isChecked()){
                            ztahg = "2";
                        }
                        try {
                            getShensuInfo(qianDao.getSlass_no(),qianDao.getRiqi(),qianDao.getJieci(),
                                    qianDao.getS_Code(), qianDao.getR_BH(),qianDao.getXueqi(),
                                    editText_popshensu_beizhu.getText().toString()+"",
                                    qianDao.getKaoqinzhuangtai(),ztahg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

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

    /**
     * 获取本学期的课表
     * @param Class_no
     * @param S_Date
     * @param Jc
     * @param S_Code
     * @param R_BH
     * @param Term
     * @param Remark
     * @param S_Status
     * @param A_Status
     * @throws IOException
     */
    @SuppressLint("LongLogTag")
    private void getShensuInfo(int Class_no,String S_Date,String Jc,int S_Code,
                               int R_BH ,String Term,String Remark,int S_Status,String A_Status) throws IOException {

        //创建请求参数
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Class_no", Class_no);
            jsonObject.put("S_Date", S_Date);
            jsonObject.put("Jc", Jc);
            jsonObject.put("S_Code", S_Code);
            jsonObject.put("R_BH", R_BH);
            jsonObject.put("Term", Term);
            jsonObject.put("Remark", Remark);
            jsonObject.put("S_Status", S_Status);
            jsonObject.put("A_Status", A_Status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e(tag, jsonObject.toString());

        HttpPost httpPost = new HttpPost("http://jwkq.xupt.edu.cn:8080/Apply/ApplyData");
        httpPost.setEntity(new StringEntity(jsonObject.toString()));
        httpPost.setHeader("Cookie", sessionId + "; " + set_Cookie);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        Log.e(tag, "" + httpResponse.getStatusLine().getStatusCode());
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            HttpEntity httpEntity = httpResponse.getEntity();
            //将返回的httpresponse用json解析
            String stringFanhui = EntityUtils.toString(httpEntity, "utf-8").toString();
            Log.e(tag,stringFanhui);
            jsonObject1 = null;
            try {
                jsonObject1 = new JSONObject(stringFanhui);
                Log.e(tag,jsonObject1.getString("Msg"));
                if (jsonObject1.getBoolean("IsSucceed")){
                    dismiss();
                }
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
            Log.e(tag,"服务器数据异常");
        }
    }

}
