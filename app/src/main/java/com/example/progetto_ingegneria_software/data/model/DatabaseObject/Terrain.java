package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.Database;
import com.example.progetto_ingegneria_software.data.model.Mapper;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Terrain extends Mapper {

    protected String title;
    protected String description;

    // Matrix representation of the terrain
    protected List<Integer> adj_matrix;
    protected String uid;

    public static TerrainDB terrainDB;
    public enum CELL_STATES {
        EMPTY_CELL(-1),
        NOT_EMPTY(0);

        public final Integer id;

        private CELL_STATES(Integer id) {
            this.id = id;
        }

    }


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

    public void setUid(String uid)
    {
        this.uid = uid;
    }


    public String getUid()
    {
        return this.uid;
    }



    public static class TerrainDB extends Database {
        /**
         * Creates an Object to interact with the specified collection
         */
        public TerrainDB() {
            super("Terrains");
        }

        @Override
        public <T extends Mapper> void addRecord(T obj) {

            if (obj instanceof Terrain)
            {
                Terrain t = (Terrain)obj;
                t.setUid(Auth.getCurrentUser().getUid());
            }

            super.addRecord(obj);
        }

        public void getAllFilteredByUser(DatabaseCallback<List<Terrain>> listDatabaseCallback)
        {
            Map<String,Object> hashMap = new HashMap<>();
            hashMap.put("uid", Auth.getCurrentUser().getUid());

            this.getAll(x->{

                List<Terrain> list = new ArrayList<>();
                for (QueryDocumentSnapshot document : x) {
                    try {
                        Terrain item = document.toObject(Terrain.class);
                        list.add(item);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }

                    listDatabaseCallback.onComplete(list);
                }
            },hashMap);
        }
    }

}
