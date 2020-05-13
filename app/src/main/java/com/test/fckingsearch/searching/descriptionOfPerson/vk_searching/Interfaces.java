package com.test.fckingsearch.searching.descriptionOfPerson.vk_searching;

import android.content.Context;

import com.test.fckingsearch.objects.Person;

import java.util.List;

public interface Interfaces {

    interface View{
        void setAdapter(RV_persons_vk adapter);
        void startNextVkActivity(String vk_id);
    }

    interface Presenter{

        void initAdapter(Context context, Person person, int city , int age_from, int age_to);

        interface connectionBetweenRvAndView{
            void tellViewToStartNextActivity(String vk_id);
        }

    }

    interface Model{
        List<Person> getPersons(Person person,int city, int age_from, int age_to);
    }
}
