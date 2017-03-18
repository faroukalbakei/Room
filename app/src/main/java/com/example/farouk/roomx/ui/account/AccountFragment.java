package com.example.farouk.roomx.ui.account;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.app.Prefs;
import com.example.farouk.roomx.model.ExtrasItem;
import com.example.farouk.roomx.model.User;
import com.example.farouk.roomx.ui.explore.ExploreFragment;
import com.example.farouk.roomx.ui.main.IconTextTabsActivity;
import com.example.farouk.roomx.util.Const;
import com.example.farouk.roomx.util.FragmentType;
import com.example.farouk.roomx.util.RecyclerTouchListener;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.UserinfoLogin;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.util.Utils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AccountFragment extends Fragment implements DatePickerDialog.OnDateSetListener, VolleyCallback {

    private List<ExtrasItem> extrasItem;

    private AccountAdapter mAdapter;
    private RecyclerView recyclerView;

    Dialog mBottomSheetDialog;
    View dialogv;

    EditText DName;
    EditText DEmail;
    EditText Dmobile;

    Button Dsave;
    Button Editeb;
    EditText Dcity;

    EditText Ddatte;
    TextView tv_name;
    private String username;
    int fragmentType;
    private boolean isDataLoaded = false;

    public void setFragmentType(int fragmentType) {
        this.fragmentType = fragmentType;
    }

    public static AccountFragment newInstance(int fragmentType) {
        AccountFragment fragment = new AccountFragment();
        fragment.setFragmentType(fragmentType);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_acount, container, false);
        if (Utils.isInternetAvailable(getActivity())) {
            Requests requests = new Requests(getContext());
            requests.getUserProfile(this, getContext());
        } else
            Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
        dialogv = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet, null);

        mBottomSheetDialog = new Dialog(getContext(), R.style.MaterialDialogSheet);

        DName = (EditText) dialogv.findViewById(R.id.et_dilog_name);
        DEmail = (EditText) dialogv.findViewById(R.id.et_dilog_Email);
        Dmobile = (EditText) dialogv.findViewById(R.id.et_dilog_mobile);
        Dcity = (EditText) dialogv.findViewById(R.id.ed_dialog_City);
        Ddatte = (EditText) dialogv.findViewById(R.id.ed_dialog_test);
        Dsave = (Button) dialogv.findViewById(R.id.bt_dilog_save);
        Editeb = (Button) rootView.findViewById(R.id.bt_EditAccount);
        Ddatte.setRawInputType(InputType.TYPE_NULL);
        //-------

        tv_name = (TextView) rootView.findViewById(R.id.tv_NameUser);
        tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShowProfileActivity.class));
            }
        });

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_viewAcount);
        // recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ExtrasItem roomm = extrasItem.get(position);
/*                if (position == 0) {
                    startActivity(new Intent(getActivity(),ShowProfileActivity.class));
                }else */
                if (position == 0) {
                    Toast.makeText(getActivity(), "invite friend", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getActivity(), "RoomX Gifts", Toast.LENGTH_SHORT).show();

                } else if (position == 2) {
/*                    Intent intent = new Intent(getContext(), AddRoomFragment.class);
                    startActivity(intent);*/

                    Intent intent = new Intent(getContext(), IconTextTabsActivity.class);
                    if(fragmentType==FragmentType.BE_HOST.getValue()){
                        intent.putExtra(Const.BE_HOST, false);
                    }else  intent.putExtra(Const.BE_HOST, true);

                    startActivity(intent);
                    getActivity().finish();

                } else if (position == 3) {

                    Toast.makeText(getActivity(), "Help", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Editeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), ActivityEditProfile.class));

//                mBottomSheetDialog.setContentView(dialogv);
//                mBottomSheetDialog.setCancelable(true);
//                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
//
//                mBottomSheetDialog.show();
            }
        });

        Ddatte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newInstance();
            }
        });

        extrasItem = new ArrayList<>();
        mAdapter = new AccountAdapter(extrasItem, getContext());
        recyclerView.setAdapter(mAdapter);
        prepareDetailData();
        return rootView;
    }

/*    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
 *//*           if (isVisibleToUser && !isDataLoaded ) {

                if(Utils.isInternetAvailable(getActivity())){
                    Requests requests = new Requests(getContext());
                    requests.getUserProfile(this, getContext());
                }else
                    Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
                isDataLoaded = true;
            }*//*
        if (isVisibleToUser&& isAdded()&& !isDataLoaded ){
           // getActivity().setTitle(getResources().getString(R.string.title_activity_account));
            if (Utils.isInternetAvailable(getActivity())) {
                Requests requests = new Requests(getContext());
                requests.getUserProfile(this, getContext());
            } else
                Toast.makeText(getActivity(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
            isDataLoaded = true;
        }

    }*/

    private void prepareDetailData() {
        ExtrasItem detailing;
/*        detailing = new ExtrasItem(photolink, username);
        extrasItem.add(detailing);*/

        detailing = new ExtrasItem(R.drawable.frin, getString(R.string.Invite));
        extrasItem.add(detailing);

        detailing = new ExtrasItem(R.drawable.ic_gifts, getString(R.string.gifts));
        extrasItem.add(detailing);
        String title;
        if (fragmentType == FragmentType.BE_HOST.getValue()) {
            title = getString(R.string.be_traveller);
        } else title = getString(R.string.host);
        detailing = new ExtrasItem(R.drawable.ic_behost, title);
        extrasItem.add(detailing);


        detailing = new ExtrasItem(R.drawable.infoi, getString(R.string.Help));
        extrasItem.add(detailing);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        Ddatte.setText(date);
    }

    public void newInstance() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.Navy));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

    }


    public void indata() {

        String name = DName.getText().toString();
        String email = DEmail.getText().toString();
        String mob = Dmobile.getText().toString();
        String date = Ddatte.getText().toString();
        String city = Dcity.getText().toString();
        //userinfoLogin.SetDialogData(name, email, mob, city, date);

        DName.setText("");
        DEmail.setText("");
        Dmobile.setText("");
        Ddatte.setText("");
        Dcity.setText("");
        mBottomSheetDialog.setContentView(dialogv);
        mBottomSheetDialog.dismiss();


    }


    @Override
    public void onSuccess(Response response) {

        User user = (User) response.getObject();
        username = user.getName();
        tv_name.setText(username + "");

        Log.d("user onSuccess", user.toString());

    }
}
