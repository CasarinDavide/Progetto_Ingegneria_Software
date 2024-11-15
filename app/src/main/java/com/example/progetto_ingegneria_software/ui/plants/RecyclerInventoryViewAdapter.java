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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecyclerInventoryViewAdapter extends RecyclerView.Adapter<RecyclerInventoryViewAdapter.ViewHolder> {

    // init p per evitare problemi di nullpointer ref
    private List<Species> p;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView common_name;
        private final ImageView species_thumbnail;
        private Integer id;

        public ViewHolder(View view) {
            super(view);

            common_name = view.findViewById(R.id.species_common_name_textView);
            species_thumbnail = view.findViewById(R.id.species_thumbnail_imageView);
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

    public RecyclerInventoryViewAdapter(List<Species> p) {
        this.p = p;
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
    public RecyclerInventoryViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_card_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Replaces the data of the View with the given data of the List<Post> dataset
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerInventoryViewAdapter.ViewHolder holder, int position) {
        Species species = p.get(position);
        holder.common_name.setText(species.getCommonName());

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
