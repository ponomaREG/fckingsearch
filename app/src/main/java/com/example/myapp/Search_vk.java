package com.example.myapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Search_vk extends AppCompatActivity {
    private final static int CODE_CITY = 2;
    private ListView lv;
    private String fio,first_name,last_name,birthdate;
    private int age=16,year,month,day,count,age_from,age_to,city;
    private Json json;
    private Map map;
    private ArrayList arr_vl;

    private final static  String CODE = "845fe159831e0789a8",
            SECRET_KEY = "YxegLocn6NNYSJLZOkbZ",
            SERVICE_KEY="4b3a64304b3a64304b3a6430394b50b8cd44b3a4b3a643017d599a200c3100af40d2120",
            ACCESS_TOKEN = "394a2675e294ec4b83bc1b5d49607af45c6bd4d5aa8e3f55857baf40f579d2a82687ba542bf20125b51f9",
            REDIRECT_URI="http://vk.com/callback";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_vk);
        Intent intent = getIntent();
        json = null;
        map = null;
        try {
            json = new Json();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fio = intent.getStringExtra("fio");
        birthdate = intent.getStringExtra("birthdate");
        String[] fio_split = fio.split(" ");
        first_name = fio_split[1];
        last_name = fio_split[0];
        birthdate = birthdate.split(" ")[0];
        String[] birth_date = birthdate.split("-");
        year = Integer.parseInt(birth_date[0]);
        if(year > Calendar.getInstance().get(Calendar.YEAR)) year-=100;
        month = Integer.parseInt(birth_date[1]);
        city = intent.getIntExtra("city",0);
        age_from = intent.getIntExtra("age_from",0);
        age_to = intent.getIntExtra("age_to",0);
        Log.d("TAG","TAG");
        day = Integer.parseInt(birth_date[2]);
        lv = findViewById(R.id.listview_search_vk);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            age = getAge(year,month,day);
        }
        if(!((age_from>0)&&(age_to>0))){
            age_from = age;
            age_to = age;
        }
        try {
            arr_vl = json.jsonify(first_name,last_name,age_from,age_to,city);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("ARR_SIZE_SEARCH",String.valueOf(arr_vl.size()));
        AnotherCustomAdapter aca = null;
        if((arr_vl.size()>0)) {
            try {
                aca = new AnotherCustomAdapter(this, arr_vl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("TAG", "TAG3");
            lv.setAdapter(aca);
            Log.d("AGE", String.valueOf(age));
            Log.d("FIRST_NAME", first_name);
            Log.d("LAST_NAME", last_name);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private int getAge(int year, int month, int day){
        LocalDate now = LocalDate.now();
        LocalDate birth_date = LocalDate.of(year,month,day);
        return (Period.between(birth_date,now).getYears());
    }
}
