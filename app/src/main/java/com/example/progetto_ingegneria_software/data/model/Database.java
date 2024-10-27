package com.example.progetto_ingegneria_software.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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
        }).addOnFailureListener(exception -> Log.w(TAG, "Failed to fetch the field '" + field + "'.", exception));

        return value;
    }

    public void addDocument(String documentId, Object info) {
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error; Failed to delete document '" + documentId + "'.", e);
            }
        });
    }


    /**
     * Runs a query against the specified collection, checks the value of the field is unique
     *
     * @param collection Collection to search in
     * @param field Name of the field to check
     * @param value Value of the field to check
     * @return true if the query output is empty, false otherwise
     */
    public boolean isUnique(String collection, String field, String value) {
        AtomicBoolean r = new AtomicBoolean(false);

        getCollection().whereEqualTo(field, value).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                r.set(task.getResult().isEmpty());
            }
        });

        return r.get();
    }
}