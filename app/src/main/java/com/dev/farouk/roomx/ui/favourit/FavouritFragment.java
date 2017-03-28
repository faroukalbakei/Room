package com.dev.farouk.roomx.ui.favourit;


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

import com.dev.farouk.roomx.R;
import com.dev.farouk.roomx.model.PlaceObject;
import com.dev.farouk.roomx.model.Response;
import com.dev.farouk.roomx.service.Requests;
import com.dev.farouk.roomx.service.VolleyCallback;
import com.dev.farouk.roomx.ui.explore.Callback;
import com.dev.farouk.roomx.ui.explore.ExploreAdapter;
import com.dev.farouk.roomx.util.Const;
import com.dev.farouk.roomx.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class FavouritFragment extends Fragment implements VolleyCallback ,Callback{

    private final static String API_KEY = "7e8f60e325cd06e164799af1e317d7a7";

    private RecyclerView recyclerView;
    private com.dev.farouk.roomx.ui.explore.ExploreAdapter mAdapter;
    private static final String TAG = FavouritFragment.class.getSimpleName();
    TextView emptyView;
    Long placeId;
    private List<PlaceObject> placeObjects;
    private ExploreAdapter exploreAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);
        emptyView=(TextView)rootView.findViewById(R.id.empty_list_textView);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_fav);
        // recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        exploreAdapter = new ExploreAdapter(getContext(), 0);

/*        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        placeId =placeObjects.get(position).getId();
                        Log.d("placeId", String.valueOf(placeId));
                        Intent intent= new Intent(getActivity(),PlaceDetailsActivity.class);
                        intent.putExtra(Const.PLACE_ID,placeId);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );*/
        if(Utils.isInternetAvailable(getContext())){
            Requests requests = new Requests(getContext());
            requests.getPlacesList(this, getContext(), Const.getFavList_URL);
        }else
            Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();

        //  prepareDetailData();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.fragment_explore);

    }

/*        @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser&& isAdded() ) {
          //  getActivity().setTitle(getResources().getString(R.string.title_activity_fav));
            if(Utils.isInternetAvailable(getContext())){
                Requests requests = new Requests(getContext());
                requests.getPlacesList(this, getContext(), Const.getFavList_URL);
            }else
                Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
        }
    }*/
    @Override
    public void onSuccess(Response response) {
        PlaceObject object,placeObject = null;
        if(response.getResult()==1){
            object = (PlaceObject) response.getObject();
            placeObject = PlaceObject.findById(PlaceObject.class,object.getId());
            Toast.makeText(getActivity(),response.getMessage()+"",Toast.LENGTH_LONG).show();
            placeObject.setIsFavourate(1);
            placeObject.save();
        }else if(response.getResult()==0){


            object = (PlaceObject) response.getObject();
            placeObject = PlaceObject.findById(PlaceObject.class,object.getId());
            Toast.makeText(getActivity(),response.getMessage()+"",Toast.LENGTH_LONG).show();
            placeObject.setIsFavourate(0);
            placeObject.save();
        }else {
            placeObjects=(List<PlaceObject>) response.getObject();

            if (placeObjects != null && placeObjects.size() < 1) {
                emptyView.setVisibility(View.VISIBLE);
            } else emptyView.setVisibility(View.GONE);
            exploreAdapter.setPlaceList(placeObjects);
            exploreAdapter.callbackInfo(this,this);
            recyclerView.setAdapter(exploreAdapter);        }

    }

    @Override
    public void likeRoom(int po) {
        Log.d("fav placeId", String.valueOf(placeObjects.get(po).getPid()));

        if (Utils.isInternetAvailable(getContext())) {
            Requests requests = new Requests(getContext());
            requests.addToWishList(this, getContext(), String.valueOf(placeObjects.get(po).getPid()), placeObjects.get(po).getId());
        } else
            Toast.makeText(getContext(), "لا يوجد انترنت", Toast.LENGTH_LONG);
    }
}
