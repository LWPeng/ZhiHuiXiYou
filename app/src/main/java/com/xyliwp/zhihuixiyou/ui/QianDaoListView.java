package com.xyliwp.zhihuixiyou.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.xyliwp.zhihuixiyou.R;

/**
 * 自定义listview 上拉加载 不要下拉刷新。
 * Created by lwp940118 on 2016/11/1.
 */
public class QianDaoListView extends ListView implements AbsListView.OnScrollListener{

    public static final int LOAD = 1;
    private LayoutInflater inflater;
    private View footer;
    private TextView textView;


    private int scrollState;
    // 只有在listview第一个item显示的时候（listview滑到了顶部）才进行下拉刷新， 否则此时的下拉只是滑动listview
    private boolean isRecorded;
    private boolean isLoading;// 判断是否正在加载
    private boolean loadEnable = true;// 开启或者关闭加载更多功能
    private boolean isLoadFull;
    private int pageSize = 10;
    private OnLoadListener onLoadListener;

    public QianDaoListView(Context context) {
        super(context);
        initView(context);
    }

    public QianDaoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public QianDaoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 加载更多监听
     * @param onLoadListener
     */
    public void setOnLoadListener(OnLoadListener onLoadListener){
        this.onLoadListener = onLoadListener;
        this.loadEnable = true;
    }

    public boolean idLoadEnable(){
        return loadEnable;
    }

    // 这里的开启或者关闭加载更多，并不支持动态调整
    public void setLoadEnable(boolean loadEnable) {
        this.loadEnable = loadEnable;
        this.removeFooterView(footer);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 初始化组件
     * @param context
     */
    private void initView(Context context){
        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.up_la_jiazai, null);
        textView = (TextView)footer.findViewById(R.id.textview_up_jiazai);
        textView.setText("加载更多");
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    public void onLoad(){
        if (onLoadListener != null){
            onLoadListener.onLoad();
        }
    }

    public void onLoadComplete(){

        footer.setVisibility(View.GONE);
        isLoading = false;
    }

    public void setJinDu(String s){
        textView.setText(s);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        this.scrollState = scrollState;
        ifNeedLoad(view,scrollState);
    }

    private void ifNeedLoad(AbsListView view, int scrollState){
        if (!loadEnable){
            return;
        }
        footer.setVisibility(View.VISIBLE);
        try {
            if (scrollState == OnScrollListener.SCROLL_STATE_IDLE&&!isLoading
                    && view.getLastVisiblePosition() == view.getPositionForView(footer)
                    && !isLoadFull){
                onLoad();
                isLoading = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 定义加载更多接口
     */
    public interface OnLoadListener {
        public void onLoad();
    }

}
