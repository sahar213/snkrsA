package com.sahar.snkrsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sahar.snkrsa.model.Product;

import java.util.ArrayList;

public class CartPage extends AppCompatActivity implements View.OnClickListener {
    private TextView cartItems;
    private Button btnBackFcart;
    private static ArrayList<Product> cart = ProductPage.cart; // הסל שהוגדר ב-ProductPage

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        cartItems = findViewById(R.id.cart_items);
        btnBackFcart =findViewById(R.id.btnBackFcart);

        // הצגת פרטי המוצרים בסל
        StringBuilder cartContent = new StringBuilder();

        if (cart != null && !cart.isEmpty()) {
            for (Product product : cart) {
                cartContent.append("Image: ").append(product.getImageName()).append("\n");
                cartContent.append("Name: ").append(product.getName()).append("\n");
                cartContent.append("Price: ").append(product.getPrice()).append("\n");
                cartContent.append("Description: ").append(product.getDescription()).append("\n\n");
            }
        } else {
            cartContent.append("הסל ריק.");
        }
btnBackFcart.setOnClickListener(this);
        cartItems.setText(cartContent.toString());
    }

    @Override
    public void onClick(View v) {
            Intent goLog = new Intent(CartPage.this, ProductPage.class);
            startActivity(goLog);
    }
}
