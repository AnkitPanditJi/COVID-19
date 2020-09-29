package com.blavasgps.covidcheckup.commons.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.blavasgps.covidcheckup.R;
import com.blavasgps.covidcheckup.model.FirstPageData;

import java.util.ArrayList;

public class FirstPageAdapter extends RecyclerView.Adapter<FirstPageAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<FirstPageData> driverDataArrayList;
    private onDriverClickListener driverClickListener;
    Activity activity;

    public void updateList(ArrayList<FirstPageData> list)
    {
        driverDataArrayList = list;
        notifyDataSetChanged();
    }

    public FirstPageAdapter(Activity ctx, ArrayList<FirstPageData> driverDataArrayList, onDriverClickListener driverClickListener){
        inflater = LayoutInflater.from(ctx);
        this.activity = ctx;
        this.driverDataArrayList = driverDataArrayList;
        this.driverClickListener = driverClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.first_page_item_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FirstPageData driverData = driverDataArrayList.get(position);

        holder.MainTitle.setText(driverData.getCountry_name());
        holder.HelplineNumber.setText(driverData.getContact_number());
        holder.EmailId.setText(driverData.getEmail_id());
        holder.TollFreeNumber.setText(driverData.getToll_number());
        holder.SubTitle.setText(driverData.getCall_number_one());
        holder.helpLineDetails.setText(driverData.getDepartment_type());

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

        TextView MainTitle, SubTitle, HelplineNumber, TollFreeNumber, helpLineDetails, EmailId;
        CardView mainCard;

        public MyViewHolder(View itemView) {
            super(itemView);
            mainCard = (CardView) itemView.findViewById(R.id.mainCard);
            MainTitle = (TextView)itemView.findViewById(R.id.tvTitleName);
            SubTitle = (TextView)itemView.findViewById(R.id.sub_title);
            HelplineNumber = (TextView)itemView.findViewById(R.id.helpline_number);
            TollFreeNumber = (TextView)itemView.findViewById(R.id.toll_free_number);
            EmailId = (TextView)itemView.findViewById(R.id.email_id);
            helpLineDetails = (TextView)itemView.findViewById(R.id.helpline_details);

        }

    }

    public interface onDriverClickListener
    {
        void onDriverClick(int pos, View v);
    }
}