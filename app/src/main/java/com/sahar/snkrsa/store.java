package com.sahar.snkrsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.sahar.snkrsa.model.Product;

import java.util.ArrayList;
import java.util.List;

import com.sahar.snkrsa.services.DatabaseService;
import com.sahar.snkrsa.utils.ProductAdapter;

public class store extends AppCompatActivity {
    private GridView gridView;

    private DatabaseReference databaseProducts;
    private DatabaseService databaseService;
    private ArrayList<Product> allProduct = new ArrayList<>();

    ProductAdapter productAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        databaseService = DatabaseService.getInstance();

        gridView = findViewById(R.id.gridView);


        productAdapter=new ProductAdapter(this,allProduct);

        gridView.setAdapter(productAdapter);

        // חיבור למסד הנתונים של Firebase


        // קריאה למידע מתוך Firebase
        databaseService.getProducts(new DatabaseService.DatabaseCallback<List<Product>>() {

            public void onCompleted(List<Product> object) {

                allProduct.addAll(object);

                productAdapter.notifyDataSetChanged();


            }
            @Override
            public void onFailed(Exception e) {

            }
        });
    }


    public void mainbtn3(View view) {
        Intent goLog = new Intent(store.this, MainMain.class);
        startActivity(goLog);
    }
}