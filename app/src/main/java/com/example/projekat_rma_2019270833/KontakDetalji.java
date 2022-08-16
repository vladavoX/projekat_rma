package com.example.projekat_rma_2019270833;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class KontakDetalji extends AppCompatActivity {

    // Action bar
    private ActionBar actionBar;

    // view
    private TextView imeTv, telefonTv, emailTv, opisTv, vremeDodavanjaTv, vremeAzuriranjaTv;

    private String id;

    // database
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kontak_detalji);

        // init db
        dbHelper = new DbHelper(this);

        // init actionBar
        actionBar = getSupportActionBar();

        // actionBar naslov
        actionBar.setTitle("Kontakt");

        // dugme za nazad
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        // dobavljamo data iz intenta
        Intent intent = getIntent();
        id = intent.getStringExtra("kontaktId");

        // init view
        imeTv = findViewById(R.id.imeTv);
        telefonTv = findViewById(R.id.telefonTv);
        emailTv = findViewById(R.id.emailTv);
        opisTv = findViewById(R.id.opisTv);
        vremeDodavanjaTv = findViewById(R.id.vremeDodavanjaTv);
        vremeAzuriranjaTv = findViewById(R.id.vremeAzuriranjaTv);
        
        loadDataById();

    }

    private void loadDataById() {
        // query za dobavljanje data po id-u
        String selectQuery = "SELECT * FROM " + Kontakti.TABLE_NAME + " WHERE " + Kontakti.K_ID + " =\"" + id + "\"";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()){
            do {
                // dobavljamo data
                String ime = "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_IME));
                String telefon = "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_TELEFON));
                String email = "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_EMAIL));
                String opis = "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_OPIS));
                String vremeDodavanja = "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_VREME_DODAVANJA));
                String vremeAzuriranja = "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_VREME_AZURIRANJA));

                // konvretujemo vreme
                Calendar calendar = Calendar.getInstance(Locale.getDefault());

                calendar.setTimeInMillis(Long.parseLong(vremeDodavanja));
                String dodVreme = "" + DateFormat.format("dd/MM/yy hh:mm:aa", calendar);

                calendar.setTimeInMillis(Long.parseLong(vremeAzuriranja));
                String azuVreme = "" + DateFormat.format("dd/MM/yy hh:mm:aa", calendar);

                imeTv.setText(ime);
                telefonTv.setText(telefon);
                emailTv.setText(email);
                opisTv.setText(opis);
                vremeDodavanjaTv.setText(dodVreme);
                vremeAzuriranjaTv.setText(azuVreme);

            } while (cursor.moveToNext());
        }

        db.close();
    }

    // dugme za nazad klik
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}