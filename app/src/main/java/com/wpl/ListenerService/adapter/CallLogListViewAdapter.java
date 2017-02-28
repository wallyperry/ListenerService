package com.wpl.ListenerService.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.wpl.ListenerService.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/28.
 */
public class CallLogListViewAdapter extends BaseAdapter {

    private ArrayList list;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public CallLogListViewAdapter(ArrayList list, int resource, Context context) {
        this.list = list;
        this.resource = resource;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }
        bindView(position, view);
        return view;
    }

    private void bindView(int position, View view) {
        Map<String, Object> map = (Map<String, Object>) list.get(position);

        TextView name = (TextView) view.findViewById(R.id.callLog_name);
        TextView phone = (TextView) view.findViewById(R.id.callLog_number);
        ImageView type = (ImageView) view.findViewById(R.id.callLog_type);
        TextView date = (TextView) view.findViewById(R.id.callLog_date);
        TextView duration = (TextView) view.findViewById(R.id.callLog_duration);

        name.setText(map.get("name").toString());
        phone.setText(map.get("number").toString());
        date.setText(map.get("date").toString());


        switch (map.get("duration").toString()) {
            case "0分钟":
                duration.setText("<1分钟");
                break;
            default:
                duration.setText(map.get("duration").toString());
                break;
        }
        switch (map.get("type").toString()) {
            case "未接":
                type.setImageResource(R.mipmap.ic_call_missed_grey600_18dp);
                break;
            case "拨出":
                type.setImageResource(R.mipmap.ic_call_made_grey600_18dp);
                break;
            case "接听":
                type.setImageResource(R.mipmap.ic_call_received_grey600_18dp);
                break;
            default:
                break;
        }
    }
}
