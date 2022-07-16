package com.emergence.study_app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emergence.study_app.Adapter.Course_Adapter;
import com.emergence.study_app.newTech.app.PreferencesManager;
import com.emergence.study_app.newTech.constants.BaseFragment;
import com.emergence.study_app.newTech.retrofit.API_Config;
import com.emergence.study_app.newTech.retrofit.ApiServices;
import com.emergence.study_app.newTech.retrofit.model.My_Order.DataItem;
import com.emergence.study_app.newTech.retrofit.model.My_Order.ResponseMyOrder;
import com.emergence.study_app.newTech.utils.Utils;
import com.example.study_app.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Course_Fragment extends BaseFragment {
    RecyclerView recyclerView;
    LinearLayout l_nodata;
    PreferencesManager preferencesManager;
    List<DataItem> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_course_, container, false);
        preferencesManager=new PreferencesManager(getActivity());
        String str_id=preferencesManager.getUserId();
        l_nodata=view.findViewById(R.id.no_data);
        recyclerView=view.findViewById(R.id.recycler_course);
        getMyOrder(str_id);

        return view;
    }

    @Override
    public void onViewCreatedStuff(View view, @Nullable Bundle savedInstanceState) {

    }

    private void getMyOrder(String str_id) {
        showLoading();
        ApiServices apiServices= API_Config.getApiClient_ByPost();
        Call<ResponseMyOrder> myOrderCall=apiServices.getMyOrders("MyOrders",str_id,"Package");
        myOrderCall.enqueue(new Callback<ResponseMyOrder>() {
            @Override
            public void onResponse(Call<ResponseMyOrder> call, Response<ResponseMyOrder> response) {
                hideLoading();
                Utils.CheckUserSession(getActivity(),preferencesManager.getUserId(),preferencesManager.getSession_id());

                if (response.body().getRes().equalsIgnoreCase("success")){
                    for (int i=0;i<response.body().getData().size();i++){
                        if (response.body().getData().get(i).getExpiry_date().equalsIgnoreCase("")){
                            list.add(response.body().getData().get(i));
                            Course_Adapter course_adapter=new Course_Adapter(list,getActivity());
                            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setAdapter(course_adapter);
                            recyclerView.setLayoutManager(gridLayoutManager);
                        }else {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date strDate = null;
                            Date strDate1=null;
                            try {
                                strDate = sdf.parse(response.body().getData().get(i).getExpiry_date());
                                strDate1 = sdf.parse(Utils.getDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (!strDate1.after(strDate)) {
                                list.add(response.body().getData().get(i));
                                Course_Adapter course_adapter=new Course_Adapter(list,getActivity());
                                GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(course_adapter);
                                recyclerView.setLayoutManager(gridLayoutManager);
                            }else {
//                            Course_Adapter course_adapter=new Course_Adapter(response.body().getData(),getActivity());
//                            GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
//                            recyclerView.setHasFixedSize(true);
//                            recyclerView.setAdapter(course_adapter);
//                            recyclerView.setLayoutManager(gridLayoutManager);
                            }
                        }



                    }



                }else {
                    recyclerView.setVisibility(View.GONE);
                    l_nodata.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<ResponseMyOrder> call, Throwable t) {
                hideLoading();

            }
        });

    }
}