package com.example.locationfinder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
DatabaseHelper db;
public EditText Lat;
public EditText Long;
public EditText Address;
public Button Generate;
public TextView AddressResult;
double Latitude,Longitude;
public Button Add,View,Delete,Update,Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);

        Lat = findViewById(R.id.LatInput);
        Long = findViewById(R.id.LongInput);
        Address = findViewById(R.id.Address);

        Generate = findViewById(R.id.Generate);
        AddressResult = findViewById(R.id.AddressResult);
        Add = findViewById(R.id.Add);
        View = findViewById(R.id.View);
        Delete = findViewById(R.id.Delete);
        Update = findViewById(R.id.Update);
        Search = findViewById(R.id.Search);


        Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddress();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Addr = Address.getText().toString();
                Latitude = Double.parseDouble(Lat.getText().toString());
                Longitude = Double.parseDouble(Long.getText().toString());

                db.insertDetails(Addr, String.valueOf(Latitude), String.valueOf(Longitude));
                Toast.makeText(MainActivity.this, "New Entry added", Toast.LENGTH_SHORT).show();
            }
        });

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Addr = Address.getText().toString();
                Latitude = Double.parseDouble(Lat.getText().toString());
                Longitude = Double.parseDouble(Long.getText().toString());
                db.updateDetails(Addr, String.valueOf(Latitude), String.valueOf(Longitude));
                Toast.makeText(MainActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
            }
        });

        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Addr = Address.getText().toString();
                db.deleteDetails(Addr);
                Toast.makeText(MainActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = db.getDetails();
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID :"+res.getString(0)+"\n");
                    buffer.append("Address :"+res.getString(1)+"\n");
                    buffer.append("Latitude :"+res.getString(2)+"\n");
                    buffer.append("Longitude :"+res.getString(3)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Location Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });

        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor;
                String find = Address.getText().toString();
                cursor = db.searchDetails(find);
                StringBuffer buffer = new StringBuffer();

                while(cursor.moveToNext()){
                    buffer.append("ID :"+cursor.getString(0)+"\n");
                    buffer.append("Address :"+cursor.getString(1)+"\n");
                    buffer.append("Latitude :"+cursor.getString(2)+"\n");
                    buffer.append("Longitude :"+cursor.getString(3)+"\n\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Search Results");
                builder.setMessage(buffer.toString());
                builder.show();

            }
        });

    }

    public void getAddress(){
        Geocoder G = new Geocoder(MainActivity.this, Locale.getDefault());
        Latitude = Double.parseDouble(Lat.getText().toString());
        Longitude = Double.parseDouble(Long.getText().toString());
        try {
            List<Address> addressList = G.getFromLocation(Latitude,Longitude,1);
            if (addressList.size()>0) {
                AddressResult.setText(addressList.get(0).getAddressLine(0));
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}


