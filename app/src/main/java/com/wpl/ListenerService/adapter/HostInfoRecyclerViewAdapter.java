package com.wpl.ListenerService.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.recyclerViewBaseAdapter.BaseAdapter;
import com.wpl.ListenerService.adapter.recyclerViewBaseAdapter.ViewHolder;
import com.wpl.ListenerService.bean.FeedbackData;

import java.util.List;

/**
 * adapter
 * Created by Administrator on 2017/2/22.
 */
public class HostInfoRecyclerViewAdapter extends BaseAdapter<FeedbackData> {

    public HostInfoRecyclerViewAdapter(Context context, List<FeedbackData> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, FeedbackData data, int position) {
        Log.e("Adapter", "第" + position + "条数据的网络：" + data.getNetType());
        try {
            holder.setText(R.id.itemFeedback_poi, "" + data.getAoi());
            holder.setText(R.id.itemFeedback_date, "" + data.getCreatedAt());
            holder.setText(R.id.itemFeedback_city, "" + data.getCity());
            holder.setText(R.id.itemFeedback_callText, "" + String.valueOf(data.getCallLog().size()));
            holder.setText(R.id.itemFeedback_smsText, "" + String.valueOf(data.getSmsLog().size()));

            ImageView netType = (ImageView) holder.getConvertView().findViewById(R.id.itemFeedback_netType);
            switch (data.getNetType()) {
                case "WIFI":
                    netType.setImageResource(R.mipmap.ic_signal_wifi_statusbar_4_bar_grey600_24dp);
                    break;
                case "CMNET":
                    netType.setImageResource(R.mipmap.ic_signal_cellular_4_bar_grey600_24dp);
                    break;
                default:
                    break;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_client_feedback_recycler_view;
    }
}
