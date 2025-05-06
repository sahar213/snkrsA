package com.sahar.snkrsa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sahar.snkrsa.model.Cart;
import com.sahar.snkrsa.model.ItemCart;
import com.sahar.snkrsa.model.Product;
import com.sahar.snkrsa.services.AuthenticationService;
import com.sahar.snkrsa.services.DatabaseService;
import com.sahar.snkrsa.utils.ImageUtil;
import com.sahar.snkrsa.utils.SharedPreferencesUtil;

import java.util.ArrayList;

public class ProductPage extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productDescription;
    private Button buyButton, addToCartButton, viewCartButton;
    private Cart cart = null;

    Spinner spColor, spSizes;

    Product productSelected;
    DatabaseService databaseService;
    AuthenticationService authenticationService;
    String uid;
    private double total = 0;
    String[] sizeArr;
    String[] colorArr;// =new String[];

    String[] asrrSize2;

    ArrayAdapter<String> adapterColor, adapterSize;

    Spinner spAmount;

    int amount = 1;
    String stAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);


        initViews();



        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
//        / get the instance of the database service
        databaseService = DatabaseService.getInstance();
        uid=authenticationService.getCurrentUserId();



      databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart object) {

                if(object==null)
                    cart=new Cart();
                else  cart=object;
            }

            @Override
            public void onFailed(Exception e) {
                cart=new Cart();
            }
        });


//         קבלת פרטי המוצר מהIntent
        Intent intent = getIntent();


        productSelected= (Product) intent.getSerializableExtra("product");


initProduct(productSelected);
//
//
//

//         טיפול בכפתור "קנה עכשיו"
//
//
//         טיפול בכפתור "הוסף לסל"
        addToCartButton.setOnClickListener(v -> {




            String color=spColor.getSelectedItem().toString();
            String size=spSizes.getSelectedItem().toString();
            productSelected.setSize(size);
            productSelected.setColor(color);
            stAmount=spAmount.getSelectedItem().toString();
            amount=Integer.parseInt(stAmount);
            ItemCart itemCart=new ItemCart(productSelected,amount);



//           הוספת המוצר לסל הקניות (cart)

            cart.addItemToCart(itemCart);

            Toast.makeText(ProductPage.this, "המוצר נוסף לעגלה", Toast.LENGTH_SHORT).show();

            databaseService.updateCart(cart ,uid, new DatabaseService.DatabaseCallback<Void>() {
                @Override
                public void onCompleted(Void object) {
                    Toast.makeText(ProductPage.this, "המוצר נוסף לעגלה", Toast.LENGTH_SHORT).show();

                    Intent cartIntent = new Intent(ProductPage.this, CartPage.class);
                    startActivity(cartIntent);
                }

                @Override
                public void onFailed(Exception e) {

                }
            });
//
//
        });
//
//               מעבר למסך עגלת הקניות
        viewCartButton.setOnClickListener(v -> {
            Intent cartIntent = new Intent(ProductPage.this, CartPage.class);
            startActivity(cartIntent);
        });
    }





    private void initProduct(Product productSelected) {

        if( productSelected!=null) {






                colorArr= getResources().getStringArray(R.array.arrColor);


                adapterColor=new ArrayAdapter<>(ProductPage.this,   android.R.layout.simple_spinner_dropdown_item,colorArr);
                spColor.setAdapter(adapterColor);


                 sizeArr= getResources().getStringArray(R.array.arrSize);
                 //asrrSize2 = getResources().getStringArray(R.id.asrrSize2);




                Log.d("sizeArr", sizeArr[0]);


                String Type = productSelected.getType();

                    adapterSize=new ArrayAdapter<>(ProductPage.this,   android.R.layout.simple_spinner_dropdown_item,sizeArr);

                 spSizes.setAdapter(adapterSize);
                 productName.setText(productSelected.getName());
                 productPrice.setText(productSelected.getPrice()+"");
                 productDescription.setText(productSelected.getDescription());
                productImage.setImageBitmap(ImageUtil.convertFrom64base(productSelected.getImageName()));
}


    }

    private void initViews() {

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        buyButton = findViewById(R.id.buy_button);
        addToCartButton = findViewById(R.id.btnAddToCart);
        viewCartButton = findViewById(R.id.btnViewCart2);
        spColor = findViewById(R.id.spProductColor);
        spSizes = findViewById(R.id.spProductSize);
        spAmount = findViewById(R.id.spAmount);
    }
}


