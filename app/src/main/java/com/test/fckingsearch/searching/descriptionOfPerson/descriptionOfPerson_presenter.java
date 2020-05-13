package com.test.fckingsearch.searching.descriptionOfPerson;



public class descriptionOfPerson_presenter implements Interfaces.Presenter {

    private Interfaces.View view;

    descriptionOfPerson_presenter(Interfaces.View view){
        this.view = view;
    }

    @Override
    public void OnButtonSearchInVkClick() {
        int city = 0;
        int age_from = 0;
        int age_to = 0;

        if(view.isCityCheckBoxChecked()) city = 2;
        if(view.isRangeCheckBoxChecked()){
            age_from = view.getMinSelectedRangeValue();
            age_to = view.getMaxSelectedRangeValue();
        }

        view.startNextActivity(city,age_from,age_to);
    }

    @Override
    public void OnCheckBoxCityClick() {

    }

    @Override
    public void OnCheckBoxAgeDiapasonClick() {
        view.inverseVisibilityOfAgeDiapasonSeekBar();
    }

}
