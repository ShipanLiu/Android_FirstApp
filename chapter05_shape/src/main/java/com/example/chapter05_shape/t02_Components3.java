package com.example.chapter05_shape;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;

public class t02_Components3 extends AppCompatActivity {

    // Spinner dropdown
    private Spinner spinner_dropdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t02_components3);

        // Spinner dropdown
        Spinner spinner_dropdown = findViewById(R.id.sp_dropdown1);



    }
}