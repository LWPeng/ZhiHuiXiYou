package com.xyliwp.zhihuixiyou.ui;

import android.os.Handler;
import android.view.View;
import android.widget.AbsListView;

import com.xyliwp.zhihuixiyou.utils.QianDao;

import java.util.List;

/**
 * Created by lwp940118 on 2016/10/31.
 */
public class QianDaoListViewScrollListner implements AbsListView.OnScrollListener{

    private int totallItemCount;
    private int lastItem;
    private boolean isLoading;
    private View footView;
    private List<QianDao> qianDaos;
    private OnLoadDataListener onLoadDataListener;

    public QianDaoListViewScrollListner(View footer, List<QianDao> qianDaos){
        this.footView = footer;
        this.qianDaos = qianDaos;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (!isLoading && lastItem == totallItemCount && scrollState == SCROLL_STATE_IDLE){
            footView.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (onLoadDataListener!=null){
                        loadMoreData();
                        onLoadDataListener.onLoadData(qianDaos);
                        loadComplete();
                    }
                }
            },2000);
        }
    }

    /**
     * 加载完成
     */
    private void loadComplete(){
        isLoading = false;
        footView.setVisibility(View.GONE);
    }

    private void loadMoreData(){
        isLoading = true;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItem = firstVisibleItem+visibleItemCount;
        this.totallItemCount = totalItemCount;
    }

    public interface OnLoadDataListener{
        void onLoadData(List<QianDao> qianDaos);
    }

    /**
     * 设置实例的回调
     * @param onLoadDataListener
     */
    public void setOnLoadDataListener(OnLoadDataListener onLoadDataListener){
        this.onLoadDataListener = onLoadDataListener;
    }

}
