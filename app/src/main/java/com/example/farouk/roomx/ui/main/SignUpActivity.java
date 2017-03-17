package com.example.farouk.roomx.ui.main;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farouk.roomx.FireChatHelper.ChatHelper;
import com.example.farouk.roomx.R;
import com.example.farouk.roomx.UsersChatAdapter;
import com.example.farouk.roomx.model.Response;
import com.example.farouk.roomx.model.Userf;
import com.example.farouk.roomx.model.UserinfoLogin;
import com.example.farouk.roomx.service.Requests;
import com.example.farouk.roomx.service.VolleyCallback;
import com.example.farouk.roomx.util.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;


public class SignUpActivity extends AppCompatActivity implements VolleyCallback {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

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


        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
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

            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                          //  Log.e(TAG, "performFirebaseRegistration:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {

                            } else {
                                // Add the user to users table.
                            /*DatabaseReference database= FirebaseDatabase.getInstance().getReference();
                            User user = new User(task.getResult().getUser().getUid(), email);
                            database.child("users").push().setValue(user);*/
                                onAuthSuccess(task.getResult().getUser());

                            }
                        }
                    });
        }else
             Toast.makeText(this, "لا يوجد انترنت", Toast.LENGTH_LONG).show();

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
                       // finish();

                    } else {
                        Utils.snakebar(response.getMessage(),bt_Singnup_login);// show multiple line
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
      //  finish();

    }
    private void onAuthSuccess(FirebaseUser user) {
        createNewUser(user.getUid());

    }


    private void createNewUser(String userId){
        Userf user = new Userf(getUserDisplayName(),
                getUserEmail(),
                UsersChatAdapter.ONLINE
                ,
                ChatHelper.generateRandomAvatarForUser(),
                new Date().getTime()
        );
        this.mDatabase.child("users").child(userId).setValue(user);
    }



    private String getUserDisplayName() {
        return Name.getText().toString().trim();
    }
    private String getUserEmail() {
        return Email.getText().toString().trim();
    }

    private String getUserPassword() {
        return password.toString().trim();
    }

}
