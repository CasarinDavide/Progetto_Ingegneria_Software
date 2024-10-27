
package com.example.progetto_ingegneria_software.data.model.PlantApiObject.PestDiseaseList;

import java.util.List;

import com.example.progetto_ingegneria_software.data.model.PlantApiObject.DefaultImage;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class PestDisease {

    public static class Solution {

        @SerializedName("description")
        private String mDescription;
        @SerializedName("subtitle")
        private String mSubtitle;

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getSubtitle() {
            return mSubtitle;
        }

        public void setSubtitle(String subtitle) {
            mSubtitle = subtitle;
        }

    }

    public static class Description {

        @SerializedName("description")
        private String mDescription;
        @SerializedName("subtitle")
        private String mSubtitle;

        public String getDescription() {
            return mDescription;
        }

        public void setDescription(String description) {
            mDescription = description;
        }

        public String getSubtitle() {
            return mSubtitle;
        }

        public void setSubtitle(String subtitle) {
            mSubtitle = subtitle;
        }

    }


    @SerializedName("common_name")
    private String mCommonName;
    @SerializedName("description")
    private List<Description> mDescription;
    @SerializedName("family")
    private Object mFamily;
    @SerializedName("host")
    private List<String> mHost;
    @SerializedName("id")
    private Long mId;
    @SerializedName("images")
    private List<DefaultImage> mImages;
    @SerializedName("other_name")
    private Object mOtherName;
    @SerializedName("scientific_name")
    private String mScientificName;
    @SerializedName("solution")
    private List<Solution> mSolution;

    public String getCommonName() {
        return mCommonName;
    }

    public void setCommonName(String commonName) {
        mCommonName = commonName;
    }

    public List<Description> getDescription() {
        return mDescription;
    }

    public void setDescription(List<Description> description) {
        mDescription = description;
    }

    public Object getFamily() {
        return mFamily;
    }

    public void setFamily(Object family) {
        mFamily = family;
    }

    public List<String> getHost() {
        return mHost;
    }

    public void setHost(List<String> host) {
        mHost = host;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public List<DefaultImage> getImages() {
        return mImages;
    }

    public void setImages(List<DefaultImage> images) {
        mImages = images;
    }

    public Object getOtherName() {
        return mOtherName;
    }

    public void setOtherName(Object otherName) {
        mOtherName = otherName;
    }

    public String getScientificName() {
        return mScientificName;
    }

    public void setScientificName(String scientificName) {
        mScientificName = scientificName;
    }

    public List<Solution> getSolution() {
        return mSolution;
    }

    public void setSolution(List<Solution> solution) {
        mSolution = solution;
    }

}
