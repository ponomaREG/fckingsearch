package com.test.fckingsearch.searching.descriptionOfPerson.vk_searching;

import android.util.Log;

import com.test.fckingsearch.objects.Json;
import com.test.fckingsearch.objects.Person;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class vk_searching_model implements Interfaces.Model{

    private Json json = new Json();

    @Override
    public List<Person> getPersons(Person person_target, int city, int age_from, int age_to) {
        List<Person> persons = new ArrayList<>();
        if((age_from == 0)||(age_to == 0)){
            String[] dateOfBirthday = person_target.getDateOfBirth().split(" ")[0].split("-");
            int year = Integer.parseInt(dateOfBirthday[0]);
            int month = Integer.parseInt(dateOfBirthday[1]);
            int day = Integer.parseInt(dateOfBirthday[2]);

            if(year > Calendar.getInstance().get(Calendar.YEAR)) year = year - 100;

            age_from = Person.getCurrentAgeFromBirthDayAndCurrentTime(year,month,day);
            age_to = age_from+1;
            age_from--;
        }
        Map raw_result = json.jsonifyVK(person_target, age_from, age_to, city);
        raw_result = (Map) raw_result.get("response");

        assert raw_result != null;
        for(Object array:(ArrayList) Objects.requireNonNull(raw_result.get("items"))){
            Map raw_map_person = (Map) array;
            Person person = new Person();

            int id_res = (int) (double) raw_map_person.get("id");
            String photo_id = (String) raw_map_person.get("photo_id");
            String first_name_res = (String) raw_map_person.get("first_name");
            String last_name_res = (String) raw_map_person.get("last_name");

            person.setId(id_res);
            person.setFirst_name(first_name_res)
                    .setLast_name(last_name_res)
                    .buildFio()
                    .buildID();

            if(photo_id != null) person.setLink_image(getLinkOfPhotoWithMaximumSize(photo_id));
            persons.add(person);
        }
        return persons;
    }

    private String getLinkOfPhotoWithMaximumSize(String photo_with_id){
        String[] split_s = photo_with_id.split("_");

        int id_user = Integer.parseInt(split_s[0]);
        int id_photo = Integer.parseInt(split_s[1]);

        Map raw_result = json.jsonifyVkPhoto(id_user,id_photo);
        Log.d("RAW RESULT",raw_result.toString());
        raw_result = (Map) raw_result.get("response");

        if(raw_result == null) return null;

        ArrayList photos = (ArrayList) raw_result.get("items");

        if(photos == null) return null;
        if(photos.size() == 0) return null;
        Map map_photo = (Map) photos.get(0);
        String needed_key = null;
        for(Object key:map_photo.keySet()){
            String key_s = (String) key;
            if(key_s.contains("photo_")){
                needed_key = key_s;
            }
        }
        if(needed_key == null) return null;
        Log.d("STRING URL OF IMAGE",(String) Objects.requireNonNull(map_photo.get(needed_key)));
        return (String) map_photo.get(needed_key);


    }
}
