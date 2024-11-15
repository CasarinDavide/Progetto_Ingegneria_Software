package com.example.progetto_ingegneria_software.ui.plants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.PlantRequestContainer;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.PlantsApi;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.example.progetto_ingegneria_software.databinding.FragmentPlantDetailBinding;
import com.example.progetto_ingegneria_software.databinding.FragmentSpeciesBinding;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlantDetailFragment extends Fragment {

    private FragmentPlantDetailBinding binding;
    private ImageView plantImage;
    private TextView commonNameText, scientificNameText, familyText, originText, typeText, dimensionText,
            cycleText, wateringText, sunlightText, pruningMonthsText, growthRateText, leafColorText, descriptionText;
    private Button add_to_cart;

    private Integer plant_id = 0;
    private Species plant;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlantDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle args = getArguments();
        if (args != null) {
            plant_id = Integer.parseInt(args.getString("id"));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                plant = args.getSerializable("plant",Species.class);
            }
        }

        plantImage = root.findViewById(R.id.plant_image);
        commonNameText = root.findViewById(R.id.common_name_text);
        scientificNameText = root.findViewById(R.id.scientific_name_text);
        familyText = root.findViewById(R.id.family_text);
        originText = root.findViewById(R.id.origin_text);
        typeText = root.findViewById(R.id.type_text);
        dimensionText = root.findViewById(R.id.dimension_text);
        cycleText = root.findViewById(R.id.cycle_text);
        wateringText = root.findViewById(R.id.watering_text);
        sunlightText = root.findViewById(R.id.sunlight_text);
        pruningMonthsText = root.findViewById(R.id.pruning_months_text);
        growthRateText = root.findViewById(R.id.growth_rate_text);
        leafColorText = root.findViewById(R.id.leaf_color_text);
        descriptionText = root.findViewById(R.id.description_text);
        add_to_cart = root.findViewById(R.id.add_to_cart_button);

        if (plant_id > 0)
        {
            PlantsApi.getPlantDetailsById(plant_id,0,x->{

                // devo farlo runnare su un thread apparte che sennò scoppia
                Thread thread = new Thread(()->
                {
                    try {

                        String def_image = "";
                        if (x.getDefaultImage()!= null)
                        {
                            def_image = x.getDefaultImage().getOriginalUrl();
                        } else if (plant != null && plant.getDefaultImage() != null)  {
                            def_image = plant.getDefaultImage().getOriginalUrl();
                        }

                        if (!def_image.isEmpty())
                        {
                            getBitmapFromURL(def_image,bitmap->{
                                getActivity().runOnUiThread(()->{
                                    plantImage.setImageBitmap(bitmap);
                                });
                            });
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                thread.start();

                getActivity().runOnUiThread(()->{

                    add_to_cart.setOnClickListener(view->{
                        User.userDB.pushToInventory(plant,()->{
                            getActivity().runOnUiThread(()->{
                                Toast toast = Toast.makeText(getContext(),"Inserimento avvenuto con successo",Toast.LENGTH_LONG);
                                toast.show();
                            });
                        });
                    });

                    commonNameText.setText(x.getCommonName());
                    scientificNameText.setText(!x.getScientificName().isEmpty() ?x.getScientificName().get(0):"");
                    //familyText.setText(x.getFamily());
                    originText.setText(!x.getOrigin().isEmpty()?x.getOrigin().get(0):"");
                    typeText.setText(x.getType());
                    dimensionText.setText(x.getDimension());
                    cycleText.setText(x.getCycle());
                    wateringText.setText(x.getWatering());
                    sunlightText.setText(!x.getSunlight().isEmpty()?x.getSunlight().get(0):"");
                    pruningMonthsText.setText(!x.getPruningMonth().isEmpty()?x.getPruningMonth().get(0):"");
                    growthRateText.setText(x.getGrowthRate());
                    leafColorText.setText(!x.getLeafColor().isEmpty()?x.getLeafColor().get(0):"");
                    descriptionText.setText(x.getDescription());
                });
            });
        }


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface BitmapDownloadCallBack{
        void onResponse(Bitmap bitmap);
    }

    public static void getBitmapFromURL(String src, BitmapDownloadCallBack callBack) throws IOException {
        URL newurl = null;
        try {
            newurl = new URL(src);
            Bitmap bitmap =  BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
            callBack.onResponse(bitmap);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}
