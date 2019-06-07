package com.example.jitu.blooddonationapp.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.jitu.blooddonationapp.Utils.CONSTANTS_AND_URL;
import com.example.jitu.blooddonationapp.Utils.Preference;
import com.example.jitu.blooddonationapp.R;
import com.example.jitu.blooddonationapp.Models.ProfileDataItem;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    android.app.ActionBar actionBar;
    ImageView imageView;
    ArrayList<ProfileDataItem> profile_datumItems;
    RecyclerView recyclerView;
    CircleImageView circleImageView;



    private LinearLayout linearprofile,linearprofilea;
    private CircleImageView proimage;
    private TextView id;
    private TextView proname;
    private TextView problood;
    private TextView promobile;
    private TextView proemail;
    private TextView procontry;
    private TextView prostate;
    private TextView procity;
    private TextView editprofile;
    String mobile;
    String Id;
    LinearLayout linearLayout;
    private String fullScreenInd;
    String imageUrl;
    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2019-04-23 18:39:29 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        linearprofile = (LinearLayout)findViewById( R.id.linearprofile );
        proimage = (CircleImageView)findViewById( R.id.proimage );
        id = (TextView)findViewById( R.id.id );
        proname = (TextView)findViewById( R.id.proname );
        problood = (TextView)findViewById( R.id.problood );
        promobile = (TextView)findViewById( R.id.promobile );
        proemail = (TextView)findViewById( R.id.proemail );
        procontry = (TextView)findViewById( R.id.procontry );
        prostate = (TextView)findViewById( R.id.prostate );
        procity = (TextView)findViewById( R.id.procity );
        editprofile = (TextView)findViewById( R.id.edittext1);
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
      /*  Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.rad));

        actionBar=getActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());

        View mCustomView = mInflater.inflate(R.layout.custom_profile, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

*/


        fullScreenInd = getIntent().getStringExtra("fullScreenIndicator");
        if ("y".equals(fullScreenInd)) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();

            circleImageView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            circleImageView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            circleImageView.setAdjustViewBounds(false);
            circleImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }

      circleImageView=findViewById(R.id.proimage);

       // circleImageView.setImageURI(Uri.parse(imageUrl));


        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(UserProfileActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.profile_zoom_image, null);
                PhotoView photoView = mView.findViewById(R.id.imageView);
                photoView.setImageResource(R.drawable.myrequest);
                mBuilder.setView(mView);
                AlertDialog mDialog = mBuilder.create();
            //    mDialog.getWindow().setLayout(700,900);
                mDialog.show();
            }
        });


        imageView=(ImageView)findViewById(R.id.backpress);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserProfileActivity.this,Edit_profile.class);
                intent.putExtra("id",Id);
                startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.recylcerView1);
        user_profile();
        profile_datumItems = new ArrayList<>();




    }

    public void startZoomInAnimation(View view) {
        circleImageView=findViewById(R.id.proimage);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_animation);
        circleImageView.startAnimation(animation);
    }






    private void user_profile() {
        String userId = (Preference.getInstance(getApplicationContext())).getuser_id();
        String URL_PRODUCTS = CONSTANTS_AND_URL.Users_Url+"viewSingleUser/"+userId;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("status").equalsIgnoreCase("200"))
                               // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                            {
                                JSONObject jsonObject1=jsonObject.getJSONObject("Data");
                                ProfileDataItem profileData= new ProfileDataItem();profileData.setId(jsonObject1.optString("user_id"));
                                profileData.setName(jsonObject1.optString("name"));
                                profileData.setBlood(jsonObject1.optString("blood_group"));
                                profileData.setMobile(jsonObject1.optString("mobile"));
                                profileData.setEmail(jsonObject1.optString("email"));
                                profileData.setCountry(jsonObject1.optString("country"));
                                profileData.setState(jsonObject1.optString("state"));
                                profileData.setCity(jsonObject1.optString("city"));
                                profileData.setImage(jsonObject1.optString("image"));

                                imageUrl = "https://www.pexels.com/photo/nature-red-love-romantic-67636/";
                                profile_datumItems.add(profileData);

                                Id=jsonObject1.optString("user_id");


                                Glide.with(getApplicationContext())
                                        .load("https://www.pexels.com/photo/nature-red-love-romantic-67636/")
                                        .into(imageView);
                                String user_name=jsonObject1.optString("name");
                                if (user_name.length()>0)
                                {
                                    proname.setText(jsonObject1.optString("name"));
                                }
                                else
                                {
                                    proname.setText("Full Name");
                                }
                                if (jsonObject1.optString("blood_group").length()>0)
                                {
                                    problood.setText(jsonObject1.optString("blood_group"));
                                }
                                else
                                {
                                    problood.setText("Blood Group");
                                }

                                mobile = jsonObject1.optString("mobile");
                                if (mobile.length()>0)
                                {
                                    promobile.setText(jsonObject1.optString("mobile"));
                                }
                                else
                                {
                                    promobile.setText("Mobile Number");
                                }
                                if (jsonObject1.optString("email").length()>0)
                                {
                                    proemail.setText(jsonObject1.optString("email"));
                                }
                                else
                                {
                                    proemail.setText("Email Address");
                                }
                                if (jsonObject1.optString("country").length()>0)
                                {
                                    procontry.setText(jsonObject1.optString("country"));
                                }
                                else
                                {
                                    procontry.setText("Country");
                                }
                                if (jsonObject1.optString("state").length()>0)
                                {
                                    prostate.setText(jsonObject1.optString("state"));
                                }
                                else
                                {
                                    prostate.setText("State");
                                }
                                if (jsonObject1.optString("city").length()>0)
                                {
                                    procity.setText(jsonObject1.optString("city"));
                                }
                                else
                                {
                                    procity.setText("City");
                                }




                            }
                          /*  recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            UserAdapter adapter = new UserAdapter(UserProfileActivity.this, profile_datumItems);
                            recyclerView.setAdapter(adapter);*/
                        } catch (JSONException e) {
                            Log.d("user_id",response);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);
    }

    public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ProductViewHolder> {

        Context activity;
        ArrayList<ProfileDataItem> profile_datumItems;

        public UserAdapter(FragmentActivity activity, ArrayList<ProfileDataItem> profile_datumItems) {
            this.activity=activity;
            this.profile_datumItems = profile_datumItems;

        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_profile, null);
            return new ProductViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {
            final ProfileDataItem product = profile_datumItems.get(position);
            holder.id.setText(product.getId());
            holder.name.setText(product.getName(position));
            holder.blood.setText(product.getBlood());
            holder.mobile.setText(product.getMobile());
            holder.email.setText(product.getEmail());
            holder.contry.setText(product.getCountry());
            holder.state.setText(product.getState());
            holder.city.setText(product.getCity());

           holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), Edit_profile.class).putExtra("id2", profile_datumItems.get(position).getId()));



                }
            });
           }

        @Override
        public int getItemCount() {
            return profile_datumItems.size();
        }

        class ProductViewHolder extends RecyclerView.ViewHolder {

            TextView id,name,blood,mobile,email,contry,state,city,textView;
            RelativeLayout relativeLayout;

            ProductViewHolder(View itemView) {
                super(itemView);
                id = itemView.findViewById(R.id.id);
                name = itemView.findViewById(R.id.proname);
                blood = itemView.findViewById(R.id.problood);
                mobile=itemView.findViewById(R.id.promobile);
                email= itemView.findViewById(R.id.proemail);
                contry = itemView.findViewById(R.id.procontry);
                state = itemView.findViewById(R.id.prostate);
                city   = itemView.findViewById(R.id.procity);
                textView=(TextView)findViewById(R.id.edittext1);
                relativeLayout=(RelativeLayout)findViewById(R.id.userp);

            }


        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}









