package com.sansany.dynamictablelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private EditText etxtName;
    private EditText etxtLastName;
    private EditText etxtMemo;

    private final String[] header = {"_id", "firstname", "lastname", "memo"};
    private final ArrayList<String[]> rows = new ArrayList<>();
    private TableDynamic tableDynamic;
    private MyDbHelper db;
    private SQLiteDatabase database;
    private DatabaseController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MyDbHelper(this);
        controller = new DatabaseController(this);

        tableLayout = findViewById(R.id.table);
        etxtName = findViewById(R.id.txt_Name);
        etxtName.requestFocus();
        etxtLastName = findViewById(R.id.txt_LastName);
        etxtMemo = findViewById(R.id.txt_memo);
        Button button = findViewById(R.id.btn_01);

        dataTables();
    }

    public void dataTables(){
        tableLayout.removeAllViews();
        tableDynamic = new TableDynamic(tableLayout, getApplicationContext());
        tableDynamic.addHeader(header);
        tableDynamic.addData(selectName());
        tableDynamic.backgroundHeader(Color.BLUE);
        tableDynamic.backgroundData(Color.LTGRAY, Color.CYAN);
        tableDynamic.lineColor(Color.BLUE);
        tableDynamic.textColorData(Color.BLUE);
        tableDynamic.textColorHeader(Color.WHITE);
    }

    public void save( View view ) {
        if (etxtName == null || etxtName.length() == 0) {
            Toast.makeText(this, "데이터를 입력 하세요?", Toast.LENGTH_SHORT).show();
        } else {
            etxtName.requestFocus();
            insertData();
            addItem();
        }
    }

    public void addItem(){
        String[] lastItems = null;
        database = db.getReadableDatabase();
        String selectQuery = "select * from " + MyDbHelper.TABLE_MEMBER;
        //Cursor 사용
        Cursor cursor = database.rawQuery(selectQuery, null);

        cursor.moveToLast();
        String[] itemAdd = new String[]{
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3)};
        tableDynamic.addItems(itemAdd);
        tableDynamic.lineColor(Color.BLUE);

        cursor.close();
        database.close();
    }

    public ArrayList<String[]> selectName(){
        String[] fullName = null;
        database = db.getReadableDatabase();
        String selectQuery = "select * from " + MyDbHelper.TABLE_MEMBER;
        //Cursor 사용
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            fullName = new String[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                fullName[i] = cursor.getString(0);
                i++;
                rows.add(new String[]{
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)});
            }
        }
        cursor.close();
        database.close();

        return rows;
    }

    private void insertData() {
        String fName = etxtName.getText().toString();
        String lName = etxtLastName.getText().toString();
        String memo = etxtMemo.getText().toString();

        controller.open();
        Cursor cursorInsert = controller.insertData(fName, lName, memo);

        etxtName.setText("");
        etxtLastName.setText("");
        etxtMemo.setText("");

        controller.close();

    }
}