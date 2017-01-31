package com.example.farouk.roomx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {



    EditText Email;
    EditText Password;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = (EditText) findViewById(R.id.et_login_email);
        Password = (EditText) findViewById(R.id.et_login_password);
    }


    public void SingnUP(View view){

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



    public void login(View view){
        sendData();

    }



    private void sendData(){

        String email = Email.getText().toString();
        String password = Password.getText().toString();
        boolean a = isEmailValid(email);


        if (!a){
            //email is in Valid

        } else if(password.matches("")){
            //no password insert


        }else {

            user = new User();
            user.setlogin(email, password);
            boolean x = user.Verification();
            Toast.makeText(this,"^_^",Toast.LENGTH_LONG).show();



            Intent intent = new Intent(this, Acounting.class);
            startActivity(intent);
            finish();
/*
            if (!x){

                Email.setText("");
                Email.setText("");



                //Toast.makeText(this,"done",Toast.LENGTH_LONG).show();

            }else {

                Toast.makeText(this,"invaled",Toast.LENGTH_LONG).show();

            }
*/

        }


    }
}
