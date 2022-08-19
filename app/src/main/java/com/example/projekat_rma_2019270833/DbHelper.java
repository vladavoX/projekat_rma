package com.example.projekat_rma_2019270833;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Konstante.DATABASE_NAME, null, Konstante.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Konstante.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Konstante.TABLE_NAME);

        onCreate(db);
    }

    // Kreiramo kontakt u db
    public long insertKontakt(String slika, String ime, String telefon, String email, String opis, String vremeDodavanja, String vremeAzuriranja){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Konstante.K_SLIKA, slika);
        contentValues.put(Konstante.K_IME, ime);
        contentValues.put(Konstante.K_TELEFON, telefon);
        contentValues.put(Konstante.K_EMAIL, email);
        contentValues.put(Konstante.K_OPIS, opis);
        contentValues.put(Konstante.K_VREME_DODAVANJA, vremeDodavanja);
        contentValues.put(Konstante.K_VREME_AZURIRANJA, vremeAzuriranja);

        long id = db.insert(Konstante.TABLE_NAME, null, contentValues);
        db.close();

        return id;
    }

    // Dobavljamo kontakte iz db
    public ArrayList<ModelKontakt> getAll(String orderBy){
        ArrayList<ModelKontakt> arrayList = new ArrayList<>();

        // select query
        String selectQuery = "SELECT * FROM " + Konstante.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

        // prolazimo kroz sve i dodajemo u listu
        if (cursor.moveToFirst()){
            do {
                ModelKontakt modelKontakt = new ModelKontakt(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Konstante.K_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_IME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_SLIKA)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_TELEFON)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_EMAIL)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_OPIS)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_VREME_DODAVANJA)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_VREME_AZURIRANJA))
                );
                arrayList.add(modelKontakt);
            } while (cursor.moveToNext());
        }

        db.close();
        return arrayList;
    }

    // Azuriramo kontakt u db
    public void updateKontakt(String id, String slika, String ime, String telefon, String email, String opis, String vremeDodavanja, String vremeAzuriranja){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(Konstante.K_SLIKA, slika);
        contentValues.put(Konstante.K_IME, ime);
        contentValues.put(Konstante.K_TELEFON, telefon);
        contentValues.put(Konstante.K_EMAIL, email);
        contentValues.put(Konstante.K_OPIS, opis);
        contentValues.put(Konstante.K_VREME_DODAVANJA, vremeDodavanja);
        contentValues.put(Konstante.K_VREME_AZURIRANJA, vremeAzuriranja);

        db.update(Konstante.TABLE_NAME, contentValues, Konstante.K_ID + " =? ", new String[]{id});
        db.close();
    }

    // Brisemo kontakt
    public void deleteKontakt(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Konstante.TABLE_NAME, Konstante.K_ID + " =? ", new String[]{id});
        db.close();
    }

    // Brisemo sve kontakte
    public void deleteAllKontakti() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + Konstante.TABLE_NAME);
        db.close();
    }

    // Search kontakta
    public ArrayList<ModelKontakt> getSearchKontakt(String query) {
        ArrayList<ModelKontakt> kontaktList = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // query za search
        String searchQuery = "SELECT * FROM " + Konstante.TABLE_NAME + " WHERE " + Konstante.K_IME + " LIKE '%" + query + "%'";

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(searchQuery, null);

        if (cursor.moveToNext()){
            do {
                ModelKontakt modelKontakt = new ModelKontakt(
                        "" + cursor.getInt(cursor.getColumnIndexOrThrow(Konstante.K_ID)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_IME)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_SLIKA)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_TELEFON)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_EMAIL)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_OPIS)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_VREME_DODAVANJA)),
                        "" + cursor.getString(cursor.getColumnIndexOrThrow(Konstante.K_VREME_AZURIRANJA))
                );
                kontaktList.add(modelKontakt);
            } while (cursor.moveToNext());
        }
        db.close();
        return kontaktList;
    }
}
