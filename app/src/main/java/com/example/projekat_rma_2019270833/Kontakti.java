package com.example.projekat_rma_2019270833;

public class Kontakti {

    // database
    public static final String DATABASE_NAME = "KONTAKT_DB";
    public static final int DATABASE_VERSION = 1;

    // tabela
    public static final String TABLE_NAME = "KONTAKT_TABLE";

    // kolone u tabeli
    public static final String K_ID = "ID";
    public static final String K_SLIKA = "SLIKA";
    public static final String K_IME = "IME";
    public static final String K_TELEFON = "TELEFON";
    public static final String K_EMAIL = "EMAIL";
    public static final String K_OPIS = "OPIS";
    public static final String K_VREME_DODAVANJA = "VREME_DODAVANJA";
    public static final String K_VREME_AZURIRANJA = "VREME_AZURIRANJA";

    // query za kreiranje tabele
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
            + K_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + K_SLIKA + " TEXT, "
            + K_IME + " TEXT, "
            + K_TELEFON + " TEXT, "
            + K_EMAIL + " TEXT, "
            + K_OPIS + " TEXT, "
            + K_VREME_DODAVANJA + " TEXT, "
            + K_VREME_AZURIRANJA + " TEXT "
            + " );";

}
