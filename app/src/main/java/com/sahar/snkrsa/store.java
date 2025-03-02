package com.sahar.snkrsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sahar.snkrsa.model.Product;

import java.util.ArrayList;
import java.util.List;

public class store extends AppCompatActivity {
    private GridView gridView;
    private List<Product> productList;
    private DatabaseReference databaseProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        gridView = findViewById(R.id.gridView);
        productList = new ArrayList<>();

        // חיבור למסד הנתונים של Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseProducts = database.getReference("Products");

        // קריאה למידע מתוך Firebase
        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear(); // מחק את המידע הקודם
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Product product = productSnapshot.getValue(Product.class);
                    productList.add(product);
                }

                // יצירת האדפטר עם המידע המתקבל
                ProductAdapter adapter = new ProductAdapter(store.this, productList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // טיפול בשגיאות
                Toast.makeText(store.this, "Failed to load products.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void mainbtn3(View view) {
        Intent goLog = new Intent(store.this, MainMain.class);
        startActivity(goLog);
    }
}