package com.example.activitease;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class User extends SQLiteOpenHelper {

//    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "InterestManager";
    private static final String TABLE_NAME = "Interestss";
    private static final String Id = "id";
    private static final String Col1 = "interestName";
    private static final String Col2 = "amount";
    private static final String Col3 = "period";
    private static final String Col4 = "length";
    private static final String Col5 = "numNotifications";



    public User(Context context){
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createInterestsTable = "CREATE TABLE " + TABLE_NAME + "(" + Id
                + " INTEGER PRIMARY KEY," + Col1 + " TEXT," + Col2 + " TEXT," + Col3 + " TEXT, "
                + Col4 + " TEXT, " + Col5 + " TEXT "+ ")";
        db.execSQL(createInterestsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    void addInterest(Interest interest){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put(Id, interest.getId());
        values.put(Col1, interest.getInterestName());
        values.put(Col2, interest.getPeriodFreq());
        values.put(Col3, interest.getBasePeriodSpan());
        values.put(Col4, interest.getActivityLength());
        values.put(Col5, interest.getNumNotifications());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    //Wait Anthony you didn't have this public, so you added that to this function
//    public Interest getInterest(String interestName){
//        SQLiteDatabase db = this.getReadableDatabase();
//
//
//        //Originally, Cursor was an error. At the end, verify that the user imported the Cursor class.
//
//
//        Cursor cursor = db.query(TABLE_NAME, new String[]{Col1, Col2, Col3,
//                        Col4, Col5}, Col1 + "=?",
//                new String[]{String.valueOf(interestName)}, null, null, null, null);
//
//        if(cursor != null){
//            cursor.moveToFirst();
//        }
//
//        Interest interest = new Interest(cursor.getString(0),
//                Integer.parseInt(cursor.getString(1)), cursor.getString(2),
//                Integer.parseInt(cursor.getString(3)), Integer.parseInt(cursor.getString(4)));
//        return interest;
//    }

    public List<Interest> getAllInterests(){
        List<Interest> interestList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do {
                Interest interest = new Interest();
                interest.setInterestName(cursor.getString(0));
                interest.setPeriodFreq(Integer.parseInt(cursor.getString(1)));
                interest.setBasePeriodSpan(Integer.parseInt(cursor.getString(2)));
                interest.setActivityLength(Integer.parseInt(cursor.getString(3)));
                interest.setNumNotifications(Integer.parseInt(cursor.getString(4)));

                interestList.add(interest);
            }
            while(cursor.moveToNext());
        }

        return interestList;
    }

//    public int updateInterest(Interest interest){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(Col2, interest.getActivityAmount());
//        values.put(Col3, interest.getActivityPeriod());
//        values.put(Col4, interest.getActivityLength());
//        values.put(Col5, interest.getNumNotifications());
//
//        return db.update(TABLE_NAME, values, Col1 + "=?",
//                new String[]{String.valueOf(interest.getInterestName())});
//    }

    public void deleteInterest(Interest interest){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, Id + "=?",
                new String[]{String.valueOf(interest.getId())});
        db.close();
    }

    public int getInterestCount(){
        String countQuery = "Select  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();

    }


/* The following is the original code*/

//
//    public void deleteInterest(Interest interest){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, Col1 + "=?",
//                new String[]{String.valueOf(interest.getInterestName())});
//        db.close();
//    }
//
//    public int getInterestCount(Interest interest){
//        String countQuery = "Select  * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
//
//        return cursor.getCount();
//
//    }
}
