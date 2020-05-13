package com.test.fckingsearch.objects;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.test.fckingsearch.R;

public class PhotoDownloader {

    private final static String URL_PHOTO = "https://карта-онлайн.рф/uploads/photos/stud/%s";
//    private final static String SEARCH_PHOTOS = "https://api.vk.com/method/photos.get?access_token=394a2675e294ec4b83bc1b5d49607af45c6bd4d5aa8e3f55857baf40f579d2a82687ba542bf20125b51f9&v=5.1&photo_ids=%s&album_id=profile&owner_id=%s\n";



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

    public void setImageWithPicassoWithFullLink(String full_link, ImageView imageView){
        Picasso.get().load(full_link)
                .placeholder(R.drawable.avatar_placeholder)
                .into(imageView);
    }
}
