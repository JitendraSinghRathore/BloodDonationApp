package com.example.jitu.blooddonationapp.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
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
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jitu.blooddonationapp.R;
import com.example.jitu.blooddonationapp.Utils.CONSTANTS_AND_URL;
import com.example.jitu.blooddonationapp.Utils.Preference;
import com.example.jitu.blooddonationapp.Utils.SessionManager;
import com.example.jitu.blooddonationapp.Fragments.SingleBloodRequestFragment;
import com.example.jitu.blooddonationapp.Utils.Utility;
import com.onurkaganaldemir.ktoastlib.KToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddUserRequest extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText etbloood,etunit,etlocatin,etname,etmobile;
    String userid,bloods,unit,locatin,fullnames,mobiles;
    Button button;
    ActionBar actionBar;
    SessionManager sessionManager;
    JSONObject jObject;
    Spinner spinner;
    List<String> categories;
    String item;
    ArrayAdapter<String> dataAdapter;
    String selected_blood_group;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_request);
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
        View mCustomView = mInflater.inflate(R.layout.custom_sendrequest, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        spinner = (Spinner) findViewById(R.id.spinner);
        categories = new ArrayList<String>();
        categories.add("B_G");
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

        //Id Find Of All Edit Text And Button
        etbloood = (EditText) findViewById(R.id.user_id);
       // etid = (EditText) findViewById(R.id.etblood);
        etunit = (EditText) findViewById(R.id.etunit);
        etlocatin = (EditText) findViewById(R.id.etlocation);
        etname = (EditText) findViewById(R.id.etfullname);
        etmobile = (EditText) findViewById(R.id.etmobile);
        button = (Button) findViewById(R.id.send_request);
        etmobile = (EditText) findViewById(R.id.etmobile);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //   userid=etid.toString().trim();
            //   bloods=etbloood.getText().toString().trim();
               unit=etunit.getText().toString().trim();
               fullnames=etname.getText().toString().trim();
               mobiles=etmobile.getText().toString().trim();
               locatin=etlocatin.getText().toString().trim();


               if (fullnames.equals(""))
               {
                 KToast.errorToast(AddUserRequest.this,"enter name",Gravity.BOTTOM,KToast.LENGTH_SHORT);
               }
               else if (item.equals(categories.get(0))) {
                   // MyToast.show(AddNewUser.this,"Please enter your address!!!", false);
                   // Toast.makeText(AddnewRoomAllotment.this, "Please select student!", Toast.LENGTH_SHORT).show();

                   KToast.errorToast(AddUserRequest.this, "Please enter blood group!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
               }
               else if (unit.equals("")) {
                   KToast.errorToast(AddUserRequest.this, "Please Enter Blood Units", Gravity.BOTTOM, KToast.LENGTH_AUTO);
               }else if (fullnames.equals("")) {
                   KToast.errorToast(AddUserRequest.this, "Please Enter FullName!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
               } else if (mobiles.equals("") || mobiles.length() < 12) {
                   KToast.errorToast(AddUserRequest.this, "Please Enter Mobile Number With +91!", Gravity.BOTTOM, KToast.LENGTH_AUTO);
               }

               else
               {
                   add_request();
               }
            }
        });


    }
    private void add_request() {

       userid = (Preference.getInstance(getApplicationContext())).getuser_id();

        RequestQueue queue = Volley.newRequestQueue(AddUserRequest.this);
        String url = CONSTANTS_AND_URL.Requests_Url+ "addRequest";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                try {

                    JSONObject json = new JSONObject(response);
                    jObject = new JSONObject(response);
                    if (jObject.getString("status").equalsIgnoreCase("1")) {

                        JSONObject jsonObject=jObject.getJSONObject("Data");
                        Intent intent=new Intent(AddUserRequest.this, SingleBloodRequestFragment.class);
                        startActivity(intent);


                    }
                    else
                    {
                       Intent intent=new Intent(AddUserRequest.this, MainActivity.class);
                       startActivity(intent);
                        KToast.successToast(AddUserRequest.this, "AllBloodRequestFragment Send Successful", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                    }

                } catch (JSONException e) {
                    Utility.createAlertDialog(getApplicationContext(),"Exception: "+ e.getLocalizedMessage(),"Ok","");
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "error msg");
                error.printStackTrace();
                Utility.createAlertDialog(getApplicationContext(),"Error: " + error,"Ok","");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user_id",userid);
                map.put("blood_group",item);
                map.put("country",locatin);
                map.put("units",unit);
                map.put("name",fullnames);
                map.put("mobile",mobiles);


                return map;
            }
        };
        queue.add(stringRequest);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


