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
import java.util.Set;

public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private int count;
    private String[] subjects;

    public ClassRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
        Utils.setDummyData();
        this.subjects = Utils.getAllSubjectNames();

        for(String i : subjects){
            System.out.println(i);
        }


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_subject,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ClassRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtClassName.setText(subjects[position]);
        if(Utils.getSubject2AttendanceMap().containsKey(subjects[position])) {
            holder.txtPercentage.setText(String.valueOf(Utils.getSubject2AttendanceMap().get(subjects[position])));
        }

        System.out.println("onbindviewhilder called for : "+String.valueOf(position));


    }

    @Override
    public int getItemCount() {
        return subjects.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private TextView txtClassName,txtPercentage;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            parent=itemView.findViewById(R.id.ParentCard);
            txtClassName = itemView.findViewById(R.id.class_name);
            txtPercentage = itemView.findViewById(R.id.percentage);





            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(mContext,"open the new Activity",Toast.LENGTH_SHORT).show();


                }
            });
        }
    }

}

