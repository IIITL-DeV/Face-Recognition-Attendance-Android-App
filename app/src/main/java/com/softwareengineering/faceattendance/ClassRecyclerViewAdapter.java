package com.softwareengineering.faceattendance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<user> userArrayList;

    public ClassRecyclerViewAdapter(Context context, ArrayList<user> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(context)
                .inflate(R.layout.list_item_subject,parent,false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ClassRecyclerViewAdapter.ViewHolder holder, int position) {

        user ouruser = userArrayList.get(position);

        holder.txtClassName.setText(ouruser.subject);
        holder.txtPercentage.setText(ouruser.percentage);

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        private TextView txtClassName,txtPercentage;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            cardView=itemView.findViewById(R.id.ParentCard);
            txtClassName = itemView.findViewById(R.id.class_name);
            txtPercentage = itemView.findViewById(R.id.percentage);





            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context,"open the new Activity",Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

}

