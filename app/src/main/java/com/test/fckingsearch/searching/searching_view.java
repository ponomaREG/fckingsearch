package com.test.fckingsearch.searching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.test.fckingsearch.R;
import com.test.fckingsearch.objects.Person;
import com.test.fckingsearch.searching.descriptionOfPerson.descriptionOfPerson_view;

import java.util.List;

public class searching_view extends AppCompatActivity implements Interfaces.View {

    //TODO: Сделать прогресс-бар, оптимизацию и вк поиск, возмонжо общий класс
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
    public void setAdapter(final RV_peoples adapter) {
        final RecyclerView rv = findViewById(R.id.searching_rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rv.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rv.addItemDecoration(divider);

        rv.setAdapter(adapter);

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("find last",layoutManager.findLastVisibleItemPosition()+"");
                if(layoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount()-1){
                    Intent intent = getIntent();
                    String query = intent.getStringExtra("fio");
                    if(!presenter.addNewPersonsToAdapter(query)) rv.removeOnScrollListener(this);
                }
            }

        });
    }

    @Override
    public void addNewPersonsToAdapter(List<Person> new_persons) {
        RecyclerView rv = findViewById(R.id.searching_rv);
        RV_peoples adapter = (RV_peoples) rv.getAdapter();
        assert adapter != null;
        adapter.pushNewPeoples(new_persons);
        adapter.notifyDataSetChanged();
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
