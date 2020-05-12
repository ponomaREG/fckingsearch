package com.test.fckingsearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                EditText input = findViewById(R.id.main_searchContainer_inputFIO);

                Intent intent = new Intent(main.this, searching_view.class);
                intent.putExtra("fio", input.getText().toString());
                startActivity(intent);
            }
        };

        button.setOnClickListener(ocl);
    }
}
