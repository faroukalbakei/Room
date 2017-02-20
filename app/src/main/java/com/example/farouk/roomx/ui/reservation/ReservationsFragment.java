package com.example.farouk.roomx.ui.reservation;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.Reservation;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends Fragment implements VolleyCallback {
    private RecyclerView recyclerView;
    ReservationAdapter mAdapter;
    TextView emptyView;
    private List<Reservation> reservationList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView=inflater.inflate(R.layout.fragment_reservations, container, false);
        //getActivity().setTitle(getResources().getString(R.string.title_activity_reserve));
        emptyView=(TextView)rootView.findViewById(R.id.empty_list_textView);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_viewBooking);
        //recyclerView.setHasFixedSize(true);
        reservationList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if(Utils.isInternetAvailable()){
            Requests requests = new Requests(getContext());
            requests.getUserReservations(this, getContext());
        }else
            Toast.makeText(getContext(),"لا يوجد انترنت", Toast.LENGTH_LONG);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onSuccess(Response response) {
        reservationList = (List<Reservation>) response.getObject();
        Log.d("reservationList", reservationList.toString());

        if(reservationList.size()<1){
            emptyView.setVisibility(View.VISIBLE);
        }else emptyView.setVisibility(View.GONE);
        recyclerView.setAdapter(new ReservationAdapter(reservationList,getContext()));
    }
}
