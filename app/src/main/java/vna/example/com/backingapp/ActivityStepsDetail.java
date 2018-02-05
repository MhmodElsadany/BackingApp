package vna.example.com.backingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import static android.R.attr.id;

public class ActivityStepsDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();


        StepsDetailFragment mStepsDetailFragment=new StepsDetailFragment();
        mStepsDetailFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment3 ,mStepsDetailFragment,"").commit();

    }

}
