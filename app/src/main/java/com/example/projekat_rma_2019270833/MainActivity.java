package com.example.projekat_rma_2019270833;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    // view
    private ActionBar actionBar;
    private FloatingActionButton add_btn;
    private RecyclerView kontaktRV;

    // database
    private DbHelper dbHelper;

    // adapter
    private AdapterKontakt adapterKontakt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init db
        dbHelper = new DbHelper(this);

        // init actionBar
        actionBar = getSupportActionBar();

        // actionBar naslov
        actionBar.setTitle("Kontakti");

        // inicijalizacija
        add_btn = findViewById(R.id.add_btn);
        kontaktRV = findViewById(R.id.kontaktRV);

        kontaktRV.setHasFixedSize(true);


        // Click listener
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // prelazak na drugi activity za dodavanje kontakta
                Intent intent = new Intent(MainActivity.this,AddEditContact.class);
                startActivity(intent);
            }
        });

        loadData();
    }

    private void loadData() {
        adapterKontakt = new AdapterKontakt(this, dbHelper.getAll());
        kontaktRV.setAdapter(adapterKontakt);
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadData(); // refresh data
    }
}