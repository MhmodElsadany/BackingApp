package vna.example.com.backingapp.UserInterface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import vna.example.com.backingapp.CustomAdapter.BackingAdapter;
import vna.example.com.backingapp.Models.BackingsItemModel;
import vna.example.com.backingapp.Models.IngrediantItem;
import vna.example.com.backingapp.Models.RelamData;
import vna.example.com.backingapp.Models.StepItem;
import vna.example.com.backingapp.R;
import vna.example.com.backingapp.others.Singleton;

public class MainActivity extends AppCompatActivity {
    StringRequest stringRequest;
    ArrayList<BackingsItemModel> backingItem = new ArrayList<>();
    RecyclerView mRecyclerView;
    ArrayList<IngrediantItem> ingrediantsList = new ArrayList<>();
    ArrayList<StepItem> stepsList = new ArrayList<>();
    Realm realm;
    LinearLayoutManager mLayoutMananger;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.backing_list);

        mLayoutMananger = new LinearLayoutManager(this);


        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        try {
                            Log.i("data", response);

                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                backingItem.add(new BackingsItemModel(jsonObject1.getString("name"), jsonObject1.getString("image")));

                                JSONArray jsonArrayIngrediant = jsonObject1.getJSONArray("ingredients");
                                JSONArray jsonArrayStep = jsonObject1.getJSONArray("steps");
                                String integerateItem = "";

                                for (int j = 0; j < jsonArrayIngrediant.length(); j++) {
                                    JSONObject jsonObjectinteg = jsonArrayIngrediant.getJSONObject(j);
                                    ingrediantsList.add(new IngrediantItem
                                            (jsonObjectinteg.getString("quantity"), jsonObjectinteg.getString("measure"),
                                                    jsonObjectinteg.getString("ingredient")));
                                    integerateItem += jsonObjectinteg.getString("quantity")
                                            + "  " + jsonObjectinteg.getString("measure") + "  " +
                                            jsonObjectinteg.getString("ingredient") + "\n";


                                }


                                RelamData relamData = new RelamData();
                                realm = Realm.getInstance(getApplicationContext());
                                realm.beginTransaction();
                                List<RelamData> mCars = realm.allObjects(RelamData.class);

                                if (mCars.size() < 5) {
                                    relamData.setId(i + 1 + "");
                                    relamData.setName(jsonObject1.getString("name"));
                                    relamData.setIntegtatesItem(integerateItem);
                                    realm.copyToRealmOrUpdate(relamData);
                                    realm.commitTransaction();
                                }
                                realm.close();

                                for (int j = 0; j < jsonArrayStep.length(); j++) {
                                    JSONObject jsonObjectstep = jsonArrayStep.getJSONObject(i);
                                    stepsList.add(new StepItem
                                            (jsonObjectstep.getString("id"), jsonObjectstep.getString("shortDescription"),
                                                    jsonObjectstep.getString("description")
                                                    , jsonObjectstep.getString("videoURL"),
                                                    jsonObjectstep.getString("thumbnailURL")));


                                }
                            }
                        } catch (Exception e) {

                        }
                        mRecyclerView.setLayoutManager(mLayoutMananger);
                        if (savedInstanceState != null) {
                            int sPosition = savedInstanceState.getInt("test", -1);
                            if (sPosition != -1) {
                                mRecyclerView.scrollToPosition(sPosition);
                            }
                        }

                        BackingAdapter moviesAdapter = new BackingAdapter(MainActivity.this, backingItem);
                        mRecyclerView.setAdapter(moviesAdapter);
                        moviesAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                });
        Singleton.getInstance(MainActivity.this).addRequestQue(stringRequest);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int reclerbeforerotate = mLayoutMananger.findFirstCompletelyVisibleItemPosition();
        outState.putInt("test", reclerbeforerotate);
        Log.i("reclerbeforerotate", reclerbeforerotate + "");

    }

}
