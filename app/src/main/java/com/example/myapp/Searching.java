package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class Searching extends AppCompatActivity {
    ArrayList<SubjectData> arr;
    ArrayList<String[]> arr2;
    ListView listview;
    Button btn_back;
    DBHelper dbhelp;
    SQLiteDatabase db;
    String input;
    Cursor cursor,cursor2;
    String[] info;
    ThreadDownload thd;
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        arr = new ArrayList<>();
        arr2 = new ArrayList<>();
        thd = new ThreadDownload();

        listview = findViewById(R.id.litsview);
        btn_back = findViewById(R.id.button_back);

        dbhelp = new DBHelper(getBaseContext());
        db = dbhelp.getReadableDatabase();

        input = getIntent().getStringExtra("fio");
        cursor = db.rawQuery("select * from cards_students where fio like '%"+input+"%' order by clc_birthdate desc;",null);
        cursor.moveToFirst();
        String url;
        if (cursor.getCount()>0) {
            do {
                cursor2 = db.rawQuery(String.format("select photo from cards_order where person_id=%d;", cursor.getInt(0)), null);
                cursor2.moveToFirst();
                if (cursor2.getCount() > 0) {
                    url = cursor2.getString(0);
                } else {
                    url = "null";
                }
                String text = cursor.getString(cursor.getColumnIndex("fio")) + " " + cursor.getString(cursor.getColumnIndex("clc_birthdate"));
                arr.add(new SubjectData(text,null,url,null,null));
                info = new String[]{
                        cursor.getString(cursor.getColumnIndex("fio")),
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("clc_birthdate")),
                        cursor.getString(cursor.getColumnIndex("doc")),
                        cursor.getString(cursor.getColumnIndex("dateendallow")),
                        url
                };
                arr2.add(info);
            } while (cursor.moveToNext());
        }
        if (arr.size()<1){
            ((ViewGroup)listview.getParent()).removeView(listview);
        }
        else {
            CustomAdapter adapter = new CustomAdapter(this, arr,arr2);
            listview.setAdapter(adapter);
        }
        btn_back = findViewById(R.id.button_back);
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        btn_back.setOnClickListener(ocl);

    }
}
