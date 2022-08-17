package com.example.projekat_rma_2019270833;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterKontakt extends RecyclerView.Adapter<AdapterKontakt.KontaktViewHanlder> {

    private Context context;
    private ArrayList<ModelKontakt> kontaktList;
    private DbHelper dbHelper;

    public AdapterKontakt(Context context, ArrayList<ModelKontakt> kontaktList) {
        this.context = context;
        this.kontaktList = kontaktList;
        dbHelper = new DbHelper(context);
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
        String telefon = modelKontakt.getTelefon();
        String email = modelKontakt.getEmail();
        String opis = modelKontakt.getOpis();
        String vremeDodavanja = modelKontakt.getVremeDodavanja();
        String vremeAzuriranja = modelKontakt.getVremeAzuriranja();

        // setujemo data u view
        holder.kontaktIme.setText(ime);

        // click listener za poziv (Ne radi)
        holder.kontaktPoziv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // click listener kontakt detalji
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KontakDetalji.class);
                intent.putExtra("kontaktId", id);
                context.startActivity(intent);
            }
        });

        // click listener kontakt edit
        holder.kontaktEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // intent za prelazak na edit
                Intent intent = new Intent(context, AddEditContact.class);

                // saljemo podatke kontakta sa trenutne pozicije
                intent.putExtra("ID", id);
                intent.putExtra("IME", ime);
                intent.putExtra("TELEFON", telefon);
                intent.putExtra("EMAIL", email);
                intent.putExtra("OPIS", opis);
                intent.putExtra("VREME_DODAVANJA", vremeDodavanja);
                intent.putExtra("VREME_AZURIRANJA", vremeAzuriranja);

                intent.putExtra("isAzuriranje", true);

                context.startActivity(intent);
            }
        });

        // click listener kontakt delete
        holder.kontaktDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteKontakt(id);
                // refreshujemo data pozivanje onResume iz MainActivity-a
                ((MainActivity)context).onResume();
            }
        });
    }

    @Override
    public int getItemCount() {
        return kontaktList.size();
    }

    class KontaktViewHanlder extends RecyclerView.ViewHolder {

        ImageView kontaktPoziv, kontaktEdit, kontaktDelete;
        TextView kontaktIme;

        public KontaktViewHanlder(@NonNull View itemView) {
            super(itemView);

            kontaktPoziv = itemView.findViewById(R.id.kontakt_poziv);
            kontaktIme = itemView.findViewById(R.id.kontakt_ime);
            kontaktEdit = itemView.findViewById(R.id.kontakt_edit);
            kontaktDelete = itemView.findViewById(R.id.kontakt_delete);
        }
    }
}
