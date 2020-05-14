package com.test.fckingsearch.searching;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;

import com.test.fckingsearch.objects.Person;
import com.test.fckingsearch.searching.descriptionOfPerson.descriptionOfPerson_view;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class searching_presenter implements Interfaces.Presenter, Interfaces.Presenter.connectionBetweenRvAndView {

    private Interfaces.View view;
    private Interfaces.Model model;

    private int max_visible_page = 1;

    searching_presenter(Interfaces.View view){
        this.view = view;
        this.model = new searching_model(this);
    }

        //TODO: Обработка ошибок , связь между моделью и представлением
    @Override
    public void getAdapter(Context context, String query) {
        List<Person> persons = model.getPersons(query,1);
        Log.d("PERSONS",persons+"");
        if(persons != null){
            if(persons.size() == 0) view.showMessageItsAll();
        }else {
            view.showErrorCommon();
            persons = new ArrayList<>();
        }
        view.setAdapter(new RV_peoples(LayoutInflater.from(context),persons,this));
    }

    @Override
    public boolean addNewPersonsToAdapter(String query) {
        max_visible_page++;
        BackgroundLoading backgroundLoading = new BackgroundLoading();
        backgroundLoading.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, query);
        return true;
    }



    @Override
    public void tellViewToOpenBsvWithDescriptionOfPerson(Person person) {
        view.showBsvWithDescriptionOfPerson(descriptionOfPerson_view.newInstance(person));
    }

    @SuppressLint("StaticFieldLeak")
    class BackgroundLoading extends AsyncTask<String,Void,Void>{
        List<Person> persons = new ArrayList<>();

        @Override
        protected Void doInBackground(String... strings) {
            String query = strings[0];
            persons = model.getPersons(query,max_visible_page);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(persons !=null){
                 if(persons.size() == 0) {
                        view.showMessageItsAll();
                 }else{
                     view.addNewPersonsToAdapter(persons);
                     view.addOnScrollListenerOnRv();
                 }
                view.hideProgressBar();
            }else {
                view.showErrorCommon();
            }
        }
    }
}
