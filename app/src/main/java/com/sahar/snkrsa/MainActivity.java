package com.sahar.snkrsa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnLoginF, btnRegF;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnLoginF= findViewById(R.id.btnLoginF);
        btnRegF=findViewById(R.id.btnRegF);

    }

    public void regf(View view) {
        if (view==btnRegF){
            Intent goLog=new Intent(getApplicationContext(), Register.class);
            startActivity(goLog);
    }
    }

    public void loginf(View view) {
        if (view==btnLoginF){
            Intent goLog=new Intent(getApplicationContext(), login_activity.class);
            startActivity(goLog);
    }
    }}