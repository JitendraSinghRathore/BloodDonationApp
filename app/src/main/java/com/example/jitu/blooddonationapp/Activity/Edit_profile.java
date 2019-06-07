package com.example.jitu.blooddonationapp.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.jitu.blooddonationapp.R;
import com.example.jitu.blooddonationapp.Utils.CONSTANTS_AND_URL;
import com.onurkaganaldemir.ktoastlib.KToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Edit_profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    android.app.ActionBar actionBar;
    TextInputEditText e1, e2, e3, e4, e5, e6, e7;
    ImageView imageView;
    String id;
    double latitude,longitude;
    LocationManager locationManager;
    Button submit;
    String full_name,blood_group,mobile_number, email, country, state, city ,user_id;
    Spinner spinner;
    List<String> categories;
    String item;
    ArrayAdapter<String> dataAdapter;
    String selected_blood_group;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.rad));

        actionBar = getActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());

        View mCustomView = mInflater.inflate(R.layout.custom_edit, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        spinner = (Spinner) findViewById(R.id.spinner);
        categories = new ArrayList<String>();
        categories.add("Blood Group");
        categories.add("O+");
        categories.add("O-");
        categories.add("A+");
        categories.add("A-");
        categories.add("B+");
        categories.add("B-");
        categories.add("AB+");
        categories.add("AB-");
        spinner.setOnItemSelectedListener(this);

        // Creating adapter for spinner

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        Intent intent=getIntent();
        user_id= intent.getStringExtra("id");

        imageView = (ImageView) findViewById(R.id.backpress);

        submit=(Button) findViewById(R.id.editupdate);
        imageView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
//               Edit_profile.super.onBackPressed();
//               finish();
                Intent intent=new Intent(Edit_profile.this,Edit_profile.class);
                imageView.setImageResource(R.id.map);
                startActivity(intent);
            }
        });

        e1 = (TextInputEditText) findViewById(R.id.editname);
        e2 = (TextInputEditText) findViewById(R.id.editblood);
        e3 = (TextInputEditText) findViewById(R.id.editmobile);
        e4 = (TextInputEditText) findViewById(R.id.editemail);
        e5 = (TextInputEditText) findViewById(R.id.editcountry);
        e6 = (TextInputEditText) findViewById(R.id.editstate);
        e7 = (TextInputEditText) findViewById(R.id.city);
        show_data();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                full_name = e1.getText().toString().trim();
                blood_group = e2.getText().toString().trim();
                mobile_number = e3.getText().toString().trim();
                email = e4.getText().toString().trim();
                country = e5.getText().toString().trim();
                state = e6.getText().toString().trim();
                city = e7.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


                if (full_name.isEmpty()) {
                    // MyToast.show(AddNewUser.this,"Please enter full name!!!", false);
                    KToast.errorToast(Edit_profile.this, "Please enter full name!!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                } else if (item.equals("B_G")) {
                    // MyToast.show(AddNewUser.this,"Please enter your address!!!", false);
                    // Toast.makeText(AddnewRoomAllotment.this, "Please select student!", Toast.LENGTH_SHORT).show();

                    KToast.errorToast(Edit_profile.this, "Please enter blood group!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                } else if (mobile_number.isEmpty()||mobile_number.length()<12) {
                    // MyToast.show(AddNewUser.this,"Please your enter contact number!!!", false);
                    KToast.errorToast(Edit_profile.this, "Please Enter Mobile Number With 91!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                } else if (email.isEmpty() || !email.matches(emailPattern)) {
                    //  MyToast.show(AddNewUser.this,"Please enter valid email address!!!", false);
                    KToast.errorToast(Edit_profile.this, "Please enter valid email address!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                } else if (country.isEmpty()) {
                    // MyToast.show(AddNewUser.this,"Please enter your address!!!", false);
                    // Toast.makeText(AddnewRoomAllotment.this, "Please select student!", Toast.LENGTH_SHORT).show();
                    KToast.errorToast(Edit_profile.this, "Please enter your address!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                } else if (state.isEmpty()) {
                    // MyToast.show(AddNewUser.this,"Please your enter contact number!!!", false);
                    KToast.errorToast(Edit_profile.this, "Please your enter contact number!!", Gravity.BOTTOM, KToast.LENGTH_AUTO);

                } else if (city.isEmpty()) {
                    // MyToast.show(AddNewUser.this,"Please your enter contact number!!!", false);
                    KToast.errorToast(Edit_profile.this, "Please your enter contact number!!", Gravity.BOTTOM, KToast.LENGTH_AUTO);

                } else {
                    send_data();

                }
            }
        });
    }

    public void show_data()
    {
        StringRequest request= new StringRequest(Request.Method.GET, CONSTANTS_AND_URL.Users_Url+"viewSingleUser/"+user_id,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            if(jsonObject.getString("status").equalsIgnoreCase("200"))

                            {
                                JSONObject jsonObject1=jsonObject.getJSONObject("Data");

                                e1.setText(jsonObject1.optString("name"));
                                e2.setText(jsonObject1.optString("blood_group"));
                                e3.setText(jsonObject1.optString("mobile"));
                                e4.setText(jsonObject1.optString("email"));
                                e5.setText(jsonObject1.optString("country"));
                                e6.setText(jsonObject1.optString("state"));
                                e7.setText(jsonObject1.optString("city"));

                                selected_blood_group = jsonObject1.optString("blood_group");
                                spinner.setPrompt(selected_blood_group);

                                if (selected_blood_group != null) {
                                    int spinnerPosition = dataAdapter.getPosition(selected_blood_group);
                                    spinner.setSelection(spinnerPosition);
                                }
                            }

                        }catch (JSONException e){

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public void send_data() {
        full_name = e1.getText().toString().trim();
      //  blood_group = e2.getText().toString().trim();
       // blood_group = spinner.getPrompt().toString();
        mobile_number = e3.getText().toString().trim();
        email = e4.getText().toString().trim();
        country = e5.getText().toString().trim();
        state = e6.getText().toString().trim();
        city = e7.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(Edit_profile.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CONSTANTS_AND_URL.Users_Url+"editUserById/"+user_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jObject = new JSONObject(response);
                    if (jObject.getString("status").equalsIgnoreCase("1")) {
                        startActivity(new Intent(getApplicationContext(),UserProfileActivity.class));
                        KToast.successToast(Edit_profile.this, "Successfully update!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                        finish();

                    }


                } catch (JSONException e) {
                    //     MyToast.show(getApplicationContext(), "Error while sending data try again !!!.", false);
                    KToast.errorToast(Edit_profile.this, "Error while sending data try again!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                    //  Utility.hideProgressDialog();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Utility.hideProgressDialog();
                Log.d("error", "error msg");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("name", full_name);
                map.put("blood_group", item);
                map.put("mobile", mobile_number);
                map.put("email", email);
                map.put("country", country);
                map.put("state", state);
                map.put("city", city);
            //    map.put("user_id", id);
                map.put("latitude",Double.toString(latitude));
                map.put("longitude",Double.toString(longitude));
                //  map.put("password", password1);
               // map.put("id", id);
                return map;
            }
        };
        queue.add(stringRequest);


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item

        item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
       // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
