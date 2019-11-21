package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ArrayList<String> arr = new ArrayList<>();
    final String TABLE_NAME="cards_students";
    private SQLiteDatabase db;
    private DBHelper DBHelp;
    Button button;
    TextView tvw;
    EditText input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.search);
        input = findViewById(R.id.input_FIO);
        tvw = findViewById(R.id.get_test);
        button.setOnClickListener(this);
        DBHelp = new DBHelper(getBaseContext());
        DBHelp.copyDataBase();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.search):
                Intent intent = new Intent(this, Searching.class);
                intent.putExtra("fio", input.getText().toString());
                startActivity(intent);
        }
    }
}
