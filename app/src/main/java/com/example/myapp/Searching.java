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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;

public class Searching extends AppCompatActivity implements Result.OnFragmentInteractionListener{


    ArrayList<SubjectData> arr;
    ArrayList<String[]> arr2;
    ArrayList<SubjectInfo> arr3;
    ListView listview;
    Button btn_back;
    DBHelper dbhelp;
    SQLiteDatabase db;
    String input;
    Cursor cursor;
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
        cursor = db.rawQuery("select count(*) from cards_students where fio like '%"+input+"%';",null);
        cursor.moveToFirst();

        //((ViewGroup)listview.getParent()).removeView(listview);
        if(cursor.getInt(0)>0) {
            FragManag fm = new FragManag(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, input, db,cursor.getInt(0));
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
        cursor.close();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


//TODO:PHOTO AND CLEANUP

}
 class FragManag extends FragmentPagerAdapter {
    private int count_of_pages = 0;
    private ArrayList<SubjectData> arr;
    private ArrayList<SubjectInfo> arr2;
    private ArrayList<ArrayList<SubjectInfo>> arr2_t;
    private ArrayList<ArrayList<SubjectData>> arr_t;
    private Result[] pages_array;
    private SQLiteDatabase db;
    private String input;

     public FragManag(@NonNull FragmentManager fm, int behavior,String input,SQLiteDatabase db,int total_count) {
         super(fm, behavior);
//         this.arr=subject.getArr1();
//         this.arr2=arr2;
         this.input = input;
         this.count_of_pages = (total_count/20)+1;
         Log.d("total count",String.valueOf(total_count));
         Log.d("total_pages",String.valueOf(count_of_pages));
         this.db = db;
//         Log.d("COUNT_OF_PAGES",String.valueOf(count_of_pages));
//         this.arr_t = Searching.makeDiff(this.arr);
//         Log.d("ARR_T_SIZE",String.valueOf(arr_t.size()));
//         this.arr2_t = Searching.makeDiff_2(this.arr2);
//         Log.d("ARR2_T_SIZE",String.valueOf(arr2_t.size()));
         pages_array = new Result[count_of_pages];
//         for (int i=0;i<count_of_pages;i++){
//             Log.d("ARR_T_SIZE_IN_ITER",String.valueOf(this.arr_t.get(i).size()));
//             Log.d("ARR2_T_SIZE_IN_ITER",String.valueOf(this.arr2_t.get(i).size()));
//             pages_array[i] = new Result().newInstance((arr_t.get(i)), (arr2_t.get(i)),i);
//         }

     }

     @NonNull
     @Override
     public Fragment getItem(int position) {
//         Log.d("ARR_x_SIZE", Arrays.toString(arr_t.toArray()));
         if(pages_array[position]==null){
             //pages_array[position]=new Result().newInstance();
//             Subject subject;
             Subject sub = getResultFromCurrentPage(position+1,input);
             pages_array[position] = new Result().newInstance(sub.getArr1(), sub.getArr2(),position);
         }
         return pages_array[position];
     }



     @Override
     public int getCount() {
         return count_of_pages;
     }



     private Subject getResultFromCurrentPage(int page,String input){
         int l_1 ,l_2=20;
         l_1 = (page-1)*20;
         String query = "select * from cards_students where fio like '%"+input+"%' order by clc_birthdate desc limit "+l_1+","+l_2+";";
         String query_2 = "select photo from cards_order where person_id==%d;";
         Log.d("QUERY",query);
         Cursor c = db.rawQuery(query,null);
         Cursor c_2;
         String url;
         String info[];
         ArrayList<SubjectInfo> arr_info = new ArrayList<>();
         ArrayList<SubjectData> arr_data = new ArrayList<>();
         c.moveToFirst();
         do{
             c_2 = db.rawQuery(String.format(query_2,c.getInt(0)),null);
             c_2.moveToFirst();
             if (c_2.getCount() > 0) {
                 url = c_2.getString(0);
             } else {
                 url = "null";
             }
             String text = c.getString(c.getColumnIndex("fio")) + " " + c.getString(c.getColumnIndex("clc_birthdate"));
             arr_data.add(new SubjectData(text,null,url,null,null));
             info = new String[]{
                     c.getString(c.getColumnIndex("fio")),
                     c.getString(c.getColumnIndex("email")),
                     c.getString(c.getColumnIndex("clc_birthdate")),
                     c.getString(c.getColumnIndex("doc")),
                     c.getString(c.getColumnIndex("dateendallow")),
                     url
             };;
             arr_info.add(new SubjectInfo(info[0],info[1],info[2],info[3],info[4],info[5]));
         }while (c.moveToNext());
         c.close();
         return new Subject(arr_data,arr_info);
     }

    @Deprecated
     private int getCountOP(String input){
         String query = "select * from cards_students where fio like '%"+input+"%' order by clc_birthdate desc";
         Cursor c = db.rawQuery(query,null);
         c.moveToFirst();
         int count = c.getInt(0);
         c.close();
         return count;
     }


 }
