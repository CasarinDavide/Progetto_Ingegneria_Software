package com.example.progetto_ingegneria_software.data.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

// classe che deve essere ereditata da tutti gli oggetti che sono Mapper di Istanze a DB
public abstract class Mapper {

    protected String document_id;

    // da tenere sotto controllo perchè ho paura con questa funzione potrebbe rompere davvero tutto
    // ritorna una mappa contentente <Nome del campo a Db,valore dell istanza>

    public Map<String,Object> getDictionary(){

        Map<String,Object> objectMap = new HashMap<>();
        Class<? extends Object> c = this.getClass();
        Field[] fields = c.getDeclaredFields();

        for (Field field : fields) {
            String name = field.getName();

            // non voglio passare il document id
            if (!name.equals("document_id") && !name.contains("DB"))
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

        return objectMap;
    }

    // Getter and setter for document ID
    public String getDocumentId() {
        return this.document_id;
    }

    public void setDocumentId(String documentId) {
        this.document_id = documentId;
    }

}
