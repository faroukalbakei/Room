package com.example.farouk.roomx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.UserinfoLogin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingnUp extends AppCompatActivity implements VolleyCallback {


    EditText Name;
    EditText Email;
    EditText Password;
    EditText Confirm;
    EditText Mobile;
    UserinfoLogin userinfoLogin;
    String name,email,password,mobile,confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singn_up);
        Name = (EditText) findViewById(R.id.et_Singnup_Name);
        Email = (EditText) findViewById(R.id.et_Singnup_Email);
        Password = (EditText) findViewById(R.id.et_Singnup_password);
        Confirm = (EditText) findViewById(R.id.et_Singnup_ConfirmPassword);
        Mobile = (EditText) findViewById(R.id.et_Singnup_mobile);
        Name.setText("ahmed");
        Email.setText("ahmed@ahmad.com");
        Password.setText("12345678");
        Confirm.setText("12345678");
        Mobile.setText("12345678");
    }


    public void Singe(View view) {

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

        name = Name.getText().toString();
        email = Email.getText().toString();
        password = Password.getText().toString();
        confirm = Confirm.getText().toString();
        mobile = Mobile.getText().toString();

        boolean a = isEmailValid(email);

        if (!a) {
            Toast.makeText(this, "email Valid: ", Toast.LENGTH_LONG).show();

        } else {

            if (name.matches("")) {

                Toast.makeText(this, "name : ", Toast.LENGTH_LONG).show();
            } else if (password.matches("")) {
                Toast.makeText(this, "password : ", Toast.LENGTH_LONG).show();


            } else if (confirm.matches("")) {

                Toast.makeText(this, "confirm : ", Toast.LENGTH_LONG).show();

            } else if (mobile.matches("")) {

                Toast.makeText(this, "mobile : ", Toast.LENGTH_LONG).show();

            } else {

                if (!password.equalsIgnoreCase(confirm)) {

                    Toast.makeText(this, "check passwored", Toast.LENGTH_LONG).show();

                } else {

                    Requests requests = new Requests();
                    requests.makeRegister(this,this, name, email, password, confirm, mobile);




                }


            }
        }
    }
    @Override
    public void onSuccess(Response response) {
        Requests requests = new Requests();
        if (response.getResult() == 1) {


            requests.makeLogin(new VolleyCallback(){
                @Override
                public void onSuccess(Response response){
                    int isValid = response.getResult();

                    if (isValid == 1) {

                        // Toast.makeText(this, "^_^", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), explore.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), response.getMessage() + "", Toast.LENGTH_LONG).show();

                    }
                }
            }, this, email, password);

        }
    }

}
