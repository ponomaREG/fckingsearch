package com.example.myapp;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;


class SubjectData {
    String Text;
    ImageView Image;
    String url;
    TextView title;
    Bitmap bmp_get;
    public SubjectData(String text, ImageView image, String url, TextView title, Bitmap bmp) {
        this.Text = text;
        this.Image = image;
        this.url = url;
        this.title = title;
        this.bmp_get = bmp;
    }

}