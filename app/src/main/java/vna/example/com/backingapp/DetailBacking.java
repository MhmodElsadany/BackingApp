package vna.example.com.backingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class DetailBacking extends AppCompatActivity implements DetailBackingFragment.ActivitiListener {
    boolean twoPane=false;
    String TAG= "DetailBacking";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_main_detail);
        DetailBackingFragment mDetailBackingFragment=new DetailBackingFragment();
        if(savedInstanceState ==null){
            Log.i("DetailBacking", "onSavedInstanceState not null");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment1, mDetailBackingFragment,"").commit();
        }
        Log.i("DetailBacking", "recieved name is "+getIntent().getStringExtra("name"));
        if(findViewById(R.id.fragment2) !=null){
            twoPane = true;
        }
    }

    @Override
    public void setDtaFragment(String id, String videoURL, String Description) {

        Bundle bundle = new Bundle();
        int intId =  Integer.parseInt(id);
        bundle.putInt("id",intId);
        bundle.putString("videoURL",videoURL);
        bundle.putString("Description",Description);
        if (twoPane) {
            StepsDetailFragment stepsDetailFragment = new StepsDetailFragment();
            stepsDetailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment2,stepsDetailFragment).commit();


        } else {


            Intent intent = new Intent(this, ActivityStepsDetail.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}