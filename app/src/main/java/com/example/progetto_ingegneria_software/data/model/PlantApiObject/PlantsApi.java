package com.example.progetto_ingegneria_software.data.model.PlantApiObject;

import android.util.Pair;

import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class PlantsApi {

    // costanti classiche
    protected static final String API_KEY = "sk-drEw671921c5bd51e7198";
    protected static final String baseUrl = "https://perenual.com/api/";
    protected static final OkHttpClient client = new OkHttpClient();

    // DA NON TOCCARE
    // classe per gestire le callback
    public interface PlantsApiCallback<T> {
        void onResponse(T container);
    }


    /**
     *  i primi parametri sono forniti dall'eccelente documentazione che ha messo a disposizione perenual
     *  non so il dominio dei valori, se qualcuno lo scopre lo aggiunga...
     * @param edible
     * @param poisonous
     * @param cycle
     * @param watering
     * @param sunlight
     * @param indoor
     * @param hardiness
     * @param page
     * @param callback funzione callback del chiamante, l'idea è che io chiamo la funzione asincrona, quando avrà finito riporto il risultato che voglio
     *                 al chiamante ( nel nostro caso sarà sempre il chiamante )
     */
    public static void getAllSpeciesAsync(String edible, String poisonous, String cycle, String watering,
                                          String sunlight, String indoor, String hardiness, Integer page,
                                          PlantsApiCallback<PlantRequestContainer<Species>> callback) {

        // faccio append del pair <Nome parametro da passare,valore da passare>

        ArrayList<Pair<String, String>> params = new ArrayList<>();
        if (!edible.isEmpty())
            params.add(new Pair<>("edible", edible));

        if (!poisonous.isEmpty())
            params.add(new Pair<>("poisonous", poisonous));

        if (!cycle.isEmpty())
            params.add(new Pair<>("cycle", cycle));

        if (!watering.isEmpty())
            params.add(new Pair<>("watering", watering));

        if (!sunlight.isEmpty())
            params.add(new Pair<>("sunlight", sunlight));

        if (!indoor.isEmpty())
            params.add(new Pair<>("indoor", indoor));

        if (!hardiness.isEmpty())
            params.add(new Pair<>("hardiness", hardiness));

        if (page > 0)
            params.add(new Pair<>("page", page.toString()));

        // chiamo funzione generica per fare una chiamata all'api
        // il terzo parametro è una funzione cunsumer che prende in input una stringa ( json in questo caso )
        // da questo json va a creare un oggetto JAVA corrispondente al valore ricevuto

        // TypeToken perchè quelli della libreria Gson non avevano previsto l'utilizzo dei generics quando qualcuno va a deserializzare la classe dal json
        // quindi bisogna fare cosi
        // se il json non è valido creo un oggetto vuoto per evitare almeno che l'utente riceve un nullPointerException
        // creato l'oggetto JAVA faccio la callback al chiamante passandogli l'oggetto che il chiamante andrà ad elaborare

        makeRequest("species-list", params, json -> {
            PlantRequestContainer<Species> speciesContainer;
            try {
                speciesContainer = new Gson().fromJson(json, new TypeToken<PlantRequestContainer<Species>>(){}.getType());
            } catch (Exception e) {
                speciesContainer = new PlantRequestContainer<>();
            }
            callback.onResponse(speciesContainer);
        });
    }

    /**
     *
     * @param url url da cercare
     * @param params parametri opzionali
     * @param onSuccessAction funzione che verrà eseguita se e solo se la richiesta è un successo
     */
    protected static void makeRequest(String url, ArrayList<Pair<String, String>> params, Consumer<String> onSuccessAction) {

        // genero l'url e faccio chiamata GET ( per questo faccio l'append dei parametri sull'url )
        StringBuilder paramsString = new StringBuilder("?key=" + API_KEY);
        for (Pair<String, String> param : params) {
            paramsString.append("&").append(param.first).append("=").append(param.second);
        }
        String finalUrl = baseUrl + url + paramsString;

        // cose di libreria...
        Request request = new Request.Builder().url(finalUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    // se ho la risposta
                    // prendo il json che ho ricevuto
                    // e lo passo alla funzione cunsumer che farà qualcosa con questo json
                    onSuccessAction.accept(response.body().string());
                }
            }
        });
    }
}
