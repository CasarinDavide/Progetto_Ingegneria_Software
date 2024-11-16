package com.example.progetto_ingegneria_software.ui.plants;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.example.progetto_ingegneria_software.R;
import com.example.progetto_ingegneria_software.data.model.DatabaseObject.Terrain;
import com.example.progetto_ingegneria_software.databinding.ActivityAddTerrainBinding;

import java.util.List;

public class AddTerrainActivity extends AppCompatActivity {

    private ActivityAddTerrainBinding binding;

    private Terrain terrain;

    // TODO CAMBIARE CON PARAMETRI IN QUALCHE MODO NON DEVONO ESSERE COSTANTI
    private final Integer ROWS = 10;
    private final Integer COLS = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddTerrainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        terrain = new Terrain(COLS,ROWS);

        Button create_btn = findViewById(R.id.activity_add_terrain_create_btn);
        GridLayout terrainPerimeter = findViewById(R.id.activity_add_terrain_terrainCanvas_grid_layout);
        EditText title_textView = (EditText)findViewById(R.id.activity_add_terrain_name_editText);
        EditText description_editText = (EditText) findViewById(R.id.activity_add_terrain_description_editText);

        terrainPerimeter.setColumnCount(COLS);
        terrainPerimeter.setRowCount(ROWS);


        for (int row = 0; row < terrainPerimeter.getRowCount(); row++) {
            for (int col = 0; col < terrainPerimeter.getColumnCount(); col++) {
                // Create a new ImageView
                PositionalImageView imageView = new PositionalImageView(getApplicationContext(),row,col);
                // Set the image resource (make sure ic_advice exists in drawable)
                imageView.setImageResource(R.drawable.ic_terrain);

                imageView.setOnClickListener(x->{

                    List<Integer> matrix = terrain.getTerrain();


                    int pos = imageView.getColumn() + imageView.getRow() * COLS;
                    Integer state = matrix.get(pos);


                    if (state.equals(Terrain.CELL_STATES.EMPTY_CELL.id))
                    {
                        imageView.setImageResource(R.drawable.ic_grass_terrain);
                        matrix.set(pos, Terrain.CELL_STATES.NOT_EMPTY.id);
                    }
                    else
                    {
                        imageView.setImageResource(R.drawable.ic_terrain);
                        matrix.set(pos, Terrain.CELL_STATES.EMPTY_CELL.id);
                    }
                });
                // Optionally set layout parameters for spacing and size
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = GridLayout.LayoutParams.WRAP_CONTENT;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.columnSpec = GridLayout.spec(col, 1); // span 1 column
                params.rowSpec = GridLayout.spec(row, 1);     // span 1 row
                params.setMargins(4, 4, 4, 4); // Add margins

                imageView.setLayoutParams(params);
                imageView.setAdjustViewBounds(true); // Maintain aspect ratio

                // Add the ImageView to the GridLayout
                terrainPerimeter.addView(imageView, params);
            }
        }

        terrainPerimeter.requestLayout();

        create_btn.setOnClickListener(x->
        {
            terrain.setTitle(title_textView.getText().toString());
            terrain.setDescription(description_editText.getText().toString());
            Terrain.terrainDB.addRecord(terrain);
            Toast toast = Toast.makeText(this,"Inserimento avvenuto con successo",Toast.LENGTH_LONG);
            toast.show();
            this.finish();
        });

    }

    public static class PositionalImageView extends AppCompatImageView {

        private int row;      // Row position
        private int column;   // Column position

        public PositionalImageView(Context context) {
            super(context);
        }

        // Constructor with row and column positions
        public PositionalImageView(Context context, int row, int column) {
            super(context);
            this.row = row;
            this.column = column;
        }

        // Getter and Setter for row
        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        // Getter and Setter for column
        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }
    }



}