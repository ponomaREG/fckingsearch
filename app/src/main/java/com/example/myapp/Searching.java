package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

public class Searching extends AppCompatActivity implements Result.OnFragmentInteractionListener{


    static int COUNT_OF_PAGES = 0;
    ArrayList<SubjectData> arr;
    ArrayList<String[]> arr2;
    ArrayList<SubjectInfo> arr3;
    ListView listview;
    Button btn_back;
    DBHelper dbhelp;
    SQLiteDatabase db;
    String input;
    Cursor cursor,cursor2;
    String[] info;
    ThreadDownload thd;
    ViewPager vp;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        arr = new ArrayList<>();
        arr2 = new ArrayList<>();
        arr3 = new ArrayList<>();
        thd = new ThreadDownload();
        vp = findViewById(R.id.viewpager);
        listview = findViewById(R.id.litsview);
        btn_back = findViewById(R.id.button_back);
        dbhelp = new DBHelper(getBaseContext());
        db = dbhelp.getReadableDatabase();
        input = getIntent().getStringExtra("fio");
        cursor = db.rawQuery("select * from cards_students where fio like '%"+input+"%' order by clc_birthdate desc;",null);
        cursor.moveToFirst();
        String url;
        if (cursor.getCount()>0) {
            do {
                cursor2 = db.rawQuery(String.format("select photo from cards_order where person_id=%d;", cursor.getInt(0)), null);
                cursor2.moveToFirst();
                if (cursor2.getCount() > 0) {
                    url = cursor2.getString(0);
                } else {
                    url = "null";
                }
                String text = cursor.getString(cursor.getColumnIndex("fio")) + " " + cursor.getString(cursor.getColumnIndex("clc_birthdate"));
                arr.add(new SubjectData(text,null,url,null,null));
                info = new String[]{
                        cursor.getString(cursor.getColumnIndex("fio")),
                        cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("clc_birthdate")),
                        cursor.getString(cursor.getColumnIndex("doc")),
                        cursor.getString(cursor.getColumnIndex("dateendallow")),
                        url
                };
                SubjectInfo si = new SubjectInfo(info[0],info[1],info[2],info[3],info[4],info[5]);
                arr3.add(si);
                arr2.add(info);
            } while (cursor.moveToNext());
        }
        if (arr.size()<1){
            //((ViewGroup)listview.getParent()).removeView(listview);
        }
        else {
            FragManag fm = new FragManag(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,arr,arr3);
            vp.setAdapter(fm);
        }

        btn_back = findViewById(R.id.button_back);
        View.OnClickListener ocl = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
        btn_back.setOnClickListener(ocl);

    }


    static ArrayList<ArrayList<SubjectData>> makeDiff(ArrayList<SubjectData> arr){
        ArrayList<ArrayList<SubjectData>> arr_x = new ArrayList<ArrayList<SubjectData>>();
        ArrayList<SubjectData> arr_y = new ArrayList<SubjectData>();
        for (int i = 0 ;i<arr.size();i++){
                if (((i % 20) == 0) && (i != 0)) {
                    Log.d("ARR_x_SIZE123",String.valueOf(arr_x.size()));
                    arr_x.add((ArrayList<SubjectData>) arr_y.clone());
                    Log.d("ARR_Y_SIZE",String.valueOf(arr_y.size()));

                    arr_y.clear();
                    Log.d("ARR_x_SIZE123",String.valueOf(arr_x.size()));
                }
            arr_y.add(arr.get(i));
            Log.d("ARR_Y_SIZE_ITER",String.valueOf(arr_y.size()));
        }
        arr_x.add(arr_y);
        Log.d("ARR_x_SIZE_",String.valueOf(arr_x.size()));
        for (int i=0;i<arr_x.size();i++){
            Log.d("_I",String.valueOf(i));
            Log.d("_A_SIZE",String.valueOf(arr_x.get(i).size()));
        }
        return arr_x;
    }


    static ArrayList<ArrayList<SubjectInfo>> makeDiff_2(ArrayList<SubjectInfo> arr){
        ArrayList<ArrayList<SubjectInfo>> arr_x = new ArrayList<ArrayList<SubjectInfo>>();
        ArrayList<SubjectInfo> arr_y = new ArrayList<>();
        for (int i = 0 ;i<arr.size();i++){
            if (((i % 20) == 0) && (i != 0)) {
                arr_x.add((ArrayList<SubjectInfo>) arr_y.clone());
                Log.d("ARR2_Y_SIZE",String.valueOf(arr_y.size()));
                arr_y.clear();
            }
            arr_y.add(arr.get(i));
            Log.d("ARR2_Y_SIZE_ITER",String.valueOf(arr_y.size()));
        }
        arr_x.add(arr_y);
        Log.d("ARR2_x_SIZE",String.valueOf(arr_x.size()));
        for (int i=0;i<arr_x.size();i++){
            Log.d("_I",String.valueOf(i));
            Log.d("_A_SIZE",String.valueOf(arr_x.get(i).size()));
        }
        return arr_x;

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
 class FragManag extends FragmentPagerAdapter {
    private int count_of_pages = 0;
    private ArrayList<SubjectData> arr;
    private ArrayList<SubjectInfo> arr2;
    private ArrayList<ArrayList<SubjectInfo>> arr2_t;
    private ArrayList<ArrayList<SubjectData>> arr_t;
    private Result[] pages_array;

     public FragManag(@NonNull FragmentManager fm, int behavior,ArrayList<SubjectData> arr,ArrayList<SubjectInfo> arr2) {
         super(fm, behavior);
         this.arr=arr;
         this.arr2=arr2;
         this.count_of_pages = (arr.size()/20)+1;
         Log.d("COUNT_OF_PAGES",String.valueOf(count_of_pages));
         this.arr_t = Searching.makeDiff(this.arr);
         Log.d("ARR_T_SIZE",String.valueOf(arr_t.size()));
         this.arr2_t = Searching.makeDiff_2(this.arr2);
         Log.d("ARR2_T_SIZE",String.valueOf(arr2_t.size()));
         pages_array = new Result[count_of_pages];
         for (int i=0;i<count_of_pages;i++){
             Log.d("ARR_T_SIZE_IN_ITER",String.valueOf(this.arr_t.get(i).size()));
             Log.d("ARR2_T_SIZE_IN_ITER",String.valueOf(this.arr2_t.get(i).size()));
             pages_array[i] = new Result().newInstance((arr_t.get(i)), (arr2_t.get(i)),i);
         }
     }

     @NonNull
     @Override
     public Fragment getItem(int position) {
         Log.d("ARR_x_SIZE", Arrays.toString(arr_t.toArray()));
         return pages_array[position];
     }

     @Override
     public int getCount() {
         return count_of_pages;
     }

 }