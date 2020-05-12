package com.test.fckingsearch.objects;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.test.fckingsearch.R;

public class PhotoDownloader {

    private final static String URL_PHOTO = "https://карта-онлайн.рф/uploads/photos/stud/%s";



    public void setImageWithPicasso(Person person){
        Picasso.get().load(String.format(URL_PHOTO,person.getLink_image()))
                .placeholder(R.drawable.avatar_placeholder)
                .into(person.getImageViewForAvatar());
    }

    public void setImageWithPicasso(String link, ImageView view){
        Picasso.get().load(String.format(URL_PHOTO,link))
                .placeholder(R.drawable.avatar_placeholder)
                .into(view);
    }
}
