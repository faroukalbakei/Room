package com.example.farouk.roomx.ui.favourit;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.PlaceObject;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.ui.explore.ExploreAdapter;
import com.example.farouk.roomx.util.NetworkConnection;

import java.util.List;

public class FavouritFragment extends Fragment implements VolleyCallback {

    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";

    private RecyclerView recyclerView;
    private com.example.farouk.roomx.ui.explore.ExploreAdapter mAdapter;
    public ImageButton btlike;
    private static final String TAG = FavouritFragment.class.getSimpleName();
    TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);
        btlike = (ImageButton) getActivity().findViewById(R.id.imge_love);
       // getActivity().setTitle(getResources().getString(R.string.title_activity_fav));
        emptyView=(TextView)rootView.findViewById(R.id.empty_list_textView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_fav);
        // recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(NetworkConnection.isInternetAvailable()){
            Requests requests = new Requests(getContext());
            requests.getWishlistList(this, getContext());
        }else
            Toast.makeText(getContext(),"لا يوجد انترنت", Toast.LENGTH_LONG);

        //  prepareDetailData();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.fragment_explore);

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
