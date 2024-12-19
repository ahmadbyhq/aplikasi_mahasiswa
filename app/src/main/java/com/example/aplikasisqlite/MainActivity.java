package com.example.aplikasisqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.aplikasisqlite.adapter.Adapter;
import com.example.aplikasisqlite.helper.DbHelper;
import com.example.aplikasisqlite.model.Data;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// Kelas MainActivity mewarisi AppCompatActivity dan merupakan titik awal aplikasi
public class MainActivity extends AppCompatActivity {

    // Deklarasi ListView untuk menampilkan daftar data
    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    DbHelper SQLite = new DbHelper(this);

    // Konstanta untuk menyimpan nama kolom database
    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Inisialisasi SQLite database
        SQLite = new DbHelper(getApplicationContext());

        // Set up floating action button
        FloatingActionButton fab = findViewById(R.id.fab);

        // Inisialisasi ListView
        listView = findViewById(R.id.list_view);

        // Set up onClick listener untuk floating action button
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent untuk pindah ke halaman AddEdit
                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                startActivity(intent);
            }
        });

        // Inisialisasi adapter dan set ke listView
        adapter = new Adapter(MainActivity.this, itemList);
        listView.setAdapter(adapter);

        // Set up onItemLongClickListener untuk edit dan hapus data
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final String idx = itemList.get(position).getId();
                final String name = itemList.get(position).getName();
                final String address = itemList.get(position).getAddress();

                // Opsi dialog untuk Edit dan Delete
                final CharSequence[] dialogItem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            // Aksi untuk Edit
                            case 0:
                                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                                intent.putExtra(TAG_ID, idx);
                                intent.putExtra(TAG_NAME, name);
                                intent.putExtra(TAG_ADDRESS, address);
                                startActivity(intent);
                                break;
                            // Aksi untuk Delete
                            case 1:
                                SQLite.delete(Integer.parseInt(idx));
                                itemList.clear();
                                getAllData();
                                break;
                        }
                    }
                }).show();
                return true;
            }
        });

        // Memuat semua data dari database
        getAllData();
    }

    // Fungsi untuk mengambil semua data dari database
    private void getAllData() {
        ArrayList<HashMap<String, String>> rows = SQLite.getAllData();

        for (HashMap<String, String> row : rows) {
            String id = row.get(TAG_ID);
            String name = row.get(TAG_NAME);
            String address = row.get(TAG_ADDRESS);

            Data data = new Data();
            data.setId(id);
            data.setName(name);
            data.setAddress(address);

            itemList.add(data);
        }

        // Memberitahu adapter bahwa data telah berubah
        adapter.notifyDataSetChanged();
    }

    // Memuat ulang data ketika aktivitas dilanjutkan
    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        getAllData();
    }
}
