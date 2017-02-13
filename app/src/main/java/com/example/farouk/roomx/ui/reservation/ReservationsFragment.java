package com.example.farouk.roomx.ui.reservation;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.ui.explore.ExploreAdapter;
import com.example.farouk.roomx.util.RecyclerTouchListener;
import com.example.farouk.roomx.model.Bookings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReservationsFragment extends Fragment implements VolleyCallback {
    private List<Bookings> bookingList;
    private RecyclerView recyclerView;
    private ReservationAdapter mAdapter;
    TextView emptyView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView=inflater.inflate(R.layout.fragment_reservations, container, false);
        //getActivity().setTitle(getResources().getString(R.string.title_activity_reserve));
        emptyView=(TextView)rootView.findViewById(R.id.empty_list_textView);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_viewBooking);
        //recyclerView.setHasFixedSize(true);
        bookingList = new ArrayList<>();
        mAdapter = new ReservationAdapter(bookingList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Bookings bookings = bookingList.get(position);
                Toast.makeText(getActivity(), bookings.getPlaceName() + " is selected!", Toast.LENGTH_SHORT).show();

                //  Toast.makeText(getApplicationContext(), (String) btlike.getTag(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareDetailData();


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void prepareDetailData()  {
        try {
            String string = "January 2, 2010";
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = format.parse(string);

            Bookings bokings = new Bookings("red see ", date, date);
            bookingList.add(bokings);

            bokings = new Bookings("roots ", date, date);
            bookingList.add(bokings);
        }catch (Exception ex){


        }


        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(Response response) {
        List<PlaceObject> placeObjects = (List<PlaceObject>) response.getObject();
        if(placeObjects.size()<1){
            emptyView.setVisibility(View.VISIBLE);
        }else emptyView.setVisibility(View.GONE);
        recyclerView.setAdapter(new ExploreAdapter(placeObjects,getContext()));
    }
}
