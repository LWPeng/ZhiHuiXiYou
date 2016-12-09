package com.xyliwp.zhihuixiyou.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xyliwp.zhihuixiyou.MainActivity;
import com.xyliwp.zhihuixiyou.R;
import com.xyliwp.zhihuixiyou.utils.IsNetWorkUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import static com.xyliwp.zhihuixiyou.utils.Content.*;


/**
 * 需求：登录页面的爬取
 * Created by lwp940118 on 2016/10/11.
 */
public class ActivityLogin extends Activity{

    private final String tag = "ActivityLogin ------- ";

    //登录button
    private Button button_login;
    //编辑框的定义
    private EditText editText_username;
    private EditText editText_password;
    //验证码输入框
    private EditText editText_identifying;
    //网上获取的验证码
    private ImageView imageView_identifying;
    //记住密码的复选框
    private CheckBox checkBox_keeppassword;
    //记住密码的存储xml
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //用户名 和密码 及其验证码;
    private String username;
    private String password;
    private String indentifying;
    //验证码的图片
    private Bitmap bitmap_indenfying;
    private HttpPost httpPost;

    /**
     * handler 网络加载后 消息通知用来更改界面，
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    imageView_identifying.setImageBitmap(bitmap_indenfying);
                    Log.e(tag,"更新验证码");
                    break;
                case 2:
                    Log.e(tag,"登录成功，系统将跳转至mainactivity");
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 3:
                    Toast.makeText(getApplicationContext(),"用户名或密码错误",Toast.LENGTH_SHORT).show();
                    Log.e(tag,"登录失败");
                    break;
                case 4:
                    Toast.makeText(getApplicationContext(),"服务器异常",Toast.LENGTH_SHORT).show();
                    Log.e(tag,"登录失败");
                    break;
                case 5:
                    Toast.makeText(getApplicationContext(),"验证码输入错误",Toast.LENGTH_SHORT).show();
                    Log.e(tag,"登录失败");
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylogin);
        if (IsNetWorkUtils.isConnectNET(this)) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            httpClient = new DefaultHttpClient();
            //初始化 登录网页的获取
            initLogin();
            //绑定id
            initFindId();
            //button的点击事件
            onCilcButton();
            //保存密码的设置
            isKeepWords();
        }else{
            Toast.makeText(this,"请检查网络是否可用",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 是否保存密码 如果是 则把相关的密码和账号 布置到相应的 编辑框中
     */
    private void isKeepWords(){
        boolean iskeepword = sharedPreferences.getBoolean("iskeeppassword",false);
        if (iskeepword){
            //将账号密码 布置到编辑框中
            String username = sharedPreferences.getString("username","");
            String password = sharedPreferences.getString("password","");
            editText_username.setText(username);
            editText_password.setText(password);
            checkBox_keeppassword.setChecked(true);
        }
    }

    /**
     * 对每个控件的id绑定
     */
    private void initFindId(){
        button_login = (Button)findViewById(R.id.button_login);
        imageView_identifying = (ImageView)findViewById(R.id.imageview_identifying);
        editText_identifying = (EditText)findViewById(R.id.edittext_identifying);
        editText_password = (EditText)findViewById(R.id.edittext_password);
        editText_username = (EditText)findViewById(R.id.edittext_username);
        checkBox_keeppassword = (CheckBox)findViewById(R.id.checkbox_keeppassword);
    }

    /**
     * button的点击事件
     */
    private void onCilcButton(){
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText_username.getText().toString();
                if (username.equals("")){
                    Toast.makeText(getApplicationContext(),"用户名不能为空",Toast.LENGTH_SHORT).show();;
                }else {
                    if (usernameYanZheng(username)){
                        password = editText_password.getText().toString();
                        indentifying = editText_identifying.getText().toString();
                        if (password.equals("")){
                            Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();;
                        }else if(indentifying.equals("")){
                            Toast.makeText(getApplicationContext(),"验证码不能为空",Toast.LENGTH_SHORT).show();
                        }else{
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        login(username,password,indentifying);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(),"请输入8位学号",Toast.LENGTH_SHORT).show();;
                    }
                }

            }
        });

        //点击图片 更换验证码
        imageView_identifying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bitmap_indenfying  = getIdentifyingImage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    /**
     * 用于用户名的验证
     * @param user
     * @return
     */
    private boolean usernameYanZheng(String user){
        if (user.length() == 8){
            return  true;
        }
        return false;
    }

    /**
     * 获取验证码的方法
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    private Bitmap getIdentifyingImage() throws ClientProtocolException,IOException{
        Date date = new Date();
        String identifyingImage = "http://jwkq.xupt.edu.cn:8080/Common/GetValidateCode?time="+date.getTime();
       // HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(identifyingImage);
        HttpResponse httpResponse = httpClient.execute(httpGet);
//        Log.e(tag,httpResponse.toString());
        if (httpResponse.getStatusLine().getStatusCode() == 200){
            Header[] header = httpResponse.getHeaders("Set-Cookie");
            if (header.length != 0) {
                String ss = header[0].getValue().toString();
                sessionId = ss.substring(0, ss.length() - 18);
                Log.e(tag, "sessionId   " + sessionId);
            }
//            for (int i = 0 ; i <header.length;i++)
//                Log.e(tag,header[i].toString());
            byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
            Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
            Log.e(tag,identifyingImage);
            return bitmap;
        }
        return null;
    }

    /**
     * 进入登录网页
     */
    private void initLogin(){
        //打开该网页
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String loginUri = "http://jwkq.xupt.edu.cn:8080/Account/Login";
                    //获取智慧教室的登录网页
                    HttpGet httpGetLogin = new HttpGet(loginUri);
                    httpClient.execute(httpGetLogin);
                    //获取验证码
                    bitmap_indenfying  = getIdentifyingImage();
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 登录验证
     * @param username
     * @param password
     * @param indentifying
     */
    private void login(String username,String password,String indentifying) throws ClientProtocolException
            , IOException, JSONException {
        //用json解析，参数华为 utf-8格式
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("UserName",username);
        jsonObject.put("UserPassword",password);
        jsonObject.put("ValiCode",indentifying);
        httpPost = new HttpPost("http://jwkq.xupt.edu.cn:8080/Account/Login");
        httpPost.setEntity(new StringEntity(jsonObject.toString()));
        //大宋请求 等待回应
//        httpPost.setHeader("Set-Cookie",sessionId);
        HttpResponse httpResponse = httpClient.execute(httpPost);
        Log.e(tag,""+httpResponse.getStatusLine().getStatusCode());
        if (httpResponse.getStatusLine().getStatusCode() == 200){
            HttpEntity httpEntity = httpResponse.getEntity();
            Header[] header = httpResponse.getHeaders("Set-Cookie");
//            Header[] header1 = httpResponse.getHeaders("Referer");
//            Log.e(tag,"Referer----"+header1[0].toString());
            if (header.length !=0) {
                String ss = header[0].getValue().toString();
                set_Cookie = ss.substring(0, ss.length() - 18);
                Log.e(tag, "登录页面的cookie   " + set_Cookie);
            }
//            for (int i = 0 ; i <header.length;i++)
//                Log.e(tag,header[i].toString());
            //将返回的httpresponse用json解析
            String stringFanhui = EntityUtils.toString(httpEntity,"utf-8").toString();
            JSONObject jsonObject1 = new JSONObject(stringFanhui);
            Log.e(tag,"解析返回xml"+stringFanhui);
            Log.e(tag,""+jsonObject1.getBoolean("IsSucceed"));
            if (jsonObject1.getBoolean("IsSucceed")){
                jsonObjectMain = new JSONObject(jsonObject1.getString("Obj"));
                //记录账号和密码
                editor = sharedPreferences.edit();
                if (checkBox_keeppassword.isChecked()){
                    editor.putBoolean("iskeeppassword",true);
                    editor.putString("username",username);
                    editor.putString("password",password);
                }else{
                    editor.clear();
                }
                editor.commit();
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }else if (jsonObject1.getInt("Obj") == 1003){
                //验证码错误
                Log.e(tag,jsonObject1.getString("Msg"));
                Message message = new Message();
                message.what = 5;
                handler.sendMessage(message);
            }else if (jsonObject1.getInt("Obj")== 1000){
                //用户名或密码错误
                Log.e(tag,jsonObject1.getString("Msg"));
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);
            }

        }else{  //没有进入200 请链接网络，
            //请检查网络连接
            Message message = new Message();
            message.what = 4;
            handler.sendMessage(message);
        }
    }

}
