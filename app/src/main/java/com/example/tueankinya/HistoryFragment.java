package com.example.tueankinya;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.tueankinya.dao.DrugDatabaseHelper;
import com.example.tueankinya.utils.TimeCalculator;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tueankinya.adapter.HistoryAdapter;
import com.example.tueankinya.model.DrugTime;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    public HistoryFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyRecyclerView = view.findViewById(R.id.history_item);
        historyAdapter = new HistoryAdapter(getDrugTimeListFromDatabase());
        historyRecyclerView.setAdapter(historyAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        historyRecyclerView.setLayoutManager(layoutManager);

        return view;
    }

    private List<DrugTime> getDrugTimeListFromDatabase() {
        DrugDatabaseHelper dbHelper = new DrugDatabaseHelper(getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + DrugDatabaseHelper.TABLE_DRUG;

        Cursor cursor = db.rawQuery(query, null);

        List<DrugTime> drugTimeList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_ID));
                @SuppressLint("Range") String drugName = cursor.getString(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_DRUG_NAME));
                @SuppressLint("Range") String startTime = cursor.getString(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_START_TIME));
                @SuppressLint("Range") String endTime = cursor.getString(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_END_TIME));
                @SuppressLint("Range") String morning = cursor.getString(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_MORNING));
                @SuppressLint("Range") String daytime = cursor.getString(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_DAY));
                @SuppressLint("Range") String evening = cursor.getString(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_EVENING));
                @SuppressLint("Range") String nighttime = cursor.getString(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_NIGHT));
                @SuppressLint("Range") boolean beforeTime = cursor.getInt(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_BEFORE_TIME)) == 1;
                @SuppressLint("Range") boolean afterTime = cursor.getInt(cursor.getColumnIndex(DrugDatabaseHelper.COLUMN_AFTER_TIME)) == 1;

                DrugTime drugTime = new DrugTime(id, drugName, startTime, endTime, morning, daytime, evening, nighttime, beforeTime, afterTime);
                drugTimeList.add(drugTime);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return drugTimeList;
    }
}