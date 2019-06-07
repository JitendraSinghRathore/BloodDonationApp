package com.example.jitu.blooddonationapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jitu.blooddonationapp.Models.AllRequestItems;
import com.example.jitu.blooddonationapp.Activity.BloodRequestActivity;
import com.example.jitu.blooddonationapp.R;
import com.example.jitu.blooddonationapp.Utils.CONSTANTS_AND_URL;
import com.example.jitu.blooddonationapp.Utils.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AllBloodRequestFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<AllRequestItems> data=new ArrayList<>();
    AllRequestAdapter allRequestAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.request_recyler);
        swipeRefreshLayout=(SwipeRefreshLayout) view.findViewById(R.id.swap);
        Fatch_AllRequest();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Fatch_AllRequest();
                Utility.hideProgressDialog();
            }
        });
        return view;
        }
    public  class AllRequestAdapter extends RecyclerView.Adapter<AllRequestAdapter.ViewHolder>
    {
        private static final String TAG = "RecyclerViewAdapter";
        Context context;
        ArrayList<AllRequestItems> data;
        public AllRequestAdapter(ArrayList<AllRequestItems> data) {
            this.data=data;
            }
            @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context=parent.getContext();
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.request_design,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            final AllRequestItems beanAllRequest=data.get(position);
            holder.id.setText(beanAllRequest.getRequest_id(position));
            holder.name.setText(beanAllRequest.getName(position));
            holder.unit.setText(beanAllRequest.getUnits(position));
            holder.blood.setText(beanAllRequest.getBlood_group(position));
            holder.mobile.setText(beanAllRequest.getMobile(position));
            holder.city.setText(beanAllRequest.getCity());

            Picasso.get()
                    .load(CONSTANTS_AND_URL.IMAGE_BASE_URL + beanAllRequest.getImage(position))
                    .resize(1000, 600).centerInside()
                    .into(holder.imageView);


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, beanAllRequest.getRequest_id(position), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, beanAllRequest.getName(position), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, beanAllRequest.getBlood_group(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, BloodRequestActivity.class);
                    intent.putExtra("request_id", beanAllRequest.getRequest_id(position));
                    intent.putExtra("name", beanAllRequest.getName(position));
                    intent.putExtra("units", beanAllRequest.getUnits(position));
                    intent.putExtra("blood_group", beanAllRequest.getBlood_group(position));
                    intent.putExtra("mobile", beanAllRequest.getMobile(position));
                    context.startActivity(intent);
                }
            });

            holder.linearLayoutcallrequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, beanAllRequest.getMobile(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, BloodRequestActivity.class);
                    intent.putExtra("mobile", beanAllRequest.getMobile(position));
                    String phone = beanAllRequest.getMobile(position);
                    intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    startActivity(intent);
                    }
            });
            }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView id,name,unit,blood,city,mobile;
            LinearLayout linearLayoutcallrequest,linearLayoutsharerequest;
            CardView cardView;
            ImageView imageView;
            ViewHolder(View itemView) {
                super(itemView);
                id=(TextView)itemView.findViewById(R.id.idrequest);
                name=(TextView)itemView.findViewById(R.id.name);
                unit=(TextView)itemView.findViewById(R.id.units);
                blood=(TextView)itemView.findViewById(R.id.blood);
                mobile=(TextView)itemView.findViewById(R.id.mobile);
                city=(TextView)itemView.findViewById(R.id.city);
                imageView=(ImageView) itemView.findViewById(R.id.image);
                linearLayoutcallrequest=(LinearLayout)itemView.findViewById(R.id.linercallrequest);
                linearLayoutsharerequest=(LinearLayout)itemView.findViewById(R.id.linersharerequest);
                cardView=itemView.findViewById(R.id.requestcard);
            }
        }
    }
    public  void Fatch_AllRequest()
    {
        data=new ArrayList<>();
        StringRequest request= new StringRequest(com.android.volley.Request.Method.GET, CONSTANTS_AND_URL.Requests_Url+"getAllRequest",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Utility.hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d("200",response);
                        try {
                            JSONObject json=new JSONObject(response);
                            if (json.getString("status").equalsIgnoreCase("200"))
                            {
                                JSONArray jsonArray=json.getJSONArray("Data");
                                for(int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    AllRequestItems cd=new AllRequestItems();
                                    cd.setRequest_id(jsonObject.optString("request_id"));
                                    cd.setName(jsonObject.optString("name"));
                                    cd.setBlood_group(jsonObject.optString("blood_group"));
                                    cd.setMobile(jsonObject.optString("mobile"));
                                    cd.setEmail(jsonObject.optString("email"));
                                    cd.setCountry(jsonObject.optString("country"));
                                    cd.setState(jsonObject.optString("state"));
                                    cd.setCity(jsonObject.optString("city"));
                                    cd.setLatitude(jsonObject.optString("latitude"));
                                    cd.setLongitude(jsonObject.optString("longitude"));
                                    cd.setUnits(jsonObject.optString("units"));
                                    cd.setStreet(jsonObject.optString("street"));
                                    cd.setImage(jsonObject.optString("image"));
                                    data.add(cd);
                                }

                            }
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setNestedScrollingEnabled(false);
                            RecyclerView.LayoutManager manager= new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            allRequestAdapter  =new AllRequestAdapter(data);
                            recyclerView.setAdapter(allRequestAdapter);


                        }catch (JSONException e){
                            Utility.hideProgressDialog();
                            swipeRefreshLayout.setRefreshing(false);
                            Utility.createAlertDialog(getContext(),"Exception: "+ e.getLocalizedMessage(),"Ok","");


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Utility.hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        Utility.createAlertDialog(getContext(),"Error: " + error,"Ok","");
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }



}

