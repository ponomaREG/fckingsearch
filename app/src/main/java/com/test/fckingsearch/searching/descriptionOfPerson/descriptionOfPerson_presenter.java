package com.test.fckingsearch.searching.descriptionOfPerson;


import android.util.Log;

public class descriptionOfPerson_presenter implements Interfaces.Presenter {

    private Interfaces.View view;

    descriptionOfPerson_presenter(Interfaces.View view){
        this.view = view;
    }

    @Override
    public void OnButtonSearchInVkClick() {
        Log.d("MAX",view.getMaxSelectedRangeValue()+"");
        Log.d("MIN",view.getMinSelectedRangeValue()+"");
    }

    @Override
    public void OnCheckBoxCityClick() {

    }

    @Override
    public void OnCheckBoxAgeDiapasonClick() {
        view.inverseVisibilityOfAgeDiapasonSeekBar();
    }
}
