package com.project.mondo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.project.mondo.R;
import com.project.mondo.models.DatabaseAccessObject;

public class MainActivity extends AppCompatActivity {
    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private Button registerButton;
    private Editable email;
    private Editable password;
    private DatabaseAccessObject user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        user = new DatabaseAccessObject(this);
        user.open();
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            Intent registerPage = new Intent(this, RegisterActivity.class);
            startActivity(registerPage);
            finish();
        });
        loginButton.setOnClickListener(v -> {
            emailText = findViewById(R.id.editTextTextEmailAddress);
            passwordText = findViewById(R.id.editTextTextPassword);
            email = emailText.getText();
            password = passwordText.getText();
            if (user.authenticateUser(email.toString(), password.toString())) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                Intent homePage = new Intent(this, HomePageActivity.class);
                startActivity(homePage);
                finish();
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}