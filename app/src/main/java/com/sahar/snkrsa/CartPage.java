package com.sahar.snkrsa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sahar.snkrsa.model.Cart;
import com.sahar.snkrsa.model.ItemCart;
import com.sahar.snkrsa.services.AuthenticationService;
import com.sahar.snkrsa.services.DatabaseService;
import com.sahar.snkrsa.utils.CartAdapter;

import java.util.ArrayList;

public class CartPage extends AppCompatActivity implements View.OnClickListener {
    private Button btnBackFcart;
    private Cart cart;
    private RecyclerView rvCart;
    private CartAdapter cartAdapter;

    DatabaseService databaseService;
    AuthenticationService authenticationService;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_page);

        // שירותים
        authenticationService = AuthenticationService.getInstance();
        databaseService = DatabaseService.getInstance();
        uid = authenticationService.getCurrentUserId();

        rvCart = findViewById(R.id.rvCart);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        // אתחול סל ריק בתחילה כדי למנוע NullPointer
        cart = new Cart(new ArrayList<ItemCart>());
        cartAdapter = new CartAdapter(this, cart);
        rvCart.setAdapter(cartAdapter);

        // טען את הסל מהמסד
        databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart resultCart) {
                if (resultCart != null && resultCart.getItemCarts() != null) {
                    cart.setItemCarts(resultCart.getItemCarts());
                    cartAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(Exception e) {
                Log.e("error", e.getMessage());
                Toast.makeText(CartPage.this, "שגיאה בטעינת הסל", Toast.LENGTH_SHORT).show();
            }
        });

        // כפתור חזרה
        btnBackFcart = findViewById(R.id.btnBackFcart);
        btnBackFcart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent goLog = new Intent(CartPage.this, store.class);
        startActivity(goLog);
    }
}
