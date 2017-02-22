package com.wpl.ListenerService.adapter.recyclerViewBaseAdapter;

/**
 * Created by Wpl on 2017/2/7.
 * Contact number 18244267955 and Email pl.w@outlook.com
 **/
public interface OnItemLongClickListener<T> {
    void onItemLongClick(ViewHolder viewHolder, T data, int position);
}
