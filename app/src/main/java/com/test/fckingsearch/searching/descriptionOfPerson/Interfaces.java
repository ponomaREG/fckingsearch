package com.test.fckingsearch.searching.descriptionOfPerson;

public interface Interfaces {

    interface View{
        boolean isCityCheckBoxChecked();
        boolean isRangeCheckBoxChecked();
        int getMaxSelectedRangeValue();
        int getMinSelectedRangeValue();
        void inverseVisibilityOfAgeDiapasonSeekBar();
        void startNextActivity(int city, int age_from , int age_next);
    }
    interface Presenter{
        void OnButtonSearchInVkClick();
        void OnCheckBoxCityClick();
        void OnCheckBoxAgeDiapasonClick();
    }

    interface Model{

    }
}
