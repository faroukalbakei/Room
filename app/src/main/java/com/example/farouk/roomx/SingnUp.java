package com.example.farouk.roomx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingnUp extends AppCompatActivity {



    EditText Name ;
    EditText Email;
    EditText Password;
    EditText Confirm;
    EditText Mobile;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singn_up);
        Name = (EditText) findViewById(R.id.et_Singnup_Name);
        Email = (EditText) findViewById(R.id.et_Singnup_Email);
        Password = (EditText) findViewById(R.id.et_Singnup_password);
        Confirm = (EditText) findViewById(R.id.et_Singnup_ConfirmPassword);
        Mobile = (EditText) findViewById(R.id.et_Singnup_mobile);
    }




    public void  Singe(View view){

        sendData();
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



    public void sendData() {

        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String confirm = Confirm.getText().toString();
        String mobile = Mobile.getText().toString();

        boolean a = isEmailValid(email);

        if (!a) {
            Toast.makeText(this, "email Valid: ", Toast.LENGTH_LONG).show();

        } else {

            if (name.matches("")) {

                Toast.makeText(this, "name : ", Toast.LENGTH_LONG).show();
            }  else if (password.matches("")) {
                Toast.makeText(this, "password : ", Toast.LENGTH_LONG).show();


            } else if (confirm.matches("")) {

                Toast.makeText(this, "confirm : ", Toast.LENGTH_LONG).show();

            } else if (mobile.matches("")) {

                Toast.makeText(this, "mobile : ", Toast.LENGTH_LONG).show();

            } else {

                if (!password.equalsIgnoreCase(confirm)) {

                    Toast.makeText(this, "check passwored", Toast.LENGTH_LONG).show();

                } else {




                    user = new User();

                    user.setdata(name, email, password, mobile);
                    Name.setText("");
                    Email.setText("");
                    Password.setText("");
                    Confirm.setText("");
                    Mobile.setText("");
                    Toast.makeText(this, "done", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();


                }


            }
        }}

}
