package com.example.progetto_ingegneria_software.ui.plants;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species.Species;
import com.google.firebase.database.core.persistence.PruneForest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecyclerSpeciesViewAdapter extends RecyclerView.Adapter<RecyclerSpeciesViewAdapter.ViewHolder> {

    // init p per evitare problemi di nullpointer ref
    private List<Species> p;
    private OnClickListener onClickListener;


    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView common_name;
        private final TextView species_cycle;
        private final TextView species_sunlight;
        private final ImageView species_thumbnail;
        private final TextView species_watering;
        private final TextView species_scientific_name;
        private Integer id;

        public ViewHolder(View view) {
            super(view);

            common_name = view.findViewById(R.id.species_common_name_textView);
            species_cycle = view.findViewById(R.id.species_cycle_textView);
            species_sunlight = view.findViewById(R.id.species_sunlight_textView);
            species_thumbnail = view.findViewById(R.id.species_thumbnail_imageView);
            species_watering = view.findViewById(R.id.species_watering_textView);
            species_scientific_name = view.findViewById(R.id.species_scientific_name_textView);


        }

        public void setId(Integer id)
        {
            this.id = id;
        }

        public Integer getId(Integer id)
        {
            return this.id;
        }

    }

    public RecyclerSpeciesViewAdapter(List<Species> p) {
        this.p = p;
    }

    public RecyclerSpeciesViewAdapter(List<Species> p,OnClickListener listener) {
        this(p);
        this.onClickListener = listener;
    }


    public void setTerrainList(List<Species> p)
    {
        this.p = p;
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return The newly created View
     */
    @NonNull
    @Override
    public RecyclerSpeciesViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.species_card_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Replaces the data of the View with the given data of the List<Post> dataset
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerSpeciesViewAdapter.ViewHolder holder, int position) {
        Species species = p.get(position);
        holder.species_cycle.setText(species.getCycle());
        holder.common_name.setText(species.getCommonName());
        if (!species.getSunlight().isEmpty())
        {
            holder.species_sunlight.setText(species.getSunlight().get(0));
        }

        // spero funzioni
        new Thread(() -> {
            try {
                getBitmapFromURL(species.getDefaultImage().getThumbnail(), bitmap -> {
                    // Set the image on the main thread
                    // la funzione post permette di far caricare l'immagine al main thread ( almeno da come ce scritto )
                    if (holder.species_thumbnail != null && holder.species_thumbnail.getContext() != null) {
                        holder.species_thumbnail.post(() -> holder.species_thumbnail.setImageBitmap(bitmap));
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        holder.species_watering.setText(species.getWatering());

        if (!species.getScientificName().isEmpty())
        {
            holder.species_scientific_name.setText(species.getScientificName().get(0));
        }

        // Set click listener on the item view
        holder.itemView.setOnClickListener(view -> {
            if (onClickListener != null) {
                onClickListener.onClick(position, species);
            }
        });
    }


    // Setter for the click listener
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    // Interface for the click listener
    public interface OnClickListener {
        void onClick(int position, Species model);
    }


    // DA NON TOCCARE
    // classe per gestire le callback
    public interface BitmapDownloadCallBack{
        void onResponse(Bitmap bitmap);
    }

    public static void getBitmapFromURL(String src,BitmapDownloadCallBack callBack) throws IOException {
        URL newurl = null;
        try {
            newurl = new URL(src);
            Bitmap bitmap =  BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
            callBack.onResponse(bitmap);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return p.size();
    }

    public void addItem(Species species)
    {
        this.p.add(species);
        this.notifyItemInserted(p.size()-1);
        this.notifyDataSetChanged();
    }

}
