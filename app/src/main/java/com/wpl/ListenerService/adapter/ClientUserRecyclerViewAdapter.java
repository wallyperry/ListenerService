package com.wpl.ListenerService.adapter;

import android.content.Context;

import com.wpl.ListenerService.R;
import com.wpl.ListenerService.adapter.recyclerViewBaseAdapter.BaseAdapter;
import com.wpl.ListenerService.adapter.recyclerViewBaseAdapter.ViewHolder;
import com.wpl.ListenerService.bean.ClientUser;

import java.util.List;

/**
 * adapter
 * Created by Administrator on 2017/2/22.
 */
public class ClientUserRecyclerViewAdapter extends BaseAdapter<ClientUser> {
    public ClientUserRecyclerViewAdapter(Context context, List<ClientUser> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }

    @Override
    protected void convert(ViewHolder holder, ClientUser data, int position) {
        holder.setText(R.id.itemClientUser_info, data.getPhoneInfo());
        holder.setText(R.id.itemClientUser_phone, data.getPhone());
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_client_user_recycler_view;
    }
}
