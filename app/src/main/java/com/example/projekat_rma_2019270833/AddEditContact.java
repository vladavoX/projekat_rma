package com.example.projekat_rma_2019270833;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditContact extends AppCompatActivity {

    private EditText imeInput, telefonInput, emailInput, opisInput;
    private FloatingActionButton add_btn;

    private String id, ime, telefon, email, opis, vremeDodavanja, vremeAzuriranja;
    private Boolean isAzuriranje;

    // Action bar
    private ActionBar actionBar;

    // db helper
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        // init db
        dbHelper = new DbHelper(this);

        // init actionBar
        actionBar = getSupportActionBar();

        // dugme za nazad
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        imeInput = findViewById(R.id.imeInput);
        telefonInput = findViewById(R.id.telefonInput);
        emailInput = findViewById(R.id.emailInput);
        opisInput = findViewById(R.id.opisInput);
        add_btn = findViewById(R.id.add_btn);

        // dobavi intent data
        Intent intent = getIntent();
        isAzuriranje = intent.getBooleanExtra("isAzuriranje", false);

        if (isAzuriranje){
            actionBar.setTitle("Azuriraj Kontakt");

            // uzimamo vrednosti intenta
            id = intent.getStringExtra("ID");
            ime = intent.getStringExtra("IME");
            telefon = intent.getStringExtra("TELEFON");
            email = intent.getStringExtra("EMAIL");
            opis = intent.getStringExtra("OPIS");
            vremeDodavanja = intent.getStringExtra("VREME_DODAVANJA");
            vremeAzuriranja = intent.getStringExtra("VREME_AZURIRANJA");

            // postavljamo primljene vrednosti
            imeInput.setText(ime);
            telefonInput.setText(telefon);
            emailInput.setText(email);
            opisInput.setText(opis);
        } else {
            actionBar.setTitle("Dodaj Kontakt");
        }

        // click listener
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    private void saveData(){
        // uzimamo inpute
        ime = imeInput.getText().toString();
        telefon = telefonInput.getText().toString();
        email = emailInput.getText().toString();
        opis = opisInput.getText().toString();

        // dobavljamo trenutno vreme
        String vreme = ""+System.currentTimeMillis();

        // proveravamo podatke inputa
        if(!ime.isEmpty() && !telefon.isEmpty()) { // cuvamo ako postoji ime i broj telefona
            // provera da li je edit ili create
            if (!isAzuriranje) {
                // kreiranje
                long id = dbHelper.insertKontakt(
                    ""+ime,
                    ""+telefon,
                    ""+email,
                    ""+opis,
                    ""+vreme,
                    ""+vreme
                );
                Toast.makeText(getApplicationContext(), "Kreiranje uspesno...", Toast.LENGTH_SHORT).show();
            } else {
                // azuriranje
                dbHelper.updateKontakt(
                        ""+id,
                        ""+ime,
                        ""+telefon,
                        ""+email,
                        ""+opis,
                        ""+vremeDodavanja,
                        ""+vreme
                );
                Toast.makeText(getApplicationContext(), "Azuriranje uspesno...", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Unesite broj telefona i ime...", Toast.LENGTH_SHORT).show();
        }
    }

    // dugme za nazad klik
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }

}