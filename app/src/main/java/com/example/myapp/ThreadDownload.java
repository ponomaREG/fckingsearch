package com.example.myapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ThreadDownload {
    URL url;
    Bitmap bmp;
    Thread t;
    void startDownload(final String url_path) {
         t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (url_path.equals("null")){
                        url = new URL(Deeper.URL_PHOTO_ERROR);
                        Log.d("URL","1ERROR");
                    }else{
                        url = new URL(String.format("%s%s",Deeper.URL_PHOTO,url_path));


                    }
                } catch (MalformedURLException e) {
                    Log.d("CAD","ERRORWITHURL");
                    Log.getStackTraceString(e);
                    //h.sendEmptyMessage(CODE_ERROR);
                    //Thread.currentThread().interrupt();
                }
                try {
                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    Log.d("BYTECOUNT",String.valueOf(bmp.getByteCount()));
                    if (bmp.getByteCount()<1){
                        url = new URL(Deeper.URL_PHOTO_ERROR);
                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    }
                } catch (IOException e) {
                    Log.d("BMP",Log.getStackTraceString(e));
                    //h.sendEmptyMessage(CODE_ERROR);
                    //h.sendEmptyMessage(CODE_ERROR);
                    Thread.currentThread().interrupt();

                }
                //h.sendEmptyMessage(CODE_SUCCESS);
            }
        });
         t.start();
    }
    Bitmap getBmp() { return bmp;}
    boolean checkThread(){ return t.isAlive();}
}
