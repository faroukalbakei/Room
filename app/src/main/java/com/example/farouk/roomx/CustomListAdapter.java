package com.example.farouk.roomx;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by farouk on 1/27/17.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] Titel;//info about room
    private final String[]  nameuser;
    private final String[]  taguser;
    private final String[]  date;
    private final String[]  city;
    private final Integer[] imgid;// image room
    private final Integer[] imguser;//user pic
    private final Integer[] like;// foveret






    public CustomListAdapter(Activity context, String[] Titel, Integer[] imgid,Integer[] like,String[] nameuser,String[] date,String[] city,String[] taguser,Integer[] imguser) {
        super(context, R.layout.customlist, Titel);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.Titel=Titel;
        this.imgid=imgid;
        this.date=date;
        this.nameuser=nameuser;
        this.like=like;
        this.city=city;
        this.imguser=imguser;
        this.taguser =taguser ;

    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.customlist, null,true);

        TextView infoRoom = (TextView) rowView.findViewById(R.id.tv_inf);
        ImageView picRoom = (ImageView) rowView.findViewById(R.id.img_picRoom);
        ImageView picuser = (ImageView) rowView.findViewById(R.id.imv_userphoto);
        ImageButton ImbLike = (ImageButton) rowView.findViewById(R.id.imge_love);
        TextView tfName = (TextView) rowView.findViewById(R.id.tv_name);
        TextView City = (TextView) rowView.findViewById(R.id.tv_cantry);
        TextView datecreat = (TextView) rowView.findViewById(R.id.tv_date);
        TextView taguserl = (TextView) rowView.findViewById(R.id.tv_tag);


        infoRoom.setText(Titel[position]);
        picRoom.setImageResource(imgid[position]);
        picuser.setImageResource(imguser[position]);
        tfName.setText(nameuser[position]);
        City.setText(city[position]);
        datecreat.setText(date[position]);
        taguserl.setText(taguser[position]);
        ImbLike.setImageResource(like[position]);



        return rowView;

    };
}
