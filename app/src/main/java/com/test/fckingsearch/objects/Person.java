package com.test.fckingsearch.objects;

import android.util.Log;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Person {
    //TODO:URL VK USAGE
    private String last_name,first_name,patronymic, passport, dateOfBirth, email, fio, link_image, dateEndAllow;
    private int id;
    private ImageView imageViewForAvatar;
    private String url_vk;

    public Person setLink_image(String link_image) {
        this.link_image = link_image;
        return this;
    }

    public Person setFio(String fio) {
        this.fio = fio;
        return this;
    }

    public Person setDateEndAllow(String dateEndAllow) {
        this.dateEndAllow = dateEndAllow;
        return this;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Person setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public Person setEmail(String email) {
        this.email = email;
        return this;
    }

    public Person setFirst_name(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public Person setLast_name(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public Person setPassport(String passport) {
        this.passport = passport;
        return this;
    }

    public Person setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public Person buildFio(){

        String formatter = "%s %s";
        this.fio = String.format(formatter,this.getLast_name(),this.getFirst_name());
        if(getPatronymic() != null) this.fio = String.format(getFio() + " %s",getPatronymic());
        return this;
    }

    public Person breakFio(){
        String[] array_fio = getFio().split(" ");
        setLast_name(array_fio[0]);
        setFirst_name(array_fio[1]);
        setPatronymic(array_fio[2]);
        return this;
    }

    public Person setImageViewForAvatar(ImageView imageViewForAvatar) {
        this.imageViewForAvatar = imageViewForAvatar;
        return this;
    }

    public String getUrl_vk() {
        return url_vk;
    }

    public String getFio() {
        return fio;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    String getFirst_name() {
        return first_name;
    }

    String getLast_name() {
        return last_name;
    }

    public String getPassport() {
        return passport;
    }

    private String getPatronymic() {
        return patronymic;
    }


    public String getLink_image() {
        return link_image;
    }

    ImageView getImageViewForAvatar() {
        return imageViewForAvatar;
    }

    public int getId() {
        return id;
    }

    public String getDateEndAllow() {
        return dateEndAllow;
    }

    public Person buildID(){
        this.url_vk = String.format("https://vk.com/id%s",getId());
        return this;
    }

    public static int getCurrentAgeFromBirthDayAndCurrentTime(int year, int month,int day){
        Calendar birthDay = new GregorianCalendar(year, month-1, day);
        Calendar today = new GregorianCalendar();
        today.setTime(new Date());
        Log.d("BETWEEN YEAR",today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR)+" ");
        Log.d("year",year+"");
        Log.d("month",month+"");
        Log.d("day",day+"");
        return today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

    }
}
