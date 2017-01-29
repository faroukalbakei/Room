package com.example.farouk.roomx;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by farouk on 1/29/17.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {




    private List<Room> roomList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, tag, date,city,detale;
        public ImageView userpic,roompic;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            date = (TextView) view.findViewById(R.id.tv_date);
            tag = (TextView) view.findViewById(R.id.tv_tag);
            city = (TextView) view.findViewById(R.id.tv_cantry);
            detale = (TextView) view.findViewById(R.id.tv_inf);

            userpic = (ImageView) view.findViewById(R.id.imv_userphoto);
            roompic = (ImageView) view.findViewById(R.id.img_picRoom);




        }
    }


    public DetailAdapter(List<Room> roomList) {
        this.roomList = roomList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlist, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Room rooom = roomList.get(position);

        holder.userpic.setImageResource((rooom.getUserPic()));
        holder.name.setText(rooom.getUserName());
        holder.tag.setText(rooom.getTag());
        holder.date.setText(rooom.getDateup()+"");
        holder.city.setText(rooom.getCity());

        holder.roompic.setImageResource((rooom.getRoomPic()));
        holder.detale.setText(rooom.getDetail());

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }


}
