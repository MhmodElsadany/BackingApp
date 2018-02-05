package vna.example.com.backingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import static java.security.AccessController.getContext;

public class DetailBacking extends AppCompatActivity implements DetailBackingFragment.ActivitiListener {
          boolean twoPane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_main_detail);
        DetailBackingFragment mDetailBackingFragment=new DetailBackingFragment();
        if(savedInstanceState ==null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment1, mDetailBackingFragment,"").commit();

        if(findViewById(R.id.fragment2) !=null){
            twoPane = true;
        }


    }

    @Override
    public void setDtaFragment(String id, String videoURL, String Description) {

        Bundle bundle = new Bundle();
        bundle.putString("id",id);
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
