package com.test.fckingsearch.searching;

import android.content.Context;
import android.view.LayoutInflater;

import com.test.fckingsearch.objects.Person;
import com.test.fckingsearch.searching.descriptionOfPerson.descriptionOfPerson_view;

import java.util.List;

public class searching_presenter implements Interfaces.Presenter, Interfaces.Presenter.connectionBetweenRvAndView {

    private Interfaces.View view;
    private Interfaces.Model model;

    searching_presenter(Interfaces.View view){
        this.view = view;
        this.model = new searching_model();
    }

        //TODO: Обработка ошибок , связь между моделью и представлением
    @Override
    public void getAdapter(Context context, String query) {
        List<Person> persons = model.getPersons(query,1);
        if(persons.size() == 0) view.showErrorEmptyResult();
        view.setAdapter(new RV_peoples(LayoutInflater.from(context),model.getPersons(query,1),this));
    }


    @Override
    public void tellViewToOpenBsvWithDescriptionOfPerson(Person person) {
        view.showBsvWithDescriptionOfPerson(descriptionOfPerson_view.newInstance(person));
    }
}
