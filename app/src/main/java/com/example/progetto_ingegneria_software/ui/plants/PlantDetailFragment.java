package com.example.progetto_ingegneria_software.ui.plants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.User;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.PlantsApi;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.example.progetto_ingegneria_software.databinding.FragmentPlantDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PlantDetailFragment extends Fragment {

    private FragmentPlantDetailBinding binding;
    private ImageView plantImage;
    private TextView commonNameText, scientificNameText, familyText, originText, typeText, dimensionText,
            cycleText, wateringText, sunlightText, pruningMonthsText, growthRateText, leafColorText, descriptionText;
    private FloatingActionButton add_to_cart;

    private Integer plant_id = 0;
    private Species plant;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlantDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle args = getArguments();
        if (args != null) {
            String idString = args.getString("id");
            if (idString != null) {
                try {
                    plant_id = Integer.parseInt(idString);
                } catch (NumberFormatException e) {
                    plant_id = -1; // Default value or handle the error
                }
            }

            // Retrieve the serialized object.
            Object serializedPlant = args.getSerializable("plant");
            if (serializedPlant != null && serializedPlant instanceof Species) {
                plant = (Species) serializedPlant;
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


                if (x.hasImage())
                {
                    getActivity().runOnUiThread(()-> {
                        Glide.with(plantImage.getContext())
                                .load(x.getThumbnail())
                                .into(plantImage);
                    });
                }

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

}
