package com.yildiz.mezunapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SecondAdapter extends RecyclerView.Adapter<SecondAdapter.MyViewHolder> {

    Context context;
    ArrayList<UserItem> userItemArrayList;
    private AdapterView.OnItemClickListener mItem;
    public SecondAdapter(Context context,ArrayList<UserItem> userItems){

        this.context = context;
        this.userItemArrayList = userItems;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.feed_item,parent,false);
        return new SecondAdapter.MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        UserItem userItem = userItemArrayList.get(position);
        holder.nameSurname.setText(userItem.nameSurname);
        holder.email.setText(userItem.eMail);

    }

    @Override
    public int getItemCount() {
        return userItemArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nameSurname,email;


        public MyViewHolder(View itemView){
            super(itemView);
            nameSurname = itemView.findViewById(R.id.itemNameSurnameText);
            email = itemView.findViewById(R.id.userItemEmailText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context.getApplicationContext(),String.valueOf(getAdapterPosition()),Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}


