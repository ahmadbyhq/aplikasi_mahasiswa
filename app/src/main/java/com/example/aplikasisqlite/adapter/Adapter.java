package com.example.aplikasisqlite.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aplikasisqlite.R;
import com.example.aplikasisqlite.model.Data;

import java.util.List;

public class Adapter extends BaseAdapter {
    // Aktivitas yang menggunakan adapter
    private Activity activity;
    // LayoutInflater untuk mengisi layout item
    private LayoutInflater inflater;
    // Daftar item (data) yang akan ditampilkan
    private List<Data> items;

    // Konstruktor adapter yang menerima activity dan daftar item
    public Adapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    // Mengembalikan jumlah item dalam daftar
    @Override
    public int getCount() {
        return items.size();
    }

    // Mengembalikan item pada posisi tertentu
    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    // Mengembalikan ID item pada posisi tertentu
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Mengembalikan View untuk menampilkan data pada posisi tertentu dalam daftar
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Jika inflater belum diinisialisasi, inisialisasi inflater
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Jika convertView belum dibuat, inflasi layout item dari XML
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        // Inisialisasi TextView dari layout item
        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView address = (TextView) convertView.findViewById(R.id.address);

        // Mendapatkan data dari item pada posisi tertentu
        Data data = items.get(position);

        // Mengisi TextView dengan data
        id.setText(data.getId());
        name.setText(data.getName());
        address.setText(data.getAddress());

        // Mengembalikan View yang telah diisi dengan data
        return convertView;
    }
}
