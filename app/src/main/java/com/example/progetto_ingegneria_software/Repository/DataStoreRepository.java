package com.example.progetto_ingegneria_software.Repository;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


public class DataStoreRepository {

    private static DataStoreRepository INSTANCE = null;
    private final RxDataStore<Preferences> dataStore;

    // Keys for the constants
    private static final Preferences.Key<String> PASSWORD_KEY = PreferencesKeys.stringKey("password");
    private static final Preferences.Key<String> UTENTE_KEY = PreferencesKeys.stringKey("username");

    private DataStoreRepository(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, "settings").build();
    }

    // Synchronized singleton instance
    public static synchronized DataStoreRepository getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DataStoreRepository(context);
        }
        return INSTANCE;
    }

    // Store ip_server value
    public void setUsernameKey(String username) {
        dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(UTENTE_KEY, username);
            return Single.just(mutablePreferences);
        }).subscribe();
    }


    // Retrieve ip_server value from DataStore, or use the constant if already set
    public String getUsernameKey() {
        Single<String> value = dataStore.data().firstOrError().map(prefs -> prefs.get(UTENTE_KEY)).onErrorReturnItem("null");
        return value.blockingGet();
    }

    // Store ip_server value
    public void setPasswordKey(String passwordKey) {
        dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PASSWORD_KEY, passwordKey);
            return Single.just(mutablePreferences);
        }).subscribe();
    }


    // Retrieve ip_server value from DataStore, or use the constant if already set
    public String getPasswordKey() {
        Single<String> value = dataStore.data().firstOrError().map(prefs -> prefs.get(PASSWORD_KEY)).onErrorReturnItem("null");
        return value.blockingGet();
    }


}