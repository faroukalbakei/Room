package com.example.farouk.roomx;

import android.content.Intent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements VolleyCallback {

    EditText Email;
    EditText Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = (EditText) findViewById(R.id.et_login_email);
        Password = (EditText) findViewById(R.id.et_login_password);
        Email.setText("farouk.h@hotmail.com");
        Password.setText("12345678");
    }


    public void SingnUP(View view) {

        Intent intent = new Intent(this, SingnUp.class);
        startActivity(intent);
        finish();
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
            Requests requests = new Requests();
            requests.makeLogin(this,this, email, password);
        }


    }

    @Override
    public void onSuccess(Response response) {
        int isValid = response.getResult();
        Log.d("isValid", String.valueOf(response.getResult()));

        if (isValid==1) {

            // Toast.makeText(this, "^_^", Toast.LENGTH_LONG).show();
           // Intent intent = new Intent(this, Acounting.class);
          //  startActivity(intent);

            FragmentManager fragmentManager = getSupportFragmentManager();
            Acounting fragment = new Acounting();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.activity_acounting,fragment);
            fragmentTransaction.commit();
           // finish();






        } else {

            Toast.makeText(this, response.getOnError()+"", Toast.LENGTH_LONG).show();

        }
    }
}
