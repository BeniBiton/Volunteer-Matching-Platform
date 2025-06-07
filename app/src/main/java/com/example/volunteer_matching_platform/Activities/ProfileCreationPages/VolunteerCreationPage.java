package com.example.volunteer_matching_platform.Activities.ProfileCreationPages;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.volunteer_matching_platform.Enums.Interest;
import com.example.volunteer_matching_platform.Enums.Skill;
import com.example.volunteer_matching_platform.Models.Volunteer;
import com.example.volunteer_matching_platform.Services.FireStorageService;
import com.example.volunteer_matching_platform.Services.FirestoreService;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.volunteer_matching_platform.R;
import com.volunteer_matching_platform.BuildConfig;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VolunteerCreationPage extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1001;

    private TextInputEditText editTextName, editTextAge, editTextRegion, editTextLocationAddress, editTextSkills, editTextInterests, editTextPhoneNumber;
    private MaterialButton buttonSelectAddress, buttonUploadPicture, buttonSubmitProfile;

    private final String[] skillsList = Arrays.stream(Skill.values())
            .map(Skill::getLabel)
            .toArray(String[]::new);
    private final String[] interestsList = Arrays.stream(Interest.values())
            .map(Interest::getDisplayName)
            .toArray(String[]::new);
    private Uri selectedImageUri = null;
    private boolean[] selectedSkills;
    private boolean[] selectedInterests;
    private final ArrayList<String> skillsSelected = new ArrayList<>();
    private final ArrayList<String> interestsSelected = new ArrayList<>();
    public static final int IMAGE_PICKER_REQUEST = 1000;

    private String PlacesApiKey = BuildConfig.PLACES_API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volunteer_creation_page);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyDw1Nf31ZtANkgcZQ-oUEuon2LnOqjptF4");
        }

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextRegion = findViewById(R.id.editTextRegion);
        editTextLocationAddress = findViewById(R.id.editTextLocationAddress);
        editTextSkills = findViewById(R.id.editTextSkills);
        editTextInterests = findViewById(R.id.editTextInterests);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);

        buttonSelectAddress = findViewById(R.id.buttonSelectAddress);
        buttonUploadPicture = findViewById(R.id.buttonUploadPicture);
        buttonSubmitProfile = findViewById(R.id.buttonSumbitProfile);

        selectedSkills = new boolean[skillsList.length];
        selectedInterests = new boolean[interestsList.length];

        editTextSkills.setOnClickListener(v -> showMultiSelectDialog("Select Skills", skillsList, selectedSkills, skillsSelected, editTextSkills));
        editTextInterests.setOnClickListener(v -> showMultiSelectDialog("Select Interests", interestsList, selectedInterests, interestsSelected, editTextInterests));

        buttonSelectAddress.setOnClickListener(v -> openPlaceAutocomplete());
        buttonUploadPicture.setOnClickListener(v -> FireStorageService.pickImage(this));
        buttonSubmitProfile.setOnClickListener(v -> submitProfileCreation());
    }

    private void showMultiSelectDialog(String title, String[] items, boolean[] selectedItems, List<String> selectedList, TextInputEditText editText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMultiChoiceItems(items, selectedItems, (dialog, indexSelected, isChecked) -> {
            if (isChecked && !selectedList.contains(items[indexSelected])) {
                selectedList.add(items[indexSelected]);
            } else if (!isChecked) {
                selectedList.remove(items[indexSelected]);
            }
        });
        builder.setPositiveButton("OK", (dialog, which) -> editText.setText(String.join(", ", selectedList)));
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void submitProfileCreation() {
        String name = editTextName.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String region = editTextRegion.getText().toString().trim();
        String address = editTextLocationAddress.getText().toString().trim();
        String phoneStr = editTextPhoneNumber.getText().toString().trim();

        if (name.isEmpty() || ageStr.isEmpty() || region.isEmpty() || address.isEmpty() || phoneStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age, phone;
        try {
            age = Integer.parseInt(ageStr);
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Age and Phone must be valid numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        Volunteer volunteer = new Volunteer(userId, name, email);
        volunteer.setAge(age);
        volunteer.setPhoneNumber(phone);
        volunteer.setRegion(region);
        volunteer.setCity(address);
        volunteer.setInterests(new ArrayList<>(interestsSelected));
        volunteer.setActive(true);
        volunteer.setCreatedAt(new Date());
        volunteer.setUpdatedAt(new Date());
        volunteer.setHoursVolunteered(0);
        volunteer.setSkills(convertStringsToSkills(skillsSelected));

        FirestoreService firestoreService = new FirestoreService();

        if (selectedImageUri != null) {
            FireStorageService.uploadProfilePicture(this, new Intent().setData(selectedImageUri), uri -> {
                String profilePictureUrl = uri.toString();
                volunteer.setProfileImageUrl(profilePictureUrl);
                Log.d("VolunteerCreationPage", "Image uploaded, saving profile...");

                firestoreService.saveVolunteerProfile(userId, volunteer.toMap(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error saving profile: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            });
        } else {
            firestoreService.saveVolunteerProfile(userId, volunteer.toMap(), task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Profile saved successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error saving profile: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    private void openPlaceAutocomplete() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

    private Set<Skill> convertStringsToSkills(List<String> skillsStrings) {
        Set<Skill> skillSet = new HashSet<>();
        for (String skillStr : skillsStrings) {
            try {
                String enumName = "SKILL_" + skillStr.toUpperCase().replace(" ", "_");
                skillSet.add(Skill.valueOf(enumName));
            } catch (IllegalArgumentException e) {
                Log.e("VolunteerCreationPage", "Skill not found in enum: " + skillStr);
            }
        }
        return skillSet;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                String address = place.getAddress();
                editTextLocationAddress.setText(address);
            } else {
                Toast.makeText(this, "Failed to choose location", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1000 && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
        }
    }

}
