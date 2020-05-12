package com.test.fckingsearch.objects;

import android.widget.ImageView;

public class Person {

    private String last_name,first_name,patronymic, passport, dateOfBirth, email, fio, link_image;
    private int position;
    private ImageView imageViewForAvatar;

    public Person setLink_image(String link_image) {
        this.link_image = link_image;
        return this;
    }

    public Person setFio(String fio) {
        this.fio = fio;
        return this;
    }

    public Person setPosition(int position) {
        this.position = position;
        return this;
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
        String formatter = "%s %s %s";
        this.fio = String.format(formatter,this.getLast_name(),this.getFirst_name(),this.getPatronymic());
        return this;
    }

    public Person setImageViewForAvatar(ImageView imageViewForAvatar) {
        this.imageViewForAvatar = imageViewForAvatar;
        return this;
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

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassport() {
        return passport;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public int getPosition() {
        return position;
    }

    public String getLink_image() {
        return link_image;
    }

    public ImageView getImageViewForAvatar() {
        return imageViewForAvatar;
    }

}
