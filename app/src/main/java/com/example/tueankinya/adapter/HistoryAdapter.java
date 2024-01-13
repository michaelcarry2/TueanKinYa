package com.example.tueankinya.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tueankinya.R;
import com.example.tueankinya.model.DrugTime;
import com.example.tueankinya.utils.TimeCalculator;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<DrugTime> drugTimeList;

    public HistoryAdapter(List<DrugTime> drugTimeList) {
        this.drugTimeList = drugTimeList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView drugNameTextView;
        TextView calculateDayTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drugNameTextView = itemView.findViewById(R.id.drug_name2);
            calculateDayTextView = itemView.findViewById(R.id.calculate_day);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drug_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrugTime drugTime = drugTimeList.get(position);
        holder.drugNameTextView.setText(drugTime.getDrugName());

        long daysDifference = TimeCalculator.calculateDays(drugTime.getStartTime(), drugTime.getEndTime());
        if (daysDifference != -1) {
            String schedule = "เป็นเวลา " + daysDifference + " วัน";
            holder.calculateDayTextView.setText(schedule);
        } else {
            holder.calculateDayTextView.setText("ไม่สามารถคำนวณวันได้");
        }
    }

    @Override
    public int getItemCount() {
        return drugTimeList.size();
    }

}