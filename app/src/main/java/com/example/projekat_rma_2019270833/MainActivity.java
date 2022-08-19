package com.example.projekat_rma_2019270833;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

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
        assert actionBar != null;
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
                Intent intent = new Intent(MainActivity.this, AddEditKontakt.class);
                intent.putExtra("isAzuriranje", false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.top_menu, menu);

        // search
        MenuItem item = menu.findItem(R.id.searchKontakt);
        // search area
        SearchView searchView = (SearchView) item.getActionView();
        // max vrednost sirine search-a
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchKontakt(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchKontakt(newText);
                return false;
            }
        });

        return true;
    }

    private void searchKontakt(String query) {
        adapterKontakt = new AdapterKontakt(this, dbHelper.getSearchKontakt(query));
        kontaktRV.setAdapter(adapterKontakt);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (item.getItemId() == R.id.deleteAll) {
            dbHelper.deleteAllKontakti();
            onResume();
        }

        return true;
    }
}