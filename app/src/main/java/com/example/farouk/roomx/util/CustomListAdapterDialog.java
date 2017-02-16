package com.example.farouk.roomx.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.ItemRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dev Abir on 2/16/2017.
 */

public class CustomListAdapterDialog extends BaseAdapter {

    private List<ItemRoom> listData;

    private LayoutInflater layoutInflater;

    public CustomListAdapterDialog(Context context, List<ItemRoom> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_dialog, null);
            holder = new ViewHolder();
            holder.roomItem = (TextView) convertView.findViewById(R.id.item);
            holder.availble = (ImageView) convertView.findViewById(R.id.availble);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.roomItem.setText(listData.get(position).getRoomItem().toString());
        if ((listData.get(position).getIsAvailable()) == "true") {
            holder.availble.setVisibility(View.VISIBLE);

        } else if ((listData.get(position).getIsAvailable()) == "false") {
            holder.availble.setVisibility(View.INVISIBLE);

        }

        return convertView;
    }

    static class ViewHolder {
        TextView roomItem;
        ImageView availble;
    }

}