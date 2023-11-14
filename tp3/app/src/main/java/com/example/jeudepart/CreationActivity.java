package com.example.jeudepart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation);
    }

    public void creationCompteOnClick(View view){
        Intent intent = new Intent(CreationActivity.this, JeuxActivity.class);
        startActivity(intent);
    }
}