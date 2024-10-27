
package com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species;

import com.example.progetto_ingegneria_software.data.model.PlantApiObject.DefaultImage;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Species {

    @SerializedName("common_name")
    private String mCommonName;
    @SerializedName("cycle")
    private String mCycle;
    @SerializedName("default_image")
    private DefaultImage mDefaultImage;
    @SerializedName("id")
    private Long mId;
    @SerializedName("other_name")
    private List<String> mOtherName;
    @SerializedName("scientific_name")
    private List<String> mScientificName;
    @SerializedName("sunlight")
    private List<String> mSunlight;
    @SerializedName("watering")
    private String mWatering;

    public String getCommonName() {
        return mCommonName;
    }

    public void setCommonName(String commonName) {
        mCommonName = commonName;
    }

    public String getCycle() {
        return mCycle;
    }

    public void setCycle(String cycle) {
        mCycle = cycle;
    }

    public DefaultImage getDefaultImage() {
        return mDefaultImage;
    }

    public void setDefaultImage(DefaultImage defaultImage) {
        mDefaultImage = defaultImage;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<String> getOtherName() {
        return mOtherName;
    }

    public void setOtherName(List<String> otherName) {
        mOtherName = otherName;
    }

    public List<String> getScientificName() {
        return mScientificName;
    }

    public void setScientificName(List<String> scientificName) {
        mScientificName = scientificName;
    }

    public List<String> getSunlight() {
        return mSunlight;
    }

    public void setSunlight(List<String> sunlight) {
        mSunlight = sunlight;
    }

    public String getWatering() {
        return mWatering;
    }

    public void setWatering(String watering) {
        mWatering = watering;
    }

}
