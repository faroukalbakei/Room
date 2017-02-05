package com.example.farouk.roomx;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.model.PlaceObject;

import java.util.List;
//
//import static android.R.attr.key;
//import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by farouk on 1/29/17.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyViewHolder> {


    private List<PlaceObject> roomList;

    public ImageButton btlike;

    int lik= -1;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, tag, date, city, detale;
        public ImageView userpic, roompic;
        public ImageButton btlike;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            date = (TextView) view.findViewById(R.id.tv_date);
            tag = (TextView) view.findViewById(R.id.tv_tag);
            city = (TextView) view.findViewById(R.id.tv_cantry);
            detale = (TextView) view.findViewById(R.id.tv_inf);

            userpic = (ImageView) view.findViewById(R.id.imv_userphoto);
            roompic = (ImageView) view.findViewById(R.id.img_picRoom);

            btlike = (ImageButton) view.findViewById(R.id.imge_love);



        }
    }


    public DetailAdapter(List<PlaceObject> roomList) {
        this.roomList = roomList;
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



       // holder.userpic.setImageResource((placeObject.getUserPic()));
        holder.name.setText(placeObject.getName());
       // holder.tag.setText(placeObject.getTag());
        //holder.date.setText(placeObject.getDateup() + "");
        //holder.city.setText(placeObject.get);

       // holder.roompic.setImageResource((placeObject.getRoomPic()));
        holder.detale.setText(placeObject.getDescription());

        //lik=placeObject.getLike();


        if(lik==1){
            holder.btlike.setImageResource(R.drawable.like);
        }else {
            holder.btlike.setImageResource(R.drawable.unliike);
        }
     //   holder.btlike.setImageResource();


        holder.btlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("farouk", "onClick: ");


                if(lik==1){
                    lik=0;
                    holder.btlike.setImageResource(R.drawable.like);
                }else {
                    lik=1;
                    holder.btlike.setImageResource(R.drawable.unliike);
                }
            }
        });

}




    @Override
    public int getItemCount() {
        return roomList.size();
    }


}
