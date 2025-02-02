package com.sahar.snkrsa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sahar.snkrsa.model.User;
import com.sahar.snkrsa.services.AuthenticationService;
import com.sahar.snkrsa.services.DatabaseService;
import com.sahar.snkrsa.utils.SharedPreferencesUtil;

public class Register extends AppCompatActivity implements View.OnClickListener {


    EditText etEmail, etPhone, etPassword, etlName, etfName;
    Button btnRegister,btnloginnn;
    TextView tvError;


    private boolean fix=true;


    private User newUser;


    private static final String TAG = "RegisterActivity";


    private AuthenticationService authenticationService;
    private DatabaseService databaseService;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        /// get the instance of the authentication service
        authenticationService = AuthenticationService.getInstance();
        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        initViews();





    }

    private void initViews() {
        etEmail = findViewById(R.id.etEmail);
        etfName = findViewById(R.id.etfName);
        etlName=findViewById(R.id.etlName);
        etPhone = findViewById(R.id.etPhone);
        btnRegister = findViewById(R.id.btnReg);
        btnloginnn=findViewById(R.id.btnloginnn);
        tvError = findViewById(R.id.tvError);
        etPassword=findViewById(R.id.etPassword);
        btnRegister.setOnClickListener(this);
        btnloginnn.setOnClickListener(this);

    }


    private void validateInputs() {
        fix=true;
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String fName = etfName.getText().toString().trim();
        String lName = etlName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String errorText = "";

        // בדיקה אם שדה האימייל ריק או אינו חוקי
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Log.d("check email", email);
            fix=false;
            etEmail.setError("Enter valid Email");

        }


        // בדיקה אם שדה מספר הטלפון ריק או שאינו באורך 10
        if (TextUtils.isEmpty(phone) || phone.length() != 10 || !phone.matches("\\d+")) {
            fix=false;
            etPhone.setError("Enter 10 digits Number");
        }



        if (TextUtils.isEmpty(fName)) {
            etfName.setError("First Name is required");
            fix=false;

        }
        if (TextUtils.isEmpty(lName)) {
            etlName.setError("Last Name is required");
            fix=false;

        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            fix=false;

        }      //
        if(fix) {

            /// Register user
            registerUser(email, password, fName, lName, phone);





        }





    }

    @Override
    public void onClick(View v) {
        if (v==btnloginnn){
            Intent goLog=new Intent(getApplicationContext(), login_activity.class);
            startActivity(goLog);

        }
        if (v == btnRegister) {

            validateInputs();
            Intent goLog=new Intent(Register.this, login_activity.class);
            startActivity(goLog);
        }





        }


    public void Login(View view) {

    }

    /// Register the user
    private void registerUser(String email, String password, String fName, String lName, String phone) {
        Log.d(TAG, "registerUser: Registering user...");

        /// call the sign up method of the authentication service
        authenticationService.signUp(email, password, new AuthenticationService.AuthCallback<String>() {

            @Override
            public void onCompleted(String uid) {
                Log.d(TAG, "onCompleted: User registered successfully");
                /// create a new user object
                User user = new User();
                user.setId(uid);
                user.setEmail(email);
                user.setPassword(password);
                user.setfName(fName);
                user.setlName(lName);
                user.setPhone(phone);

                /// call the createNewUser method of the database service
                databaseService.createNewUser(user, new DatabaseService.DatabaseCallback<Void>() {

                    @Override
                    public void onCompleted(Void object) {
                        Log.d(TAG, "onCompleted: User registered successfully");
                        /// save the user to shared preferences
                        SharedPreferencesUtil.saveUser(Register.this, user);
                        Log.d(TAG, "onCompleted: Redirecting to MainActivity");
                        /// Redirect to MainActivity and clear back stack to prevent user from going back to register screen
                        Intent mainIntent = new Intent(Register.this, MainActivity.class);
                        /// clear the back stack (clear history) and start the MainActivity
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(mainIntent);
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e(TAG, "onFailed: Failed to register user", e);
                        /// show error message to user
                        Toast.makeText(Register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                        /// sign out the user if failed to register
                        /// this is to prevent the user from being logged in again
                        authenticationService.signOut();
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "onFailed: Failed to register user", e);
                /// show error message to user
                Toast.makeText(Register.this, "Failed to register user", Toast.LENGTH_SHORT).show();
            }
        });


    }

}

