package com.example.activitease;


//Found a youtube tutorial and followed it. Once I finished it, I realized it was close to what I needed.
 //So I modified the code to what we need. The video can be found at the following link;
 //https://www.google.com/url?q=https://www.youtube.com/watch?v%3DK6cYSNXb9ew&sa=D&ust=1551724256967000&usg=AFQjCNHxZrvyV34V3A4RDjcORVgYKXne9w



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
    private static final String TABLE_NAME = "Interests";


    //The following were private, but made public for the testing.
    private static final String Id = "id";
    private static final String Col1 = "interestName";
    private static final String Col2 = "periodFreq";
    private static final String Col3 = "activityLength";
    private static final String Col4 = "activityAmount";
    private static final String Col5 = "numNotifications";
    private static final String Col6 = "numNotificationSpan";


    public User(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
//        String CREATE_INTERESTS_TABLE = "CREATE TABLE " + TABLE_INTERESTS + "(" + KEY_ID
//                + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_AMOUNT + " TEXT," + KEY_PERIOD + " TEXT, "
//                + KEY_LENGTH + " TEXT, " + KEY_NOTIFICATIONS + " TEXT "+ ")";
        String CREATE_INTERESTS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + Id
                + " INTEGER PRIMARY KEY," + Col1 + " TEXT, " + Col2 + " TEXT, " + Col3 + " TEXT, "
                + Col4 + " TEXT, " + Col5 + " TEXT, " + Col6 + " TEXT " + ")";
        db.execSQL(CREATE_INTERESTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        if(i > i1)
            db.execSQL("ALTER TABLE TABLE_NAME ADD COLUMN new_column INTEGER DEFAULT 0");
    }

    void addInterest(Interest interest){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Col1, interest.getInterestName());
        values.put(Col2, interest.getPeriodFreq());
        values.put(Col3, interest.getActivityLength());
        values.put(Col4, interest.getActivityAmount());
        values.put(Col5, interest.getNumNotifications());
        values.put(Col6, interest.getNumNotificationSpan());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    //Intended to be a User's interest search, currently only pulls the first interest
    public Interest getInterest(String interestName){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
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
                cursor.getString(2), Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),  Integer.parseInt(cursor.getString(5)), cursor.getString(6));

        db.close();
        return interest;
    }

    public List<Interest> getAllInterests(){
        List<Interest> interestList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Interest interest = new Interest();
                interest.setInterestName(cursor.getString(1));
                interest.setPeriodFreq(cursor.getString(2));
                interest.setActivityLength(Integer.parseInt(cursor.getString(3)));
                interest.setActivityAmount(Integer.parseInt(cursor.getString(4)));
                interest.setNumNotifications(Integer.parseInt(cursor.getString(5)));
                interest.setNumNotificationSpan(cursor.getString(6));

                interestList.add(interest);
            }
            while(cursor.moveToNext());
        }

        return interestList;
    }

    public int updateInterest(Interest interest){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Col1, interest.getInterestName());
        values.put(Col2, interest.getPeriodFreq());
        values.put(Col3, interest.getActivityLength());
        values.put(Col4, interest.getNumNotificationSpan());
        values.put(Col5, interest.getNumNotifications());
        values.put(Col6, interest.getNumNotificationSpan());
        return db.update(TABLE_NAME, values, Col1 + "=?",
                new String[]{String.valueOf(interest.getInterestName())});
    }

    public void deleteInterest(Interest interest){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, Col1 + "=?",
                new String[]{String.valueOf(interest.getInterestName())});
        db.close();
    }

    public int getInterestCount(Interest interest){
        String countQuery = "Select  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();

    }
}
