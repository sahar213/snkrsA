package com.sahar.snkrsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainMain extends AppCompatActivity {


    private Button btnStore;
    private Button btnReTrain;
    private ImageButton ibtnLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

     btnStore=findViewById(R.id.btnStore);
      btnReTrain=findViewById(R.id.btnReTrain);
    }


    public void store(View view) {
        if (view == btnStore) {
            Intent goLog = new Intent(MainMain.this, store.class);
            startActivity(goLog);

        }
    }

    public void ReTrain(View view) {
        if (view == btnReTrain) {
            Intent goLog = new Intent(MainMain.this, Re_Train.class);
            startActivity(goLog);
        }
    }

    public void mainbtn(View view) {

            Intent goLog = new Intent(MainMain.this, MainMain.class);
            startActivity(goLog);


    }
}