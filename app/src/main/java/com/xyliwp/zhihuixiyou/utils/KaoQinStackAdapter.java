package com.xyliwp.zhihuixiyou.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.display.DisplayManager;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.xyliwp.zhihuixiyou.R;

import java.util.ArrayList;

import static com.xyliwp.zhihuixiyou.utils.Content.*;

public class KaoQinStackAdapter extends StackAdapter<Integer> {

    public KaoQinStackAdapter(Context context) {
        super(context);
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemLargeHeaderViewHolder) {
            ColorItemLargeHeaderViewHolder h = (ColorItemLargeHeaderViewHolder) holder;
            h.onBind(data, position);
        }
        if (holder instanceof ColorItemWithNoHeaderViewHolder) {
            ColorItemWithNoHeaderViewHolder h = (ColorItemWithNoHeaderViewHolder) holder;
            h.onBind(data, position);
        }
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            h.onBind(data, position);
        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case R.layout.kaoqin_list_card_item_larger_head:
                view = getLayoutInflater().inflate(R.layout.kaoqin_list_card_item_larger_head, parent, false);
                return new ColorItemLargeHeaderViewHolder(view);
            case R.layout.kaoqin_list_item_card_with_no_head:
                view = getLayoutInflater().inflate(R.layout.kaoqin_list_item_card_with_no_head, parent, false);
                return new ColorItemWithNoHeaderViewHolder(view);
            default:
                view = getLayoutInflater().inflate(R.layout.kaoqin_list_card_item, parent, false);
                return new ColorItemViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.kaoqin_list_card_item;
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mtextviewdata;
        TextView textView_zongji;
        TextView textView_yingdao;
        TextView textView_zhegnchang;
        TextView textView_chidao;
        TextView textView_queqin;
        PieChart pieChart;
        private KeCheng keCheng;

        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mtextviewdata = (TextView)view.findViewById(R.id.kebiao_listcard_data);
            textView_chidao = (TextView)view.findViewById(R.id.textview_chidao);
            textView_queqin = (TextView)view.findViewById(R.id.textview_quqin);
            textView_yingdao = (TextView)view.findViewById(R.id.textview_yingdao);
            textView_zhegnchang = (TextView)view.findViewById(R.id.textview_zhengchang);
            textView_zongji = (TextView)view.findViewById(R.id.textview_zongji);
            pieChart = (PieChart)view.findViewById(R.id.piechart);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            keCheng = keChengs.get(position);
            mtextviewdata.setText(keCheng.getKechengming());
            textView_yingdao.setText("应到:\n"+keCheng.getYingdao());
            textView_zongji.setText("总计:\n"+keCheng.getZongji());
            textView_zhegnchang.setText("正常:\n"+keCheng.getZhegnchang());
            textView_queqin.setText("缺勤:\n"+keCheng.getQueqin());
            textView_chidao.setText("迟到:\n"+keCheng.getChidao());
            PieData pieData = getPieData(3,100);
            shoeChart(pieChart,pieData);
        }

        private void shoeChart(PieChart pieChart, PieData pieData){
            pieChart.setHoleColorTransparent(true);
            //实心圆
            pieChart.setHoleRadius(0);
            pieChart.setDescription(keCheng.getKechengming());
            //可以在扇形统计图的中间加入汉字
            pieChart.setDrawCenterText(true);
            pieChart.setDrawHoleEnabled(true);
            //初始旋转角度
            pieChart.setRotationAngle(90);
            //可以手动旋转
            pieChart.setRotationEnabled(true);
            //显示成百分比
            pieChart.setUsePercentValues(true);
            pieChart.setData(pieData);
            //设置比例
            Legend legend = pieChart.getLegend();
            legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
            legend.setXEntrySpace(7f);
            legend.setYEntrySpace(5f);
            //设置动画
            pieChart.animateXY(1000,1000);
        }

        private PieData getPieData(int count,float range){
            //每块并上的内容
            ArrayList<String> xValues = new ArrayList<String>();
            xValues.add("正常");
            xValues.add("迟到");
            xValues.add("缺勤");
            //每块的实际数据
            ArrayList<Entry> yValues = new ArrayList<Entry>();
            float q1 = keCheng.getZhegnchang();
            float q2 = keCheng.getChidao();
            float q3 = keCheng.getQueqin();
            yValues.add(new Entry((q1/(q1+q2+q3))*100,0));
            yValues.add(new Entry((q2/(q1+q2+q3))*100,1));
            yValues.add(new Entry((q3/(q1+q2+q3))*100,2));
            PieDataSet pieDataSet = new PieDataSet(yValues,"");
            pieDataSet.setSliceSpace(0f);
            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.parseColor("#ffD81B60"));
            colors.add(Color.parseColor("#ff4A148C"));
            colors.add(Color.parseColor("#ff29B6F6"));
            pieDataSet.setColors(colors);
            PieData pieData = new PieData(xValues,pieDataSet);
            return  pieData;
        }

    }

    static class ColorItemWithNoHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        TextView mtextviewdata;
        TextView textView_zongji;
        TextView textView_yingdao;
        TextView textView_zhegnchang;
        TextView textView_chidao;
        TextView textView_queqin;

        public ColorItemWithNoHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mtextviewdata = (TextView)view.findViewById(R.id.kebiao_listcard_data);
            textView_chidao = (TextView)view.findViewById(R.id.textview_chidao);
            textView_queqin = (TextView)view.findViewById(R.id.textview_quqin);
            textView_yingdao = (TextView)view.findViewById(R.id.textview_yingdao);
            textView_zhegnchang = (TextView)view.findViewById(R.id.textview_zhengchang);
            textView_zongji = (TextView)view.findViewById(R.id.textview_zongji);
        }

        @Override
        public void onItemExpand(boolean b) {
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            KeCheng keCheng = keChengs.get(position);
            mtextviewdata.setText(keCheng.getKechengming());
            textView_yingdao.setText("应到:\n"+keCheng.getYingdao());
            textView_zongji.setText("总计:\n"+keCheng.getZongji());
            textView_zhegnchang.setText("正常:\n"+keCheng.getZhegnchang());
            textView_queqin.setText("缺勤:\n"+keCheng.getQueqin());
            textView_chidao.setText("迟到:\n"+keCheng.getChidao());
        }

    }

    static class ColorItemLargeHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mtextviewdata;
        TextView textView_zongji;
        TextView textView_yingdao;
        TextView textView_zhegnchang;
        TextView textView_chidao;
        TextView textView_queqin;

        public ColorItemLargeHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mtextviewdata = (TextView)view.findViewById(R.id.kebiao_listcard_data);
            textView_chidao = (TextView)view.findViewById(R.id.textview_chidao);
            textView_queqin = (TextView)view.findViewById(R.id.textview_quqin);
            textView_yingdao = (TextView)view.findViewById(R.id.textview_yingdao);
            textView_zhegnchang = (TextView)view.findViewById(R.id.textview_zhengchang);
            textView_zongji = (TextView)view.findViewById(R.id.textview_zongji);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        @Override
        protected void onAnimationStateChange(int state, boolean willBeSelect) {
            super.onAnimationStateChange(state, willBeSelect);
            if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
                onItemExpand(true);
            }
            if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
                onItemExpand(false);
            }
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);

            KeCheng keCheng = keChengs.get(position);
            mtextviewdata.setText(keCheng.getKechengming());
            textView_yingdao.setText("应到:\n"+keCheng.getYingdao());
            textView_zongji.setText("总计:\n"+keCheng.getZongji());
            textView_zhegnchang.setText("正常:\n"+keCheng.getZhegnchang());
            textView_queqin.setText("缺勤:\n"+keCheng.getQueqin());
            textView_chidao.setText("迟到:\n"+keCheng.getChidao());

            itemView.findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CardStackView)itemView.getParent()).performItemClick(ColorItemLargeHeaderViewHolder.this);
                }
            });
        }

    }

}
