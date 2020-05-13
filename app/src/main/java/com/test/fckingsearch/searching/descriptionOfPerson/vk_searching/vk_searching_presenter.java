package com.test.fckingsearch.searching.descriptionOfPerson.vk_searching;

import android.content.Context;
import android.view.LayoutInflater;

import com.test.fckingsearch.objects.Person;

public class vk_searching_presenter implements Interfaces.Presenter, Interfaces.Presenter.connectionBetweenRvAndView {

    private Interfaces.View view;
    private Interfaces.Model model;

    vk_searching_presenter(Interfaces.View view){
        this.view = view;
        this.model = new vk_searching_model();
    }

    @Override
    public void initAdapter(Context context, Person person, int city, int age_from, int age_to) {
        view.setAdapter(new RV_persons_vk(LayoutInflater.from(context),model.getPersons(person,city,age_from,age_to),this));
    }

    @Override
    public void tellViewToStartNextActivity(String vk_id) {
        view.startNextVkActivity(vk_id);
    }
}
