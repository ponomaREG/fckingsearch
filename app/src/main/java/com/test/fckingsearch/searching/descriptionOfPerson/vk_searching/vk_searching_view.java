package com.test.fckingsearch.searching.descriptionOfPerson.vk_searching;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.test.fckingsearch.R;
import com.test.fckingsearch.objects.Person;

public class vk_searching_view extends AppCompatActivity implements Interfaces.View{

    private Interfaces.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vk_searching_view);


        initPresenter();
        initAdapter();
    }

    private void initPresenter(){
        this.presenter = new vk_searching_presenter(this);
    }

    private void initAdapter(){
        Bundle extras = getIntent().getExtras();

        assert extras != null;
        String fio = extras.getString("fio");
        String passport = extras.getString("passport");
        String birthday = extras.getString("birthday");
        String link_image = extras.getString("link_image");
        String email = extras.getString("email");
        int city = extras.getInt("city");
        int age_from = extras.getInt("age_from");
        int age_to = extras.getInt("age_to");

        Person person = new Person();
        person.setFio(fio)
                .setPassport(passport)
                .setDateOfBirth(birthday)
                .setLink_image(link_image)
                .setEmail(email)
                .breakFio();

        presenter.initAdapter(this,person,city,age_from,age_to);
    }

    @Override
    public void setAdapter(RV_persons_vk adapter) {
        RecyclerView rv = findViewById(R.id.vk_searching_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration divider = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.addItemDecoration(divider);
        rv.setAdapter(adapter);
    }

    @Override
    public void startNextVkActivity(String vk_id) {
        Intent intent_vk = new Intent(Intent.ACTION_VIEW, Uri.parse(vk_id));
        startActivity(intent_vk);
    }
}
