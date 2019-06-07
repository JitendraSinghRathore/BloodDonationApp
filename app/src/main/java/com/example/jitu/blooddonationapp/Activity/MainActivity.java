package com.example.jitu.blooddonationapp.Activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jitu.blooddonationapp.Fragments.AllBloodRequestFragment;
import com.example.jitu.blooddonationapp.Fragments.AllDonorFragment;
import com.example.jitu.blooddonationapp.Fragments.MoreDetailsFragment;
import com.example.jitu.blooddonationapp.R;
import com.example.jitu.blooddonationapp.Utils.CONSTANTS_AND_URL;
import com.example.jitu.blooddonationapp.Utils.Preference;
import com.example.jitu.blooddonationapp.Fragments.SingleBloodRequestFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LocationListener{
    ViewPager viewPager;
    android.app.ActionBar actionBar;
    Dialog myDialog;
    Menu menu;
    MapView mapView;
    Button sendrequest;
    JSONObject jObject;
    String userid,distance,users;
    double latitude,longitude;
    AllDonorFragment all_donorActivity;
    TextView nearby;
    TabLayout tabLayout;
    RecyclerView recyclerView;
    TextView seekdistance;
    RelativeLayout r1;
    LocationManager locationManager;
    ImageView mapicon,listicon;
    private int tabIcon[] = {R.drawable.threegroup, R.drawable.request, R.drawable.box, R.drawable.more};

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDialog = new Dialog(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapfar);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.rad));



        actionBar = getActionBar();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
        View mCustomView   = mInflater.inflate(R.layout.custom_mainpage, null);
        getSupportActionBar().setCustomView(mCustomView);
        mCustomView.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        nearby=(TextView) findViewById(R.id.nearby);
        nearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowPopup();

            }
        });

        mapicon=(ImageView)findViewById(R.id.map);
        listicon=(ImageView)findViewById(R.id.list);

        //views id find below
        mapView=(MapView)findViewById(R.id.mapview);
        recyclerView=(RecyclerView)findViewById(R.id.recy);
        mapicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        sendrequest=(Button)findViewById(R.id.sendrequest);
         sendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(getApplicationContext(), AddUserRequest.class);
              startActivity(intent);
          }
         });


        viewPager = (ViewPager) findViewById(R.id.views);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcon();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }
                @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    actionBar = getActionBar();
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
                    View mCustomView   = mInflater.inflate(R.layout.custom_mainpage, null);
                    getSupportActionBar().setCustomView(mCustomView);
                    mCustomView.setVisibility(View.VISIBLE);
                    getSupportActionBar().setDisplayShowCustomEnabled(true);
                    nearby=(TextView) findViewById(R.id.nearby);
                    nearby.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ShowPopup();

                        }
                    });
                    //icons id find below


                    //apply click listener on map_icon
                    mapicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                          Intent intent=new Intent(MainActivity.this, MapsActivity.class);
                          startActivity(intent);
                            }
                    });
                    //apply click listener on list_icon
                    listicon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            listicon.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
//                            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                            ft.replace(R.id.recylcerView, new AllDonorFragment());
//                            ft.commit();
                            mapView.setVisibility(View.INVISIBLE);
                            mapicon.setVisibility(View.VISIBLE);



                        }
                    });
                } else if (position == 1) {
                    actionBar = getActionBar();
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
                    View mCustomView = mInflater.inflate(R.layout.custom_request_main, null);
                    getSupportActionBar().setCustomView(mCustomView);
                    getSupportActionBar().setDisplayShowCustomEnabled(true);
                    //toolbar.getMenu().clear();
                  //  toolbar.inflateMenu(R.menu.menu2);
                } else if (position == 2) {
                    actionBar = getActionBar();
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
                    View mCustomView = mInflater.inflate(R.layout.custom_myrequest_main, null);
                    getSupportActionBar().setCustomView(mCustomView);
                    getSupportActionBar().setDisplayShowCustomEnabled(true);
                  //  toolbar.getMenu().clear();
                  //  toolbar.inflateMenu(R.menu.menu3);
                } else if (position == 3) {
                    actionBar = getActionBar();
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    LayoutInflater mInflater = LayoutInflater.from(getApplicationContext());
                    View mCustomView = mInflater.inflate(R.layout.custom_more_main, null);
                    getSupportActionBar().setCustomView(mCustomView);
                    getSupportActionBar().setDisplayShowCustomEnabled(true);
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Nullable
    @Override
    public ActionBar getActionBar() {

        return actionBar;
    }
    private void setupTabIcon() {
        tabLayout.getTabAt(0).setIcon(tabIcon[0]);
        tabLayout.getTabAt(1).setIcon(tabIcon[1]);
        tabLayout.getTabAt(2).setIcon(tabIcon[2]);
        tabLayout.getTabAt(3).setIcon(tabIcon[3]);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#c9060d"));
        tabLayout.setSelectedTabIndicatorHeight((int) (1 * getResources().getDisplayMetrics().density));
        tabLayout.setTabTextColors(Color.parseColor("#c9060d"), Color.parseColor("#c9060d"));
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new AllDonorFragment(), "All Donors");
        viewPagerAdapter.addFrag(new AllBloodRequestFragment(), "Request");
        viewPagerAdapter.addFrag(new SingleBloodRequestFragment(), "My Request");
        viewPagerAdapter.addFrag(new MoreDetailsFragment(), "More");
       // viewPagerAdapter.addFrag(new Map_frag(), "MoreDetailsFragment");
        viewPager.setAdapter(viewPagerAdapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




    public void ShowPopup() {
        final TextView txtclose;
        myDialog.setContentView(R.layout.mainpopup);
        final Button btn5km = (Button) myDialog.findViewById(R.id.btn5km);
        final Button btn20km = (Button) myDialog.findViewById(R.id.btn20km);
        final Button btn50km = (Button) myDialog.findViewById(R.id.btn50km);
        final Button btn100km = (Button) myDialog.findViewById(R.id.btn100km);
        final SeekBar seekBar = (SeekBar) myDialog.findViewById(R.id.seekbar);
//        EditText etdistance=(EditText) myDialog.findViewById(R.id.seekkm);
        seekdistance=(TextView) myDialog.findViewById(R.id.seektext);
        final Button fileter=(Button) myDialog.findViewById(R.id.btnfilter);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("x");
        getLocation();

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)

        {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);

        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                progress = progress / 5;
                progress = progress * 5;
                seekdistance.setText(String.valueOf(progress));
                seekdistance.setText(String.valueOf(progress));

            }
        });


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               myDialog.dismiss();

            }
        });

        btn5km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(5);
                }
        });
        btn20km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(20);



            }
        });
        btn50km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(50);

            }
        });
        btn100km.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setProgress(100);

            }
        });
        fileter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                distance = seekdistance.getText().toString();
                userid = (Preference.getInstance(getApplicationContext())).getuser_id();
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url = CONSTANTS_AND_URL.Users_Url+"getAllUsersById";
                StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                        try {

                            JSONObject json = new JSONObject(response);
                            jObject = new JSONObject(response);
                            if (jObject.getString("status").equalsIgnoreCase("200")) {

                                JSONArray user_data = jObject.getJSONArray("Data");
                                 Intent i=new Intent(MainActivity.this, MainActivity.class);
                                 startActivity(i);
                                 myDialog.dismiss();
                            }

                        } catch (JSONException e) {
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error", "error msg");
                        error.printStackTrace();
                  /*   //   MyToast.show(MainActivity.this,"OPPS!NO INTERNERT" +
                                "please check your network connection" +error, true);*/

                        // onPostExecute(null);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> map = new HashMap<>();
                        map.put("user_id",userid);
                        map.put("latitude",Double.toString(latitude));
                        map.put("longitude",Double.toString(longitude));
                        map.put("radius",distance);



                        return map;
                    }
                };
                queue.add(stringRequest);




            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();




    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirm Exit...");
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity();
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
    void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 5, this);
        }
        catch(SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();


    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

}
