package com.example.farouk.roomx;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Acounting extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    private List<Detelsitem> detelsitem = new ArrayList<>();

    private AcountingAdapter mAdapter;
    private RecyclerView recyclerView;
    static String sdate;
    TextView tvDAte;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acounting);
        tvDAte = (TextView) findViewById(R.id.tv_dialog_date);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // recyclerView.setHasFixedSize(true);
        mAdapter = new AcountingAdapter(detelsitem);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Detelsitem roomm = detelsitem.get(position);
                if (position == 0) {
                    Toast.makeText(getApplicationContext(), "invite friends", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    Toast.makeText(getApplicationContext(), "RoomX Gifts", Toast.LENGTH_SHORT).show();

                } else if (position == 2) {
                    Toast.makeText(getApplicationContext(), "Be Host", Toast.LENGTH_SHORT).show();

                } else if (position == 3) {

                    Toast.makeText(getApplicationContext(), "Help", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareDetailData();
    }

    private void prepareDetailData() {


        Detelsitem detailing = new Detelsitem(R.drawable.friends, getString(R.string.Invite));
        detelsitem.add(detailing);

        detailing = new Detelsitem(R.drawable.gift, getString(R.string.gifts));
        detelsitem.add(detailing);


        detailing = new Detelsitem(R.drawable.house, getString(R.string.host));
        detelsitem.add(detailing);


        detailing = new Detelsitem(R.drawable.question, getString(R.string.Help));
        detelsitem.add(detailing);


        mAdapter.notifyDataSetChanged();
    }

    public void openBottomSheet(View v) {

        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        EditText DName = (EditText) findViewById(R.id.et_dilog_name);
        EditText DEmail = (EditText) findViewById(R.id.et_dilog_Email);
        EditText Dmobile = (EditText) findViewById(R.id.et_dilog_mobile);



        ImageButton Ddate = (ImageButton) findViewById(R.id.bt_dilog_date);

        ImageButton Dcity = (ImageButton) findViewById(R.id.bt_dilog_city);
        Button Dsave = (Button) findViewById(R.id.bt_dilog_save);


        final Dialog mBottomSheetDialog = new Dialog(Acounting.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();



}

    public void calendar(View v) {

      newInstance();
        Toast.makeText(getApplicationContext(), "Clicked Backup", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

       // TextView tv = (TextView) findViewById(R.id.tv_dialog_date);
        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;

        sdate = date;
        Toast.makeText(this,sdate,Toast.LENGTH_LONG).show();
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
        dpd.show(getFragmentManager(), "Datepickerdialog");



    }


}
