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
public class SmsLogListViewAdapter extends BaseAdapter {

    private ArrayList list;
    private int resource;
    private Context context;
    private LayoutInflater inflater;

    public SmsLogListViewAdapter(ArrayList list, int resource, Context context) {
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

        TextView name = (TextView) view.findViewById(R.id.smsLog_name);
        TextView date = (TextView) view.findViewById(R.id.smsLog_date);
        TextView type = (TextView) view.findViewById(R.id.smsLog_type);
        TextView content = (TextView) view.findViewById(R.id.smsLog_content);

        name.setText(map.get("name").toString());
        date.setText(map.get("date").toString());
        content.setText(map.get("content").toString());
        type.setText(map.get("type").toString());
    }
}
