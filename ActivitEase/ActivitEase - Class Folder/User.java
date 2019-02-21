package com.example.databasefucking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class User extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "InterestsManager";
    private static final String TABLE_INTERESTS = "Interests ";
    private static final String KEY_NAME = "interestName";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_PERIOD = "period";
    private static final String KEY_LENGTH = "length";
    private static final String KEY_NOTIFICATIONS = "numNotifications";



    public User(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_INTERESTS_TABLE = "CREATE TABLE " + TABLE_INTERESTS + "(" + KEY_NAME
                + " INTEGER PRIMARY KEY," + KEY_AMOUNT + " TEXT," + KEY_PERIOD + " TEXT, "
                + KEY_LENGTH + " TEXT, " + KEY_NOTIFICATIONS + " TEXT "+ ")";
        db.execSQL(CREATE_INTERESTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INTERESTS);

        onCreate(db);
    }

    void addInterest(Interest interest){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, interest.getInterestName());
        values.put(KEY_AMOUNT, interest.getActivityAmount());
        values.put(KEY_PERIOD, interest.getActivityPeriod());
        values.put(KEY_LENGTH, interest.getActivityLength());
        values.put(KEY_NOTIFICATIONS, interest.getNumNotifications());

        db.insert(TABLE_INTERESTS, null, values);
        db.close();
    }

    Interest getInterest(String interestName){
        SQLiteDatabase db = this.getReadableDatabase();


        //Originally, Cursor was an error. At the end, verify that the user imported the Cursor class.


        Cursor cursor = db.query(TABLE_INTERESTS, new String[]{KEY_NAME, KEY_AMOUNT, KEY_PERIOD,
                KEY_LENGTH, KEY_NOTIFICATIONS}, KEY_NAME + "=?",
                new String[]{String.valueOf(interestName)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Interest interest = new Interest(cursor.getString(0),
                Integer.parseInt(cursor.getString(1)), cursor.getString(2),
                Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)));
        return interest;
    }

    public List<Interest> getAllInterests(){
        List<Interest> interestList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_INTERESTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Interest interest = new Interest();
                interest.setInterestName(cursor.getString(0));
                interest.setActivityAmount(Integer.parseInt(cursor.getString(1)));
                interest.setActivityPeriod(cursor.getString(2));
                interest.setActivityLength(Integer.parseInt(cursor.getString(3)));
                interest.setNumNotifications(Integer.parseInt(cursor.getString(4)));

                interestList.add(interest);
            }
            while(cursor.moveToNext());
        }

        return interestList;
    }

    public int updateInterest(Interest interest){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AMOUNT, interest.getActivityAmount());
        values.put(KEY_PERIOD, interest.getActivityPeriod());
        values.put(KEY_LENGTH, interest.getActivityLength());
        values.put(KEY_NOTIFICATIONS, interest.getNumNotifications());

        return db.update(TABLE_INTERESTS, values, KEY_NAME + "=?",
                new String[]{String.valueOf(interest.getInterestName())});
    }

    public void deleteInterest(Interest interest){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INTERESTS, KEY_NAME + "=?",
                new String[]{String.valueOf(interest.getInterestName())});
        db.close();
    }

    public int getInterestCount(Interest interest){
        String countQuery = "Select  * FROM " + TABLE_INTERESTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();

    }
}
