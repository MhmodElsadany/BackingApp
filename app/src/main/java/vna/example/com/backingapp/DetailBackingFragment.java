package vna.example.com.backingapp;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import vna.example.com.backingapp.Models.IngrediantItem;
import vna.example.com.backingapp.Models.StepItem;

import static android.content.Context.MODE_PRIVATE;

/**
 * A placeholder fragment containing a simple view.
 */


class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailItemRowHolder> {
    ArrayList<String> stepItemsllist;
    Context mcontext;
    ArrayList<StepItem> stepsList = new ArrayList<>();
    DetailAdapter.Listner listner = null;

    public DetailAdapter(Context mcontext, ArrayList<String> stepItemsllist, ArrayList<StepItem> stepsList) {
        this.mcontext = mcontext;
        this.stepItemsllist = stepItemsllist;
        this.stepsList = stepsList;
    }

    @Override
    public DetailAdapter.DetailItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_row, null);
        DetailAdapter.DetailItemRowHolder ALRH = new DetailAdapter.DetailItemRowHolder(v);
        return ALRH;

    }

    @Override
    public void onBindViewHolder(DetailAdapter.DetailItemRowHolder holder, final int position) {
        holder.step_Des_name.setText(stepItemsllist.get(position));

        if (!holder.step_Des_name.getText().equals(stepItemsllist.get(0)))
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.setData(stepsList.get(position - 1).getId(),
                            stepsList.get(position - 1).getVideoURL(), stepsList.get(position - 1).getDescription());

                }
            });

    }

    public void setLithner(DetailAdapter.Listner lithner) {
        this.listner = lithner;
    }


    public interface Listner {
        void setData(String id, String videoURL, String Description);
    }

    @Override
    public int getItemCount() {

        return stepItemsllist.size();
    }

    public class DetailItemRowHolder extends RecyclerView.ViewHolder {
        TextView step_Des_name;
        CardView mCardView;

        public DetailItemRowHolder(View itemView) {
            super(itemView);

            step_Des_name = (TextView) itemView.findViewById(R.id.text);

            mCardView = (CardView) itemView.findViewById(R.id.card_txt);
        }
    }

}


public class DetailBackingFragment extends Fragment implements DetailAdapter.Listner {
    public String TAG = "DetailBackingFragment";
    ArrayList<IngrediantItem> ingrediantsList = new ArrayList<>();
    public static ArrayList<StepItem> stepsList = new ArrayList<>();
    RecyclerView recyclerView;
    ArrayList<String> stepsLlist = new ArrayList<>();
    static ActivitiListener activitiListener = null;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public DetailBackingFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ActivitiListener) {
            activitiListener = (ActivitiListener) context;
        } else {
            System.out.print("errrror occurd");
        }
    }

    public interface ActivitiListener {
        void setDtaFragment(String id, String videoURL, String Description);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_detail, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.steps_detai_llist);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(getActivity().getIntent().getStringExtra("name"));
        Log.i(TAG, "onCreateView : " + getActivity().getIntent().getStringExtra("name"));
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setHomeButtonEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        try {
                            Log.i("data", response);

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String name = jsonObject1.getString("name");
                                String id = jsonObject1.getString("id");

                                if (name.equals(getActivity().getIntent().getStringExtra("name"))) {
                                    editor = sharedPreferences.edit();
                                    editor.putString("idd", id);
                                    Log.i("id", id);
                                    editor.commit();
                                    Log.i(TAG, "found recipe " + name);
                                    Intent intent = new Intent(getActivity(), MyWidgetProvider.class);
                                    intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                                    intent.putExtra("name", name);

                                    int[] ids = AppWidgetManager.getInstance(getActivity()).getAppWidgetIds(new ComponentName(getActivity(), MyWidgetProvider.class));
                                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                                    getActivity().sendBroadcast(intent);

                                    JSONArray jsonArrayIngrediant = jsonObject1.getJSONArray("ingredients");
                                    JSONArray jsonArrayStep = jsonObject1.getJSONArray("steps");

                                    if (ingrediantsList != null || stepsList != null) {
                                        ingrediantsList.clear();
                                        stepsList.clear();
                                    }
                                    for (int j = 0; j < jsonArrayIngrediant.length(); j++) {
                                        JSONObject jsonObjectinteg = jsonArrayIngrediant.getJSONObject(j);
                                        ingrediantsList.add(new IngrediantItem
                                                (jsonObjectinteg.getString("quantity"), jsonObjectinteg.getString("measure"),
                                                        jsonObjectinteg.getString("ingredient")));
                                        Log.i("الوصفات", jsonObjectinteg.getString("quantity") + jsonObjectinteg.getString("measure")
                                                + jsonObjectinteg.getString("ingredient"));

                                    }
                                    int posit = 1;
                                    int id_step = 0;
                                    for (int j = 0; j < jsonArrayStep.length(); j++) {
                                        JSONObject jsonObjectstep = jsonArrayStep.getJSONObject(j);
                                        id_step = Integer.parseInt(jsonObjectstep.getString("id"));
                                        if (posit != id_step + 1) {
                                            id_step = posit - 1;
                                        }
                                        posit++;
                                        stepsList.add(new StepItem
                                                (id_step + "", jsonObjectstep.getString("shortDescription"),
                                                        jsonObjectstep.getString("description")
                                                        , jsonObjectstep.getString("videoURL"),
                                                        jsonObjectstep.getString("thumbnailURL")));

                                        Log.i("steps", jsonObjectstep.getString("id") + jsonObjectstep.getString("shortDescription")
                                                + jsonObjectstep.getString("description"));


                                    }
                                }
                            }
                        } catch (Exception e) {

                        }
                        if (stepsLlist != null) {
                            stepsLlist.clear();
                        }
                        String integerateItem = "";
                        for (int i = 0; i < ingrediantsList.size(); i++) {
                            integerateItem += ingrediantsList.get(i).getQuantity() + "  " + ingrediantsList.get(i).getMeasure() + "  " +
                                    ingrediantsList.get(i).getIngredient() + "\n";
                        }

                        stepsLlist.add(integerateItem);

                        for (int i = 0; i < stepsList.size(); i++) {
                            String steping = stepsList.get(i).getShortDescription();
                            stepsLlist.add(steping);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        DetailAdapter mDetailAdapter = new DetailAdapter(getActivity(), stepsLlist, stepsList);
                        mDetailAdapter.setLithner(DetailBackingFragment.this);
                        recyclerView.setAdapter(mDetailAdapter);
                        mDetailAdapter.notifyDataSetChanged();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                    }
                }) {
        };
        Singleton.getInstance(getActivity()).addRequestQue(stringRequest);


        return view;

    }

    @Override
    public void setData(String id, String videoURL, String Description) {

        DetailBackingFragment.activitiListener.setDtaFragment(id, videoURL, Description);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sharedPreferences = activity.getSharedPreferences("daataa", MODE_PRIVATE);


    }
}


/*       */

