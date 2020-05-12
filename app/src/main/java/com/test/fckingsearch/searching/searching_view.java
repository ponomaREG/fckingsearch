package com.test.fckingsearch.searching;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.test.fckingsearch.R;
import com.test.fckingsearch.searching.descriptionOfPerson.descriptionOfPerson_view;

public class searching_view extends AppCompatActivity implements Interfaces.View {


    private Interfaces.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_view);

        initPresenter();
        initAdapter();
    }

    private void initPresenter(){
        this.presenter = new searching_presenter(this);
    }

    private void initAdapter(){
        Intent intent = getIntent();
        String query = intent.getStringExtra("fio");
        if(query == null) finish();
        presenter.getAdapter(this, query);
    }

    @Override
    public void setAdapter(RV_peoples adapter) {
        RecyclerView rv = findViewById(R.id.searching_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(divider);
        rv.setAdapter(adapter);
    }

    @Override
    public void showBsvWithDescriptionOfPerson(descriptionOfPerson_view bsv) {
        bsv.setCancelable(true);
        bsv.show(getSupportFragmentManager(), descriptionOfPerson_view.TAG);
    }


    @Override
    public void showErrorNoInternet() {
        Toast.makeText(this,"Отсутствует подключение к Интернету",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorEmptyResult() {
        Toast.makeText(this, "Ничего не найдено", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorUnexpected() {
        Toast.makeText(this, "Непредвиденная ошибка", Toast.LENGTH_SHORT).show();
    }
}
