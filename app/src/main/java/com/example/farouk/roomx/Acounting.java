package com.example.farouk.roomx;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.model.User;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Acounting extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


    private List<Detelsitem> detelsitem = new ArrayList<>();


    private AcountingAdapter mAdapter;
    private RecyclerView recyclerView;

     Dialog mBottomSheetDialog;
    View dialogv;
    EditText DName;
    EditText DEmail;
    EditText Dmobile;
    ImageButton Ddate;
    Button Dsave;
    EditText Dcity;
    TextView tvDAte;
    EditText Ddatte;
    TextView tv_name;
    User user ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acounting);

        //-------
        user = new User();
        dialogv = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        mBottomSheetDialog = new Dialog(Acounting.this, R.style.MaterialDialogSheet);
       // tvDAte =(TextView) dialogv.findViewById(R.id.tv_dialog_farouk);
        DName = (EditText) dialogv.findViewById(R.id.et_dilog_name);
        DEmail = (EditText) dialogv.findViewById(R.id.et_dilog_Email);
        Dmobile = (EditText) dialogv.findViewById(R.id.et_dilog_mobile);
       //  Ddate = (ImageButton) dialogv.findViewById(R.id.bt_dilog_date);
        Dcity =(EditText) dialogv.findViewById(R.id.ed_dialog_City);
        Ddatte =(EditText) dialogv.findViewById(R.id.ed_dialog_test);
         Dsave = (Button) dialogv.findViewById(R.id.bt_dilog_save);
        Ddatte.setRawInputType(InputType.TYPE_NULL);
        //-------

        tv_name = (TextView) findViewById(R.id.tv_NameUser);

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
                    Toast.makeText(getApplicationContext(), "invite friend", Toast.LENGTH_SHORT).show();
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

        tv_name.setText(user.getName());



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

    public void openBottomSheet(View v) {
/*
         dialogv = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
         DName = (EditText) dialogv.findViewById(R.id.et_dilog_name);
         DEmail = (EditText) dialogv.findViewById(R.id.et_dilog_Email);
         Dmobile = (EditText) dialogv.findViewById(R.id.et_dilog_mobile);



        ImageButton Ddate = (ImageButton) findViewById(R.id.bt_dilog_date);

        ImageButton Dcity = (ImageButton) findViewById(R.id.bt_dilog_city);
        Button Dsave = (Button) findViewById(R.id.bt_dilog_save);
*/


        mBottomSheetDialog.setContentView(dialogv);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);

        mBottomSheetDialog.show();



}

    public void calendar(View v) {



         newInstance();
        Toast.makeText(getApplicationContext(), "Clicked Backup", Toast.LENGTH_SHORT).show();
       // Ddatte.setEnabled(true);

    }
    public void save(View v) {
        indata();

       // newInstance();
        Toast.makeText(getApplicationContext(), "Clicked Backup", Toast.LENGTH_SHORT).show();


    }





    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {


        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;




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
        dpd.show(getFragmentManager(), "Datepickerdialog");



    }


    public  void indata() {

        String name = DName.getText().toString();
        String email = DEmail.getText().toString();
        String mob = Dmobile.getText().toString();
        String date = tvDAte.getText().toString();
        String city = Dcity.getText().toString();
        user.SetDialogData(name, email, mob, city, date);

        DName.setText("");
        DEmail.setText("");
        Dmobile.setText("");
        tvDAte.setText("");
        Dcity.setText("");
        mBottomSheetDialog.setContentView(dialogv);
        mBottomSheetDialog.dismiss();


    }
}
