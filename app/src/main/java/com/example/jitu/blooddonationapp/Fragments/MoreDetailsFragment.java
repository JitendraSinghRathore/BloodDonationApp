package com.example.jitu.blooddonationapp.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jitu.blooddonationapp.Activity.AboutUsActivity;
import com.example.jitu.blooddonationapp.Activity.AppFeedbackActivity;
import com.example.jitu.blooddonationapp.Activity.AppReviewActivity;
import com.example.jitu.blooddonationapp.Utils.CONSTANTS_AND_URL;
import com.example.jitu.blooddonationapp.Utils.Preference;
import com.example.jitu.blooddonationapp.Activity.FrontPageActivity;
import com.example.jitu.blooddonationapp.R;
import com.example.jitu.blooddonationapp.Utils.SessionManager;
import com.example.jitu.blooddonationapp.Models.ProfileDataItem;
import com.example.jitu.blooddonationapp.Activity.UserProfileActivity;
import com.example.jitu.blooddonationapp.Utils.Utility;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class MoreDetailsFragment extends Fragment {
    RelativeLayout relativeLayout;
    Button logout;
    ArrayList<ProfileDataItem> profile_datumItems;
    RecyclerView recyclerView;
    SessionManager sessionManager;
    ImageView profile_img;
    TextView name,blood_donation;
    TextView invite,rate,feedback,aboutus;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout user_details;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        sessionManager = new SessionManager(getContext());



        recyclerView=view.findViewById(R.id.recycler_profile);
        swipeRefreshLayout=view.findViewById(R.id.swip);


        logout = (Button) view.findViewById(R.id.logout);
        invite = (TextView) view.findViewById(R.id.invite);
        rate = (TextView) view.findViewById(R.id.rate_us);
        feedback = (TextView) view.findViewById(R.id.feedback);
        aboutus = (TextView) view.findViewById(R.id.about_us);

        profile_img = (ImageView) view.findViewById(R.id.froword_icon);
        name = (TextView) view.findViewById(R.id.name_text);
        blood_donation = (TextView) view.findViewById(R.id.blood_group);


        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserProfileActivity.class);
                startActivity(intent);
            }
        });

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserProfileActivity.class);
                startActivity(intent);
            }
        });
        blood_donation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserProfileActivity.class);
                startActivity(intent);
            }
        });



        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserProfileActivity.class);
                startActivity(intent);
            }
        });

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(), AppReviewActivity.class);
//                startActivity(intent);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AppReviewActivity.class);
                startActivity(intent);



            }
        });




        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),AppFeedbackActivity.class);
                startActivity(intent);


            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AboutUsActivity.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirm Logout...");
                builder.setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            public void onClick(DialogInterface dialog, int id) {


                                final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                                        R.style.AppTheme_Dark_Dialog);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setMessage(" Logout please wait...");
                                progressDialog.setCancelable(false);
                                progressDialog.show();
                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            public void run() {
                                                sessionManager.setLogin(false);
                                                // On complete call either onLoginSuccess or onLoginFailed

                                                startActivity(new Intent(getActivity(), FrontPageActivity.class));
                                                progressDialog.dismiss();
                                            }
                                        }, 1000);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });


     //   recyclerView = view.findViewById(R.id.recylcerView_more);
        user_profile();
        profile_datumItems = new ArrayList<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                user_profile();
                Utility.hideProgressDialog();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    void click()
    {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UserProfileActivity.class);
                startActivity(intent);
                Toast.makeText(getContext(),"ofiof",Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void user_profile() {
       // Utility.showProgressDialog(getContext(),"Loading");
        profile_datumItems = new ArrayList<>();
        String userId = (Preference.getInstance(getActivity())).getuser_id();
       // Utility.showProgressDialog(getContext(),"Loading");
        String URL_PRODUCTS = CONSTANTS_AND_URL.Users_Url+ "viewSingleUser/"+userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            Utility.hideProgressDialog();
                            swipeRefreshLayout.setRefreshing(false);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("200")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
                                ProfileDataItem profileData = new ProfileDataItem();
                                profileData.setId(jsonObject1.optString("user_id"));
                                profileData.setName(jsonObject1.optString("name"));
                                profileData.setBlood(jsonObject1.optString("blood_group"));
                                profileData.setImage(jsonObject1.optString("image"));

                                String namestring=jsonObject1.optString("name");

                                if (namestring.length()>0)
                                {
                                    name.setText(jsonObject1.optString("name"));
                                }
                                else
                                {
                                    name.setText("Name");
                                }

                                if (namestring.length()>0)
                                {
                                    blood_donation.setText(jsonObject1.optString("blood_group"));
                                }
                                else
                                {
                                    blood_donation.setText("Blood Group");
                                }


                                profile_datumItems.add(profileData);
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                            MoreAdapter adapter = new MoreAdapter (getActivity(), profile_datumItems);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Log.d("user_id", response);
                            e.printStackTrace();
                            Utility.hideProgressDialog();
                            swipeRefreshLayout.setRefreshing(false);
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

    public class MoreAdapter extends RecyclerView.Adapter<MoreAdapter.MoreViewHolder>
    {
        Context activity;
        ArrayList<ProfileDataItem> profile_datumItems;

        public MoreAdapter(FragmentActivity activity, ArrayList<ProfileDataItem> profile_datumItems) {
            this.activity=activity;
            this.profile_datumItems = profile_datumItems;

        }

        @NonNull
        @Override
        public MoreAdapter.MoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.more_design, null);
            return  new MoreViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MoreAdapter.MoreViewHolder holder, int position) {
            final ProfileDataItem product = profile_datumItems.get(position);
            holder.name.setText(product.getName(position));
            holder.blood.setText(product.getBlood());
            String image_url = "http://devoysoftech.com/demo/blood_donation/webroot/img/upload/image1525765838-photo1.png"+product.getImage(position);
            Picasso.get()
                    .load(image_url)
                    .resize(1000, 600).centerInside()
                    .into(holder.circleImageView);
        }

        @Override
        public int getItemCount() {
            return profile_datumItems.size();
        }

        public class MoreViewHolder extends RecyclerView.ViewHolder {
            TextView name,blood;
            CircleImageView circleImageView;

            public MoreViewHolder(View view) {
                super(view);
                name = itemView.findViewById(R.id.name_more);
                blood = itemView.findViewById(R.id.blood_group_more);
                circleImageView=itemView.findViewById(R.id.image_more);
            }
        }
    }

}
