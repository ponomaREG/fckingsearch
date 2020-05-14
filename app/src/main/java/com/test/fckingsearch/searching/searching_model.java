package com.test.fckingsearch.searching;

import android.util.Log;

import com.test.fckingsearch.objects.Json;
import com.test.fckingsearch.objects.Person;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class searching_model implements Interfaces.Model{

    private Json json = new Json();


    @Override
    public List<Person> getPersons(String query, int page) {
        List<Person> persons = new ArrayList<>();
        Map raw_result;
        try {
            raw_result = json.jsonify(query,page);
            Log.d("RAW_RESULT",raw_result+"");
            if(raw_result == null) return null;
        } catch (Exception e) {
            return null;
        }
        Log.d("MAP",raw_result.toString());
        ArrayList array_result = (ArrayList) raw_result.get("data");
        assert array_result != null;
        for(Object person:array_result){
            Map person_map = (Map) person;
            Person new_person = new Person();

            String birthday = (String) person_map.get("birthdate");
            String fio = (String) person_map.get("fio");
            String email = (String) person_map.get("email");
            String passport = (String) person_map.get("psp");
            String link_photo = (String) person_map.get("photo");
            String dateEndAllow = (String) person_map.get("dateendallow");

            new_person.setFio(fio)
                    .setEmail(email)
                    .setDateOfBirth(birthday)
                    .setLink_image(link_photo)
                    .setPassport(passport)
                    .setDateEndAllow(dateEndAllow);

            persons.add(new_person);
        }
        return persons;
    }
}
