package com.blavasgps.covidcheckup.commons.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blavasgps.covidcheckup.R;
import com.blavasgps.covidcheckup.model.SecondPageData;

import java.util.ArrayList;

public class SecondPageAdapter extends RecyclerView.Adapter<SecondPageAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<SecondPageData> driverDataArrayList;
    private onDriverClickListener driverClickListener;
    Activity activity;

    public void updateList(ArrayList<SecondPageData> list)
    {
        driverDataArrayList = list;
        notifyDataSetChanged();
    }

    public SecondPageAdapter(Activity ctx, ArrayList<SecondPageData> driverDataArrayList, onDriverClickListener driverClickListener){
        inflater = LayoutInflater.from(ctx);
        this.activity = ctx;
        this.driverDataArrayList = driverDataArrayList;
        this.driverClickListener = driverClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.second_page_item_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        SecondPageData driverData = driverDataArrayList.get(position);

        holder.Name.setText(driverData.getName());
        holder.details.setText(driverData.getDetails());
        holder.department.setText(driverData.getDepartment());
        holder.websiteURL.setText(driverData.getWebsite_url());

        holder.mainCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driverClickListener.onDriverClick(position, view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return driverDataArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name, details, department, websiteURL;
        CardView mainCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            mainCard = (CardView) itemView.findViewById(R.id.mainCard);
            Name = (TextView) itemView.findViewById(R.id.tvName);
            details = (TextView) itemView.findViewById(R.id.details);
            department = (TextView) itemView.findViewById(R.id.department);
            websiteURL = (TextView) itemView.findViewById(R.id.website_url);

        }

    }

    public interface onDriverClickListener
    {
        void onDriverClick(int pos, View v);
    }
}