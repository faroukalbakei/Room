package com.example.farouk.roomx;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.farouk.roomx.app.Prefs;
import com.example.farouk.roomx.model.LoginResponse;
import com.example.farouk.roomx.model.Movie;
import com.example.farouk.roomx.model.MoviesResponse;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.ResponsePlace;
import com.example.farouk.roomx.model.User;
import com.example.farouk.roomx.model.UserinfoLogin;
import com.example.farouk.roomx.service.ApiClient;
import com.example.farouk.roomx.service.ApiInterface;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import timber.log.Timber;

public class explore extends Fragment implements VolleyCallback {

    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";

    private List<Room> roommList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DetailAdapter mAdapter;
    public ImageButton btlike;
    private static final String TAG = explore.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_explore, container, false);
        btlike = (ImageButton) getActivity().findViewById(R.id.imge_love);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        // recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

/*        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        UserinfoLogin user = Prefs.with(getContext()).getUser();
        Call<LoginResponse> call = apiService.login("farouk.h@hotmail.com", "12345678");
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                int statusCode = response.code();
                LoginResponse movies = response.body();

                Timber.d(" movies %s",movies.toString());
            }

            @Override

            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });*/


//        String token =user.getToken();
//        Call<ResponsePlace> call2 = apiService.getExploreList("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOjIsImlzcyI6Imh0dHA6XC9cL3lhbGF0cy5jb21cL2dhaXRoYXBpXC9wdWJsaWNcL2FwaVwvYXV0aGVudGljYXRlIiwiaWF0IjoxNDg1MDIxMTE3LCJleHAiOjE1MTY1NTcxMTcsIm5iZiI6MTQ4NTAyMTExNywianRpIjoiMmZmNDlkMGZjZGRlOTc5ZDYzZmZlYzgxOTgyOGYxM2MifQ._Td7XAuJJH6SDYYPsLKEnx2nq-FM6p-iM-p8C1P5UiE");
//        call2.enqueue(new Callback<ResponsePlace>() {
//            @Override
//            public void onResponse(Call<ResponsePlace> call, retrofit2.Response<ResponsePlace> response) {
//                int statusCode = response.code();
//                List<PlaceObject> placeObjectList = response.body().getValue();
//                recyclerView.setAdapter(new DetailAdapter(placeObjectList));
//                Log.e(TAG, placeObjectList.toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponsePlace> call, Throwable t) {
//                Log.e(TAG, t.toString());
//
//            }
//        });


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Room roomm = roommList.get(position);
                Toast.makeText(getActivity(), roomm.getUserName() + " is selected!", Toast.LENGTH_SHORT).show();

                //  Toast.makeText(getApplicationContext(), (String) btlike.getTag(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        //  prepareDetailData();
        Requests requests = new Requests();
        requests.getExploreList(this, getContext());
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_explore);

    }

    private void prepareDetailData() {
        Room rroom = new Room(R.drawable.ttt, "farouk albakri", "@farouk", "1990", "gaza", R.drawable.room, 1, "new flate you want to rent 120 m have agood beed ");
        roommList.add(rroom);

        rroom = new Room(R.drawable.tttt, "abir", "@devabir", "1993", "gaza", R.drawable.flate, 0, "new flate you want to rent 120 m have agood beed ");
        roommList.add(rroom);

        rroom = new Room(R.drawable.tttt, "abir", "@devabir", "1993", "gaza", R.drawable.flate, 0, "new flate you want to rent 120 m have agood beed ");
        roommList.add(rroom);


        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(Response response) {

        List<PlaceObject> placeObjects = (List<PlaceObject>) response.getObject();
        recyclerView.setAdapter(new DetailAdapter(placeObjects,getContext()));
    }
}
