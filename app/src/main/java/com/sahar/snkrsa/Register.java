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

public class Register extends AppCompatActivity implements View.OnClickListener {


    EditText etEmail, etPhone, etPassword, etlName, etfName;
    Button btnRegister,btnloginnn;
    TextView tvError;


    private boolean fix=true;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    private User newUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);






        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

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

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);



        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        mAuth = FirebaseAuth.getInstance();

    }


    private void validateInputs() {
        fix=true;
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String fName = etfName.getText().toString().trim();
        String lName = etlName.getText().toString().trim();
        String Password = etPassword.getText().toString().trim();
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
        if (TextUtils.isEmpty(Password)) {
            etPassword.setError("Password is required");
            fix=false;

        }      //
        if(fix)
        {



            mAuth.createUserWithEmailAndPassword(email, Password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Log.d("TAG", "createUserWithEmail:success");




                                newUser=new User(mAuth.getUid(), fName, lName, phone, email, Password);

                                myRef.child(mAuth.getUid()).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(Register.this, "Registration seccessful", Toast.LENGTH_LONG).show();
                                    }
                                })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(Register.this, "Error", Toast.LENGTH_LONG).show();
                                            }
                                        });



                              Log.d("TAGUser", newUser.toString());


                                SharedPreferences.Editor editor = sharedpreferences.edit();

                                editor.putString("email", email);
                                editor.putString("password", Password);

                                editor.apply();


                                Intent go = new Intent(Register.this, login_activity.class);
                                startActivity(go);


                            }

                            else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Register.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });

        }



    }

    @Override
    public void onClick(View v) {
        if (v==btnloginnn){
            Intent goLog=new Intent(getApplicationContext(), login_activity.class);
            startActivity(goLog);

        }
        if (v == btnRegister&&fix==true) {

            validateInputs();
            Intent goLog=new Intent(getApplicationContext(), login_activity.class);
            startActivity(goLog);
        }





        }


    public void Login(View view) {

    }
}

