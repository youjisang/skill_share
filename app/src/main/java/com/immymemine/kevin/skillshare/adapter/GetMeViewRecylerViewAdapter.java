package com.immymemine.kevin.skillshare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.immymemine.kevin.skillshare.R;


import java.util.ArrayList;


/**
 * Created by JisangYou on 2017-12-07.
 */

public class GetMeViewRecylerViewAdapter extends RecyclerView.Adapter<GetMeViewRecylerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> skillsdata;

    public GetMeViewRecylerViewAdapter(Context context, ArrayList<String> skillsdata) {
        this.context = context;
        this.skillsdata = skillsdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item_list_skills, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            holder.textViewSkill.setText(skillsdata.get(position).toString());

    }

    @Override
    public int getItemCount() {
        return skillsdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewSkill;

        public ViewHolder(View itemView) {
            super(itemView);
//            FlexboxLayoutManager.LayoutParams flexboxLayoutParams =
//                    (FlexboxLayoutManager.LayoutParams) itemView.getLayoutParams();
//            flexboxLayoutParams.setFlexGrow(1.0f);
            textViewSkill = itemView.findViewById(R.id.text_view_skill);
        }
    }
}
