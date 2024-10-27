
package com.example.progetto_ingegneria_software.data.model.PlantApiObject.SpeciesCareGuide;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SpeciesCareGuide {

    public static class Section {

        @SerializedName("description")
        private String mDescription;
        @SerializedName("id")
        private Long mId;
        @SerializedName("type")
        private String mType;

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            mType = type;
        }

    }

    @SerializedName("common_name")
    private String mCommonName;
    @SerializedName("id")
    private Long mId;
    @SerializedName("scientific_name")
    private List<String> mScientificName;
    @SerializedName("section")
    private List<Section> mSection;
    @SerializedName("species_id")
    private Long mSpeciesId;

    public String getCommonName() {
        return mCommonName;
    }

    public void setCommonName(String commonName) {
        mCommonName = commonName;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<String> getScientificName() {
        return mScientificName;
    }

    public void setScientificName(List<String> scientificName) {
        mScientificName = scientificName;
    }

    public List<Section> getSection() {
        return mSection;
    }

    public void setSection(List<Section> section) {
        mSection = section;
    }

    public Long getSpeciesId() {
        return mSpeciesId;
    }

    public void setSpeciesId(Long speciesId) {
        mSpeciesId = speciesId;
    }

}
