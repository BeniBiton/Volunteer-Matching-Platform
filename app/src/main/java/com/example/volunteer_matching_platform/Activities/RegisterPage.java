package com.example.volunteer_matching_platform.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.volunteer_matching_platform.Services.AuthService;
import com.example.volunteer_matching_platform.Services.FirestoreService;
import com.volunteer_matching_platform.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, confirmPasswordEditText;
    private RadioGroup userTypeGroup;
    private Button registerButton;

    private AuthService authService;
    private FirestoreService firestoreService;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register);

        authService = new AuthService();
        firestoreService = new FirestoreService();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        userTypeGroup = findViewById(R.id.userRoleGroup);
        registerButton = findViewById(R.id.registrationButton);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        int selectedUserTypeId = userTypeGroup.getCheckedRadioButtonId();

        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Password do not match");
            return;
        }

        RadioButton selectedUserTypeButton = findViewById(selectedUserTypeId);
        String userType = selectedUserTypeButton.getText().toString();

        authService.registerUser(email, password, task -> {
            if (task.isSuccessful()) {
                String userId = authService.getCurrentUser().getUid();
                Map<String, Object> profileData = new HashMap<>();
                profileData.put("email", email);
                profileData.put("userType", userType);
                Log.d("RegisterPage", "the register with ID:" + userId + " is success");

                firestoreService.saveUserProfile(userId, profileData, profileTask -> {
                    if (profileTask.isSuccessful()) {
                        navigateToHomeScreen(userType);
                    } else {
                        showError("Failed to save profile");
                    }
                });
            } else {
                showError("Registration failed: " + task.getException().getMessage());
            }
        });
    }

    private void navigateToHomeScreen(String userType) {
        Toast.makeText(this, "Navigating to " + userType + " screen", Toast.LENGTH_SHORT).show();
        // this will be ok when i will make the page
//      Intent intent = userType.equals("Babysitter") ?
//              new Intent(this, .class) :
//               new Intent(this, .class);
//
//        startActivity(intent);
        finish();
    }

    private void showError(String massage) {
        Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
    }
}
