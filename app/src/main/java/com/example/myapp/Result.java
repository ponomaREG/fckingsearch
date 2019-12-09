package com.example.myapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Result.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Result#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Result extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "ARRAY_SUB";
    private static final String ARG_PARAM2 = "ARRAY_INFO";
    private static ArrayList<SubjectData> arr_1;
    private static ArrayList<SubjectInfo> arr_2;
    // TODO: Rename and change types of parameters
    private ArrayList<SubjectInfo> arr2;
    private ArrayList<SubjectData> arr;
    private OnFragmentInteractionListener mListener;



    public Result() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     *
     * @return A new instance of fragment Result.
     */
    // TODO: Rename and change types and number of parameters
    public Result newInstance(ArrayList<SubjectData> arr,ArrayList<SubjectInfo> arr2,int page) {
        Result fragment = Result.this;
        Bundle args = new Bundle();
        this.arr = arr;
        this.arr2 = arr2;
        Log.d("ARR_IN_RESULT_SIZE",String.valueOf(this.arr.size()));
        Log.d("ARR2_IN_RESULT_SIZE",String.valueOf(this.arr2.size()));
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v  = inflater.inflate(R.layout.fragment_result, container, false);
        ListView listview = v.findViewById(R.id.litsview);
        CustomAdapter adapter = new CustomAdapter(getContext(), this.arr,getStringArrayList(this.arr2));
        listview.setAdapter(adapter);
        return v;
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }





    private ArrayList<String[]> getStringArrayList(ArrayList<SubjectInfo> arr){
        ArrayList<String[]> arr2 = new ArrayList<>();
        for (int i = 0;i<arr.size();i++){
            String [] info = new String[]{
                    arr.get(i).fio,
                    arr.get(i).email,
                    arr.get(i).clc_birthdate,
                    arr.get(i).doc,
                    arr.get(i).dateendallow,
                    arr.get(i).url
            };
            arr2.add(info);
        }
        return arr2;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }





    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }






    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}