package com.test.fckingsearch.searching.descriptionOfPerson;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.test.fckingsearch.R;
import com.test.fckingsearch.objects.Person;
import com.test.fckingsearch.objects.PhotoDownloader;
import com.test.fckingsearch.searching.descriptionOfPerson.vk_searching.vk_searching_view;

import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.util.Objects;

public class descriptionOfPerson_view extends BottomSheetDialogFragment implements Interfaces.View{

    public static String TAG = "DESCRIPTION OF PERSON";

    private Interfaces.Presenter presenter;

    public static descriptionOfPerson_view newInstance(Person person) {

        Bundle args = new Bundle();

        descriptionOfPerson_view fragment = new descriptionOfPerson_view();

        String fio = person.getFio();
        String passport = person.getPassport();
        String birthday = person.getDateOfBirth();
        String link_image = person.getLink_image();
        String email = person.getEmail();
        String dateEndAllow = person.getDateEndAllow();

        args.putString("fio",fio);
        args.putString("passport",passport);
        args.putString("birthday",birthday);
        args.putString("link_image",link_image);
        args.putString("email",email);
        args.putString("dateEndAllow",dateEndAllow);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getArguments() == null){ this.dismiss(); }
        Bundle args = getArguments();
        PhotoDownloader downloader = new PhotoDownloader(); // TODO: статик переменная

        String fio = args.getString("fio");
        String passport = args.getString("passport");
        String birthday = args.getString("birthday");
        String link_image = args.getString("link_image");
        String dateEndAllow = args.getString("dateEndAllow");
        assert link_image != null;
        String email = args.getString("email");

        View view = inflater.inflate(R.layout.searching_description_of_person,container,false);

        TextView fio_v = view.findViewById(R.id.descriptionOfPerson_container_fio_v);
        TextView passport_v = view.findViewById(R.id.descriptionOfPerson_container_passport_v);
        TextView email_v = view.findViewById(R.id.descriptionOfPerson_container_email_v);
        TextView birthday_v = view.findViewById(R.id.descriptionOfPerson_container_birthday_v);
        TextView dateEbdAllow_v = view.findViewById(R.id.descriptionOfPerson_container_dateEndAllow_v);
        ImageView imageView_avatar = view.findViewById(R.id.descriptionOfPerson_avatar);

        fio_v.setText(fio);
        passport_v.setText(passport);
        email_v.setText(email);
        birthday_v.setText(birthday);
        dateEbdAllow_v.setText(dateEndAllow);

        downloader.setImageWithPicasso(link_image,imageView_avatar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initOclToCheckBoxes();
        initOclToButtonSearchInVk();
    }


    private void initPresenter(){
        this.presenter = new descriptionOfPerson_presenter(this);
    }

    private void initOclToCheckBoxes(){
        CheckBox checkBox_city = Objects.requireNonNull(getView()).findViewById(R.id.descriptionOfPerson_searchVK_containerCity_checkBox);
        CheckBox checkBox_ageDiapason = Objects.requireNonNull(getView()).findViewById(R.id.descriptionOfPerson_searchVK_containerAgeDiapason_checkBox);

        View.OnClickListener ocl = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(v.getId() == R.id.descriptionOfPerson_searchVK_containerCity_checkBox) presenter.OnCheckBoxCityClick();
                else presenter.OnCheckBoxAgeDiapasonClick();
            }
        };

        checkBox_city.setOnClickListener(ocl);
        checkBox_ageDiapason.setOnClickListener(ocl);
    }

    private void initOclToButtonSearchInVk(){
        Button buttonSearchInVk = Objects.requireNonNull(getView()).findViewById(R.id.descriptionOfPerson_buttonFindInVk);

        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.OnButtonSearchInVkClick();
            }
        };

        buttonSearchInVk.setOnClickListener(ocl);
    }

    @Override
    public boolean isCityCheckBoxChecked() {
        CheckBox checkBox_city = Objects.requireNonNull(getView()).findViewById(R.id.descriptionOfPerson_searchVK_containerCity_checkBox);
        return checkBox_city.isChecked();
    }

    @Override
    public boolean isRangeCheckBoxChecked() {
        CheckBox checkBox_ageDiapason = Objects.requireNonNull(getView()).findViewById(R.id.descriptionOfPerson_searchVK_containerAgeDiapason_checkBox);
        return checkBox_ageDiapason.isChecked();
    }

    @Override
    public int getMaxSelectedRangeValue() {
        RangeSeekBar seekBar = Objects.requireNonNull(getView()).findViewById(R.id.descriptionOfPerson_searchVK_seekBarWithAgeDiapason);
        return seekBar.getSelectedMaxValue().intValue();
    }

    @Override
    public int getMinSelectedRangeValue() {
        RangeSeekBar seekBar = Objects.requireNonNull(getView()).findViewById(R.id.descriptionOfPerson_searchVK_seekBarWithAgeDiapason);
        return seekBar.getSelectedMinValue().intValue();
    }

    @Override
    public void inverseVisibilityOfAgeDiapasonSeekBar() {
        RangeSeekBar seekBar = Objects.requireNonNull(getView()).findViewById(R.id.descriptionOfPerson_searchVK_seekBarWithAgeDiapason);
        int visibility = seekBar.getVisibility();
        if(visibility == View.VISIBLE) seekBar.setVisibility(View.GONE);
        else seekBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void startNextActivity(int city, int age_from , int age_to) {
        Bundle args = getArguments();
        assert args != null;
        args.putInt("city",city);
        args.putInt("age_from",age_from);
        args.putInt("age_to",age_to);

        Intent intent_next_activity = new Intent(getContext(), vk_searching_view.class);
        intent_next_activity.putExtras(args);

        startActivity(intent_next_activity);
    }
}
