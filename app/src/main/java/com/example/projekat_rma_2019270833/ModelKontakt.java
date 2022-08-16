package com.example.projekat_rma_2019270833;

public class ModelKontakt {

    private String id, ime, telefon, email, opis, vremeDodavanja, vremeAzuriranja;

    public ModelKontakt(String id, String ime, String telefon, String email, String opis, String vremeDodavanja, String vremeAzuriranja) {
        this.id = id;
        this.ime = ime;
        this.telefon = telefon;
        this.email = email;
        this.opis = opis;
        this.vremeDodavanja = vremeDodavanja;
        this.vremeAzuriranja = vremeAzuriranja;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getVremeDodavanja() {
        return vremeDodavanja;
    }

    public void setVremeDodavanja(String vremeDodavanja) {
        this.vremeDodavanja = vremeDodavanja;
    }

    public String getVremeAzuriranja() {
        return vremeAzuriranja;
    }

    public void setVremeAzuriranja(String vremeAzuriranja) {
        this.vremeAzuriranja = vremeAzuriranja;
    }
}
