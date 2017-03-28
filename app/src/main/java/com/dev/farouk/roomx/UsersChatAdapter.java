package com.dev.farouk.roomx;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.dev.farouk.roomx.FireChatHelper.ChatHelper;
import com.dev.farouk.roomx.model.Userf;
import com.dev.farouk.roomx.FireChatHelper.ExtraIntent;
import com.dev.farouk.roomx.util.Utils;

import java.util.List;


public class UsersChatAdapter extends RecyclerView.Adapter<UsersChatAdapter.ViewHolderUsers> {

    public static final String ONLINE = "online";
    public static final String OFFLINE = "offline";
    private List<Userf> mUsers;
    private Context mContext;
    private String mCurrentUserEmail;
    private Long mCurrentUserCreatedAt;
    private String mCurrentUserId;

    public UsersChatAdapter(Context context, List<Userf> fireChatUsers) {
        mUsers = fireChatUsers;
        mContext = context;
    }

    @Override
    public ViewHolderUsers onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderUsers(mContext, LayoutInflater.from(parent.getContext()).inflate(R.layout.user_profile, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolderUsers holder, int position) {

        Userf fireChatUser = mUsers.get(position);

        // Set avatar
        int userAvatarId= ChatHelper.getDrawableAvatarId(fireChatUser.getAvatarId());
        Drawable avatarDrawable = ContextCompat.getDrawable(mContext,userAvatarId);
        holder.getUserAvatar().setImageDrawable(avatarDrawable);

        // Set display name
        holder.getUserDisplayName().setText(fireChatUser.getDisplayName());

        // Set presence status
        holder.getStatusConnection().setText(fireChatUser.getConnection());

        // Set presence text color
        if(fireChatUser.getConnection().equals(ONLINE)) {
            // Green color
            holder.getStatusConnection().setTextColor(Color.parseColor("#4CAF50"));
        }else {
            // Red color
            holder.getStatusConnection().setTextColor(Color.parseColor("#FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void refill(Userf users) {
        mUsers.add(users);
        notifyDataSetChanged();
    }

    public void changeUser(int index, Userf user) {
        mUsers.set(index,user);
        notifyDataSetChanged();
    }

    public void setCurrentUserInfo(String userUid, String email, long createdAt) {
        mCurrentUserId = userUid;
        mCurrentUserEmail = email;
        mCurrentUserCreatedAt = createdAt;
    }

    public void clear() {
        mUsers.clear();
    }


    /* ViewHolder for RecyclerView */
    public class ViewHolderUsers extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mUserAvatar;
        private TextView mUserDisplayName;
        private TextView mStatusConnection;
        private Context mContextViewHolder;

        public ViewHolderUsers(Context context, View itemView) {
            super(itemView);
            mUserAvatar = (ImageView)itemView.findViewById(R.id.img_avatar);
            mUserDisplayName = (TextView)itemView.findViewById(R.id.text_view_display_name);
            mStatusConnection = (TextView)itemView.findViewById(R.id.text_view_connection_status);
            mContextViewHolder = context;

            itemView.setOnClickListener(this);
        }

        public ImageView getUserAvatar() {
            return mUserAvatar;
        }

        public TextView getUserDisplayName() {
            return mUserDisplayName;
        }
        public TextView getStatusConnection() {
            return mStatusConnection;
        }


        @Override
        public void onClick(View view) {

            Userf user = mUsers.get(getLayoutPosition());

            if(user!=null){
                String chatRef = user.createUniqueChatRef(mCurrentUserCreatedAt,mCurrentUserEmail);
                if(chatRef!=null){
                    Log.i("mCurrentUserCreatedAt", String.valueOf(mCurrentUserCreatedAt));
                    Log.i("mCurrentUserEmail",mCurrentUserEmail);
                    Intent chatIntent = new Intent(mContextViewHolder, ChatActivity.class);
                    chatIntent.putExtra(ExtraIntent.EXTRA_CURRENT_USER_ID, mCurrentUserId);
                    chatIntent.putExtra(ExtraIntent.EXTRA_RECIPIENT_ID, user.getRecipientId());
                    chatIntent.putExtra(ExtraIntent.EXTRA_CHAT_REF, chatRef);

                    // Start new activity
                    mContextViewHolder.startActivity(chatIntent);
                }else{
                    Log.i("mCurrentUserCreatedAt", "null");
                    Utils.snakebar(mContext.getResources().getString(R.string.general_error),mStatusConnection);
                }

            }else{
                Log.i("Userf", "null");
                Utils.snakebar(mContext.getResources().getString(R.string.general_error),mStatusConnection);
            }


        }
    }

}
