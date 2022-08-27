package com.example.projekat_rma_2019270833;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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

    // sort kategorija
    private String sortByNewest = Konstante.K_VREME_DODAVANJA + " DESC";
    private String sortByOldest = Konstante.K_VREME_DODAVANJA + " ASC";
    private String sortByNameAsc = Konstante.K_IME + " ASC";
    private String sortByNameDesc = Konstante.K_IME + " DESC";

    // default sortiranje
    private String currentSort = sortByNameAsc;

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


        // Click listener add dugme
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // prelazak na drugi activity za dodavanje kontakta
                Intent intent = new Intent(MainActivity.this, AddEditKontakt.class);
                intent.putExtra("isAzuriranje", false);
                startActivity(intent);
            }
        });

        loadData(currentSort);
    }

    private void loadData(String currentSort) {
        adapterKontakt = new AdapterKontakt(this, dbHelper.getAll(currentSort));
        kontaktRV.setAdapter(adapterKontakt);
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadData(currentSort); // refresh data
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
        } else if (item.getItemId() == R.id.sortKontakt) {
            sortDialog();
        }

        return true;
    }

    private void sortDialog() {
        String[] opcije = {"Najnoviji", "Najstariji", "Ime Rastuce", "Ime Opadajuce"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sortiraj po");
        builder.setItems(opcije, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0){
                    loadData(sortByNewest);
                } else if (which == 1){
                    loadData(sortByOldest);
                } else if (which == 2){
                    loadData(sortByNameAsc);
                } else if (which == 3){
                    loadData(sortByNameDesc);
                }
            }
        });
        builder.create().show();
    }
}