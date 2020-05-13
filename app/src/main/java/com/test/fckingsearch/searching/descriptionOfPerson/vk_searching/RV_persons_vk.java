package com.test.fckingsearch.searching.descriptionOfPerson.vk_searching;

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

public class RV_persons_vk extends RecyclerView.Adapter<RV_persons_vk.Holder>{

    private List<Person> persons;
    private LayoutInflater inflater;
    private Interfaces.Presenter.connectionBetweenRvAndView presenter_to_view;
    private PhotoDownloader photoDownloader = new PhotoDownloader();

    RV_persons_vk(LayoutInflater inflater, List<Person> persons, Interfaces.Presenter.connectionBetweenRvAndView presenter_to_view){
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

        holder.fio.setText(current_person.getFio());

        photoDownloader.setImageWithPicassoWithFullLink(current_person.getLink_image(),holder.avatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter_to_view.tellViewToStartNextActivity(current_person.getUrl_vk());
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    static class Holder extends RecyclerView.ViewHolder{

        private ImageView avatar;
        private TextView fio;

        Holder(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.rv_item_avatar);
            fio = itemView.findViewById(R.id.rv_item_fio);
        }
    }
}
