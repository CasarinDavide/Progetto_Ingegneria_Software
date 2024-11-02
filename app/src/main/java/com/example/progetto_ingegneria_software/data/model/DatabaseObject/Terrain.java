package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import com.example.progetto_ingegneria_software.data.model.Database;
import com.example.progetto_ingegneria_software.data.model.Mapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Terrain extends Mapper {


    public static TerrainDB terrainDB;
    public enum CELL_STATES {
        EMPTY_CELL(-1),
        NOT_EMPTY(0);

        public final Integer id;

        private CELL_STATES(Integer id) {
            this.id = id;
        }

    }
    public static class TerrainDB extends Database {
        /**
         * Creates an Object to interact with the specified collection
         */
        public TerrainDB() {
            super("Terrains");
        }
    }

    protected String title;
    protected String description;

    // Matrix representation of the terrain
    protected List<Integer> adj_matrix;

    public Terrain() {
        this.adj_matrix = new ArrayList<>();
    }

    public Terrain(Integer columns,Integer rows)
    {
        int tot = rows * columns;
        List<Integer> adj_matrix = new ArrayList<>();
        for (int i = 0; i < tot; i++) {
            adj_matrix.add(i,CELL_STATES.EMPTY_CELL.id);
        }

        this.adj_matrix = adj_matrix;
    }

    static {
        terrainDB = new TerrainDB();
    }

    public Terrain(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Terrain(String title, String description, List<Integer> adj_matrix) {
        this(title, description);
        this.adj_matrix = adj_matrix;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdj_matrix(List<Integer> matrix) {
        this.adj_matrix = matrix;
    }

    public List<Integer> getAdjMatrix() {
        return this.adj_matrix;
    }



}
