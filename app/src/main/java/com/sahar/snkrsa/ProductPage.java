package com.sahar.snkrsa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sahar.snkrsa.model.Product;
import com.sahar.snkrsa.utils.ImageUtil;

import java.util.ArrayList;

public class ProductPage extends AppCompatActivity {

    private ImageView productImage;
    private TextView productName, productPrice, productDescription;
    private Button buyButton, addToCartButton, viewCartButton;
    static ArrayList<Product> cart = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page);

        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        buyButton = findViewById(R.id.buy_button);
        addToCartButton = findViewById(R.id.btnAddToCart);
        viewCartButton=findViewById(R.id.btnViewCart2);


        // קבלת פרטי המוצר מהIntent
        Intent intent = getIntent();
        String name = intent.getStringExtra("product_name");
        String price = intent.getStringExtra("product_price");
        String description = intent.getStringExtra("product_description");
        String imageName = intent.getStringExtra("product_image");

//        int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
//        if (imageResId != 0) {
//            productImage.setImageResource(imageResId); // הצגת התמונה
//       } else {
//             אם לא נמצאה התמונה, הצג תמונה ברירת מחדל
//            productImage.setImageResource(R.drawable.ic_launcher_background);


        // הצגת פרטי המוצר
       productName.setText(name);
  productPrice.setText(price);
     productDescription.setText(description);
        productImage.setImageBitmap(ImageUtil.convertFrom64base(imageName));
//
 //        טיפול בכפתור "קנה עכשיו"
        buyButton.setOnClickListener(v -> {
            Toast.makeText(ProductPage.this, "Product purchased!", Toast.LENGTH_SHORT).show();
        });

  //       טיפול בכפתור "הוסף לסל"
        addToCartButton.setOnClickListener(v -> {
         //   cart.add(new Product( name, price, description, "imageResId"));
            Product product = new Product(name, price, description, imageName);

            // הוספת המוצר לסל הקניות (cart)
            cart.add(product);

            // הצגת הודעה למשתמש
            Toast.makeText(ProductPage.this, "Added to cart!", Toast.LENGTH_SHORT).show();
            Log.d("ProductPage", "Product added to cart: " + product.getName());
            Log.d("ProductPage", "Product added to cart: " + product.getPrice());
            Log.d("ProductPage", "Product added to cart: " + product.getDescription());
            Log.d("ProductPage", "Product added to cart: " + product.getImageName());
        });

  //       מעבר למסך עגלת הקניות
        viewCartButton.setOnClickListener(v -> {
            Intent cartIntent = new Intent(ProductPage.this, CartPage.class);
            startActivity(cartIntent);
        });
    }


}
