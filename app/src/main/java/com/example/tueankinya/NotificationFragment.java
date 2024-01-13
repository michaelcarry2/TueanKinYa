package com.example.tueankinya;

import android.graphics.Typeface;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.app.DatePickerDialog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tueankinya.dao.DrugDatabaseHelper;
import com.example.tueankinya.model.DrugTime;

public class NotificationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private DrugTime drugTime;
    private EditText drugName;
    private TextView BeforeTime, AfterTime, buttonEndTime, buttonStartTime;
    private Button submitButton, MorningTime, NightTime, DayTime, EveningTime;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());

    public NotificationFragment() {
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        drugName = view.findViewById(R.id.input_drug_name);
        buttonStartTime = view.findViewById(R.id.button_start_time);
        buttonEndTime = view.findViewById(R.id.button_end_time);
        MorningTime = view.findViewById(R.id.morning_time);
        DayTime = view.findViewById(R.id.day_time);
        EveningTime = view.findViewById(R.id.evening_time);
        NightTime = view.findViewById(R.id.night_time);
        BeforeTime = view.findViewById(R.id.before_time);
        AfterTime = view.findViewById(R.id.after_time);
        submitButton = view.findViewById(R.id.summit_button);

        buttonStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(buttonStartTime, null, true);
            }
        });

        buttonEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(buttonEndTime, buttonStartTime.getText().toString(), false);
            }
        });

        MorningTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(MorningTime);
            }
        });

        DayTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(DayTime);
            }
        });

        EveningTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(EveningTime);
            }
        });

        NightTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker(NightTime);
            }
        });

        BeforeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BeforeTime.isSelected()) {
                    clearTextView(BeforeTime);
                } else {
                    targetTextViewClick(BeforeTime);
                }
            }
        });

        AfterTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AfterTime.isSelected()) {
                    clearTextView(AfterTime);
                } else {
                    targetTextViewClick(AfterTime);
                }
            }
        });

        if (getArguments() != null) {
            drugTime = getArguments().getParcelable("drugTimeModel");
            if (drugTime != null) {
                updateFromDrugTimeModel(drugTime);
            }
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotificationToDatabase();
            }
        });

        return view;
    }

    private void showDatePicker(final TextView targetTextView, String startDateString, final boolean isStartTime) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar startDateCalendar = null;
        if (startDateString != null && !startDateString.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            try {
                Date startDate = sdf.parse(startDateString);
                if (startDate != null) {
                    startDateCalendar = Calendar.getInstance();
                    startDateCalendar.setTime(startDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        String selectedDate = selectedDay + " " + getMonthName(selectedMonth) + " " + selectedYear;
                        targetTextView.setText(selectedDate);

                        if (isStartTime) {
                            buttonStartTime.setBackgroundResource(R.drawable.active_border);
                        } else {
                            buttonEndTime.setBackgroundResource(R.drawable.active_border);
                        }
                    }
                },
                year, month, day
        );

        if (startDateCalendar != null) {
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }

        datePickerDialog.show();
    }

    private void showTimePicker(final TextView targetTextView) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar selectedTime = Calendar.getInstance();
                        selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedTime.set(Calendar.MINUTE, minute);

                        String formattedTime = timeFormat.format(selectedTime.getTime());
                        targetTextView.setText(formattedTime);

                        targetTextView.setBackgroundResource(R.drawable.rounder_primary_gb);
                        targetTextView.setTypeface(null, Typeface.BOLD);
                    }
                },
                hour, minute, false
        );

        timePickerDialog.show();
    }

    private void targetTextViewClick(TextView targetTextView) {
        targetTextView.setSelected(true);
        targetTextView.setBackgroundResource(R.drawable.rounder_primary_gb);
        targetTextView.setTypeface(null, Typeface.BOLD);
    }

    private void clearTextView(TextView targetTextView) {
        targetTextView.setSelected(false);
        targetTextView.setBackgroundResource(R.drawable.rounder_gray_bg);
        targetTextView.setTypeface(null, Typeface.BOLD);
    }

    private String getMonthName(int month) {
        String[] monthNames = {"ม.ค", "ก.พ", "มี.ค", "เม.ย", "พ.ค", "มิ.ย", "ก.ค", "ส.ค", "ก.ย", "ต.ค", "พ.ย", "ธ.ค"};
        return monthNames[month];
    }

    private void updateFromDrugTimeModel(DrugTime drugTimeModel) {
        buttonStartTime.setText(drugTimeModel.getStartTime());
        buttonEndTime.setText(drugTimeModel.getEndTime());
        MorningTime.setText(drugTimeModel.getMorning());
        DayTime.setText(drugTimeModel.getDay());
        EveningTime.setText(drugTimeModel.getEvening());
        NightTime.setText(drugTimeModel.getNight());

        if (drugTimeModel.getBeforeTime() != null && drugTimeModel.getBeforeTime()) {
            targetTextViewClick(BeforeTime);
        } else {
            clearTextView(BeforeTime);
        }

        if (drugTimeModel.getAfterTime() != null && drugTimeModel.getAfterTime()) {
            targetTextViewClick(AfterTime);
        } else {
            clearTextView(AfterTime);
        }
    }

    private void saveNotificationToDatabase() {
        String drugNameValue = drugName.getText().toString();
        String startTime = buttonStartTime.getText().toString();
        String endTime = buttonEndTime.getText().toString();
        String morningTime = MorningTime.getText().toString();
        String dayTime = DayTime.getText().toString();
        String eveningTime = EveningTime.getText().toString();
        String nightTime = NightTime.getText().toString();
        Boolean beforeTimeSelected = BeforeTime.isSelected();
        Boolean afterTimeSelected = AfterTime.isSelected();

        if (TextUtils.isEmpty(drugNameValue) || TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            return;
        }

        DrugTime drugTime = new DrugTime(
                0,
                drugNameValue,
                startTime,
                endTime,
                morningTime,
                dayTime,
                eveningTime,
                nightTime,
                beforeTimeSelected,
                afterTimeSelected
        );
        try (DrugDatabaseHelper dbHelper = new DrugDatabaseHelper(requireContext())) {
            long rowId = dbHelper.insertDrug(drugTime);

            if (rowId > 0) {
                Toast.makeText(requireContext(), "บันทึกสำเร็จ", Toast.LENGTH_SHORT).show();

                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HistoryFragment historyFragment = new HistoryFragment();
                fragmentTransaction.replace(R.id.frame_layout, historyFragment);
                fragmentTransaction.replace(R.id.frame_layout, historyFragment, "HistoryFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            } else {
                Toast.makeText(requireContext(), "เกิดข้อผิดพลาดในการบันทึก", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}