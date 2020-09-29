package com.blavasgps.covidcheckup.Design.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blavasgps.covidcheckup.R;
import com.blavasgps.covidcheckup.commons.adapter.SecondPageAdapter;
import com.blavasgps.covidcheckup.commons.server.ServerConfig;
import com.blavasgps.covidcheckup.commons.server.ServerConnector;
import com.blavasgps.covidcheckup.model.FirstPageData;
import com.blavasgps.covidcheckup.model.SecondPageData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class SecondFragment extends Fragment implements SecondPageAdapter.onDriverClickListener{
    RecyclerView recyclerView;
    ArrayList<SecondPageData> list;
    SecondPageAdapter adapter;
    public static ProgressDialog progressDialog;

    public SecondFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        init(rootView);
        doFirstAPI();
        return rootView;
    }

    public void init(View rootView)
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Login, Please wait...");

        recyclerView = rootView.findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        adapter = new SecondPageAdapter(getActivity(), list, this);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void doFirstAPI() {

        String urlParameters = "";
        try {
            progressDialog.show();
            urlParameters = "";
            ServerConnector connector = new ServerConnector(urlParameters);
            connector.setIsget(true);
            //   connector.setHeaderTypeAppJson(true);
            connector.setDataDownloadListner(response -> {
                Log.e("Response", "Response : " + response);
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("success"))
                    {
                        if(jsonObject.getBoolean("success") == true)
                        {

                            if(jsonObject.has("data"))
                            {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                if(jsonArray != null && jsonArray.length() > 0)
                                {
                                    list = new ArrayList<>();
                                    for (int i=0; i < jsonArray.length(); i++)
                                    {
                                        JSONObject driverObj = jsonArray.getJSONObject(i);
                                        SecondPageData driverData = new SecondPageData().toObject(driverObj);
                                        list.add(driverData);
                                    }
                                    adapter.updateList(list);

                                }
                            }

                        }else
                        {
                            Toast.makeText(getContext(), "No Record Found", Toast.LENGTH_LONG).show();

                        }
                    }else
                    {
                        Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_LONG).show();

                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    progressDialog.dismiss();
                }
            });
            connector.execute(ServerConfig.WORLD_WIDE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDriverClick(int pos, View v) {
        switch (v.getId()) {
            case R.id.mainCard:
                SecondPageData data = list.get(pos);
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(data.getWebsite_url()));
                startActivity(browserIntent);

                break;

        }
    }
}
