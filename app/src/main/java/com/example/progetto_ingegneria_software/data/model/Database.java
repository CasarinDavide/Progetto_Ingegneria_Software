package com.example.progetto_ingegneria_software.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Map;

public class Database {

    private final FirebaseFirestore db;
    private final String collection;
    //Field used only for Log errors
    private final String TAG = "Database";
    //Field used to get the specified value in getField
    private Object value;

    public Database(String collection) {
        db = FirebaseFirestore.getInstance();
        this.collection = collection;
    }

    public CollectionReference getCollection() {
        return db.collection(collection);
    }

    public DocumentReference getDocument(String documentId) {
        return getCollection().document(documentId);
    }

    public Object getField(String documentId, String field) {
        getDocument(documentId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                value = documentSnapshot.get(field);
            }
        }).addOnFailureListener(exception -> Log.w(TAG, "Failed to fetch the field '" + field + "'.", exception) );

        return value;
    }

    public void addDocument(String documentId, Map<String, Object> info) {
        DocumentReference d = getDocument(documentId);

        d.set(info).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Created document '" + documentId + "'.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Failed to create document '" + documentId + "'. ", e);
            }
        });
    }

    public void updateField(String documentId, String field, Object data) {
        DocumentReference d = getDocument(documentId);

        d.update(field, data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Successful update");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error, maybe the field '" + field + "' does not exist", e);
            }
        });
    }

    public void deleteDocument(String documentId) {
        DocumentReference d = getDocument(documentId);

        d.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Document '" + documentId + "' deleted successfully");
            }
        }). addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error; Failed to delete document '" + documentId + "'.", e);
            }
        });
    }
}