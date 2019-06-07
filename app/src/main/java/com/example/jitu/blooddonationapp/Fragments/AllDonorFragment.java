package com.example.jitu.blooddonationapp.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jitu.blooddonationapp.Activity.SingleDonorActivity;
import com.example.jitu.blooddonationapp.Models.AllDonerItems;
import com.example.jitu.blooddonationapp.R;
import com.example.jitu.blooddonationapp.Utils.Utility;
import com.example.jitu.blooddonationapp.Utils.CONSTANTS_AND_URL;
import com.google.android.gms.maps.MapView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AllDonorFragment extends Fragment {
    ArrayList<AllDonerItems> alldoner_data = new ArrayList<>();
    RecyclerView recyclerView;
    private ProgressDialog mDialog;
    MapView mapView;
    DonorsAdapter donorsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    Button button;
    private Intent intent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all__donors, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recy);





        All_Doners();

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swap);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                All_Doners();
                Utility.hideProgressDialog();
            }
        });
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }



    public Intent getIntent() {
        return intent;
    }


    public void All_Doners() {
        alldoner_data = new ArrayList<>();
        Utility.showProgressDialog(getContext(), "Loading");
        StringRequest request = new StringRequest(Request.Method.GET, CONSTANTS_AND_URL.Requests_Url +"getAllUsers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Utility.hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d("200", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            if (json.getString("status").equalsIgnoreCase("200"))
                              //  Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                            {
                                JSONArray jsonArray = json.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    AllDonerItems cd = new AllDonerItems();
                                    cd.setUser_id(jsonObject.optString("user_id"));
                                    cd.setImage(jsonObject.optString("image"));
                                    cd.setName(jsonObject.optString("name"));
                                    cd.setBlood_group(jsonObject.optString("blood_group"));
                                    cd.setMobile(jsonObject.optString("mobile"));

                                    alldoner_data.add(cd);
                                }

                            }
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setNestedScrollingEnabled(false);
                            RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            donorsAdapter = new DonorsAdapter(alldoner_data);
                            recyclerView.setAdapter(donorsAdapter);

                        } catch (JSONException e) {
                            Utility.hideProgressDialog();
                            swipeRefreshLayout.setRefreshing(false);
                            Utility.createAlertDialog(getContext(), "Exception: " + e.getLocalizedMessage(), "Ok", "");

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Utility.hideProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        error.printStackTrace();
                        Utility.createAlertDialog(getContext(),"Error: " + error,"Ok","");

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public class DonorsAdapter extends RecyclerView.Adapter<DonorsAdapter.ViewHolder> {
        private static final String TAG = "RecyclerViewAdapter";
        Context requrstdesign;
        ArrayList<AllDonerItems> allDoner_data;

        public DonorsAdapter(ArrayList<AllDonerItems> data) {
            this.allDoner_data = allDoner_data;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            requrstdesign = parent.getContext();
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_donor_design, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            final AllDonerItems allDoner_items = alldoner_data.get(position);
            //    holder.id.setText(beanAllDoner.getUser_id(position))
            holder.id.setText(allDoner_items.getUser_id(position));
            holder.name.setText(allDoner_items.getName(position));
            holder.blood.setText(allDoner_items.getBlood_group(position));

            Picasso.get()
                    .load(CONSTANTS_AND_URL.IMAGE_BASE_URL + allDoner_items.getImage(position))
                    .resize(1000, 600).centerInside()
                    .into(holder.circleImageView);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: clicked on: " + allDoner_items.getName(position));
                    Intent intent = new Intent(requrstdesign, SingleDonorActivity.class);
                    intent.putExtra("user_id", allDoner_items.getUser_id(position));
                    intent.putExtra("name", allDoner_items.getName(position));
                    intent.putExtra("blood_group", allDoner_items.getBlood_group(position));
                    intent.putExtra("mobile", allDoner_items.getMobile(position));
                    requrstdesign.startActivity(intent);
                }
            });

            holder.linearLayoutcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: clicked on: " + allDoner_items.getName(position));
                    //Toast.makeText(requrstdesign, beanAllDoner.getMobile(position), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(requrstdesign, SingleDonorActivity.class);
                    intent.putExtra("mobile", allDoner_items.getMobile(position));
                    String phone = allDoner_items.getMobile(position);
                    intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                    Log.d(phone, "phone");
                    startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return alldoner_data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView name, blood, id;
            CircleImageView circleImageView;
            LinearLayout linearLayoutcall, linearLayoutshare;
            CardView cardView;

            ViewHolder(View itemView) {
                super(itemView);
                id = (TextView) itemView.findViewById(R.id.user_id);
                id.setVisibility(View.GONE);
                circleImageView = (CircleImageView) itemView.findViewById(R.id.image);
                name = (TextView) itemView.findViewById(R.id.namedonor);
                linearLayoutcall = (LinearLayout) itemView.findViewById(R.id.linercall);
                linearLayoutshare = (LinearLayout) itemView.findViewById(R.id.linershare);
                blood = (TextView) itemView.findViewById(R.id.blood);
                cardView = itemView.findViewById(R.id.card);
                linearLayoutshare.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        String shareBody = "Here is the share content body";
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                        startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        try {
                            startActivity(sharingIntent);
                        } catch (android.content.ActivityNotFoundException ex) {
                        }
                    }
                });

            }
        }


    }


}
