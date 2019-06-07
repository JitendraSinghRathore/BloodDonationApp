package com.example.jitu.blooddonationapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jitu.blooddonationapp.Models.UserBloodRequestItem;
import com.example.jitu.blooddonationapp.R;
import com.example.jitu.blooddonationapp.Utils.CONSTANTS_AND_URL;
import com.example.jitu.blooddonationapp.Utils.Preference;
import com.example.jitu.blooddonationapp.Utils.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SingleBloodRequestFragment extends Fragment {
    Button myrequest;
    ArrayList<UserBloodRequestItem> List;
    RecyclerView recyclerView;
    String userid;
    JSONObject jObject;
    SwipeRefreshLayout swipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_my__request, container, false);


        recyclerView = view.findViewById(R.id.recylcerView);
        swipeRefreshLayout = view.findViewById(R.id.swip);

        single_request();


        List = new ArrayList<>();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                single_request();
                Utility.hideProgressDialog();
            }
        });



        return view;


    }
       private void single_request() {
        Utility.showProgressDialog(getContext(),"Loading");
        String  useridn= (Preference.getInstance(getActivity())).getuser_id();
        String URL = CONSTANTS_AND_URL.Requests_Url+"viewSingleRequest/"+useridn;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                   new Response.Listener<String>() {
                   @Override
                    public void onResponse(String response) {
                    Utility.hideProgressDialog();
                    swipeRefreshLayout.setRefreshing(false);
                        try {
                          JSONObject jsonObject=new JSONObject(response);
                          if(jsonObject.getString("status").equalsIgnoreCase("200"))
                          {
                              {
                                  JSONArray jsonArray=jsonObject.getJSONArray("Data");

                                  for(int i=0;i<jsonArray.length();i++)
                                  {
                                      JSONObject jsonObject1=jsonArray.getJSONObject(i);
                                      UserBloodRequestItem cd=new UserBloodRequestItem();
                                      cd.setId(jsonObject1.optString("user_id"));
                                      cd.setUnits(jsonObject1.optString("units"));
                                      cd.setBlood_group(jsonObject1.optString("blood_group"));
                                      cd.setCity(jsonObject1.optString("country"));

                                      List.add(cd);


                                  }

                              }

                          }
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                            ProductsAdapter adapter = new ProductsAdapter(getActivity(), List);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Log.d("user_id",response);
                            Utility.hideProgressDialog();
                            swipeRefreshLayout.setRefreshing(false);
                            e.printStackTrace();
                            Utility.createAlertDialog(getContext(),"Exception: "+ e.getLocalizedMessage(),"Ok","");

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utility.hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        Utility.createAlertDialog(getContext(),"Error: " + error,"Ok","");
                    }
                });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {

        Context activity;
        ArrayList<UserBloodRequestItem> productList;

        public ProductsAdapter(FragmentActivity activity, ArrayList<UserBloodRequestItem> productList) {
            this.activity=activity;
            this.productList=productList;

        }
        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.my_request_design, null);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
            UserBloodRequestItem product = productList.get(position);
            holder.unit.setText(product.getUnits());
            holder.blood.setText(product.getBlood_group());
            holder.city.setText(product.getCity());
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder {

            TextView id,unit,blood,city;


            ProductViewHolder(View itemView) {
                super(itemView);

                unit=(TextView)itemView.findViewById(R.id.unit);
                blood=(TextView)itemView.findViewById(R.id.blood);
                city=(TextView)itemView.findViewById(R.id.city);
                id=(TextView)itemView.findViewById(R.id.user_id);
            }

        }
    }
}








