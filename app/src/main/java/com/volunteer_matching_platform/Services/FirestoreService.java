package com.volunteer_matching_platform.Services;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreService {
    private FirebaseFirestore firestore;


    public FirestoreService() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public void getUserRole(String userId, OnCompleteListener<DocumentSnapshot> listener) {
        firestore.collection("users").document(userId).get().addOnCompleteListener(listener);
    }

}
