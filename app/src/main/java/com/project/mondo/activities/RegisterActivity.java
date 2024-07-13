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
import com.project.mondo.database.DatabaseAccessObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameTextView;
    private EditText emailTextView;
    private EditText passwordTextView;
    private Button registerButton;
    private Editable name;
    private Editable email;
    private Editable password;
    private DatabaseAccessObject user;

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
        user = new DatabaseAccessObject(this);
        user.open();
        registerButton = findViewById(R.id.registerPageButton);
        registerButton.setOnClickListener(v -> {
            nameTextView = findViewById(R.id.editTextText);
            name = nameTextView.getText();
            emailTextView = findViewById(R.id.editTextTextEmailAddress2);
            email = emailTextView.getText();
            passwordTextView = findViewById(R.id.editTextTextPassword2);
            password = passwordTextView.getText();
            user.insertUser(name.toString(),
                    email.toString(),
                    password.toString());
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            Intent loginPage = new Intent(this, MainActivity.class);
            startActivity(loginPage);
            finish();
        });
    }
    protected void onDestroy() {
        super.onDestroy();
        user.close();
    }
}