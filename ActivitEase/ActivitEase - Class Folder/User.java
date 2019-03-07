package com.example.activitease;

/*
Found a youtube tutorial and followed it. Once I finished it, I realized it was close to what I needed.
 So I modified the code to what we need. The video can be found at the following link;
 https://www.google.com/url?q=https://www.youtube.com/watch?v%3DK6cYSNXb9ew&sa=D&ust=1551724256967000&usg=AFQjCNHxZrvyV34V3A4RDjcORVgYKXne9w
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class User extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "InterestManager";

    //The following were private, but made public for the testing.
    private static final String TABLE_INTERESTS = "Interests";
    private static final String KEY_ID = "id";
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
//        String CREATE_INTERESTS_TABLE = "CREATE TABLE " + TABLE_INTERESTS + "(" + KEY_ID
//                + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_AMOUNT + " TEXT," + KEY_PERIOD + " TEXT, "
//                + KEY_LENGTH + " TEXT, " + KEY_NOTIFICATIONS + " TEXT "+ ")";
        String CREATE_INTERESTS_TABLE = "CREATE TABLE " + TABLE_INTERESTS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + " interestName TEXT, amount TEXT, period TEXT, length TEXT, numNotifications TEXT)";
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
//        db.close();
    }


    //Intended to be a User's interest search, currently only pulls the first interest
    public Interest getInterest(String interestName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INTERESTS, null);
        cursor.moveToFirst();

        while(cursor != null){
            if(interestName != cursor.getString(1))
                break;
            else
                cursor.moveToNext();
        }

//        if(cursor != null){
//            cursor.moveToFirst();
//        }

        Interest interest = new Interest(cursor.getString(1),
                Integer.parseInt(cursor.getString(2)), cursor.getString(3),
                Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)));

        db.close();
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
                interest.setInterestName(cursor.getString(1));
                interest.setActivityAmount(Integer.parseInt(cursor.getString(2)));
                interest.setActivityPeriod(cursor.getString(3));
                interest.setActivityLength(Integer.parseInt(cursor.getString(4)));
                interest.setNumNotifications(Integer.parseInt(cursor.getString(5)));

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
