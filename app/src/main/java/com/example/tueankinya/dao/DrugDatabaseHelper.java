package com.example.tueankinya.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;

import com.example.tueankinya.model.DrugTime;

public class DrugDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "drug.sqlite";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_DRUG = "drug";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DRUG_NAME = "drug_name";
    public static final String COLUMN_START_TIME = "start_time";
    public static final String COLUMN_END_TIME = "end_time";
    public static final String COLUMN_MORNING = "morning";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_EVENING = "evening";
    public static final String COLUMN_NIGHT = "night";
    public static final String COLUMN_BEFORE_TIME = "before_time";
    public static final String COLUMN_AFTER_TIME = "after_time";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_DRUG + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_DRUG_NAME + " TEXT,"
            + COLUMN_START_TIME + " TEXT,"
            + COLUMN_END_TIME + " TEXT,"
            + COLUMN_MORNING + " TEXT,"
            + COLUMN_DAY + " TEXT,"
            + COLUMN_EVENING + " TEXT,"
            + COLUMN_NIGHT + " TEXT,"
            + COLUMN_BEFORE_TIME + " INTEGER,"
            + COLUMN_AFTER_TIME + " INTEGER"
            + ")";

    public DrugDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRUG);
        onCreate(db);
    }

    public long insertDrug(DrugTime drugTimeModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DRUG_NAME, drugTimeModel.getDrugName());
        values.put(COLUMN_START_TIME, drugTimeModel.getStartTime());
        values.put(COLUMN_END_TIME, drugTimeModel.getEndTime());
        values.put(COLUMN_MORNING, drugTimeModel.getMorning());
        values.put(COLUMN_DAY, drugTimeModel.getDay());
        values.put(COLUMN_EVENING, drugTimeModel.getEvening());
        values.put(COLUMN_NIGHT, drugTimeModel.getNight());
        values.put(COLUMN_BEFORE_TIME, drugTimeModel.getBeforeTime() ? 1 : 0);
        values.put(COLUMN_AFTER_TIME, drugTimeModel.getAfterTime() ? 1 : 0);

        long rowId = db.insert(TABLE_DRUG, null, values);
        db.close();
        return rowId;
    }
}