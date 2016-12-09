package com.xyliwp.zhihuixiyou.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xyliwp.zhihuixiyou.R;
import com.xyliwp.zhihuixiyou.ui.PopWindowShenSu;
import com.xyliwp.zhihuixiyou.ui.QianDaoListView;
import com.xyliwp.zhihuixiyou.ui.ShuXinPopWindow;
import com.xyliwp.zhihuixiyou.utils.QianDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.xyliwp.zhihuixiyou.utils.Content.*;

/**
 * Created by lwp940118 on 2016/10/17.
 */
public class Fragment_Prod extends Fragment{

    private static final String tag = "Fragment_Prod ------ ";
    private View rootView;
    private ImageButton imageButton_qiandaoshuaxin;
    private LinearLayout linearLayout_classtanle;
    private ShuXinPopWindow shuXinPopWindow;
    private QianDaoListViewAdapter qianDaoListViewAdapter;
    private QianDaoListView qianDaoListView;
    private int tagpage = 1;
    private int total;
    private View viewHedle;
    private String flagchebox = "";
    private String status = "1";

    private Button button_heald_data1;
    private Button button_heald_data2;
    private Button button_heald_jintain;
    private Button button_heald_jinyizhou;
    private Button button_heald_jinyiyue;
    private CheckBox checkBox_heald_zhegnchang;
    private CheckBox checkBox_heald_chidao;
    private CheckBox checkBox_heald_queqin;
    private LinearLayout linearLayout_heald_chaxun;

    private String data1 = "";
    private String data2 = "";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Calendar calendar = Calendar.getInstance();
    private String chi = "";

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:     //获取信息失败
                    if (shuXinPopWindow != null) {
                        shuXinPopWindow.dismiss();
                    }
                    qianDaoListView.onLoadComplete();
                    qianDaoListViewAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "考勤获取失败", Toast.LENGTH_SHORT).show();
                    linearLayout_classtanle.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    if (shuXinPopWindow != null){
                        shuXinPopWindow.dismiss();
                    }
                    qianDaoListView.onLoadComplete();
                    qianDaoListViewAdapter.notifyDataSetChanged();
                    linearLayout_classtanle.setVisibility(View.VISIBLE);
                    break;
                case 3:         //打开popwindows
                    linearLayout_classtanle.setVisibility(View.INVISIBLE);
                    shuXinPopWindow = new ShuXinPopWindow(getActivity(),"考勤获取中...");
                    shuXinPopWindow.showPopupWindow(imageButton_qiandaoshuaxin);
                    break;
                case 4:     //获取信息失败
                    if (shuXinPopWindow != null) {
                        shuXinPopWindow.dismiss();
                    }
                    qianDaoListView.onLoadComplete();
                    qianDaoListView.setJinDu("无新数据可加载");
                    qianDaoListViewAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "该时间段无签到信息", Toast.LENGTH_SHORT).show();
                    linearLayout_classtanle.setVisibility(View.VISIBLE);
                    break;
                case 5:     //获取信息失败
                    if (shuXinPopWindow != null) {
                        shuXinPopWindow.dismiss();
                    }
                    qianDaoListView.onLoadComplete();
                    qianDaoListView.setJinDu("无新数据可加载");
                    qianDaoListViewAdapter.notifyDataSetChanged();
                    linearLayout_classtanle.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (View)inflater.inflate(R.layout.fragment_prod,container,false);
        //绑定id
        initFindById();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tagpage =1;
                    getKebiaoInfo(tagpage);
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
        linearLayout_classtanle = (LinearLayout)rootView.findViewById(R.id.linearlayout_kaoqin);
        imageButton_qiandaoshuaxin = (ImageButton)rootView.findViewById(R.id.imagebutton_kaoqin_shuaxin);
        qianDaoListView = (QianDaoListView)rootView.findViewById(R.id.qiandaoListview);
        qianDaoListViewAdapter = new QianDaoListViewAdapter(getContext(),daka);
        qianDaoListView.setAdapter(qianDaoListViewAdapter);
        qianDaoListView.setOnLoadListener(new QianDaoListView.OnLoadListener() {
            @Override
            public void onLoad() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tagpage++;
                            getKebiaoInfo(tagpage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        imageButton_qiandaoshuaxin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Message message1 = new Message();
                            message1.what = 3;
                            handler.sendMessage(message1);
                            tagpage = 1;
                            getKebiaoInfo(tagpage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        healdkongjian();
    }

    /**
     * 对listview的头部布局里面的进行设置和点击时间的设置
     */
    private void healdkongjian(){

        viewHedle = LayoutInflater.from(getContext()).inflate(R.layout.mylist_head,null);
        qianDaoListView.addHeaderView(viewHedle);
        button_heald_data1 = (Button)viewHedle.findViewById(R.id.button_heald_data1);
        button_heald_data2 = (Button)viewHedle.findViewById(R.id.button_heald_data2);
        button_heald_jintain = (Button)viewHedle.findViewById(R.id.button_heald_jintain);
        button_heald_jinyiyue = (Button)viewHedle.findViewById(R.id.button_heald_jinyiyue);
        button_heald_jinyizhou = (Button)viewHedle.findViewById(R.id.button_heald_jinyizhou);
        linearLayout_heald_chaxun = (LinearLayout)viewHedle.findViewById(R.id.linearlayout_heald_chaxun);
        checkBox_heald_chidao = (CheckBox)viewHedle.findViewById(R.id.checkbox_heald_chidao);
        checkBox_heald_queqin = (CheckBox)viewHedle.findViewById(R.id.checkbox_heald_queqin);
        checkBox_heald_zhegnchang = (CheckBox)viewHedle.findViewById(R.id.checkbox_heald_zhegnchang);
        //对其点击事件的设置
        calendar.setTime(new Date());
        Date date = calendar.getTime();
        data1 = simpleDateFormat.format(date);
        data2 = data1;
        button_heald_data1.setText(data1);
        button_heald_data2.setText(data2);
        linearLayout_heald_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               returnFlagchebox();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tagpage =1;
                            getKebiaoInfo(tagpage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        button_heald_jintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setTime(new Date());
                status = "1";
                Date date = calendar.getTime();
                data1 = simpleDateFormat.format(date);
                data2 = data1;
                button_heald_data1.setText(data1);
                button_heald_data2.setText(data2);
                button_heald_jintain.setBackgroundResource(R.drawable.kaoqinsharp2);
                button_heald_jinyiyue.setBackgroundResource(R.drawable.kaoqinsharp1);
                button_heald_jinyizhou.setBackgroundResource(R.drawable.kaoqinsharp1);
            }
        });
        button_heald_jinyizhou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "2";
                calendar.setTime(new Date());
                Date date = calendar.getTime();
                data2 = simpleDateFormat.format(date);
                calendar.add(Calendar.DATE, -7);
                data1 = simpleDateFormat.format(calendar.getTime());
                button_heald_data1.setText(data1);
                button_heald_data2.setText(data2);
                button_heald_jintain.setBackgroundResource(R.drawable.kaoqinsharp1);
                button_heald_jinyiyue.setBackgroundResource(R.drawable.kaoqinsharp1);
                button_heald_jinyizhou.setBackgroundResource(R.drawable.kaoqinsharp2);
            }
        });
        button_heald_jinyiyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = "3";
                calendar.setTime(new Date());
                Date date = calendar.getTime();
                data2 = simpleDateFormat.format(date);
                calendar.add(Calendar.MONTH,  -1);
                data1 = simpleDateFormat.format(calendar.getTime());
                button_heald_data1.setText(data1);
                button_heald_data2.setText(data2);
                button_heald_jintain.setBackgroundResource(R.drawable.kaoqinsharp1);
                button_heald_jinyiyue.setBackgroundResource(R.drawable.kaoqinsharp2);
                button_heald_jinyizhou.setBackgroundResource(R.drawable.kaoqinsharp1);
            }
        });

        button_heald_data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    calendar.setTime(simpleDateFormat.parse(data1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        data1 = simpleDateFormat.format(calendar.getTime());
                        button_heald_data1.setText(data1);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });
        button_heald_data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    calendar.setTime(simpleDateFormat.parse(data2));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DatePickerDialog pickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        data2 = simpleDateFormat.format(calendar.getTime());
                        button_heald_data2.setText(data2);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });

    }

    /**
     * 返回考勤情况的flag参数
     * @return
     */
    private String returnFlagchebox(){
        flagchebox = "";
        if (checkBox_heald_zhegnchang.isChecked()){
            if (flagchebox.length() != 0){
                flagchebox += "a1";
            }else{
                flagchebox += "1";
            }
        }
        if (checkBox_heald_chidao.isChecked()){
            if (flagchebox.length() != 0){
                flagchebox += "a2";
            }else{
                flagchebox += "2";
            }
        }
        if (checkBox_heald_queqin.isChecked()){
            if (flagchebox.length() != 0){
                flagchebox += "a3";
            }else{
                flagchebox += "3";
            }
        }
        Log.e(tag,flagchebox);
        return flagchebox;
    }

    /**
     * 获取本学期的课表
     * @throws IOException
     */
    @SuppressLint("LongLogTag")
    private void getKebiaoInfo(int tagpage) throws IOException {
        if (tagpage == 1) {
            daka.removeAll(daka);
        }
        final OkHttpClient okHttpClient = new OkHttpClient();
        //创建请求参数
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("WaterDate",data1+"a"+data2);
        builder.add("Status", status);
        builder.add("Flag", flagchebox);
        builder.add("page", ""+tagpage);
        builder.add("rows", "10");
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url("http://jwkq.xupt.edu.cn:8080/User/GetAttendList")
                .post(body)
                .addHeader("Cookie", sessionId + "; " + set_Cookie)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        Log.e(tag,"测试response----"+response.toString());
        if (response.isSuccessful()) {
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            try {
                String si = response.body().string();
                jsonObject = new JSONObject(si);
                Log.e(tag,si);
                total = jsonObject.getInt("total");
                if (total != 0) {
                    jsonArray = jsonObject.getJSONArray("rows");
                    jieXiShuJu(jsonArray);
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }else{
                    Message message = new Message();
                    message.what = 4;
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
        if (total == daka.size()){
            Message message = new Message();
            message.what = 5;
            handler.sendMessage(message);
        }
        for (int i = 0 ;i < jsonArray.length();i++){
            String js = jsonArray.get(i).toString();
            JSONObject jsonObject1 = new JSONObject(js);
            QianDao qianDao = new QianDao();
            qianDao.setRiqi(jsonObject1.getString("WaterDate"));
            qianDao.setXueqi(jsonObject1.getString("Term_No"));
            qianDao.setKechengdaima(jsonObject1.getString("S_Code"));
            qianDao.setKechengminghceng(jsonObject1.getString("S_Name"));
            qianDao.setJieci(jsonObject1.getString("JT_No"));
            qianDao.setJiaoshi(jsonObject1.getString("RoomNum"));
            qianDao.setLoudong(jsonObject1.getString("BName"));
            qianDao.setKaoqinzhuangtai(jsonObject1.getInt("Status"));
            qianDao.setShaukashijian(jsonObject1.getString("WaterTime"));
            qianDao.setS_Code(jsonObject1.getInt("SBH"));
            qianDao.setSlass_no(jsonObject1.getInt("Class_No"));
            qianDao.setR_BH(jsonObject1.getInt("RBH"));
            daka.add(qianDao);
        }
        Log.e(tag,"条目"+daka.size());
    }
    public class QianDaoListViewAdapter extends BaseAdapter {

        private Context context;
        private List<QianDao> qindaode;
        private LayoutInflater layoutInflater;
        private QianDao qianDao;

        public QianDaoListViewAdapter(Context context , List<QianDao> qindaode){
            this.qindaode = qindaode;
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return qindaode.size();
        }

        @Override
        public Object getItem(int position) {
            return qindaode.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHorld viewHorld = null;
            if (viewHorld == null){
                viewHorld = new ViewHorld();
                convertView = layoutInflater.from(context).inflate(R.layout.qiandaolistview_item,null);
                viewHorld.textView_daka_riqi = (TextView)convertView.findViewById(R.id.textview_daka_riqi);
                viewHorld.textView_daka_kechegndaima = (TextView)convertView.findViewById(R.id.textview_daka_kechegndaima);
                viewHorld.textView_daka_kecehgnmingcheng = (TextView)convertView.findViewById(R.id.textview_daka_kechegnming);
                viewHorld.textView_daka_jieci = (TextView)convertView.findViewById(R.id.textview_daka_jieci);
                viewHorld.textView_daka_jiaoshi = (TextView)convertView.findViewById(R.id.textview_daka_jioashi);
                viewHorld.textView_daka_loudong = (TextView)convertView.findViewById(R.id.textview_daka_loudong);
                viewHorld.textView_daka_kaoqinzhuangtai = (TextView)convertView.findViewById(R.id.textview_daka_zhuangtai);
                viewHorld.textView_daka_shuakashijian = (TextView)convertView.findViewById(R.id.textview_daka_shuakashijian);
                viewHorld.button_daka_shensu = (Button) convertView.findViewById(R.id.button_daka_shensu);
                convertView.setTag(viewHorld);
            }else{
                viewHorld = (ViewHorld)convertView.getTag();
            }
            qianDao = qindaode.get(position);
            viewHorld.textView_daka_riqi.setText(qianDao.getRiqi());
            viewHorld.textView_daka_kechegndaima.setText(qianDao.getKechengdaima());
            viewHorld.textView_daka_kecehgnmingcheng.setText(qianDao.getKechengminghceng());
            viewHorld.textView_daka_jieci.setText(qianDao.getJieci());
            viewHorld.textView_daka_jiaoshi.setText(qianDao.getJiaoshi());
            viewHorld.textView_daka_loudong.setText(qianDao.getLoudong());
            if (qianDao.getKaoqinzhuangtai() == 1) {
                viewHorld.textView_daka_kaoqinzhuangtai.setText("正常签到");
                viewHorld.textView_daka_kaoqinzhuangtai.setBackgroundResource(R.drawable.kaoqinzhengchangsharp);
            }else{
                viewHorld.textView_daka_kaoqinzhuangtai.setText("缺    勤");
                viewHorld.textView_daka_kaoqinzhuangtai.setBackgroundResource(R.drawable.kaoqinsharp2);
            }
            viewHorld.textView_daka_shuakashijian.setText(qianDao.getShaukashijian());
            if (bijiaoDta(daka.get(position).getRiqi())){
                viewHorld.button_daka_shensu.setVisibility(View.GONE);
            }
            viewHorld.button_daka_shensu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PopWindowShenSu popWindowShenSu = new PopWindowShenSu(getContext(),position);
                    popWindowShenSu.showPopupWindow(imageButton_qiandaoshuaxin);
                }
            });

            return convertView;
        }
        public class ViewHorld{
            TextView textView_daka_riqi;
            TextView textView_daka_kechegndaima;
            TextView textView_daka_kecehgnmingcheng;
            TextView textView_daka_jieci;
            TextView textView_daka_jiaoshi;
            TextView textView_daka_loudong;
            TextView textView_daka_kaoqinzhuangtai;
            TextView textView_daka_shuakashijian;
            Button button_daka_shensu;
        }

        /**
         *日期的比较 用于 是否显示申诉按钮
         * @param qian
         * @return
         */
        private boolean bijiaoDta(String qian){
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, -7);
            Date date = calendar.getTime();
            Log.e(tag,"测试日期:"+date.toString());
            try {
                Date dt2 = simpleDateFormat.parse(qian);
                if (date.getTime() > dt2.getTime()) {
                    return true;
                }  else {
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return false;
        }

    }

}
