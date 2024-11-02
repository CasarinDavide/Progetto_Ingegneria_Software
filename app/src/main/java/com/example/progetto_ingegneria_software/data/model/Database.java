package com.example.progetto_ingegneria_software.data.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Database {

    public interface DatabaseCallback<T> {
        void onComplete(T value);
    }

    private final FirebaseFirestore db;
    private final String collection;
    //Field used only for Log errors
    private final String TAG = "Database";

    /**
     * Creates an Object to interact with the specified collection
     * @param collection Collection to interact with
     */
    public Database(String collection) {
        db = FirebaseFirestore.getInstance();
        this.collection = collection;
    }

    /**
     * @return Reference to the collection to interact with
     */
    public CollectionReference getCollection() {
        return db.collection(collection);
    }

    /**
     *
     * @param documentId Id of the document
     * @return Reference to the specified document
     */
    public DocumentReference getDocument(String documentId) {
        return getCollection().document(documentId);
    }

    /**
     * Adds a document to the collection with the specified Id and info
     * @param documentId Id of the document to add in the collection
     * @param data Data to store inside the field
     */
    public void addDocument(String documentId, Object data) {
        DocumentReference d = getDocument(documentId);

        d.set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    /**
     * Updates the specified field with the given data
     * @param documentId Id of the document to update
     * @param field Name of the field to update
     * @param data Data to overwrite the field with
     */

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

    /**
     * Deletes a document given its Id
     * @param documentId Id of the document to delete
     */
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
     * Runs a query against the collection, checks the value of the field is unique
     * @param field Name of the field to check
     * @param value Value of the field to check
     * @param callback Where to handle the value once the query returned a result
     */
    public void isUnique(String field, String value, DatabaseCallback<Boolean> callback) {

        getCollection().whereEqualTo(field, value).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    callback.onComplete(task.getResult().isEmpty());
                } else {
                    callback.onComplete(false);
                }
            }
        });
    }
}