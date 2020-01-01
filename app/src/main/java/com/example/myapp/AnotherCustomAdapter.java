package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class AnotherCustomAdapter extends BaseAdapter {
    private ArrayList arr;
    private Context context;
    private TextView textView;
    private ImageView imageView;
    private Json json;

    AnotherCustomAdapter(Context context,ArrayList arr) throws IOException {
        this.arr = arr;
        this.context = context;
        json = new Json();
        Log.d("ARR_SIZE_CUSTOM",String.valueOf(arr.size()));
    }


    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return arr.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Map map = getMapFromArray(i);
        Log.d("INCREMENT",String.valueOf(i));
        if(view==null) {
            Log.d("TAG", "TAG2");
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.activity_searching, null);
            imageView = view.findViewById(R.id.imgvwinlv);
            textView = view.findViewById(R.id.text1);
            view.setTag(i);
            view.setOnClickListener(ocl);
            Log.d("INCREMENT_VK", String.valueOf(getVkUrl(i)));
            DownloadPhoto dp = new DownloadPhoto();
            Vk_user sd = null;
            try {
                sd = new Vk_user(map.get("last_name").toString() + " " + map.get("first_name").toString(),

                        imageView,
                        getUrlPhoto(i),
                        textView,
                        null,
                        getVkUrl(i),
                        i);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            dp.execute(sd);


        }
        return view;
    }

    View.OnClickListener ocl = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int i = (int) view.getTag();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getVkUrl(i)));
            context.startActivity(intent);
        }
    };


    private Map getMapFromArray(int i){
        return (Map) arr.get(i);
    }

    private String getVkUrl(int i){
        return ("https://vk.com/id"+getIdUser(i));
    }

    private String getUrlPhoto(int i) throws IOException, ExecutionException, InterruptedException {
        Map map = getMapFromArray(i);
        Log.d("TAG","TAG4");
        int id = getIdUser(i);
        Log.d("ID_USER",String.valueOf(id));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (map.getOrDefault("photo_id",null)!=null){
                Log.d("ID_USER",String.valueOf(map.get("photo_id").toString()));
                String url = map.get("photo_id").toString().split("_")[1];
                Log.d("ID_USER",String.valueOf(url));
                String s = json.jsonify(url,String.valueOf(id));
                if (s!=null){
                    return s;
                }else return null;
            }
            else{
                return null;
            }
        }
        return null;
    }

    //TODO:make good system of query
    private int getIdUser(int i){
        Map map = getMapFromArray(i);
        String id = map.get("id").toString();
        System.out.println(id);
        if(id.contains("E")) {
            id = id.substring(0, id.length() - 2);
        }
        String[] a = id.split("\\.");
        int id_user = Integer.parseInt(a[0]+a[1]);
        return id_user;
    }
}

class DownloadPhoto extends AsyncTask<Vk_user,Void,Void>{
    Vk_user vu;
    @Override
    protected Void doInBackground(Vk_user... sd) {
        Bitmap bmp_2 = null;
        URL url;
        Log.d("URLINASYNCTASK",String.format("%s%s",Deeper.URL_PHOTO,sd[0].url));
        try {
            url = new URL(String.format("%s",sd[0].url));
            bmp_2 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch (IOException exc){
            Log.d("ERROR_ASYNC ", Arrays.toString(exc.getStackTrace()));
        }
        if (bmp_2==null){
            try{
                url = new URL(Deeper.URL_PHOTO_ERROR);
                bmp_2 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            }catch(IOException exc){
                Log.d("ERROR_ASYNC_2 ", Arrays.toString(exc.getStackTrace()));
            }
        }
        vu = sd[0];
        vu.bmp_get = bmp_2;
        vu.log();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        vu.Image.setImageBitmap(vu.bmp_get);
        vu.title.setText(String.format("ФИО:%s\nСсылка: %s",vu.Text,vu.url_vk));
    }
}
