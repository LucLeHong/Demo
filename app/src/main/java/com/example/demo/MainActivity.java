package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Button;

import com.example.demo.cp.CPActivity;
import com.example.demo.sqlite.SQLiteActivity;

public class MainActivity extends AppCompatActivity {

    Button btnSQLite;
    Button btnCT;
    CheckConnect checkConnect;
    IntentFilter filter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSQLite = findViewById(R.id.btn_SQLite);
        btnCT = findViewById(R.id.btn_CP);

        btnSQLite.setOnClickListener(view ->
            startActivity(new Intent(this, SQLiteActivity.class))

        );

        btnCT.setOnClickListener(view ->
            startActivity(new Intent(this, CPActivity.class))
        );

        checkConnect = new CheckConnect();
        filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(checkConnect, filter);

    }

    @Override
    protected void onStop() {
        unregisterReceiver(checkConnect);
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        registerReceiver(checkConnect, filter);
        super.onPostResume();
    }
}