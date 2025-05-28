package com.volunteer_matching_platform.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.volunteer_matching_platform.R;
import com.volunteer_matching_platform.Services.AuthService;
import com.volunteer_matching_platform.Services.FirestoreService;

public class LoginPage extends AppCompatActivity {

    private AuthService authService;
    private FirestoreService firestoreService;
    private EditText emailEditText, passwordEditText;
    private TextView goToRegistrationLink;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authService = new AuthService();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        goToRegistrationLink = findViewById(R.id.goToRegistrationLink);


        loginButton.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        authService.loginUser(email, password, task -> {
            if (task.isSuccessful()) {
                String userId = authService.getCurrentUser().getUid();

                firestoreService.getUserRole(userId, profileTask -> {
                    if (profileTask.isSuccessful()) {
                        String userType = profileTask.getResult().getString("userType");
                        navigateToHomeScreen(userType);
                    } else {
                        showError("Failed to retrieve profile");
                    }
                });
            } else {
                showError("Login field: " + task.getException().getMessage());
            }
        });
    }

    private void navigateToHomeScreen(String userType) {
        // this is the navigation to the next page, i will add it when i will make the page
//        Intent intent = userType.equals("Babysitter") ?
//                new Intent(this, LoginPage.class) :
//                new Intent(this, LoginPage.class);
//        startActivity(intent);
        finish();
    }

    private void showError(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }
}
