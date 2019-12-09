package com.example.myapp;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class Vk_user {
    String Text;
    ImageView Image;
    String url,url_vk;
    TextView title;
    Bitmap bmp_get;
    int number;
    public Vk_user(String text, ImageView image, String url, TextView title, Bitmap bmp, String url_vk,int number) {
        this.Text = text;
        this.Image = image;
        this.url = url;
        this.title = title;
        this.bmp_get = bmp;
        this.url_vk = url_vk;
        this.number = number;
        log();
    }

    void log(){
        Log.d("VK_USER",String.format("%s %s %s %s",Text,url,url_vk,number));
    }
}
