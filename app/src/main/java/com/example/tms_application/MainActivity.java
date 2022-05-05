package com.example.tms_application;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // These are the global variables
    EditText editName, editPassword;
    TextView result;
    Button buttonSubmit, buttonReset;
    private FirebaseAuth mAuth;


    public   void  updateUIs(String aa){
        Context context = getApplicationContext();
        String text = aa;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();


        editName  = (EditText) findViewById(R.id.editName);
        editPassword = (EditText) findViewById(R.id.editPassword);
//        result = (TextView) findViewById(R.id.tvResult);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
//        buttonReset = (Button) findViewById(R.id.buttonReset);
        /*
            Submit Button
        */
        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String email = editName.getText().toString();
                String password = editPassword.getText().toString();

                FirebaseFirestore db = FirebaseFirestore.getInstance();

                Map<String, Object> user = new HashMap<>();
                user.put("first", email);
                user.put("password", password);


                mAuth = FirebaseAuth.getInstance();



                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent i = new Intent(MainActivity.this, Dashboard.class);
                                    startActivity(i);
                                } else {

                                    Toast.makeText(MainActivity.this, "Incorret Detail", Toast.LENGTH_LONG).show();

                                }
                            }
                        });








            }
        });


    }

    private void updateUI(FirebaseUser user) {

    }
}