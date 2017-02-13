package com.example.farouk.roomx.ui.explore;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.PlaceObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
//
//import static android.R.attr.key;
//import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by farouk on 1/29/17.
 */

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.MyViewHolder> {


    private List<PlaceObject> roomList;

    Context context;
    public ImageButton btlike;

    int lik = -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, tag, date, city, detale;
        public ImageView roompic;
        CircleImageView userpic;
        public ImageButton btlike;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            date = (TextView) view.findViewById(R.id.tv_date);
            tag = (TextView) view.findViewById(R.id.tv_tag);
            city = (TextView) view.findViewById(R.id.tv_cantry);
            detale = (TextView) view.findViewById(R.id.tv_inf);

            userpic = (CircleImageView) view.findViewById(R.id.imv_userphoto);
            roompic = (ImageView) view.findViewById(R.id.img_picRoom);
            btlike = (ImageButton) view.findViewById(R.id.imge_love);


        }
    }


    public ExploreAdapter(List<PlaceObject> roomList, Context context) {
        this.roomList = roomList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customlist, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        PlaceObject placeObject = roomList.get(position);
        Log.i(" photo user",placeObject.getUser().getPhotolink());
        Picasso.with(context).load(placeObject.getUser().getPhotolink()).resize(70, 70).into(holder.userpic);
        holder.name.setText(placeObject.getName());
        holder.tag.setText(placeObject.getUser().getEmail());
        holder.date.setText(placeObject.getCreatedAt());
        holder.city.setText(placeObject.getUser().getCountry() + placeObject.getUser().getCity());
        holder.detale.setText(placeObject.getDescription());
        Picasso.with(context).load(placeObject.getRoomPhoto().get(1).getPhotolink()).resize(200, 85).into(holder.roompic);
        holder.btlike.setImageResource(R.drawable.like);
    }


    @Override
    public int getItemCount() {
        return roomList.size();
    }


}
