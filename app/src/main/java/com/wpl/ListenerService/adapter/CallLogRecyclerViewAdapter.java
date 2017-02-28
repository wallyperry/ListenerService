package com.wpl.ListenerService.adapter;

import android.content.Context;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.recyclerViewBaseAdapter.BaseAdapter;
import com.wpl.ListenerService.adapter.recyclerViewBaseAdapter.ViewHolder;
import com.wpl.ListenerService.bean.FeedbackData;

import java.util.List;

/**
 * Created by 培龙 on 2017/2/27.
 */
public class CallLogRecyclerViewAdapter extends BaseAdapter<FeedbackData> {


    public CallLogRecyclerViewAdapter(Context context, List<FeedbackData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, FeedbackData data, int position) {

    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_call_log_recycler_view;
    }
}
