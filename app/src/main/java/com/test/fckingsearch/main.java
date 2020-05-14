package com.test.fckingsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.test.fckingsearch.searching.searching_view;

public class main extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initOclToButtons();
    }

    private void initOclToButtons(){
        Button button = findViewById(R.id.main_buttonCommit);

        View.OnClickListener ocl = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditText lastName_v = findViewById(R.id.main_searchContainer_inputLastName);
                EditText firstName_v = findViewById(R.id.main_searchContainer_inputFirstName);
                EditText patronymic_v = findViewById(R.id.main_searchContainer_inputPatronymic);

                String lastName_text = lastName_v.getText().toString();
                String firstName_text = firstName_v.getText().toString();
                String patronymic_text = patronymic_v.getText().toString();

                if((lastName_text.equals(""))&&(firstName_text.equals(""))&&(patronymic_text.equals(""))) showErrorEmptyEditTexts();
                else startNextActivity(buildFio(lastName_text,firstName_text,patronymic_text));

            }
        };

        button.setOnClickListener(ocl);
    }

    private void showErrorEmptyEditTexts(){
        Toast.makeText(this, "В случае всех пустых полей будут выводится все записи\n Подождите...", Toast.LENGTH_LONG).show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startNextActivity("");
            }
        }, 1000);
    }


    private String buildFio(String last_name, String first_name , String patronymic){
        String formatter = "%s %s %s";
        formatter = String.format(formatter,last_name,first_name,patronymic);
        StringBuilder result_query = new StringBuilder();
        String[] breakFio = formatter.split(" ");
        for(int i = 0;i<breakFio.length;i++){
            result_query.append(breakFio[i]);
            if(i != breakFio.length - 1) result_query.append(" ");
        }
        Log.d("RESULT_QUERY",result_query.toString());
        return result_query.toString();
    }

    private void startNextActivity(String fio){
        Intent intent = new Intent(main.this, searching_view.class);
        intent.putExtra("fio", fio);
        startActivity(intent);
    }

}
