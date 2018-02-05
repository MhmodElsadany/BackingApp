package vna.example.com.backingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Google       Company on 11/12/2017.
 */

/*public class DetailAdapte extends RecyclerView.Adapter<DetailAdapte.DetailItemRowHolder> {
    ArrayList<String> stepItemsllist;
    Context mcontext;
    ArrayList<StepItem> stepsList=new ArrayList<>();

    public DetailAdapte(Context mcontext, ArrayList<String> stepItemsllist, ArrayList<StepItem> stepsList) {
        this.mcontext = mcontext;
        this.stepItemsllist = stepItemsllist;
        this.stepsList=stepsList;
    }

    @Override
    public DetailAdapte.DetailItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_row, null);
        DetailAdapte.DetailItemRowHolder ALRH = new DetailAdapte.DetailItemRowHolder(v);
        return ALRH;

    }

    @Override
    public void onBindViewHolder(DetailAdapte.DetailItemRowHolder holder, final int position) {
        holder.step_Des_name.setText(stepItemsllist.get(position));

        if ( !holder.step_Des_name.getText() .equals(stepItemsllist.get(0)))
            holder.step_Des_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent detail = new Intent(mcontext, ActivityStepsDetail.class);

                    detail.putExtra("Id",stepsList.get(position-1).getId());
                    Log.i("gg",stepsList.get(position-1).getId());
                    detail.putExtra("Description",stepsList.get(position-1).getDescription());
                    Log.i("gg",stepsList.get(position-1).getDescription());
                    detail.putExtra("VideoURL",stepsList.get(position-1).getVideoURL());
                    detail.putExtra("id",stepsList.get(position-1).getId());
                    Log.i("gg",stepsList.get(position-1).getVideoURL());

                    mcontext.startActivity(detail);

                }
            });

    }

    @Override
    public int getItemCount() {

        return stepItemsllist.size();
    }

    public class DetailItemRowHolder extends RecyclerView.ViewHolder {
        TextView step_Des_name;

        public DetailItemRowHolder(View itemView) {
            super(itemView);

            step_Des_name = (TextView) itemView.findViewById(R.id.text);
        }
        }

    }

*/



