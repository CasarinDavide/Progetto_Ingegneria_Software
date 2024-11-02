package com.example.progetto_ingegneria_software.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Post;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Terrain;

import java.util.List;

public class RecyclerTerrainsViewAdapter extends RecyclerView.Adapter<RecyclerTerrainsViewAdapter.ViewHolder> {

    private List<Terrain> p;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView title;
        private final TextView description;
        private Integer id;

        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.cardTitle);
            description = view.findViewById(R.id.cardDescription);
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

    public RecyclerTerrainsViewAdapter(List<Terrain> p) {
        this.p = p;
    }


    public void setTerrainList(List<Terrain> p)
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
    public RecyclerTerrainsViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Replaces the data of the View with the given data of the List<Post> dataset
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerTerrainsViewAdapter.ViewHolder holder, int position) {
        Terrain terrain = p.get(position);
        holder.title.setText(terrain.getTitle());
        holder.description.setText(terrain.getDescription());
    }


    @Override
    public int getItemCount() {
        return p.size();
    }

    public void addItem(Terrain terrain)
    {
        this.p.add(terrain);
        this.notifyItemInserted(p.size()-1);
        this.notifyDataSetChanged();
    }

}
