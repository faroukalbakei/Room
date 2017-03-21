package com.dev.farouk.roomx.ui.chat;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.farouk.roomx.R;
import com.dev.farouk.roomx.model.UserinfoLogin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by farouk on 2/4/17.
 */

public class ChatListAdabter extends RecyclerView.Adapter<ChatListAdabter.MyViewHolder> {

    private List<UserinfoLogin> cuserList = new ArrayList<>();
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
    public ChatListAdabter(List<UserinfoLogin> cuserList) {
        this.cuserList = cuserList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatuser, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ChatListAdabter.MyViewHolder holder, int position) {


        UserinfoLogin userinfoLogin = cuserList.get(position);

        holder.Name.setText(userinfoLogin.getName());
        holder.City.setText(userinfoLogin.getCity());
        holder.pic.setImageResource(userinfoLogin.getPhoto());

    }




    @Override
    public int getItemCount() {
        return cuserList.size();
    }





}
