package com.test.fckingsearch.objects;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;

import org.apache.commons.io.IOUtils;

public class Json {

    private final static String SEARCH_API = "http://161.35.108.15:8000/api/get?q=%s&count=20&page=%s";
    private final static String SEARCH_API_COUNT = "http://161.35.108.15:8000/api/get/count?q=%s";
    private final static String SEARCH_USERS = "https://api.vk.com/method/users.search?access_token=394a2675e294ec4b83bc1b5d49607af45c6bd4d5aa8e3f55857baf40f579d2a82687ba542bf20125b51f9&v=5.1&q=%s%%20%s&age_from=%s&fields=photo_id&age_to=%s";
    private final static String SEARCH_PHOTOS = "https://api.vk.com/method/photos.get?access_token=394a2675e294ec4b83bc1b5d49607af45c6bd4d5aa8e3f55857baf40f579d2a82687ba542bf20125b51f9&v=5.1&photo_ids=%s&album_id=profile&owner_id=%s\n";



    public Map jsonify(String query, int page) throws ExecutionException, InterruptedException {
        GetDataFromBackground executeQuery = new GetDataFromBackground();
        String s = executeQuery.execute(String.format(SEARCH_API,query,page)).get();
        Gson g = new Gson();
        return g.fromJson(s,Map.class);
    }





}

class GetDataFromBackground extends AsyncTask<String,Void,String> {
    private String s=null;
    @Override
    protected String doInBackground(String... strings) {
        String info = strings[0];
        try {
            s = IOUtils.toString(new URL(info), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}


