package com.example.myapp;

import java.util.ArrayList;

public class Subject {
    private String input;
    private ArrayList<SubjectData> arrayList;
    private int total_count;
    private ArrayList<SubjectInfo> arrayList2;
    Subject(ArrayList<SubjectData> arrayList,ArrayList<SubjectInfo> arrayList2){
        this.arrayList = arrayList;
        this.arrayList2 = arrayList2;
        this.input = input;
        this.total_count = total_count;
    }


    public ArrayList<SubjectData> getArr1(){
        return this.arrayList;
    }

    public ArrayList<SubjectInfo> getArr2(){
        return this.arrayList2;
    }

    public int getTotal_count(){
        return this.total_count;
    }
}
