package com.example.jitu.blooddonationapp.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.jitu.blooddonationapp.Utils.Utility;
import com.onurkaganaldemir.ktoastlib.KToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FrontPageActivity extends AppCompatActivity {
    EditText editmobile,editotp,userid;
    Dialog myDialog;
    Button sendotp, buttonverify;
    private ProgressDialog mDialog;
    String mobileno,otp;
    SessionManager sessionManager;
    JSONObject jObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);
        getSupportActionBar().hide();
        myDialog = new Dialog(this);
        ProgressDialog dlg = new ProgressDialog(this);

        sendotp = (Button) findViewById(R.id.sendotp);
        editmobile = (EditText) findViewById(R.id.edittextfront);
        userid = (EditText) findViewById(R.id.edittextid);

        sessionManager =new SessionManager(getApplicationContext());
        sessionManager.isLoggedIn();
        if (sessionManager.isLoggedIn())
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }

        sendotp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mobileno = editmobile.getText().toString().trim();
               /* if (mobileno.equals("") || mobileno.length()< 12) {
                    KToast.errorToast(FrontPageActivity.this, "Please Enter Mobile Number With 91!", Gravity.BOTTOM, KToast.LENGTH_AUTO); }
                    else {*/
                    login();
                    ShowPopup();
               // }

            }
        });

    }


    private void login() {
        Utility.showProgressDialog(FrontPageActivity.this,"Loading");
        RequestQueue queue = Volley.newRequestQueue(FrontPageActivity.this);
        String url = CONSTANTS_AND_URL.Users_Url+"login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utility.hideProgressDialog();
                try {

                    JSONObject json = new JSONObject(response);
                    jObject = new JSONObject(response);
                    if (jObject.getString("status").equalsIgnoreCase("200")) {

                        JSONObject jsonObject=jObject.getJSONObject("Data");
                        String uid = jsonObject.getString("user_id");
                        sessionManager.createLoginSession(uid);
                        Preference.getInstance(FrontPageActivity.this).setLogged(true);
                        Preference.getInstance(FrontPageActivity.this).setuser_id(uid);
                        ShowPopup();
                        editotp.setText(jObject.getString("Otp"));
                    }
                    else
                    {
                    //    MyToast.show(FrontPageActivity.this,"Connection Time Out Error 22!!!", false);
                        mDialog.dismiss();
                    }

                } catch (JSONException e) {
                    Utility.hideProgressDialog();
                    Utility.createAlertDialog(getApplicationContext(),"Exception: "+ e.getLocalizedMessage(),"Ok","");

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Utility.hideProgressDialog();
                Log.e("error", "error msg");
                error.printStackTrace();
                Utility.createAlertDialog(getApplicationContext(),"Error: " + error,"Ok","");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("mobile",mobileno);
                return map;
            }
        };
        queue.add(stringRequest);


    }
    public void ShowPopup() {
        TextView txtclose;

        final Button  btncancle;
        myDialog.setContentView(R.layout.custompopup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        editotp = (EditText) myDialog.findViewById(R.id.popedit);

        txtclose.setText("x");
        btncancle = (Button) myDialog.findViewById(R.id.btncancle);
        buttonverify = (Button) myDialog.findViewById(R.id.btnfollow);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        buttonverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp =editotp .getText().toString();
                otp();
                myDialog.dismiss();



            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();

    }

    private void otp() {
        Utility.showProgressDialog(FrontPageActivity.this,"Loading Data");
        RequestQueue queue = Volley.newRequestQueue(FrontPageActivity.this);
        String url = CONSTANTS_AND_URL.Users_Url+"verifyOtp";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utility.hideProgressDialog();
                try {
                    jObject = new JSONObject(response);
                    if (jObject.getString("status").equalsIgnoreCase("200")) {

                        editmobile.getText().clear();

                        Intent intent = new Intent(FrontPageActivity.this, MainActivity.class);


                        startActivity(intent);
                        KToast.successToast(FrontPageActivity.this, "Login successful", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                    }
                    else { }
                    }
                    catch (JSONException e) {
                    Utility.hideProgressDialog();
                    Utility.createAlertDialog(getApplicationContext(),"Exception: "+ e.getLocalizedMessage(),"Ok","");

                    //KToast.errorToast(FrontPageActivity.this, "Error while reading data try again!", Gravity.BOTTOM, KToast.LENGTH_LONG);

                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
              Utility.hideProgressDialog();
                Utility.createAlertDialog(getApplicationContext(),"Error: " + error,"Ok","");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("otp", otp);

                try {
                    JSONObject dataObject = jObject.getJSONObject("Data");
                    map.put("user_id", dataObject.getString("user_id"));

                   // Log.d("map", map.toString());

                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(FrontPageActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }

                return map;
            }
        };
        queue.add(stringRequest);

    }








}




