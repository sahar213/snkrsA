package com.sahar.snkrsa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sahar.snkrsa.model.Product;
import com.sahar.snkrsa.services.DatabaseService;
import com.sahar.snkrsa.utils.ImageUtil;

import java.util.ArrayList;

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AddProductActivity";

    private Button addProductButton, btnGallery, btnCamera;
    private EditText productNameField, productPriceField, productDescriptionField;
    private Spinner sizeSpinner, colorSpinner, typeSpinner;

    private ImageView iv;

    private DatabaseService databaseService;

    private ActivityResultLauncher<Intent> selectImageLauncher;
    private ActivityResultLauncher<Intent> captureImageLauncher;

    private static final int SELECT_PICTURE = 200;

    private String selectedColor = "";
    private String selectedSize = "";

    private ArrayList<String> colorList=new ArrayList<>();
    private ArrayList<String> sizeList= new ArrayList<>();

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ImageUtil.requestPermission(this);
        databaseService = DatabaseService.getInstance();

        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        iv.setImageURI(selectedImage);
                    }
                });

        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        iv.setImageBitmap(bitmap);
                    }
                });

        // חיבור למרכיבי הממשק
        addProductButton = findViewById(R.id.buttonSaveProduct);
        productNameField = findViewById(R.id.editTextProductName);
        productPriceField = findViewById(R.id.editTextPrice);
        productDescriptionField = findViewById(R.id.editTextDescription);

        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGoGallery);
        iv = findViewById(R.id.ivProduct2);

        sizeSpinner = findViewById(R.id.spinnerSize);
        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSize=parent.getSelectedItem().toString();
                sizeList.add(selectedSize);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        colorSpinner = findViewById(R.id.spinnerColor);

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedColor=parent.getSelectedItem().toString();
                colorList.add(selectedColor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpinner = findViewById(R.id.spinnerType);

        addProductButton.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addProductButton) {
            String name = productNameField.getText().toString().trim();
            String stprice = productPriceField.getText().toString().trim();
            String description = productDescriptionField.getText().toString().trim();


            String selectedType = typeSpinner.getSelectedItem().toString();

            String imageBase64 = ImageUtil.convertTo64Base(iv);
            type = selectedType;

            if (name.isEmpty() || stprice.isEmpty() || description.isEmpty()
                    || selectedSize.isEmpty() || selectedColor.isEmpty() || selectedType.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            double price;
            try {
                price = Double.parseDouble(stprice);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
                return;
            }

            String productId = databaseService.generateProductId();
            if (productId != null) {
                Product newProduct = new Product(productId, name, price, type, sizeList, colorList, description, imageBase64);

                databaseService.createNewProduct(newProduct, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {
                        Toast.makeText(AddProductActivity.this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Toast.makeText(AddProductActivity.this, "Failed to add product", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        if (v == btnCamera) {
            Log.d(TAG, "Capture image button clicked");
            captureImageFromCamera();
        }

        if (v == btnGallery) {
            Log.d(TAG, "Select image button clicked");
            selectImageFromGallery();
        }
    }

    private void selectImageFromGallery() {
        imageChooser();
    }

    private void captureImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageLauncher.launch(takePictureIntent);
    }

    private void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                iv.setImageURI(selectedImageUri);
            }
        }
    }



}