package com.example.farouk.roomx;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farouk.roomx.model.Bookings;
import com.example.farouk.roomx.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farouk on 2/4/17.
 */

public class ChatListAdabter extends RecyclerView.Adapter<ChatListAdabter.MyViewHolder> {

    private List<User> CuserList = new ArrayList<>();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name;
        TextView City;
        ImageView pic;


        public MyViewHolder(View view) {
            super(view);
            Name = (TextView) view.findViewById(R.id.tv_Chats_nameChate);
            City = (TextView) view.findViewById(R.id.tv_Chats_nameCity);
            pic = (ImageView) view.findViewById(R.id.imv_Chats_picUser);
        }
    }
    public ChatListAdabter(List<User> CuserList) {
        this.CuserList = CuserList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatuser, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatListAdabter.MyViewHolder holder, int position) {


        User user = CuserList.get(position);

        holder.Name.setText(user.getName());
        holder.City.setText(user.getCity());
        holder.pic.setImageResource(user.getPhoto());

    }




    @Override
    public int getItemCount() {
        return CuserList.size();
    }





}
