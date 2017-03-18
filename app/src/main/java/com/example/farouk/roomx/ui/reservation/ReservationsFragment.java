package com.example.farouk.roomx.ui.reservation;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.Reservation;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.User;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.util.Const;
import com.example.farouk.roomx.util.FragmentType;
import com.example.farouk.roomx.util.RecyclerItemClickListener;
import com.example.farouk.roomx.util.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import timber.log.Timber;

public class ReservationsFragment extends Fragment implements VolleyCallback {
    @Bind(R.id.hosted_image)
    CircleImageView hostedImage;
    @Bind(R.id.text_view_hosted_name)
    TextView textViewHostedName;
    @Bind(R.id.tv_resrv_place)
    TextView tvResrvPlace;
    @Bind(R.id.tv_resrv_Start)
    TextView tvResrvStart;
    @Bind(R.id.tv_resrv_StartDate)
    TextView tvResrvStartDate;
    @Bind(R.id.innerLine)
    View innerLine;
    @Bind(R.id.tv_resrv_End)
    TextView tvResrvEnd;
    @Bind(R.id.tv_resrv_EndDate)
    TextView tvResrvEndDate;
    @Bind(R.id.accept_textview)
    TextView acceptTextview;
    @Bind(R.id.accept_layout)
    LinearLayout acceptLayout;
    @Bind(R.id.refuse_layout)
    LinearLayout refuseLayout;
    @Bind(R.id.close_button)
    Button closeButton;
    @Bind(R.id.visit_profile_button)
    Button visitProfileButton;
    private RecyclerView recyclerView;
    ReservationAdapter mAdapter;
    TextView emptyView;
    private List<Reservation> reservationList;
    int fragmentType;
    AlertDialog alertDialog = null;
    private boolean isDataLoaded = false;

    public void setFragmentType(int fragmentType) {
        this.fragmentType = fragmentType;
    }

    public static ReservationsFragment newInstance(int fragmentType) {
        ReservationsFragment fragment = new ReservationsFragment();
        fragment.setFragmentType(fragmentType);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_reservations, container, false);

        emptyView = (TextView) rootView.findViewById(R.id.empty_list_textView);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_viewBooking);
        //recyclerView.setHasFixedSize(true);
        reservationList = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if (fragmentType == FragmentType.RESERVATION_REQUESTS.getValue()) {
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // do whatever

                            showDiaog(reservationList.get(position));
                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }
                    })
            );
        }
        emptyView.setVisibility(View.VISIBLE);
        getReservationRequest();
        return rootView;


    }

    public void getReservationRequest() {
        String url;
        if (fragmentType == FragmentType.RESERVATION_REQUESTS.getValue()) {
            url = Const.getReservationRequest_URL;
            // getActivity().setTitle(getResources().getString(R.string.title_activity_reservation_requests));
        } else {
            url = Const.getReservation_URL;
            //  getActivity().setTitle(getResources().getString(R.string.title_activity_reserve));
        }
        if (Utils.isInternetAvailable(getContext())) {
            Requests requests = new Requests(getContext());
            requests.getReservations(this, getContext(), url);
        } else
            Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
    }
/*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
 *//*           if (isVisibleToUser  ) {

                if(Utils.isInternetAvailable(getActivity())){
                    Requests requests = new Requests(getContext());
                    requests.getUserProfile(this, getContext());
                }else
                    Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
                isDataLoaded = true;
            }*//*
        if (isVisibleToUser && isAdded()&& !isDataLoaded) {
            String url;
            if (fragmentType == FragmentType.RESERVATION_REQUESTS.getValue()) {
                url = Const.getReservationRequest_URL;
               // getActivity().setTitle(getResources().getString(R.string.title_activity_reservation_requests));
            } else {
                url = Const.getReservation_URL;
              //  getActivity().setTitle(getResources().getString(R.string.title_activity_reserve));
            }
            if (Utils.isInternetAvailable(getContext())) {
                Requests requests = new Requests(getContext());
                requests.getReservations(this, getContext(), url);
            } else
                Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
            isDataLoaded = true;
        }
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onSuccess(Response response) {
        reservationList = (List<Reservation>) response.getObject();
        if (reservationList != null) {
            Log.d("reservationList", reservationList.toString());
            if (reservationList.size() < 1) {
                emptyView.setVisibility(View.VISIBLE);
            } else emptyView.setVisibility(View.GONE);
            recyclerView.setAdapter(new ReservationAdapter(reservationList, getContext(), fragmentType));
        }
/*        if (!response.isValid()) {
            emptyView.setVisibility(View.VISIBLE);
        }*/
        if (response.getResult() == 1) {
            alertDialog.dismiss();
            getReservationRequest();
        }else if (response.getResult()==0){
            alertDialog.dismiss();
            Utils.snakebar(getString(R.string.general_error),emptyView);
        }
    }

    public void showDiaog(final Reservation reservation) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_reservation_request, null);
        ButterKnife.bind(this, dialogView);
        dialogBuilder.setView(dialogView);
        List<User> userList = User.find(User.class, "uId=?", String.valueOf(reservation.getUserId()));
        User user = userList.get(0);
        Timber.i("user %s", user);
        textViewHostedName.setText(user.getName());
        Picasso.with(getContext()).load(user.getPhotolink()).into(hostedImage);
        tvResrvPlace.setText(reservation.getRoom().getName());
        tvResrvStartDate.setText(reservation.getStart());
        tvResrvEndDate.setText(reservation.getEnd());
        acceptLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReserve(1, reservation.getId());

            }
        });

        refuseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReserve(0, reservation.getId());


            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void doReserve(int isAccepted, int reservationId) {
        if (Utils.isInternetAvailable(getContext())) {
            Requests requests = new Requests(getContext());
            requests.doReservationReques(this, getContext(), Const.acceptreservation, String.valueOf(reservationId), isAccepted);
        } else
            Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
    }
}
