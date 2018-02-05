package vna.example.com.backingapp;

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

public class MainActivity extends AppCompatActivity {
    StringRequest stringRequest;
    ArrayList<BackingsItemModel> backingItem = new ArrayList<>();
    RecyclerView mRecyclerView;
    ArrayList<IngrediantItem> ingrediantsList = new ArrayList<>();
    ArrayList<StepItem> stepsList = new ArrayList<>();
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.backing_list);


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
                                backingItem.add(new BackingsItemModel(jsonObject1.getString("name")));


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


                                    Log.i("oooooooooooop" + j, jsonObjectinteg.getString("quantity") + jsonObjectinteg.getString("measure")
                                            + jsonObjectinteg.getString("ingredient"));

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
                                                    , jsonObjectstep.getString("videoURL")));
                                    Log.i("mmmmm", jsonObjectstep.getString("id") + jsonObjectstep.getString("shortDescription")
                                            + jsonObjectstep.getString("description"));


                                }
                            }
                        } catch (Exception e) {

                        }
                        Log.i("mmmmm", "mmmmm9");

                        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        BackingAdapter moviesAdapter = new BackingAdapter(MainActivity.this, backingItem/*, stepsList, ingrediantsList*/);
                        mRecyclerView.setAdapter(moviesAdapter);
                        moviesAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                return map;
            }
        };
        Singleton.getInstance(MainActivity.this).addRequestQue(stringRequest);
        Log.i("hhhhhhh", "hhhhhhhhh");
        Log.i(";;;;;;,", ingrediantsList.size() + "");
        for (int i = 0; i < ingrediantsList.size(); i++) {

            Log.i("elemnts ", ingrediantsList.get(i).getIngredient() + ingrediantsList.get(i)
                    .getMeasure() + ingrediantsList.get(i).getQuantity());
        }

    }

}
