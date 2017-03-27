package com.dev.farouk.roomx.ui.chat;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dev.farouk.roomx.R;
import com.dev.farouk.roomx.model.UserinfoLogin;
import com.dev.farouk.roomx.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment {

    private List<UserinfoLogin> cuserList;
    private RecyclerView recyclerView;
    private ChatListAdabter mAdapter;
    private boolean isDataLoaded=false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.fragment_inbox, container, false);

      //  getActivity().setTitle(getResources().getString(R.string.title_activity_inbox));
        cuserList = new ArrayList<>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_Inbox);
        //recyclerView.setHasFixedSize(true);

        mAdapter = new ChatListAdabter(cuserList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                //  Toast.makeText(getApplicationContext(), (String) btlike.getTag(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

                UserinfoLogin bookings = cuserList.get(position);
                Toast.makeText(getActivity(), bookings.getName() + " is selected!", Toast.LENGTH_SHORT).show();

            }
        }));

        prepareDetailData();

        return rootView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
            if (isVisibleToUser && !isDataLoaded && isAdded()) {
              //  getActivity().setTitle(getResources().getString(R.string.title_activity_inbox));
/*
                if(Utils.isInternetAvailable(getActivity())){
                    Requests requests = new Requests(getContext());
                    requests.getUserProfile(this, getContext());
                }else
                    Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();*/
                isDataLoaded = true;
            }

    }

    private void prepareDetailData()  {
        try {


            UserinfoLogin users = new UserinfoLogin("farouk albakri ", "Egypt", R.drawable.no_image_available);
            cuserList.add(users);

            users = new UserinfoLogin("DEV aBir ", "gaza", R.drawable.no_image_available);
            cuserList.add(users);
        }catch (Exception ex){


        }



        mAdapter.notifyDataSetChanged();
    }
}
