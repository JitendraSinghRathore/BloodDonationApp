package com.example.jitu.blooddonationapp.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jitu.blooddonationapp.R;

public class SingleDonorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single__donor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getIncomingIntent();
    }
    private void getIncomingIntent(){

        if(getIntent().hasExtra("user_id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("blood_group")&& getIntent().hasExtra("mobile")){

            String id = getIntent().getStringExtra("user_id");
            String name = getIntent().getStringExtra("name");
            String blood_group = getIntent().getStringExtra("blood_group");
            String mobile = getIntent().getStringExtra("mobile");
            setUserid(id);
            setName(name);
            setBloodgroup(blood_group);
            setmobile(mobile);
            }
    }

    private void setUserid(String id ){
        TextView nametext1 = findViewById(R.id.idd);
        nametext1.setText(id);

    }
    private void setName(String name){
        TextView nametext2 = findViewById(R.id.deletename);
        nametext2.setText(name);

    }
    private void setBloodgroup( String blood_group){


        TextView nametext3 = findViewById(R.id.deleteblood);
        nametext3.setText(blood_group);
    }

    private void setmobile( String mob){
        TextView nametext3 = findViewById(R.id.deletemobile);
        nametext3.setText(mob);
    }

}
