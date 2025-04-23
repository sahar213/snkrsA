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
    private     Cart cart=new Cart() ;

    Spinner spColor, spSizes;

    Product productSelected;
    DatabaseService databaseService;
    AuthenticationService authenticationService;
    String uid;
    private double total=0;
    String [] sizeArr;
    String [] colorArr;// =new String[];

    ArrayAdapter<String> adapterColor, adapterSize;

    Spinner spAmount;

    int amount=1;
    String stAmount;


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
        spColor=findViewById(R.id.spProductColor);
        spSizes=findViewById(R.id.spProductSize);
        spAmount=findViewById(R.id.spAmount);

        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();
        uid=AuthenticationService.getInstance().getCurrentUserId();

        databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart object) {


                if(object==null)
                    cart=new Cart();
                else cart=object;



            }

            @Override
            public void onFailed(Exception e) {
                cart=new Cart();

            }
        });







        // קבלת פרטי המוצר מהIntent
        Intent intent = getIntent();


        productSelected= (Product) intent.getSerializableExtra("product");




if( productSelected!=null) {





    String color=productSelected.getColor().toString().trim();
    colorArr= color.split(",");


   adapterColor=new ArrayAdapter<>(ProductPage.this,   android.R.layout.simple_spinner_dropdown_item,colorArr);
   spColor.setAdapter(adapterColor);

   String sizes=productSelected.getSize().toString().trim();

    sizeArr=sizes.split(",");


   Log.d("sizeArr", sizeArr[0]);

    adapterSize=new ArrayAdapter<>(ProductPage.this,   android.R.layout.simple_spinner_dropdown_item,sizeArr);
    spSizes.setAdapter(adapterSize);
    productName.setText(productSelected.getName());
    productPrice.setText(productSelected.getPrice()+"");
    productDescription.setText(productSelected.getDescription());
    productImage.setImageBitmap(ImageUtil.convertFrom64base(productSelected.getImageName()));
}
//
 //        טיפול בכפתור "קנה עכשיו"
        buyButton.setOnClickListener(v -> {


            Toast.makeText(ProductPage.this, "Product purchased!", Toast.LENGTH_SHORT).show();
        });

  //       טיפול בכפתור "הוסף לסל"
        addToCartButton.setOnClickListener(v -> {


              stAmount=spAmount.getSelectedItem().toString();
            amount=Integer.parseInt(stAmount);

            String color=spColor.getSelectedItem().toString();
            String size=spSizes.getSelectedItem().toString();
            Product userProduct=new Product(productSelected);
            userProduct.setColor(color);
            userProduct.setSize(size);



            ItemCart itemCart=new ItemCart(userProduct,amount);

          // הוספת המוצר לסל הקניות (cart)

            cart.addItemToCart(itemCart);

            Toast.makeText(ProductPage.this, "המוצר נוסף לעגלה", Toast.LENGTH_SHORT).show();

            databaseService.updateCart(cart,uid, new DatabaseService.DatabaseCallback<Void>() {
                @Override
                public void onCompleted(Void object) {
                    Toast.makeText(ProductPage.this, "המוצר נוסף לעגלה", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(Exception e) {

                }
            });









        });

  //       מעבר למסך עגלת הקניות
        viewCartButton.setOnClickListener(v -> {
            Intent cartIntent = new Intent(ProductPage.this, CartPage.class);
            startActivity(cartIntent);
        });
    }





}
