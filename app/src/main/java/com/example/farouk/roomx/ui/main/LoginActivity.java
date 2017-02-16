package com.example.farouk.roomx.ui.main;



import android.content.Intent;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements VolleyCallback {

    EditText Email;
    EditText Password;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.changeLang(getBaseContext(),"ar");
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(this.getResources().getColor(R.color.grey_300));

        Email = (EditText) findViewById(R.id.et_login_email);
        Password = (EditText) findViewById(R.id.et_login_password);
        Email.setText("farouk.h@hotmail.com");
        Password.setText("12345678");
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
//Toast.makeText(this,"login",Toast.LENGTH_LONG).show();
       // Intent intent = new Intent(this, BeHostt.class);
      // startActivity(intent);
       sendData();

    }


    private void sendData() {

        String email = Email.getText().toString();
        String password = Password.getText().toString();
        boolean a = isEmailValid(email);


        if (!a) {
            //email is in Valid

        } else if (password.matches("")) {
            //no password insert


        } else {

            if(Utils.isInternetAvailable()){
                Requests requests = new Requests(this);
                requests.makeLogin(this,this, email, password);
            }else
                Toast.makeText(getApplicationContext(),"لا يوجد انترنت", Toast.LENGTH_LONG);

        }


    }

    @Override
    public void onSuccess(Response response) {
        int isValid = response.getResult();
        Log.d("isValid", String.valueOf(response.getResult()));

        if (isValid==1) {

            // Toast.makeText(this, "^_^", Toast.LENGTH_LONG).show();
         Intent intent = new Intent(this, IconTextTabsActivity.class);
          startActivity(intent);



        } else {

            Toast.makeText(this, response.getOnError()+"", Toast.LENGTH_LONG).show();

        }
    }
}
