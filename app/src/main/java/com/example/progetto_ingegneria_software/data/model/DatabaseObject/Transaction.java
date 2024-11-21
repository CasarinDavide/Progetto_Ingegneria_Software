package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.Database;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;


public class Transaction {
    List<String> items;
    double latitude;
    double longitude;
    String uid;

    public static TransactionDB transactionDB;
    static {
        transactionDB = new TransactionDB();
    }

    public static class TransactionDB extends Database {
        public TransactionDB() {
            super("transactions");
        }

        @Override
        public DocumentReference getDocument(String documentId) {
            return super.getDocument(documentId);
        }

        /**
         * Gets current user's transactions
         * @param callback the callback interface
         */
        public void getUserTransactions(String uid, DatabaseCallback<List<Transaction>> callback) {
            getCollection().whereEqualTo("uid", uid)
                    .get()
                    .addOnCompleteListener( querySnapshotTask -> {
                        List<Transaction> l = querySnapshotTask.getResult().toObjects(Transaction.class);
                        callback.onComplete(l);
                    });

        }

        /**
         * Get all transaction, except the current user's ones
         * @param callback The callback interface
         */
        public void getTransactions(DatabaseCallback<List<Transaction>> callback) {
            getCollection()
                    .whereNotEqualTo("uid", Auth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener( querySnapshotTask -> {
                        List<Transaction> l = querySnapshotTask.getResult().toObjects(Transaction.class);
                        callback.onComplete(l);
                    });
        }

        public void addTransaction(Transaction t) {
            getCollection().add(t);
        }
    }

    public Transaction() {}

    public Transaction(String uid, double latitude, double longitude, List<String> items) {
        this.uid = uid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
