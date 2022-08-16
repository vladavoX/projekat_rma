package com.example.projekat_rma_2019270833;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Kontakti.DATABASE_NAME, null, Kontakti.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Kontakti.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Kontakti.TABLE_NAME);

        onCreate(db);
    }

    // Insertujemo data u db
    public long insertKontakt(String ime, String telefon, String email, String opis, String vremeDodavanja, String vremeAzuriranja){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Kontakti.K_IME, ime);
        contentValues.put(Kontakti.K_TELEFON, telefon);
        contentValues.put(Kontakti.K_EMAIL, email);
        contentValues.put(Kontakti.K_OPIS, opis);
        contentValues.put(Kontakti.K_VREME_DODAVANJA, vremeDodavanja);
        contentValues.put(Kontakti.K_VREME_AZURIRANJA, vremeAzuriranja);

        long id = db.insert(Kontakti.TABLE_NAME, null, contentValues);

        db.close();

        return id;
    }

    // Dobavljamo data iz db
    public ArrayList<ModelKontakt> getAll(){
        ArrayList<ModelKontakt> arrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Kontakti.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // prolazimo kroz sve i dodajemo u listu
        if (cursor.moveToFirst()){
            do {
                ModelKontakt modelKontakt = new ModelKontakt(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Kontakti.K_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_IME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_TELEFON)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_EMAIL)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_OPIS)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_VREME_DODAVANJA)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Kontakti.K_VREME_AZURIRANJA))
                );
                arrayList.add(modelKontakt);
            } while (cursor.moveToNext());
        }

        db.close();
        return arrayList;
    }
}
