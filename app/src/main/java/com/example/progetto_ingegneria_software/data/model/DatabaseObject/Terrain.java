package com.example.progetto_ingegneria_software.data.model.DatabaseObject;

import com.example.progetto_ingegneria_software.data.model.Auth;
import com.example.progetto_ingegneria_software.data.model.Database;
import com.example.progetto_ingegneria_software.data.model.Mapper;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Terrain extends Mapper {

    protected String title;
    protected String description;

    // Matrix representation of the terrain
    protected List<Integer> terrain;
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
        terrain = new ArrayList<>();
    }

    public Terrain(Integer columns,Integer rows)
    {
        int tot = rows * columns;
        List<Integer> adj_matrix = new ArrayList<>();
        for (int i = 0; i < tot; i++) {
            adj_matrix.add(i,CELL_STATES.EMPTY_CELL.id);
        }

        this.terrain = adj_matrix;
    }


    public Terrain(String title, String description) {
        this.title = title;
        this.description = description;
        terrain = new ArrayList<>();
    }

    public Terrain(String title, String description, List<Integer> terrain) {
        this(title, description);
        this.terrain = terrain;
    }


    static {
        terrainDB = new TerrainDB();
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

    public void setTerrain(List<Integer> adj_matrix) {
        this.terrain = adj_matrix;
    }

    public List<Integer> getTerrain() {
        return this.terrain;
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
                        // aggiungo a mano perchè non riesco a farlo funzionare
                        String document_id = document.getString("document_id");
                        if (document_id != null)
                            item.setDocumentId(document_id);

                        list.add(item);


                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }

                    listDatabaseCallback.onComplete(list);
                }
            },hashMap);
        }

        public void getByIdTerrain(String uid,DatabaseCallback<Terrain> callback)
        {
            this.getById(uid,Terrain.class,callback);
        }
    }


}
