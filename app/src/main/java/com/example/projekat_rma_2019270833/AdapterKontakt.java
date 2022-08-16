package com.example.projekat_rma_2019270833;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterKontakt extends RecyclerView.Adapter<AdapterKontakt.KontaktViewHanlder> {

    private Context context;
    private ArrayList<ModelKontakt> kontaktList;

    public AdapterKontakt(Context context, ArrayList<ModelKontakt> kontaktList) {
        this.context = context;
        this.kontaktList = kontaktList;
    }

    @NonNull
    @Override
    public KontaktViewHanlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.red_kontakt, parent, false);
        KontaktViewHanlder vh = new KontaktViewHanlder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull KontaktViewHanlder holder, int position) {
        ModelKontakt modelKontakt = kontaktList.get(position);

        // data
        String id = modelKontakt.getId();
        String ime = modelKontakt.getIme();

        // setujemo data u view
        holder.kontaktIme.setText(ime);

        // TODO: click listener
        holder.kontaktPoziv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return kontaktList.size();
    }

    class KontaktViewHanlder extends RecyclerView.ViewHolder {

        ImageView kontaktPoziv;
        TextView kontaktIme;

        public KontaktViewHanlder(@NonNull View itemView) {
            super(itemView);

            kontaktPoziv = itemView.findViewById(R.id.kontakt_poziv);
            kontaktIme = itemView.findViewById(R.id.kontakt_ime);
        }
    }
}
