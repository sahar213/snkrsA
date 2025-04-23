package com.sahar.snkrsa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
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

public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "AddProductActivity";

    // הגדרת כפתור ומסדי נתונים
    private Button addProductButton, btnGallery, btnCamera;
    private EditText productNameField, productPriceField, productDescriptionField;
    private Spinner sizeSpinner, colorSpinner, typeSpinner;

    String size,color,type;


    private ImageView productImageView;
    private DatabaseService databaseService;

    /// Activity result launcher for selecting image from gallery
    private ActivityResultLauncher<Intent> selectImageLauncher;
    /// Activity result launcher for capturing image from camera
    private ActivityResultLauncher<Intent> captureImageLauncher;

    ImageView iv;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;
    private String selectedColor="";
    private String selectedSize="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        /// request permission for the camera and storage
        ImageUtil.requestPermission(this);

        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();

        /// register the activity result launcher for selecting image from gallery
        selectImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        iv.setImageURI(selectedImage);
                    }
                });

        /// register the activity result launcher for capturing image from camera
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

        btnCamera=findViewById(R.id.btnCamera);
        btnGallery=findViewById(R.id.btnGoGallery);
        iv=findViewById(R.id.ivProduct2);

        // חיבור לספינרים
        sizeSpinner = findViewById(R.id.spinnerSize);
        colorSpinner = findViewById(R.id.spinnerColor);
        typeSpinner = findViewById(R.id.spinnerType);

        // הגדרת מאזין לכפתור
        addProductButton.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == addProductButton) {
            // שליפת המידע מהשדות
            String name = productNameField.getText().toString().trim();
            String stprice = productPriceField.getText().toString().trim();

            double price=Double.parseDouble(stprice);
            String description = productDescriptionField.getText().toString().trim();

            // שליפת הערכים שנבחרו בספינרים
             selectedSize += sizeSpinner.getSelectedItem().toString()+", ";
             selectedColor += colorSpinner.getSelectedItem().toString()+", ";
            String selectedType = typeSpinner.getSelectedItem().toString();

            String imageBase64 = ImageUtil.convertTo64Base(iv);

            type=typeSpinner.getSelectedItem().toString();




            // בדיקה אם כל השדות מולאו
            if (name.isEmpty() || stprice.isEmpty() || description.isEmpty() || selectedSize.isEmpty() || selectedColor.isEmpty() || selectedType.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }
            String id = DatabaseService.getInstance().generateItemId();
            // יצירת אובייקט מוצר חדש עם הערכים מהספינרים


            // יצירת מזהה ייחודי למוצר
            String productId = databaseService.generateItemId();
            if (productId != null) {



                    Product newProduct = new Product(id, name, price,type,selectedSize,selectedColor, description,imageBase64 );

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
            return;
        }
        if (v == btnGallery) {
// select image from gallery
            Log.d(TAG, "Select image button clicked");
            selectImageFromGallery();
            return;

        }
    }


    /// select image from gallery
    private void selectImageFromGallery() {
        //   Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //  selectImageLauncher.launch(intent);

        imageChooser();
    }

    /// capture image from camera
    private void captureImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageLauncher.launch(takePictureIntent);
    }





    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    iv.setImageURI(selectedImageUri);
                }
            }
        }
    }



}