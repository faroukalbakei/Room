package com.example.farouk.roomx.ui.main;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.UserinfoLogin;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.ui.explore.ExploreFragment;
import com.example.farouk.roomx.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements VolleyCallback {


    EditText Name;
    EditText Email;
    EditText Password;
    EditText Confirm;
    EditText Mobile;
    UserinfoLogin userinfoLogin;
    String name, email, password, mobile, confirm;
    Button bt_Singnup_login;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singn_up);


/*        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);*/

// finally change the color
//        window.setStatusBarColor(this.getResources().getColor(R.color.grey_300));
        Name = (EditText) findViewById(R.id.et_Singnup_Name);
        Email = (EditText) findViewById(R.id.et_Singnup_Email);
        Password = (EditText) findViewById(R.id.et_Singnup_password);
        Confirm = (EditText) findViewById(R.id.et_Singnup_ConfirmPassword);
        Mobile = (EditText) findViewById(R.id.et_Singnup_mobile);
        bt_Singnup_login = (Button) findViewById(R.id.bt_Singnup_login);
        name = Name.getText().toString();
        email = Email.getText().toString();
        password = Password.getText().toString();
        confirm = Confirm.getText().toString();
        mobile = Mobile.getText().toString();
        bt_Singnup_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
/*                if(name.isEmpty()||email.isEmpty()||password.isEmpty()||confirm.isEmpty()||mobile.isEmpty()){
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_fill_data), Toast.LENGTH_LONG).show();
                }else {
                    register();
                }*/
            }
        });
    }


    public void register() {
        if(Utils.isInternetAvailable(this)){
            Requests requests = new Requests(this);
            requests.makeRegister(this, this, Name.getText().toString(), Email.getText().toString()
                    , Password.getText().toString(), Confirm.getText().toString()
                    , Mobile.getText().toString());
        }else
            Toast.makeText(this,"لا يوجد انترنت", Toast.LENGTH_LONG);

    }


    @Override
    public void onSuccess(Response response) {
        Requests requests = new Requests(this);
        if (response.getResult() == 1) {
            requests.makeLogin(new VolleyCallback() {
                @Override
                public void onSuccess(Response response) {

                    if (response.getResult() == 1) {
                        Intent intent = new Intent(getApplicationContext(), IconTextTabsActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        Utils.snakebar(response.getMessage(),bt_Singnup_login.getRootView());// show multiple line
//                        Snackbar.make(textView.getRootView(), response.getMessage() + "", Snackbar.LENGTH_LONG).show();
                    }
                }
            }, this, Email.getText().toString(), Password.getText().toString());

        }

        if (!response.isValid()) {
            Utils.snakebar(response.getMessage(),bt_Singnup_login.getRootView());// show multiple line
        }
    }


    public void loginback(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();

    }

}
