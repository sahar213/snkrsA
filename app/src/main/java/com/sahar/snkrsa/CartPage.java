package com.sahar.snkrsa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sahar.snkrsa.model.Cart;
import com.sahar.snkrsa.model.ItemCart;
import com.sahar.snkrsa.model.Product;
import com.sahar.snkrsa.services.AuthenticationService;
import com.sahar.snkrsa.services.DatabaseService;

import java.util.ArrayList;

public class CartPage extends AppCompatActivity implements View.OnClickListener {
    private TextView cartItems;
    private Button btnBackFcart;

    // הסל שהוגדר ב-ProductPage
    private Cart cart;


    DatabaseService databaseService;
    AuthenticationService authenticationService;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        cartItems = findViewById(R.id.cart_items);
        btnBackFcart =findViewById(R.id.btnBackFcart);

        // הצגת פרטי המוצרים בסל
        StringBuilder cartContent = new StringBuilder();
        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();
        uid=AuthenticationService.getInstance().getCurrentUserId();

        databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart object) {


                if (object == null)
                    cart = new Cart();
                else cart = object;




            }

            @Override
            public void onFailed(Exception e) {
                cart = new Cart();
            }
        });

        if (cart != null ) {


            for (ItemCart product : cart.getItemCarts()) {
                // cartContent.append("Image: ").append(product.getProduct()).append("\n");
                cartContent.append("Name: ").append(product.getProduct().getName()).append("\n");
                cartContent.append("Price: ").append(product.getProduct().getPrice()).append("\n");
                cartContent.append("Description: ").append(product.getProduct().getDescription()).append("\n\n");
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
