package vna.example.com.backingapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import vna.example.com.backingapp.Models.BackingsItemModel;

/**
 * Created by Google       Company on 05/12/2017.
 */

public class BackingAdapter extends RecyclerView.Adapter<BackingAdapter.BackingItemRowHolder> {
    ArrayList<BackingsItemModel> backingItem;
  //  ArrayList<IngrediantItem> ingrediantsList;
   // ArrayList<StepItem> stepsList;
    Context mcontext;

    public BackingAdapter(Context mcontext, ArrayList<BackingsItemModel> backingItem
                          /*ArrayList<StepItem> stepsList, ArrayList<IngrediantItem> ingrediantsList*/) {
         this.mcontext = mcontext;
       // this.stepsList = stepsList;
       this.backingItem = backingItem;
       // this.ingrediantsList = ingrediantsList;
    }

    @Override
    public BackingAdapter.BackingItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.backing_row, null);
        BackingAdapter.BackingItemRowHolder ALRH = new BackingAdapter.BackingItemRowHolder(v);
        return ALRH;

    }

    @Override
    public void onBindViewHolder(BackingAdapter.BackingItemRowHolder holder, int position) {
        holder.Ingredient_name.setText(backingItem.get(position).getiIngredient_name());
    }

    @Override
    public int getItemCount() {

        return backingItem.size();
    }

    public class BackingItemRowHolder extends RecyclerView.ViewHolder {
        TextView Ingredient_name;
        FrameLayout mFrameLayout;
        public BackingItemRowHolder(View itemView) {
            super(itemView);

            Ingredient_name = (TextView) itemView.findViewById(R.id.Ingredient_name);
            mFrameLayout=(FrameLayout) itemView.findViewById(R.id.framing);
            mFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent detail = new Intent(mcontext, DetailBacking.class);
                   detail.putExtra("name",backingItem.get(getAdapterPosition()).getiIngredient_name());
                    mcontext.startActivity(detail);

                }
            });
        }

    }

}



