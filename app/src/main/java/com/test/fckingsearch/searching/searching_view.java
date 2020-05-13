package com.test.fckingsearch.searching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.fckingsearch.R;
import com.test.fckingsearch.objects.Person;
import com.test.fckingsearch.searching.descriptionOfPerson.descriptionOfPerson_view;

import java.util.List;
import java.util.Objects;

public class searching_view extends AppCompatActivity implements Interfaces.View {

    //TODO: Сделать прогресс-бар, оптимизацию и вк поиск, возмонжо общий класс
    private Interfaces.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_view);

        initPresenter();
        initAdapter();
        prepareView();
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

    private void prepareView(){
        ActionBar actionBar = getSupportActionBar();

        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(false);
        ImageView image_logo_in_actionbar = new ImageView(actionBar.getThemedContext());
        image_logo_in_actionbar.setScaleType(ImageView.ScaleType.FIT_XY);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                120,
                120,
                Gravity.CENTER
        );
        image_logo_in_actionbar.setLayoutParams(lp);
        image_logo_in_actionbar.setImageDrawable(getDrawable(R.drawable.main_icon));
        actionBar.setCustomView(image_logo_in_actionbar);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void setAdapter(final RV_peoples adapter) {
        final RecyclerView rv = findViewById(R.id.searching_rv);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        rv.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(getDrawable(R.drawable.rv_divider)));
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
