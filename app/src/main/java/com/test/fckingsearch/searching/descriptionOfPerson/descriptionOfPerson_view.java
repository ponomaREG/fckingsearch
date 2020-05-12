package com.test.fckingsearch.searching.descriptionOfPerson;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.test.fckingsearch.R;
import com.test.fckingsearch.objects.Person;
import com.test.fckingsearch.objects.PhotoDownloader;

public class descriptionOfPerson_view extends BottomSheetDialogFragment {

    public static String TAG = "DESCRIPTION OF PERSON";

    public static descriptionOfPerson_view newInstance(Person person) {

        Bundle args = new Bundle();

        descriptionOfPerson_view fragment = new descriptionOfPerson_view();

        String fio = person.getFio();
        String passport = person.getPassport();
        String birthday = person.getDateOfBirth();
        String link_image = person.getLink_image();
        String email = person.getEmail();

        args.putString("fio",fio);
        args.putString("passport",passport);
        args.putString("birthday",birthday);
        args.putString("link_image",link_image);
        args.putString("email",email);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        String email = args.getString("email");

        View view = inflater.inflate(R.layout.searching_description_of_person,container,false);

        TextView fio_v = view.findViewById(R.id.descriptionOfPerson_container_fio_v);
        TextView passport_v = view.findViewById(R.id.descriptionOfPerson_container_passport_v);
        TextView email_v = view.findViewById(R.id.descriptionOfPerson_container_email_v);
        TextView birthday_v = view.findViewById(R.id.descriptionOfPerson_container_birthday_v);
        ImageView imageView_avatar = view.findViewById(R.id.descriptionOfPerson_avatar);

        fio_v.setText(fio);
        passport_v.setText(passport);
        email_v.setText(email);
        birthday_v.setText(birthday);

        downloader.setImageWithPicasso(link_image,imageView_avatar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
