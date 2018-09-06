package com.zamanak.shamimsalamat.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.zamanak.shamimsalamat.api.result.RecentWinners;
import com.zamanak.shamimsalamat.R;

import java.util.ArrayList;

public class ChanceTreeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<RecentWinners> items;
    private Context context;
    private myViewHolder myViewHolder;
    private View itemView;

    public ChanceTreeAdapter(Context context, ArrayList<RecentWinners> item) {
        this.items = item;
        this.context = context;
    }

    @Override
    public int getItemCount() {

        return items.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        RecentWinners obj = items.get(position);
        myViewHolder = (myViewHolder) holder;
        myViewHolder.tvWinnerName.setText(obj.getName());
        myViewHolder.tvWinnerDate.setText(obj.getDate());
        myViewHolder.tvPrizeType.setText(obj.getAward() +" "+ obj.getDistance());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {


        itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_chance_tree_winners, viewGroup, false);
        return new myViewHolder(itemView);

    }


    static class myViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvWinnerName;
        AppCompatTextView tvWinnerDate;
        AppCompatTextView tvPrizeType;

        myViewHolder(View convertView) {
            super(convertView);

            tvWinnerName = convertView.findViewById(R.id.tv_winner_name);
            tvWinnerDate = convertView.findViewById(R.id.tv_winner_date);
            tvPrizeType =  convertView.findViewById(R.id.tv_prize_type);
            //ButterKnife.bind(this, convertView);
        }

    }

}