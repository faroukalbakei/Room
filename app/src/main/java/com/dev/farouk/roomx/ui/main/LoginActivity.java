package com.dev.farouk.roomx.ui.main;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.farouk.roomx.R;
import com.dev.farouk.roomx.model.Response;
import com.dev.farouk.roomx.service.Requests;
import com.dev.farouk.roomx.service.VolleyCallback;
import com.dev.farouk.roomx.util.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

public class LoginActivity extends AppCompatActivity implements VolleyCallback {

    EditText Email;
    EditText Password;
    private Snackbar snackbar;

    private FirebaseAuth mAuth;
    private FirebaseDatabase productsDatabase;
    private DatabaseReference productsDatabaseReference;
    private ChildEventListener childEventListener;
    String isEnabled;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.changeLang(getBaseContext(), "ar");
                /*for disk persistence*/
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                /*get instance of our productsDatabase*/
        productsDatabase = FirebaseDatabase.getInstance();
        /*get a reference to the products node location*/
        productsDatabaseReference = productsDatabase.getReference("enable");

        /*add the Value event listener to update the data in real-time
        - displays the productsDatabase items in a list*/
     //   addValueEventListener(productsDatabaseReference);
        productsDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue().toString());  //prints "Do you have data? You'll love Firebase."
                boolean isEnabled = (boolean) snapshot.getValue();

                if(isEnabled){
                    setContentView(R.layout.activity_main);

                    Email = (EditText) findViewById(R.id.et_login_email);
                    Password = (EditText) findViewById(R.id.et_login_password);

                    Email.setText("farouk.h@hotmail.com");
                    Password.setText("12345678");
                }else if(!isEnabled){
                    setContentView(R.layout.activity_not_enabled);

                }
                Timber.i("isEnabled %s", isEnabled);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        Utils.changeLang(getBaseContext(), "ar");
    }

    public void SingnUP(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public void login(View view) {
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        if (Utils.isInternetAvailable(getBaseContext())) {
            Requests requests = new Requests(this);
            requests.makeLogin(this, this, email, password);
        } else
            Toast.makeText(getApplicationContext(), "لا يوجد انترنت", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(Response response) {
        int isValid = response.getResult();
        Log.d("isValid", String.valueOf(response.getResult()));

        if (isValid == 1) {
            Intent intent = new Intent(this, IconTextTabsActivity.class);
            startActivity(intent);
        } else {
            snackbar = Snackbar
                    .make(Email.getRootView(), response.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                        }
                    });

            snackbar.show();
        }
    }
}
