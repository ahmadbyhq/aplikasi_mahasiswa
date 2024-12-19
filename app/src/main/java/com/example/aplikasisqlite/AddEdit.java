package com.example.aplikasisqlite;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasisqlite.MainActivity;
import com.example.aplikasisqlite.R;
import com.example.aplikasisqlite.helper.DbHelper;

public class AddEdit extends AppCompatActivity {

    EditText txt_id, txt_name, txt_address;
    Button btn_submit, btn_cancel;
    DbHelper SQLite;
    String id, name, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // Initialize DbHelper
        SQLite = new DbHelper(this);

        // Set up ActionBar with back button
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            Log.e("AddEdit", "Action bar is null");
        }

        // Initialize UI components
        txt_id = findViewById(R.id.txt_id);
        txt_name = findViewById(R.id.txt_name);
        txt_address = findViewById(R.id.txt_address);
        btn_submit = findViewById(R.id.btn_submit);
        btn_cancel = findViewById(R.id.btn_cancel);

        // Get data passed from MainActivity for editing
        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        name = getIntent().getStringExtra(MainActivity.TAG_NAME);
        address = getIntent().getStringExtra(MainActivity.TAG_ADDRESS);

        // Set title and pre-fill EditText fields if editing existing data
        if (id == null || id.isEmpty()) {
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            txt_id.setText(id);
            txt_name.setText(name);
            txt_address.setText(address);
        }

        // Button submit onClickListener
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id == null || id.isEmpty()) {
                    save(); // Add new data
                } else {
                    edit(); // Edit existing data
                }
            }
        });

        // Button cancel onClickListener
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank(); // Clear all EditText fields
                finish(); // Finish activity
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish(); // Handle back button press
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar back button click
        if (item.getItemId() == android.R.id.home) {
            blank(); // Clear all EditText fields
            finish(); // Finish activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Method to clear all EditText fields
    private void blank() {
        txt_name.requestFocus();
        txt_id.setText(null);
        txt_name.setText(null);
        txt_address.setText(null);
    }

    // Method to save data to SQLite database
    private void save() {
        String name = txt_name.getText().toString().trim();
        String address = txt_address.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(name, address); // Insert new data to database
            blank(); // Clear all EditText fields
            finish(); // Finish activity
        }
    }

    // Method to update data in SQLite database
    private void edit() {
        String name = txt_name.getText().toString().trim();
        String address = txt_address.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please input name or address ...", Toast.LENGTH_SHORT).show();
        } else {
            int id = Integer.parseInt(txt_id.getText().toString().trim());
            SQLite.update(id, name, address); // Update existing data in database
            blank(); // Clear all EditText fields
            finish(); // Finish activity
        }
    }
}
