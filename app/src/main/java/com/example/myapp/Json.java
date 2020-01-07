package com.example.myapp;


import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

public class Json {

    private final static String SEARCH_API = "http://178.62.250.191:8000/api/get?q=%s&count=20&page%d";
    private final static String SEARCH_API_COUNT = "http://178.62.250.191:8000/api/get/count?q=%s";
    private final static String SEARCH_USERS = "https://api.vk.com/method/users.search?access_token=394a2675e294ec4b83bc1b5d49607af45c6bd4d5aa8e3f55857baf40f579d2a82687ba542bf20125b51f9&v=5.1&q=%s%%20%s&age_from=%s&fields=photo_id&age_to=%s";
    private final static String SEARCH_PHOTOS = "https://api.vk.com/method/photos.get?access_token=394a2675e294ec4b83bc1b5d49607af45c6bd4d5aa8e3f55857baf40f579d2a82687ba542bf20125b51f9&v=5.1&photo_ids=%s&album_id=profile&owner_id=%s\n";
    private int count;
    private ArrayList arr;
    private Map map;
    private Gson g;
    private String s;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList jsonify(String first_name, String last_name, int age_from, int age_to,int city) throws IOException, ExecutionException, InterruptedException {

        s = String.format(SEARCH_USERS,last_name,first_name,age_from,age_to);
        if(city>0) s += String.format("&city=%s",city);
        SearchInfo search = new SearchInfo();
        s = search.execute(s).get();
        Log.d("s",s);
        g = new Gson();
        map = g.fromJson(s,Map.class);
        map = (Map) map.get("response");
        arr = (ArrayList) map.getOrDefault("items",null);
        if (arr==null){
            return null;
        }
        count = (int)(double) map.get("count");
        Log.d("ARR_SIZE_INJSON",String.valueOf(arr.size()));
        if(arr.size()<count){
            if(count>1000){
                count=1000;
            }
            search = new SearchInfo();
            s = String.format(SEARCH_USERS+"&count=%s",last_name,first_name,age_from,age_to,count);
            s = search.execute(s).get();
            map = g.fromJson(s,Map.class);
            arr = (ArrayList) ((Map) map.get("response")).get("items");

        }
        Log.d("ARR_SIZE_INJSON",String.valueOf(arr.size()));
        return arr;


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String jsonify(String photo_ids, String owner_id) throws IOException, ExecutionException, InterruptedException {
        Log.d("PHOTO_IDS",photo_ids);
        Log.d("OWNER_ID",owner_id);
        s = String.format(SEARCH_PHOTOS,photo_ids,owner_id);
        SearchInfo search = new SearchInfo();
        s = search.execute(s).get();
        g = new Gson();
        map = g.fromJson(s,Map.class);
        if(!map.containsKey("response")){
            return null;
        }
        map = (Map) map.get("response");
        if(!map.containsKey("items")){
            return null;
        }
        ArrayList arr = (ArrayList) map.getOrDefault("items",null);
        if (arr==null){
            return null;
        }

        Log.d("ARR_INTRO",arr.toString());
        if (arr.size()>0){
            map = (Map) arr.get(0);
            if (map.getOrDefault("photo_604",null)!=null){
                return map.get("photo_604").toString();
            }
            else if(map.getOrDefault("photo_130",null)!=null){
                return map.get("photo_130").toString();
            }
            else return null;
        }
        return null;
    }

    public Json() throws IOException {
    }

    public ArrayList jsonify(String q,int page) throws ExecutionException, InterruptedException {
        SearchInfo searchInfo = new SearchInfo();
        String query = String.format(SEARCH_API,q,page);
        query = searchInfo.execute(query).get();
        Gson g = new Gson();
        Map map = g.fromJson(query,Map.class);
        ArrayList arr = (ArrayList) map.get("data");
        return arr;
    }

    public int jsonify(String q) throws ExecutionException, InterruptedException {
        SearchInfo searchInfo = new SearchInfo();
        String query = String.format(SEARCH_API_COUNT,q);
        query = searchInfo.execute(query).get();
        Gson g = new Gson();
        Map map = g.fromJson(query,Map.class);
        return Integer.parseInt((String) map.get("data"));
    }
}

class SearchInfo extends AsyncTask<String,Void,String>{
    String s=null;
    @Override
    protected String doInBackground(String... strings) {
        String info = strings[0];
        try {
            s = IOUtils.toString(new URL(info), Charset.forName("utf-8"));
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
