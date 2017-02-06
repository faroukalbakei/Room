package com.example.farouk.roomx;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.UserinfoLogin;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Acounting extends Fragment implements DatePickerDialog.OnDateSetListener, VolleyCallback {

    private List<Detelsitem> detelsitem = new ArrayList<>();


    private AcountingAdapter mAdapter;
    private RecyclerView recyclerView;

    Dialog mBottomSheetDialog;
    View dialogv ;

            EditText DName;
    EditText DEmail;
    EditText Dmobile;

    Button Dsave;
    Button Editeb;
    EditText Dcity;

    EditText Ddatte;
    TextView tv_name;
    UserinfoLogin userinfoLogin;

    public Acounting() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.activity_acounting, container, false);
         dialogv = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        userinfoLogin = new UserinfoLogin();

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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_viewAcount);
        // recyclerView.setHasFixedSize(true);
        mAdapter = new AcountingAdapter(detelsitem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Detelsitem roomm = detelsitem.get(position);
                if (position == 0) {
                    Toast.makeText(getActivity(), "invite friend", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getActivity(), "RoomX Gifts", Toast.LENGTH_SHORT).show();

                } else if (position == 2) {
                    Toast.makeText(getActivity(), "Be Host", Toast.LENGTH_SHORT).show();

                } else if (position == 3) {

                    Toast.makeText(getActivity(), "Help", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareDetailData();

        tv_name.setText(userinfoLogin.getName());

        Editeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mBottomSheetDialog.setContentView(dialogv);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

                mBottomSheetDialog.show();
            }
        });

        Ddatte.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                newInstance();
            }
        });
        Requests requests =new Requests();
        requests.getUserProfile(this,getContext());

        return rootView;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    private void prepareDetailData() {


        Detelsitem detailing = new Detelsitem(R.drawable.frin, getString(R.string.Invite));
        detelsitem.add(detailing);

        detailing = new Detelsitem(R.drawable.box, getString(R.string.gifts));
        detelsitem.add(detailing);


        detailing = new Detelsitem(R.drawable.home, getString(R.string.host));
        detelsitem.add(detailing);


        detailing = new Detelsitem(R.drawable.infoi, getString(R.string.Help));
        detelsitem.add(detailing);


        mAdapter.notifyDataSetChanged();
    }



    public void save(View v) {
        indata();

        // newInstance();
        Toast.makeText(getActivity(), "Clicked Backup", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;


        // mBottomSheetDialog.setContentView(dialogv);
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
        userinfoLogin.SetDialogData(name, email, mob, city, date);

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

    }
}
