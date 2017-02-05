package com.example.farouk.roomx;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.farouk.roomx.model.Bookings;
import com.example.farouk.roomx.model.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Inbox extends Fragment {

    private List<User> CuserList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ChatListAdabter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView=inflater.inflate(R.layout.activity_inbox, container, false);


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_Inbox);
        //recyclerView.setHasFixedSize(true);

        mAdapter = new ChatListAdabter(CuserList);
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

                User bookings = CuserList.get(position);
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


            User users = new User("farouk albakri ", "Egypt", R.drawable.ttt);
            CuserList.add(users);

            users = new User("DEV aBir ", "gaza", R.drawable.tttt);
            CuserList.add(users);
        }catch (Exception ex){


        }



        mAdapter.notifyDataSetChanged();
    }
}
