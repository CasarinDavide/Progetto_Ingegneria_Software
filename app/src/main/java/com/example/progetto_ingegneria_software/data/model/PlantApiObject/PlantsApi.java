package com.example.progetto_ingegneria_software.data.model.PlantApiObject;

import android.util.Pair;
import android.widget.GridLayout;

import com.example.progetto_ingegneria_software.data.model.PlantApiObject.PestDiseaseList.PestDisease;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.SpeciesCareGuide.SpeciesCareGuide;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species_Details.SpeciesDetails;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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



   
    static class ContainerDeserializerSpecies implements JsonDeserializer<PlantRequestContainer<Species>> {
        @Override
        public PlantRequestContainer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            PlantRequestContainer plantRequestContainer = new PlantRequestContainer<>();

            JsonArray data = jsonObject.getAsJsonArray("data");  // Correctly get the 'data' array
            JsonPrimitive to = jsonObject.getAsJsonPrimitive("to");
            JsonPrimitive per_page = jsonObject.getAsJsonPrimitive("per_page");
            JsonPrimitive current_page = jsonObject.getAsJsonPrimitive("current_page");
            JsonPrimitive from = jsonObject.getAsJsonPrimitive("from");
            JsonPrimitive last_page = jsonObject.getAsJsonPrimitive("last_page");
            JsonPrimitive total = jsonObject.getAsJsonPrimitive("total");

            List<Species> speciesList = new ArrayList<>();

            for (JsonElement a : data) {

                Species species = new Species();

                JsonObject jsonObject_a = a.getAsJsonObject();

                JsonPrimitive id = (JsonPrimitive) jsonObject_a.get("id");
                JsonPrimitive common_name = (JsonPrimitive) jsonObject_a.get("common_name");
                JsonElement scientific_name = jsonObject_a.get("scientific_name");
                JsonElement other_name = jsonObject_a.get("other_name");
                JsonPrimitive cycle = (JsonPrimitive) jsonObject_a.get("cycle");
                JsonPrimitive watering = (JsonPrimitive) jsonObject_a.get("watering");
                JsonElement sunlight = jsonObject_a.get("sunlight");
                JsonElement default_image = jsonObject_a.get("default_image");

                species.setId(context.deserialize(id, Long.class));
                species.setCommonName(context.deserialize(common_name, String.class));
                species.setCycle(context.deserialize(cycle, String.class));
                species.setWatering(context.deserialize(watering, String.class));
                species.setDefaultImage(context.deserialize(default_image, DefaultImage.class));
                species.setOtherName(context.deserialize(other_name, List.class));
                species.setScientificName(context.deserialize(scientific_name, List.class));


                if (sunlight.isJsonArray()) {
                    species.setSunlight(context.deserialize(sunlight, List.class));
                } else {
                    List<String> ls = new ArrayList<>();
                    ls.add("none");
                    species.setSunlight(ls);
                }

                speciesList.add(species);
            }

            plantRequestContainer.setData(speciesList);

            // Deserialize primitive fields
            plantRequestContainer.setTotal(context.deserialize(total, Long.class));
            plantRequestContainer.setTo(context.deserialize(to, Long.class));
            plantRequestContainer.setCurrentPage(context.deserialize(current_page, Long.class));
            plantRequestContainer.setPerPage(context.deserialize(per_page, Long.class));
            plantRequestContainer.setFrom(context.deserialize(from, Long.class));
            plantRequestContainer.setLastPage(context.deserialize(last_page, Long.class));

            return plantRequestContainer;
        }
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
     * @param search_term
     * @param callback funzione callback del chiamante, l'idea è che io chiamo la funzione asincrona, quando avrà finito riporto il risultato che voglio
     *                 al chiamante ( nel nostro caso sarà sempre il chiamante )
     */
    public static void getAllSpeciesAsync(String edible, String poisonous, String cycle, String watering,
                                          String sunlight, String indoor, String hardiness, Integer page,String search_term,
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

        if (!search_term.isEmpty())
            params.add(new Pair<>("q", search_term));


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

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(new TypeToken<PlantRequestContainer<Species>>(){}.getType(), new ContainerDeserializerSpecies())
                        .create();

                speciesContainer = gson.fromJson(json,new TypeToken<PlantRequestContainer<Species>>(){}.getType());
            } catch (Exception e) {
                speciesContainer = new PlantRequestContainer<>();
            }
            callback.onResponse(speciesContainer);
        });
    }


    /**
     * Non ha una descrizione
     * @param page
     * @param callback funzione callback del chiamante, l'idea è che io chiamo la funzione asincrona, quando avrà finito riporto il risultato che voglio
     *                 al chiamante ( nel nostro caso sarà sempre il chiamante )
     */
    public static void getPlantDetailsById(Integer plant_id,Integer page,PlantsApiCallback<SpeciesDetails> callback) {

        // faccio append del pair <Nome parametro da passare,valore da passare>

        ArrayList<Pair<String, String>> params = new ArrayList<>();

        if (page > 0)
            params.add(new Pair<>("page", page.toString()));

        // chiamo funzione generica per fare una chiamata all'api
        // il terzo parametro è una funzione cunsumer che prende in input una stringa ( json in questo caso )
        // da questo json va a creare un oggetto JAVA corrispondente al valore ricevuto

        // TypeToken perchè quelli della libreria Gson non avevano previsto l'utilizzo dei generics quando qualcuno va a deserializzare la classe dal json
        // quindi bisogna fare cosi
        // se il json non è valido creo un oggetto vuoto per evitare almeno che l'utente riceve un nullPointerException
        // creato l'oggetto JAVA faccio la callback al chiamante passandogli l'oggetto che il chiamante andrà ad elaborare

        makeRequest("species/details/" + plant_id, params, json -> {
            SpeciesDetails speciesContainer;
            try {
                speciesContainer = new Gson().fromJson(json, SpeciesDetails.class);
            } catch (Exception e) {
                speciesContainer = new SpeciesDetails();
            }
            callback.onResponse(speciesContainer);
        });
    }



    /**
     * @param plant_id
     * @param page
     * @param callback funzione callback del chiamante, l'idea è che io chiamo la funzione asincrona, quando avrà finito riporto il risultato che voglio
     *                 al chiamante ( nel nostro caso sarà sempre il chiamante )
     */
    public static void getAllPlantDiseaseList(Integer plant_id,Integer page,PlantsApiCallback<PlantRequestContainer<PestDisease>> callback) {

        // faccio append del pair <Nome parametro da passare,valore da passare>

        ArrayList<Pair<String, String>> params = new ArrayList<>();

        if (page > 0)
            params.add(new Pair<>("id", plant_id.toString()));

        if (page > 0)
            params.add(new Pair<>("page", page.toString()));

        // chiamo funzione generica per fare una chiamata all'api
        // il terzo parametro è una funzione cunsumer che prende in input una stringa ( json in questo caso )
        // da questo json va a creare un oggetto JAVA corrispondente al valore ricevuto

        // TypeToken perchè quelli della libreria Gson non avevano previsto l'utilizzo dei generics quando qualcuno va a deserializzare la classe dal json
        // quindi bisogna fare cosi
        // se il json non è valido creo un oggetto vuoto per evitare almeno che l'utente riceve un nullPointerException
        // creato l'oggetto JAVA faccio la callback al chiamante passandogli l'oggetto che il chiamante andrà ad elaborare

        makeRequest("pest-disease-list" + plant_id, params, json -> {
            PlantRequestContainer<PestDisease> pestDiseasePlantRequestContainer = new PlantRequestContainer<>();
            try {
                pestDiseasePlantRequestContainer = new Gson().fromJson(json, new TypeToken<PlantRequestContainer<PestDisease>>(){}.getType());
            } catch (Exception e) {
                pestDiseasePlantRequestContainer = new PlantRequestContainer<>();
            }
            callback.onResponse(pestDiseasePlantRequestContainer);
        });
    }

    /**
     * @param species_id
     * @param page
     * @param callback funzione callback del chiamante, l'idea è che io chiamo la funzione asincrona, quando avrà finito riporto il risultato che voglio
     *                 al chiamante ( nel nostro caso sarà sempre il chiamante )
     */
    public static void getAllPlantGuides(Integer species_id,Integer page,PlantsApiCallback<PlantRequestContainer<SpeciesCareGuide>> callback) {

        // faccio append del pair <Nome parametro da passare,valore da passare>

        ArrayList<Pair<String, String>> params = new ArrayList<>();

        if (page > 0)
            params.add(new Pair<>("species_id", species_id.toString()));

        if (page > 0)
            params.add(new Pair<>("page", page.toString()));

        // chiamo funzione generica per fare una chiamata all'api
        // il terzo parametro è una funzione cunsumer che prende in input una stringa ( json in questo caso )
        // da questo json va a creare un oggetto JAVA corrispondente al valore ricevuto

        // TypeToken perchè quelli della libreria Gson non avevano previsto l'utilizzo dei generics quando qualcuno va a deserializzare la classe dal json
        // quindi bisogna fare cosi
        // se il json non è valido creo un oggetto vuoto per evitare almeno che l'utente riceve un nullPointerException
        // creato l'oggetto JAVA faccio la callback al chiamante passandogli l'oggetto che il chiamante andrà ad elaborare

        makeRequest("species-care-guide-list", params, json -> {
            PlantRequestContainer<SpeciesCareGuide> speciesCareGuidePlantRequestContainer;
            try {
                speciesCareGuidePlantRequestContainer = new Gson().fromJson(json, new TypeToken<PlantRequestContainer<SpeciesCareGuide>>(){}.getType());
            } catch (Exception e) {
                speciesCareGuidePlantRequestContainer = new PlantRequestContainer<>();
            }
            callback.onResponse(speciesCareGuidePlantRequestContainer);
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
