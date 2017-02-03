package com.example.farouk.roomx;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farouk.roomx.model.Bookings;

import java.util.ArrayList;
import java.util.List;

//
/**
 * Created by farouk on 2/3/17.
 */

public class BookigAdabter  extends RecyclerView.Adapter<BookigAdabter.MyViewHolder> {

    private List<Bookings> bookingList = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView PlaceName;
        TextView StartDAy;
        TextView EndDay;


        public MyViewHolder(View view) {
            super(view);
            PlaceName = (TextView) view.findViewById(R.id.tv_resrv_place);
            StartDAy = (TextView) view.findViewById(R.id.tv_resrv_StartDate);
            EndDay = (TextView) view.findViewById(R.id.tv_resrv_EndDate);
        }
    }

    public BookigAdabter(List<Bookings> bookingList) {
        this.bookingList = bookingList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


       Bookings bookings = bookingList.get(position);

        holder.PlaceName.setText(bookings.getPlaceName());
        holder.StartDAy.setText(bookings.getStartBooking()+"");
        holder.EndDay.setText(bookings.getEndBooking() + "");

    }




    @Override
    public int getItemCount() {
        return bookingList.size();
    }

}
