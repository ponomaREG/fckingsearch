package com.example.myapp;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

class CustomAdapter extends BaseAdapter {
    private ArrayList<SubjectData> arrayList;
    private ArrayList<String[]> arrayList2;
    private Context context;
    private String[] info;
    private TextView tittle;
    private ImageView imag;
    private int pos;
    CustomAdapter(Context context, ArrayList<SubjectData> arrayList, ArrayList<String[]> arrayList2) {
        this.arrayList=arrayList;
        this.context=context;
        this.arrayList2=arrayList2;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SubjectData subjectData = getSubjectData(position);
        pos = position;
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.activity_searching, null);
            convertView.setTag(position);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int key = Integer.parseInt(v.getTag().toString());
                    info = getInfo(key);
                    Log.d("ID_VIEW",String.valueOf(v));
                    Intent intent = new Intent(context,Deeper.class);
                    intent.putExtra("fio", info[0]);
                    intent.putExtra("passport", info[3]);
                    intent.putExtra("email", info[1]);
                    intent.putExtra("birthdate", info[2]);
                    intent.putExtra("dateendallow", info[4]);
                    intent.putExtra("url", info[5]);
                    context.startActivity(intent);
                }
            });
            tittle = convertView.findViewById(R.id.text1);
            imag = convertView.findViewById(R.id.imgvwinlv);
            subjectData.Image = imag;
            subjectData.title = tittle;
            DownloadFile df = new DownloadFile();
            df.execute(subjectData);

        }
        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }

    private String[] getInfo(int position){
        return arrayList2.get(position);
    }

    private SubjectData getSubjectData(int position){
        return arrayList.get(position);
    }
    @Deprecated
    TextView getTitle(){
        return tittle;
    }
    @Deprecated
    ImageView getImage(){
        return imag;
    }
    @Deprecated
    int getIntPosition(){
        return pos;
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadFile extends AsyncTask<SubjectData,Void,Void>{
        SubjectData sd_orig;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(SubjectData... sd) {
            Bitmap bmp_2 = null;
            URL url;
//            imgv = sd[0].Image;
//            tittleview = sd[0].title;
            Log.d("URLINASYNCTASK",String.format("%s%s",Deeper.URL_PHOTO,sd[0].url));
            try {
                url = new URL(String.format("%s%s",Deeper.URL_PHOTO,sd[0].url));
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
            sd_orig = sd[0];
            sd_orig.bmp_get = bmp_2;
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            sd_orig.Image.setImageBitmap(sd_orig.bmp_get);
            sd_orig.title.setText(sd_orig.Text);
        }
    }

}