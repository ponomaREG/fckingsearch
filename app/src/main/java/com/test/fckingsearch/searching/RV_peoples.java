package com.test.fckingsearch.searching;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.fckingsearch.R;
import com.test.fckingsearch.objects.Person;
import com.test.fckingsearch.objects.PhotoDownloader;

import java.util.List;

public class RV_peoples extends RecyclerView.Adapter<RV_peoples.Holder>{

    private LayoutInflater inflater;
    private List<Person> persons;
    private Interfaces.Presenter.connectionBetweenRvAndView presenter_to_view;
    private PhotoDownloader downloaderPhotoForAvatarOfPerson = new PhotoDownloader();

    RV_peoples(LayoutInflater inflater, List<Person> persons,Interfaces.Presenter.connectionBetweenRvAndView presenter_to_view){
        this.inflater = inflater;
        this.persons = persons;
        this.presenter_to_view = presenter_to_view;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(inflater.inflate(R.layout.rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final Person current_person = persons.get(position);
        current_person.setImageViewForAvatar(holder.avatar);

        holder.dateOfBirth.setText(current_person.getDateOfBirth());
        holder.fio.setText(current_person.getFio());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter_to_view.tellViewToOpenBsvWithDescriptionOfPerson(current_person);
            }
        });

        downloaderPhotoForAvatarOfPerson.setImageWithPicasso(current_person);
    }

    void pushNewPeoples(List<Person> new_persons){
        Log.d("PUSH","NEW PEOPLE");
        persons.addAll(new_persons);
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }


    static class Holder extends RecyclerView.ViewHolder{

        private ImageView avatar;
        private TextView fio;
        private TextView dateOfBirth;
        Holder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.rv_item_avatar);
            fio = itemView.findViewById(R.id.rv_item_fio);
            dateOfBirth = itemView.findViewById(R.id.rv_item_dateOfBirth);
        }

    }
}
