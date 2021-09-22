package com.sansany.dynamictablelayout;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseController {
    private final MainActivity ourcontext;
    private MyDbHelper dbhelper;
    private SQLiteDatabase database;

    public DatabaseController( MainActivity c) {
        ourcontext = c;
    }

    public DatabaseController open() throws SQLException {
        dbhelper = new MyDbHelper(ourcontext);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbhelper.close();
    }

    public Cursor insertData(String name, String lname, String memo) {
        ContentValues cv = new ContentValues();
        cv.put(MyDbHelper.MEMBER_FIRSTNAME, name);
        cv.put(MyDbHelper.MEMBER_LASTNAME, lname);
        cv.put(MyDbHelper.MEMBER_Memo, memo);
        database.insert(MyDbHelper.TABLE_MEMBER, null, cv);

        return null;
    }

    public Cursor readEntry() {
        String[] allColumns = new String[] { MyDbHelper.MEMBER_ID,
                MyDbHelper.MEMBER_FIRSTNAME,
                MyDbHelper.MEMBER_LASTNAME,
                MyDbHelper.MEMBER_Memo };
        Cursor cursor = database.query(MyDbHelper.TABLE_MEMBER, allColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToNext();
        }
        assert cursor != null;
        cursor.close();
        return cursor;
    }

    @SuppressLint("Range")
    public String[] selectData(){
        database = dbhelper.getReadableDatabase();
        //String selectQuery = "select * from " + MyDbHelper.TABLE_MEMBER;
        //Cursor 사용
        //Cursor cursor = database.rawQuery(selectQuery,null);
        Cursor cursor = database.query(
                MyDbHelper.TABLE_MEMBER,
                null,
                null,
                null,
                null,
                null,
                null);
        //Column 정보를 배열에 넣고
        String[] columnName = {MyDbHelper.MEMBER_ID,
                MyDbHelper.MEMBER_FIRSTNAME,
                MyDbHelper.MEMBER_LASTNAME,
                MyDbHelper.MEMBER_Memo};

        //column 정보와 길이가 같은 배열을 생성 후
        String[] returnValue = new String[columnName.length];
        //생성한 배열에 데이터를 받아줌
        while (cursor.moveToNext()){
            for (int i = 0; i < returnValue.length; i++){
                returnValue[i] = cursor.getString(cursor.getColumnIndex(columnName[i]));

                Log.e("Database Select : ", i + "-" + returnValue[i]);
            }
        }
        //Cursor를 사용 후 꼭 닫아줌
        cursor.close();

        return returnValue;
    }
}
