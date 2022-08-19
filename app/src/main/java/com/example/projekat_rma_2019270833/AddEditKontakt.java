package com.example.projekat_rma_2019270833;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditKontakt extends AppCompatActivity {

    private ImageView slikaInput;
    private EditText imeInput, telefonInput, emailInput, opisInput;
    private FloatingActionButton add_btn;

    private String id, slika, ime, telefon, email, opis, vremeDodavanja, vremeAzuriranja;
    private Boolean isAzuriranje;

    // Action bar
    private ActionBar actionBar;

    // db helper
    private DbHelper dbHelper;

    // konstante permisije
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 200;
    private static final int SLIKA_IZ_GALERIJE_CODE = 300;
    private static final int SLIKA_IZ_KAMERE_CODE = 400;

    // array permisija
    private String[] cameraPermission;
    private String[] storagePermission;

    // Slika Uri
    private Uri slikaUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        // init permisije
        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        // init db
        dbHelper = new DbHelper(this);

        // init actionBar
        actionBar = getSupportActionBar();

        // dugme za nazad
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        slikaInput = findViewById(R.id.slikaInput);
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
            slika = intent.getStringExtra("SLIKA");
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
            slikaUri = Uri.parse(slika);

            if (slika.equals("null")){
                slikaInput.setImageResource(R.drawable.ic_baseline_person_24);
            } else {
                slikaInput.setImageURI(slikaUri);
            }

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

        slikaInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImagePickerDialog();
            }
        });
    }

    private void showImagePickerDialog() {
        // opcije za odabir
        String opcije[] = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izaberite Opciju");
        builder.setItems(opcije, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    // kamera
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    } else {
                        pickFromCamera();
                    }
                } else if (which == 1){
                    // storage
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    } else {
                        pickFromStorage();
                    }
                }
            }
        }).create().show();
    }

    private void pickFromStorage() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        startActivityForResult(galleryIntent, SLIKA_IZ_GALERIJE_CODE);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "IMAGE_TITLE");
        values.put(MediaStore.Images.Media.DESCRIPTION, "IMAGE_DESC");

        slikaUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, slikaUri);

        startActivityForResult(cameraIntent, SLIKA_IZ_KAMERE_CODE);
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
                        ""+slikaUri,
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
                        ""+slikaUri,
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

    // provera permisije za kameru
    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result & result1;
    }

    // zahteva za permisiju kamere
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_PERMISSION_CODE);
    }

    // provera permsije za storage
    private boolean checkStoragePermission(){
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result1;
    }

    // zahtev za permisiju storage
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CAMERA_PERMISSION_CODE:
                if(grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    // ako su obe permisije prihvacene return true
                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    } else {
                        Toast.makeText(getApplicationContext(), "Camera i Storage Permisije potrebne...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_PERMISSION_CODE:
                if(grantResults.length > 0) {

                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    // ako su obe permisije prihvacene return true
                    if (storageAccepted){
                        pickFromStorage();
                    } else {
                        Toast.makeText(getApplicationContext(), "Storage Permisije potrebne...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == SLIKA_IZ_GALERIJE_CODE) {
                Uri result = data.getData();
                slikaInput.setImageURI(result);
            } else if (requestCode == SLIKA_IZ_KAMERE_CODE) {
                Uri result = slikaUri;
                slikaInput.setImageURI(result);
            }
        }
    }

}