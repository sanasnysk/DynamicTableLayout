package com.sansany.dynamictablelayout;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {
    // TABLE INFORMATION
    public static final String TABLE_MEMBER = "member";
    public static final String MEMBER_ID = "_id";
    public static final String MEMBER_FIRSTNAME = "firstname";
    public static final String MEMBER_LASTNAME = "lastname";
    public static final String MEMBER_Memo = "memo";

    // DATABASE INFORMATION
    static final String DB_NAME = "MEMBER.DB";
    static final int DB_VERSION = 1;

    // TABLE CREATION STATEMENT

    private static final String CREATE_TABLE = "create table " + TABLE_MEMBER
            + "(" + MEMBER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEMBER_FIRSTNAME + " TEXT NOT NULL ," + MEMBER_LASTNAME
            + " TEXT NOT NULL," + MEMBER_Memo + " TEXT)";


    public MyDbHelper( MainActivity context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMBER);
        onCreate(db);


    }
}
