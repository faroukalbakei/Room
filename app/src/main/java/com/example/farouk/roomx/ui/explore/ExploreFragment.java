package com.example.farouk.roomx.ui.explore;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.farouk.roomx.util.Const;
import com.example.farouk.roomx.util.FragmentType;
import com.example.farouk.roomx.util.Utils;
import com.example.farouk.roomx.util.RecyclerItemClickListener;

import java.util.List;

public class ExploreFragment extends Fragment implements VolleyCallback {

    private RecyclerView recyclerView;
    private ExploreAdapter mAdapter;
    public ImageButton btlike;
    private static final String TAG = ExploreFragment.class.getSimpleName();
    TextView emptyView;
    private List<PlaceObject> placeObjects;
    Long placeId;
    private ExploreAdapter exploreAdapter;
    int fragmentType;
    private boolean isDataLoaded=false;

    public void setFragmentType(int fragmentType) {
        this.fragmentType = fragmentType;
    }

    public static ExploreFragment newInstance(int fragmentType){
        ExploreFragment fragment = new ExploreFragment();
        fragment.setFragmentType(fragmentType);
        return fragment;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_explore, container, false);
        emptyView = (TextView) rootView.findViewById(R.id.empty_list_textView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        // recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
/*        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // do whatever
                       // if (view.getId() == recyclerView.getId()) {
                            placeId = placeObjects.get(position).getId();
                            Log.d("placeId", String.valueOf(placeId));
                            Intent intent = new Intent(getActivity(), PlaceDetailsActivity.class);
                            intent.putExtra(Const.PLACE_ID, placeId);
                            startActivity(intent);
                      //  }
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/


        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.fragment_explore);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
 /*           if (isVisibleToUser && !isDataLoaded ) {

                if(Utils.isInternetAvailable(getActivity())){
                    Requests requests = new Requests(getContext());
                    requests.getUserProfile(this, getContext());
                }else
                    Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
                isDataLoaded = true;
            }*/
        if (isVisibleToUser && isAdded()&& !isDataLoaded)
        {
            String url;
            if(fragmentType== FragmentType.MY_ROOMS.getValue()){
                url= Const.getMyRoom_URL;
              //  getActivity().setTitle(getResources().getString(R.string.title_activity_my_room));
            }else{
               // getActivity().setTitle(getResources().getString(R.string.title_activity_explore));
                url= Const.getExplore_URL;

            }

            if (Utils.isInternetAvailable(getContext())) {
                Requests requests = new Requests(getContext());
                requests.getPlacesList(this, getContext(), url);
            } else
                Toast.makeText(getContext(), "لا يوجد انترنت", Toast.LENGTH_LONG);
            isDataLoaded = true;
        }
    }
    @Override
    public void onSuccess(Response response) {
        Log.d("ExploreFragment", response.toString());

        PlaceObject object,placeObject = null;
        if(response.getResult()==1){
            object = (PlaceObject) response.getObject();
            placeObject = PlaceObject.findById(PlaceObject.class,object.getId());
            Toast.makeText(getActivity(),response.getMessage()+"",Toast.LENGTH_LONG).show();
            placeObject.setIsFavourate(1);
            placeObject.save();
        }else if(response.getResult()==0){
            if(fragmentType==FragmentType.MY_ROOMS.getValue()){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                object = (PlaceObject) response.getObject();
                placeObject = PlaceObject.findById(PlaceObject.class,object.getId());
                Toast.makeText(getActivity(),response.getMessage()+"",Toast.LENGTH_LONG).show();
                placeObject.setIsFavourate(0);
                placeObject.save();
            }
        }else {
            placeObjects = (List<PlaceObject>) response.getObject();
            if (placeObjects != null && placeObjects.size() < 1) {
                emptyView.setVisibility(View.VISIBLE);
            } else emptyView.setVisibility(View.GONE);
            exploreAdapter = new ExploreAdapter(placeObjects, getContext());
            exploreAdapter.callbackInfo(this);
            recyclerView.setAdapter(exploreAdapter);
        }


    }
}
