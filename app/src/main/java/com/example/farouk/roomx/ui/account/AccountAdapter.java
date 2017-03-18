package com.example.farouk.roomx.ui.account;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farouk.roomx.R;
import com.example.farouk.roomx.model.ExtrasItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by farouk on 1/30/17.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyViewHolderr> {

    private List<ExtrasItem> detelsList;
    Context context;

    public class MyViewHolderr extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView userpic;


        public MyViewHolderr(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_acounting_Features);
            userpic = (ImageView) view.findViewById(R.id.imv_acounting_icon);

        }
    }

    public AccountAdapter(List<ExtrasItem> detelsList,Context context) {
        this.detelsList = detelsList;
        this.context=context;
    }


    @Override
    public MyViewHolderr onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account, parent, false);


        return new MyViewHolderr(itemView);

    }

    @Override
    public void onBindViewHolder(MyViewHolderr holder, int position) {
        ExtrasItem rooom = detelsList.get(position);
        if(position==0){
            Picasso.with(context).load(rooom.getIconUrl()).placeholder(R.drawable.ic_profile).into(holder.userpic);
        }else holder.userpic.setImageResource((rooom.getIconed()));

        holder.name.setText(rooom.getTextd());
    }


    @Override
    public int getItemCount() {
        return detelsList.size();
    }

}
