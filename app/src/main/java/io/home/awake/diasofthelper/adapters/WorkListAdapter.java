package io.home.awake.diasofthelper.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import io.home.awake.diasofthelper.R;

import static java.security.AccessController.getContext;


public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.ViewHolder> {
    private ArrayList<HashMap> mDataset;
    private Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mDateText;
        public TextView mWorkTimeText;
        private CardView mCardView;


        public ViewHolder(View v) {
            super(v);
            mCardView = (CardView) v.findViewById((R.id.card_view));
            mDateText = (TextView) v.findViewById(R.id.dateText);
            mWorkTimeText = (TextView) v.findViewById(R.id.workTimeText);
        }
    }

    public WorkListAdapter(ArrayList<HashMap> dataset, Context context) {
        mDataset = dataset;
        mContext = context;
    }

    @Override
    public WorkListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_item, parent, false);

        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat("dd.MM.yyyy");
            holder.mDateText.setText(mDataset.get(position).get("DateIn").toString());
            holder.mWorkTimeText.setText("Списано: " + mDataset.get(position).get("TimeInWork").toString());
            if (Double.parseDouble(mDataset.get(position).get("TimeInWork").toString()) >= 8) {
                holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.jobDoneColor));
            } else {
                holder.mCardView.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.jobUndoneColor));
            }
        }
        catch (Exception e){

        }


    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}