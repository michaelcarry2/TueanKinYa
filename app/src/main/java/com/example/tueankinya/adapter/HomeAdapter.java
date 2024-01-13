package com.example.tueankinya.adapter;

import android.view.ViewGroup;

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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private final List<DrugTime> drugTimeList;

    public HomeAdapter(List<DrugTime> drugTimeList) {
        this.drugTimeList = drugTimeList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView MorningTableTime, DayTableTime, EveningTableTime, NightTableTime;
        private TextView BeforeAfterEat;
        private TextView DrugName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            MorningTableTime = itemView.findViewById(R.id.morning_table_time);
            DayTableTime = itemView.findViewById(R.id.day_table_time);
            EveningTableTime = itemView.findViewById(R.id.evening_time);
            NightTableTime = itemView.findViewById(R.id.night_table_time);
            BeforeAfterEat =itemView.findViewById(R.id.before_after_eat);
            DrugName = itemView.findViewById(R.id.drug_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DrugTime drugTime = drugTimeList.get(position);

        // ตั้งค่าชื่อยาใน TextView
        holder.DrugName.setText(drugTime.getDrugName());



        // คุณอาจต้องการตั้งค่าค่าอื่น ๆ เช่น TableTime และ BeforeAfterEat ด้วย
        // holder.TableTime.setText(drugTime.getTableTime());
        // holder.BeforeAfterEat.setText(drugTime.getBeforeAfterEat());
    }

    @Override
    public int getItemCount() {
        return drugTimeList.size();
    }
}

