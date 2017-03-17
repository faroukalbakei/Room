package com.example.farouk.roomx.ui.reservation;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.Reservation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//
/**
 * Created by farouk on 2/3/17.
 */

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {

    private  int fragmentType;
    private  Context context;
    private List<Reservation> reservations = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView PlaceName;
        TextView StartDAy;
        TextView EndDay;
        LinearLayout reservationLayout;


        public MyViewHolder(View view) {
            super(view);
            PlaceName = (TextView) view.findViewById(R.id.tv_resrv_place);
            StartDAy = (TextView) view.findViewById(R.id.tv_resrv_Start);
            EndDay = (TextView) view.findViewById(R.id.tv_resrv_End);
            reservationLayout=(LinearLayout)view.findViewById(R.id.reservtion_layout);
        }
    }

    public ReservationAdapter(List<Reservation> reservations, Context context, int fragmentType) {
        this.reservations = reservations;
        this.context = context;
        this.fragmentType = fragmentType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking, parent, false);


        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        Reservation reservation = reservations.get(position);

        holder.PlaceName.setText(reservation.getRoom().getName()+"");
        holder.StartDAy.setText(reservation.getStart()+"");
        holder.EndDay.setText(reservation.getEnd() + "");

    }


    @Override
    public int getItemCount() {
        return reservations.size();
    }



}
