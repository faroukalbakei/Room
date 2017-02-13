package com.example.farouk.roomx.ui.chat;


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

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.UserinfoLogin;
import com.example.farouk.roomx.ui.chat.ChatListAdabter;
import com.example.farouk.roomx.util.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends Fragment {

    private List<UserinfoLogin> cuserList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatListAdabter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.activity_inbox, container, false);


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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_inbox);
    }

    private void prepareDetailData()  {
        try {


            UserinfoLogin users = new UserinfoLogin("farouk albakri ", "Egypt", R.drawable.ttt);
            cuserList.add(users);

            users = new UserinfoLogin("DEV aBir ", "gaza", R.drawable.tttt);
            cuserList.add(users);
        }catch (Exception ex){


        }



        mAdapter.notifyDataSetChanged();
    }
}
