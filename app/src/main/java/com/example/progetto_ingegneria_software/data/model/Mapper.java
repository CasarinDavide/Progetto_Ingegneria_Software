package com.example.progetto_ingegneria_software.data.model;

import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;

// classe che deve essere ereditata da tutti gli oggetti che sono Mapper di Istanze a DB
// 12/11/2024
// cambiato da classe astratta a non astratta perchè sennò il getAll nella classe Database mi da problemi
public class Mapper {

    @SerializedName("document_id")
    protected String document_id;

    // da tenere sotto controllo perchè ho paura con questa funzione potrebbe rompere davvero tutto
    // ritorna una mappa contentente <Nome del campo a Db,valore dell istanza>

    public Map<String,Object> getDictionary(boolean inherited_fields){

        Map<String,Object> objectMap = new HashMap<>();
        Class<? extends Object> c = this.getClass();
        Field[] fields = c.getDeclaredFields();

        Class<?> superClass = c.getSuperclass();
        if (superClass != null && inherited_fields)
        {
            Field[] superclass_fields = superClass.getDeclaredFields();
            generateMapFromFields(superclass_fields, objectMap);
        
        }

        generateMapFromFields(fields, objectMap);

        return objectMap;
    }

    private void generateMapFromFields(Field[] fields, Map<String, Object> objectMap) {
        for (Field field : fields) {
            String name = field.getName();

            // Prima: non voglio passare il document id

            // aggiornamento del 15/11
            // voglio prendere anche il document id sennò se sto visualizzando una lista
            // non saprei quale record prendere
            // usiamo il document id come identificativo
            if (/*!name.equals("document_id") && */ !name.contains("DB"))
            {
                Boolean old_access = field.isAccessible();
                field.setAccessible(true);
                try {
                    Object value = field.get(this);
                    objectMap.put(name,value);
                }
                catch (Exception e)
                {
                    // faccio niente
                }
                finally {
                    field.setAccessible(old_access);
                }

            }
        }
    }

    // Getter and setter for document ID
    public String getDocumentId() {
        return this.document_id;
    }

    public void setDocumentId(String documentId) {
        this.document_id = documentId;
    }
    

}
