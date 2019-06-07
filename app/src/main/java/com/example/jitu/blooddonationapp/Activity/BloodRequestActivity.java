package com.example.jitu.blooddonationapp.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.jitu.blooddonationapp.R;

public class BloodRequestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_all__request);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getIncomingIntent();
    }
    private void getIncomingIntent(){

        if(getIntent().hasExtra("request_id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("units")
                && getIntent().hasExtra("blood_group")&& getIntent().hasExtra("mobile")){

            String id = getIntent().getStringExtra("user_id");
            String name = getIntent().getStringExtra("name");
            String blood_group = getIntent().getStringExtra("blood_group");
            String units = getIntent().getStringExtra("units");
            String mobile = getIntent().getStringExtra("mobile");
            setUserid(id);
            setName(name);
            setUnit(units);
            setBloodgroup(blood_group);
            setmobile(mobile);
        }
    }

    private void setUserid(String id ){
        TextView nametext1 = findViewById(R.id.id_single_req);
        nametext1.setText(id);

    }
    private void setName(String name){
        TextView nametext2 = findViewById(R.id.name_single_req);
        nametext2.setText(name);

    }

    private void setUnit(String unit){
        TextView nametext2 = findViewById(R.id.unit_single_req);
        nametext2.setText(unit);

    }
    private void setBloodgroup( String blood_group){


        TextView nametext3 = findViewById(R.id.blood_single_req);
        nametext3.setText(blood_group);
    }

    private void setmobile( String mob){
        TextView nametext3 = findViewById(R.id.number_single_req);
        nametext3.setText(mob);
    }

}
