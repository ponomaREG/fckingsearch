package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Deeper extends AppCompatActivity {
    final static String URL_PHOTO = "https://карта-онлайн.рф/uploads/photos/stud/";
    final static String URL_PHOTO_ERROR = "http://image10.bizrate-images.com/resize?sq=60&uid=2216744464";
    Intent intent;
    TextView fio,datebirth,passport,dateend,email;
    EditText age_to,age_from;
    CheckBox city,age;
    ImageView imageview;
    LinearLayout layout_age_from,layout_age_to,layout_age;
    static Handler h;
    Bitmap bmp = null;
    Button btn_back,search_vk;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeper);
        intent = getIntent();
        fio = findViewById(R.id.info_fio);
        passport = findViewById(R.id.info_passport);
        email = findViewById(R.id.info_email);
        dateend = findViewById(R.id.info_dateend);
        age_from = findViewById(R.id.age_from);
        layout_age_from = findViewById(R.id.layout_age_from);
        layout_age_to = findViewById(R.id.layout_age_to);
        layout_age = findViewById(R.id.layout_age);
        age_to = findViewById(R.id.age_to);
        age = findViewById(R.id.deeper_check_age);
        city = findViewById(R.id.deeper_check_city);
        imageview = findViewById(R.id.imageview);
        datebirth = findViewById(R.id.info_birthdate);
        fio.setText(String.format("%s%s",fio.getText().toString(),intent.getStringExtra("fio")));
        passport.setText(String.format("%s%s",passport.getText().toString(),intent.getStringExtra("passport")));
        email.setText(String.format("%s%s",email.getText().toString(),intent.getStringExtra("email")));
        dateend.setText(String.format("%s%s",dateend.getText().toString(),intent.getStringExtra("dateendallow")));
        datebirth.setText(String.format("%s%s",datebirth.getText().toString(),intent.getStringExtra("birthdate")));
        h = new Handler(){
            @Override
            public void handleMessage(Message msg){
                switch (msg.what){
                    case (RESULT_OK):
                        imageview.setImageBitmap(bmp);
                        imageview.getLayoutParams().width=800;
                        imageview.getLayoutParams().height=800;
                        imageview.setAdjustViewBounds(true);
                        Log.d("LOGGGG","Loaded");
                        break;
                    case (RESULT_CANCELED):
                        Log.d("LOGGGG","Error");
                        break;
                }
            }
        };
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    if (!intent.getStringExtra("url").equals("null")) {
                        url = new URL(String.format("%s%s", URL_PHOTO, intent.getStringExtra("url")));
                    }else {url = new URL(URL_PHOTO_ERROR);}
                } catch (MalformedURLException e) {
                    Log.d("ERROR","ERRORINURL");
                    h.sendEmptyMessage(RESULT_CANCELED);
                    Thread.currentThread().interrupt();
                }
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (IOException e) {
                    Log.d("ERROR","ERRORINBMP");
                    h.sendEmptyMessage(RESULT_CANCELED);
                    Thread.currentThread().interrupt();

                }
                h.sendEmptyMessage(RESULT_OK);


            }
        });
        t.start();
        btn_back = findViewById(R.id.button_back);
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        search_vk = findViewById(R.id.search_vk);
        View.OnClickListener ocl_2 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_activity = new Intent(getBaseContext(),Search_vk.class);
                intent_activity.putExtra("fio",intent.getStringExtra("fio"));
                boolean flag = false;
                Log.d("TAG","TAG5");
                intent_activity.putExtra("birthdate",intent.getStringExtra("birthdate"));
                if (age.isChecked()){
                    String age_to_text = age_to.getText().toString();
                    String age_from_text = age_from.getText().toString();
                    if((!age_to_text.equals(""))&&(!age_from_text.equals(""))){
                        intent_activity.putExtra("age_from",Integer.parseInt(age_from_text));
                        intent_activity.putExtra("age_to",Integer.parseInt(age_to_text));
                    }
                    else{
                        Toast t = Toast.makeText(getApplicationContext(),"Неправильные данные", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.BOTTOM,0,0);
                        t.show();
                        flag = true;
                    }
                }
                if(city.isChecked()){
                    intent_activity.putExtra("city",2);
                }
                if (!flag) {
                    startActivity(intent_activity);
                }
            }
        };
        View.OnClickListener ocl_3 = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(age.isChecked()){
                    layout_age.setVisibility(View.VISIBLE);

                }else{
                    layout_age.setVisibility(View.INVISIBLE);
                }
            }
        };
        btn_back.setOnClickListener(ocl);
        search_vk.setOnClickListener(ocl_2);
        age.setOnClickListener(ocl_3);

    }
}
