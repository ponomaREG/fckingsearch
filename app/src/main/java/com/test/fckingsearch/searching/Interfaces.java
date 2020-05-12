package com.test.fckingsearch.searching;

import android.content.Context;

import com.test.fckingsearch.objects.Person;
import com.test.fckingsearch.searching.descriptionOfPerson.descriptionOfPerson_view;

import java.util.List;

public interface Interfaces {

    interface View{
        void setAdapter(RV_peoples adapter);
        void addNewPersonsToAdapter(List<Person> new_persons);
        void showBsvWithDescriptionOfPerson(descriptionOfPerson_view bsv);
        void showErrorNoInternet();
        void showErrorEmptyResult();
        void showErrorUnexpected();
    }

    interface Presenter{
        void getAdapter(Context context,String query);
        boolean addNewPersonsToAdapter(String query);
        interface connectionBetweenRvAndView{
            void tellViewToOpenBsvWithDescriptionOfPerson(Person person);
        }
    }

    interface Model{
        List<Person> getPersons(String query, int page);
    }
}
