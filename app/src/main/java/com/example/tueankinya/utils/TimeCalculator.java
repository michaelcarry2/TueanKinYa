package com.example.tueankinya.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeCalculator {
    public static long calculateDays(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        try {
            Date dateStart = sdf.parse(startDate);
            Date dateEnd = sdf.parse(endDate);

            long diffInMillies = Math.abs(dateEnd.getTime() - dateStart.getTime());
            return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("TimeCalculator", "Error parsing date: " + e.getMessage());

            SimpleDateFormat sdfFallback = new SimpleDateFormat("dd MMM yyyy", new Locale("th", "TH"));

            try {
                Date dateStart = sdfFallback.parse(startDate);
                Date dateEnd = sdfFallback.parse(endDate);

                long diffInMillies = Math.abs(dateEnd.getTime() - dateStart.getTime());
                return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            } catch (ParseException ex) {
                ex.printStackTrace();
                return -1;
            }
        }
    }

    public static long calculateDaysWithNonNoneValues(String morning, String daytime, String evening, String nighttime) {
        if (morning.equals("none") && daytime.equals("none") && evening.equals("none") && nighttime.equals("none")) {
            return -1;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());

        try {
            Date dateStart = sdf.parse(morning.equals("none") ? daytime : morning);
            Date dateEnd = sdf.parse(evening.equals("none") ? nighttime : evening);

            long diffInMillies = Math.abs(dateEnd.getTime() - dateStart.getTime());
            return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("TimeCalculator", "Error parsing date: " + e.getMessage());
            return -1;
        }
    }
}
