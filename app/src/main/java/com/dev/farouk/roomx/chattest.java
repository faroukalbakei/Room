package com.dev.farouk.roomx;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dev.farouk.roomx.model.Userf;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import timber.log.Timber;

public class chattest extends Fragment {



    ProgressBar mProgressBarForUsers;
    RecyclerView mUsersRecyclerView;

    private String mCurrentUserUid;
    private List<String> mUsersKeyList;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mUserRefDatabase;
    private ChildEventListener mChildEventListener;
    private UsersChatAdapter mUsersChatAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.activity_chattest, container, false);

       // setContentView(R.layout.activity_chattest);
        mProgressBarForUsers = (ProgressBar) rootView.findViewById(R.id.progress_bar_users);
        mUsersRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_users);
        bindButterKnife();
        setAuthInstance();
        setUsersDatabase();
        setUserRecyclerView();
        setUsersKeyList();
        setAuthListener();
        return rootView;
    }

    private void bindButterKnife() {
        ButterKnife.bind(getActivity());
    }

    private void setAuthInstance() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void setUsersDatabase() {
        mUserRefDatabase = FirebaseDatabase.getInstance().getReference().child("users");
    }
    private void setUserRecyclerView() {
        mUsersChatAdapter = new UsersChatAdapter(getActivity(), new ArrayList<Userf>());
        mUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUsersRecyclerView.setHasFixedSize(true);
        mUsersRecyclerView.setAdapter(mUsersChatAdapter);
    }

    private void setUsersKeyList() {
        mUsersKeyList = new ArrayList<String>();
    }

    private void setAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                hideProgressBarForUsers();
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    setUserData(user);
                    queryAllUsers();
                } else {
                    // User is signed out
                  //  goToLogin();
                }
            }
        };
    }

    private void setUserData(FirebaseUser user) {
        mCurrentUserUid = user.getUid();
    }

    private void queryAllUsers() {
        mChildEventListener = getChildEventListener();
        mUserRefDatabase.limitToFirst(50).addChildEventListener(mChildEventListener);
    }
/*
    private void goToLogin() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.
        startActivity(intent);
    }
*/
    @Override
    public void onStart() {
        super.onStart();
        showProgressBarForUsers();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        clearCurrentUsers();

        if (mChildEventListener != null) {
            mUserRefDatabase.removeEventListener(mChildEventListener);
        }

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    private void clearCurrentUsers() {
        mUsersChatAdapter.clear();
        mUsersKeyList.clear();
    }

    private void logout() {
        showProgressBarForUsers();
        setUserOffline();
        mAuth.signOut();
    }

    private void setUserOffline() {
        if(mAuth.getCurrentUser()!=null ) {
            String userId = mAuth.getCurrentUser().getUid();
            mUserRefDatabase.child(userId).child("connection").setValue(UsersChatAdapter.OFFLINE);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      //  if(item.getItemId()==R.id.action_logout){
       //     logout();
       //     return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    private void showProgressBarForUsers(){
        mProgressBarForUsers.setVisibility(View.VISIBLE);
    }

    private void hideProgressBarForUsers(){
        if(mProgressBarForUsers.getVisibility()==View.VISIBLE) {
            mProgressBarForUsers.setVisibility(View.GONE);
        }
    }

    private ChildEventListener getChildEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.exists()){

                    String userUid = dataSnapshot.getKey();

                    if(dataSnapshot.getKey().equals(mCurrentUserUid)){
                        Userf currentUser = dataSnapshot.getValue(Userf.class);
                        Timber.i("dataSnapshot %s" ,dataSnapshot.getValue());
                        mUsersChatAdapter.setCurrentUserInfo(userUid, currentUser.getEmail(), currentUser.getCreatedAt());
                    }else {
                        Userf recipient = dataSnapshot.getValue(Userf.class);
                        recipient.setRecipientId(userUid);
                        mUsersKeyList.add(userUid);
                        mUsersChatAdapter.refill(recipient);
                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    String userUid = dataSnapshot.getKey();
                    if(!userUid.equals(mCurrentUserUid)) {

                        Userf user = dataSnapshot.getValue(Userf.class);

                        int index = mUsersKeyList.indexOf(userUid);
                        if(index > -1) {
                            mUsersChatAdapter.changeUser(index, user);
                        }
                    }

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

}
