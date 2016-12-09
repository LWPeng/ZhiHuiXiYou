package com.xyliwp.zhihuixiyou.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.xyliwp.zhihuixiyou.R;
import static com.xyliwp.zhihuixiyou.utils.Content.*;

public class KeBiaoStackAdapter extends StackAdapter<Integer> {

    public KeBiaoStackAdapter(Context context) {
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
            case R.layout.kebiao_list_card_item_larger_header:
                view = getLayoutInflater().inflate(R.layout.kebiao_list_card_item_larger_header, parent, false);
                return new ColorItemLargeHeaderViewHolder(view);
            case R.layout.kebiao_list_card_item_with_no_header:
                view = getLayoutInflater().inflate(R.layout.kebiao_list_card_item_with_no_header, parent, false);
                return new ColorItemWithNoHeaderViewHolder(view);
            default:
                view = getLayoutInflater().inflate(R.layout.kebiao_list_card_item, parent, false);
                return new ColorItemViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        return R.layout.kebiao_list_card_item;
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        TextView mtextviewdata;
        TextView textView_kebiao12;
        TextView textView_kebiao34;
        TextView textView_kebiao56;
        TextView textView_kebiao78;
        TextView textView_kebiao910;


        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.kebiao_listcard_week);
            mtextviewdata = (TextView)view.findViewById(R.id.kebiao_listcard_data);
            textView_kebiao12 = (TextView)view.findViewById(R.id.textview_kebiao_12);
            textView_kebiao34 = (TextView)view.findViewById(R.id.textview_kebiao_23);
            textView_kebiao56 = (TextView)view.findViewById(R.id.textview_kebiao_56);
            textView_kebiao78 = (TextView)view.findViewById(R.id.textview_kebiao_78);
            textView_kebiao910 = (TextView)view.findViewById(R.id.textview_kebiao_910);

        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);
            String s = "";
            if (position == 0){
                s = "一";
                for (int i = 0; i < kebiaoyi.size();i++){
                    KeBiao keBiao = kebiaoyi.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 1){
                s = "二";
                for (int i = 0; i < kebiaoer.size();i++){
                    KeBiao keBiao = kebiaoer.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 2){
                s = "三";
                for (int i = 0; i < kebiasan.size();i++){
                    KeBiao keBiao = kebiasan.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 3){
                s = "四";
                for (int i = 0; i < kebiaosi.size();i++){
                    KeBiao keBiao = kebiaosi.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 4){
                s = "五";
                for (int i = 0; i < kebiaowu.size();i++){
                    KeBiao keBiao = kebiaowu.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 5){
                s = "六";
                for (int i = 0; i < kebiaoliu.size();i++){
                    KeBiao keBiao = kebiaoliu.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 6){
                s = "天";
                for (int i = 0; i < kebiaoqi.size();i++){
                    KeBiao keBiao = kebiaoqi.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }
            mtextviewdata.setText(hashMap.get(((position+1)%7)));
            mTextTitle.setText("星期"+s);
            if (((position+1)%7) == tagData){
                mtextviewdata.setTextColor(Color.parseColor("#FF25120e"));
                mTextTitle.setTextColor(Color.parseColor("#FF25120e"));
            }
        }

    }

    static class ColorItemWithNoHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        TextView mTextTitle;
        TextView mtextviewdata;
        TextView textView_kebiao12;
        TextView textView_kebiao34;
        TextView textView_kebiao56;
        TextView textView_kebiao78;
        TextView textView_kebiao910;

        public ColorItemWithNoHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mTextTitle = (TextView) view.findViewById(R.id.kebiao_listcard_week);
            mtextviewdata = (TextView)view.findViewById(R.id.kebiao_listcard_data);
            textView_kebiao12 = (TextView)view.findViewById(R.id.textview_kebiao_12);
            textView_kebiao34 = (TextView)view.findViewById(R.id.textview_kebiao_23);
            textView_kebiao56 = (TextView)view.findViewById(R.id.textview_kebiao_56);
            textView_kebiao78 = (TextView)view.findViewById(R.id.textview_kebiao_78);
            textView_kebiao910 = (TextView)view.findViewById(R.id.textview_kebiao_910);
        }

        @Override
        public void onItemExpand(boolean b) {
        }

        public void onBind(Integer data, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), data), PorterDuff.Mode.SRC_IN);

            String s = "";
            if (position == 0) {
                s = "一";
                if (position == 0) {
                    s = "一";
                    for (int i = 0; i < kebiaoyi.size(); i++) {
                        KeBiao keBiao = kebiaoyi.get(i);
                        if (keBiao.getJieci().equals("1-2")) {
                            textView_kebiao12.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("3-4")) {
                            textView_kebiao34.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("5-6")) {
                            textView_kebiao56.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("7-8")) {
                            textView_kebiao78.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("9-10")) {
                            textView_kebiao910.setText(keBiao.toString());
                        }
                    }
                } else if (position == 1) {
                    s = "二";
                    for (int i = 0; i < kebiaoer.size(); i++) {
                        KeBiao keBiao = kebiaoer.get(i);
                        if (keBiao.getJieci().equals("1-2")) {
                            textView_kebiao12.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("3-4")) {
                            textView_kebiao34.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("5-6")) {
                            textView_kebiao56.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("7-8")) {
                            textView_kebiao78.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("9-10")) {
                            textView_kebiao910.setText(keBiao.toString());
                        }
                    }
                } else if (position == 2) {
                    s = "三";
                    for (int i = 0; i < kebiasan.size(); i++) {
                        KeBiao keBiao = kebiasan.get(i);
                        if (keBiao.getJieci().equals("1-2")) {
                            textView_kebiao12.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("3-4")) {
                            textView_kebiao34.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("5-6")) {
                            textView_kebiao56.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("7-8")) {
                            textView_kebiao78.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("9-10")) {
                            textView_kebiao910.setText(keBiao.toString());
                        }
                    }
                } else if (position == 3) {
                    s = "四";
                    for (int i = 0; i < kebiaosi.size(); i++) {
                        KeBiao keBiao = kebiaosi.get(i);
                        if (keBiao.getJieci().equals("1-2")) {
                            textView_kebiao12.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("3-4")) {
                            textView_kebiao34.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("5-6")) {
                            textView_kebiao56.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("7-8")) {
                            textView_kebiao78.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("9-10")) {
                            textView_kebiao910.setText(keBiao.toString());
                        }
                    }
                } else if (position == 4) {
                    s = "五";
                    for (int i = 0; i < kebiaowu.size(); i++) {
                        KeBiao keBiao = kebiaowu.get(i);
                        if (keBiao.getJieci().equals("1-2")) {
                            textView_kebiao12.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("3-4")) {
                            textView_kebiao34.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("5-6")) {
                            textView_kebiao56.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("7-8")) {
                            textView_kebiao78.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("9-10")) {
                            textView_kebiao910.setText(keBiao.toString());
                        }
                    }
                } else if (position == 5) {
                    s = "六";
                    for (int i = 0; i < kebiaoliu.size(); i++) {
                        KeBiao keBiao = kebiaoliu.get(i);
                        if (keBiao.getJieci().equals("1-2")) {
                            textView_kebiao12.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("3-4")) {
                            textView_kebiao34.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("5-6")) {
                            textView_kebiao56.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("7-8")) {
                            textView_kebiao78.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("9-10")) {
                            textView_kebiao910.setText(keBiao.toString());
                        }
                    }
                } else if (position == 6) {
                    s = "天";
                    for (int i = 0; i < kebiaoqi.size(); i++) {
                        KeBiao keBiao = kebiaoqi.get(i);
                        if (keBiao.getJieci().equals("1-2")) {
                            textView_kebiao12.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("3-4")) {
                            textView_kebiao34.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("5-6")) {
                            textView_kebiao56.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("7-8")) {
                            textView_kebiao78.setText(keBiao.toString());
                        } else if (keBiao.getJieci().equals("9-10")) {
                            textView_kebiao910.setText(keBiao.toString());
                        }
                    }
                }
            }
            mtextviewdata.setText(hashMap.get(position));
            mTextTitle.setText("星期"+s);
            if (((position+1)%7) == tagData){
                mtextviewdata.setTextColor(Color.parseColor("#FF25120e"));
                mTextTitle.setTextColor(Color.parseColor("#FF25120e"));
            }
        }

    }

    static class ColorItemLargeHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        TextView mtextviewdata;
        TextView textView_kebiao12;
        TextView textView_kebiao34;
        TextView textView_kebiao56;
        TextView textView_kebiao78;
        TextView textView_kebiao910;

        public ColorItemLargeHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.kebiao_listcard_week);
            mtextviewdata = (TextView)view.findViewById(R.id.kebiao_listcard_data);
            textView_kebiao12 = (TextView)view.findViewById(R.id.textview_kebiao_12);
            textView_kebiao34 = (TextView)view.findViewById(R.id.textview_kebiao_23);
            textView_kebiao56 = (TextView)view.findViewById(R.id.textview_kebiao_56);
            textView_kebiao78 = (TextView)view.findViewById(R.id.textview_kebiao_78);
            textView_kebiao910 = (TextView)view.findViewById(R.id.textview_kebiao_910);
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

            String s = "";
            if (position == 0){
                s = "一";
                for (int i = 0; i < kebiaoyi.size();i++){
                    KeBiao keBiao = kebiaoyi.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 1){
                s = "二";
                for (int i = 0; i < kebiaoer.size();i++){
                    KeBiao keBiao = kebiaoer.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 2){
                s = "三";
                for (int i = 0; i < kebiasan.size();i++){
                    KeBiao keBiao = kebiasan.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 3){
                s = "四";
                for (int i = 0; i < kebiaosi.size();i++){
                    KeBiao keBiao = kebiaosi.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 4){
                s = "五";
                for (int i = 0; i < kebiaowu.size();i++){
                    KeBiao keBiao = kebiaowu.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 5){
                s = "六";
                for (int i = 0; i < kebiaoliu.size();i++){
                    KeBiao keBiao = kebiaoliu.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }else if (position == 6){
                s = "天";
                for (int i = 0; i < kebiaoqi.size();i++){
                    KeBiao keBiao = kebiaoqi.get(i);
                    if (keBiao.getJieci().equals("1-2")){
                        textView_kebiao12.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("3-4")){
                        textView_kebiao34.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("5-6")){
                        textView_kebiao56.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("7-8")){
                        textView_kebiao78.setText(keBiao.toString());
                    }else if(keBiao.getJieci().equals("9-10")){
                        textView_kebiao910.setText(keBiao.toString());
                    }
                }
            }
            mtextviewdata.setText(hashMap.get(position));
            mTextTitle.setText("星期"+s);
            if (((position+1)%7) == tagData){
                mtextviewdata.setTextColor(Color.parseColor("#FF25120e"));
                mTextTitle.setTextColor(Color.parseColor("#FF25120e"));
            }
            itemView.findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CardStackView)itemView.getParent()).performItemClick(ColorItemLargeHeaderViewHolder.this);
                }
            });
        }

    }

}
