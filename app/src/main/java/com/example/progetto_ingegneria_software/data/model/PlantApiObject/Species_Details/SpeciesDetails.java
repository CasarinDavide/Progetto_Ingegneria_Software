
package com.example.progetto_ingegneria_software.data.model.PlantApiObject.Species_Details;

import java.util.ArrayList;
import java.util.List;

import com.example.progetto_ingegneria_software.data.model.PlantApiObject.DefaultImage;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SpeciesDetails {

    public static class Dimensions {

        @SerializedName("max_value")
        private Long mMaxValue;
        @SerializedName("min_value")
        private Long mMinValue;
        @SerializedName("type")
        private String mType;
        @SerializedName("unit")
        private String mUnit;

        public Long getMaxValue() {
            return mMaxValue;
        }

        public void setMaxValue(Long maxValue) {
            mMaxValue = maxValue;
        }

        public Long getMinValue() {
            return mMinValue;
        }

        public void setMinValue(Long minValue) {
            mMinValue = minValue;
        }

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            mType = type;
        }

        public String getUnit() {
            return mUnit;
        }

        public void setUnit(String unit) {
            mUnit = unit;
        }

    }

    public static class HardinessLocation {

        @SerializedName("full_iframe")
        private String mFullIframe;
        @SerializedName("full_url")
        private String mFullUrl;

        public String getFullIframe() {
            return mFullIframe;
        }

        public void setFullIframe(String fullIframe) {
            mFullIframe = fullIframe;
        }

        public String getFullUrl() {
            return mFullUrl;
        }

        public void setFullUrl(String fullUrl) {
            mFullUrl = fullUrl;
        }

    }

    public static class WateringGeneralBenchmark {

        @SerializedName("unit")
        private String mUnit;
        @SerializedName("value")
        private String mValue;

        public String getUnit() {
            return mUnit;
        }

        public void setUnit(String unit) {
            mUnit = unit;
        }

        public String getValue() {
            return mValue;
        }

        public void setValue(String value) {
            mValue = value;
        }

    }


    public static class Hardiness {

        @SerializedName("max")
        private String mMax;
        @SerializedName("min")
        private String mMin;

        public String getMax() {
            return mMax;
        }

        public void setMax(String max) {
            mMax = max;
        }

        public String getMin() {
            return mMin;
        }

        public void setMin(String min) {
            mMin = min;
        }

    }

    @SerializedName("attracts")
    private List<Object> mAttracts;
    @SerializedName("care-guides")
    private String mCareGuides;
    @SerializedName("care_level")
    private String mCareLevel;
    @SerializedName("common_name")
    private String mCommonName;
    @SerializedName("cones")
    private Boolean mCones;
    @SerializedName("cuisine")
    private Boolean mCuisine;
    @SerializedName("cycle")
    private String mCycle;
    @SerializedName("default_image")
    private DefaultImage mDefaultImage;
    @SerializedName("depth_water_requirement")
    private List<Object> mDepthWaterRequirement;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("dimension")
    private String mDimension;
    @SerializedName("dimensions")
    private Dimensions mDimensions;
    @SerializedName("drought_tolerant")
    private Boolean mDroughtTolerant;
    @SerializedName("edible_fruit")
    private Boolean mEdibleFruit;
    @SerializedName("edible_fruit_taste_profile")
    private String mEdibleFruitTasteProfile;
    @SerializedName("edible_leaf")
    private Boolean mEdibleLeaf;
    @SerializedName("family")
    private Object mFamily;
    @SerializedName("flower_color")
    private String mFlowerColor;
    @SerializedName("flowering_season")
    private Object mFloweringSeason;
    @SerializedName("flowers")
    private Boolean mFlowers;
    @SerializedName("fruit_color")
    private List<Object> mFruitColor;
    @SerializedName("fruit_nutritional_value")
    private String mFruitNutritionalValue;
    @SerializedName("fruits")
    private Boolean mFruits;
    @SerializedName("growth_rate")
    private String mGrowthRate;
    @SerializedName("hardiness")
    private Hardiness mHardiness;
    @SerializedName("hardiness_location")
    private HardinessLocation mHardinessLocation;
    @SerializedName("harvest_season")
    private Object mHarvestSeason;
    @SerializedName("id")
    private Long mId;
    @SerializedName("indoor")
    private Boolean mIndoor;
    @SerializedName("invasive")
    private Boolean mInvasive;
    @SerializedName("leaf")
    private Boolean mLeaf;
    @SerializedName("leaf_color")
    private List<String> mLeafColor;
    @SerializedName("maintenance")
    private Object mMaintenance;
    @SerializedName("medicinal")
    private Boolean mMedicinal;
    @SerializedName("origin")
    private List<String> mOrigin;
    @SerializedName("other_images")
    private String mOtherImages;
    @SerializedName("other_name")
    private List<String> mOtherName;
    @SerializedName("pest_susceptibility")
    private List<Object> mPestSusceptibility;
    @SerializedName("pest_susceptibility_api")
    private String mPestSusceptibilityApi;
    @SerializedName("plant_anatomy")
    private List<Object> mPlantAnatomy;
    @SerializedName("poisonous_to_humans")
    private Long mPoisonousToHumans;
    @SerializedName("poisonous_to_pets")
    private Long mPoisonousToPets;
    @SerializedName("propagation")
    private List<String> mPropagation;
    @SerializedName("pruning_count")
    private List<Object> mPruningCount;
    @SerializedName("pruning_month")
    private List<String> mPruningMonth;
    @SerializedName("salt_tolerant")
    private Boolean mSaltTolerant;
    @SerializedName("scientific_name")
    private List<String> mScientificName;
    @SerializedName("seeds")
    private Long mSeeds;
    @SerializedName("soil")
    private List<Object> mSoil;
    @SerializedName("sunlight")
    private List<String> mSunlight;
    @SerializedName("thorny")
    private Boolean mThorny;
    @SerializedName("tropical")
    private Boolean mTropical;
    @SerializedName("type")
    private String mType;
    @SerializedName("volume_water_requirement")
    private List<Object> mVolumeWaterRequirement;
    @SerializedName("watering")
    private String mWatering;
    @SerializedName("watering_general_benchmark")
    private WateringGeneralBenchmark mWateringGeneralBenchmark;
    @SerializedName("watering_period")
    private Object mWateringPeriod;

    public List<Object> getAttracts() {
        return mAttracts;
    }

    public void setAttracts(List<Object> attracts) {
        mAttracts = attracts;
    }

    public String getCareGuides() {
        return mCareGuides;
    }

    public void setCareGuides(String careGuides) {
        mCareGuides = careGuides;
    }

    public String getCareLevel() {
        return mCareLevel;
    }

    public void setCareLevel(String careLevel) {
        mCareLevel = careLevel;
    }

    public String getCommonName() {
        return mCommonName;
    }

    public void setCommonName(String commonName) {
        mCommonName = commonName;
    }

    public Boolean getCones() {
        return mCones;
    }

    public void setCones(Boolean cones) {
        mCones = cones;
    }

    public Boolean getCuisine() {
        return mCuisine;
    }

    public void setCuisine(Boolean cuisine) {
        mCuisine = cuisine;
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

    public List<Object> getDepthWaterRequirement() {
        return mDepthWaterRequirement;
    }

    public void setDepthWaterRequirement(List<Object> depthWaterRequirement) {
        mDepthWaterRequirement = depthWaterRequirement;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDimension() {
        return mDimension;
    }

    public void setDimension(String dimension) {
        mDimension = dimension;
    }

    public Dimensions getDimensions() {
        return mDimensions;
    }

    public void setDimensions(Dimensions dimensions) {
        mDimensions = dimensions;
    }

    public Boolean getDroughtTolerant() {
        return mDroughtTolerant;
    }

    public void setDroughtTolerant(Boolean droughtTolerant) {
        mDroughtTolerant = droughtTolerant;
    }

    public Boolean getEdibleFruit() {
        return mEdibleFruit;
    }

    public void setEdibleFruit(Boolean edibleFruit) {
        mEdibleFruit = edibleFruit;
    }

    public String getEdibleFruitTasteProfile() {
        return mEdibleFruitTasteProfile;
    }

    public void setEdibleFruitTasteProfile(String edibleFruitTasteProfile) {
        mEdibleFruitTasteProfile = edibleFruitTasteProfile;
    }

    public Boolean getEdibleLeaf() {
        return mEdibleLeaf;
    }

    public void setEdibleLeaf(Boolean edibleLeaf) {
        mEdibleLeaf = edibleLeaf;
    }

    public Object getFamily() {
        return mFamily;
    }

    public void setFamily(Object family) {
        mFamily = family;
    }

    public String getFlowerColor() {
        return mFlowerColor;
    }

    public void setFlowerColor(String flowerColor) {
        mFlowerColor = flowerColor;
    }

    public Object getFloweringSeason() {
        return mFloweringSeason;
    }

    public void setFloweringSeason(Object floweringSeason) {
        mFloweringSeason = floweringSeason;
    }

    public Boolean getFlowers() {
        return mFlowers;
    }

    public void setFlowers(Boolean flowers) {
        mFlowers = flowers;
    }

    public List<Object> getFruitColor() {
        return mFruitColor;
    }

    public void setFruitColor(List<Object> fruitColor) {
        mFruitColor = fruitColor;
    }

    public String getFruitNutritionalValue() {
        return mFruitNutritionalValue;
    }

    public void setFruitNutritionalValue(String fruitNutritionalValue) {
        mFruitNutritionalValue = fruitNutritionalValue;
    }

    public Boolean getFruits() {
        return mFruits;
    }

    public void setFruits(Boolean fruits) {
        mFruits = fruits;
    }

    public String getGrowthRate() {
        return mGrowthRate;
    }

    public void setGrowthRate(String growthRate) {
        mGrowthRate = growthRate;
    }

    public Hardiness getHardiness() {
        return mHardiness;
    }

    public void setHardiness(Hardiness hardiness) {
        mHardiness = hardiness;
    }

    public HardinessLocation getHardinessLocation() {
        return mHardinessLocation;
    }

    public void setHardinessLocation(HardinessLocation hardinessLocation) {
        mHardinessLocation = hardinessLocation;
    }

    public Object getHarvestSeason() {
        return mHarvestSeason;
    }

    public void setHarvestSeason(Object harvestSeason) {
        mHarvestSeason = harvestSeason;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Boolean getIndoor() {
        return mIndoor;
    }

    public void setIndoor(Boolean indoor) {
        mIndoor = indoor;
    }

    public Boolean getInvasive() {
        return mInvasive;
    }

    public void setInvasive(Boolean invasive) {
        mInvasive = invasive;
    }

    public Boolean getLeaf() {
        return mLeaf;
    }

    public void setLeaf(Boolean leaf) {
        mLeaf = leaf;
    }

    public List<String> getLeafColor() {
        if (mLeafColor == null) {
            return new ArrayList<>();
        }

        return mLeafColor;
    }

    public void setLeafColor(List<String> leafColor) {
        mLeafColor = leafColor;
    }

    public Object getMaintenance() {
        return mMaintenance;
    }

    public void setMaintenance(Object maintenance) {
        mMaintenance = maintenance;
    }

    public Boolean getMedicinal() {
        return mMedicinal;
    }

    public void setMedicinal(Boolean medicinal) {
        mMedicinal = medicinal;
    }

    public List<String> getOrigin() {
        if (mOrigin == null) {
            return new ArrayList<>();
        }
        return mOrigin;
    }

    public void setOrigin(List<String> origin) {

        mOrigin = origin;
    }

    public String getOtherImages() {
        return mOtherImages;
    }

    public void setOtherImages(String otherImages) {
        mOtherImages = otherImages;
    }

    public List<String> getOtherName() {

        if (mOtherName == null) {
            return new ArrayList<>();
        }
        return mOtherName;
    }

    public void setOtherName(List<String> otherName) {
        mOtherName = otherName;
    }

    public List<Object> getPestSusceptibility() {
        if (mPestSusceptibility == null) {
            return new ArrayList<>();
        }
        return mPestSusceptibility;
    }

    public void setPestSusceptibility(List<Object> pestSusceptibility) {
        mPestSusceptibility = pestSusceptibility;
    }

    public String getPestSusceptibilityApi() {
        return mPestSusceptibilityApi;
    }

    public void setPestSusceptibilityApi(String pestSusceptibilityApi) {
        mPestSusceptibilityApi = pestSusceptibilityApi;
    }

    public List<Object> getPlantAnatomy() {
        if (mPlantAnatomy == null) {
            return new ArrayList<>();
        }
        return mPlantAnatomy;
    }

    public void setPlantAnatomy(List<Object> plantAnatomy) {
        mPlantAnatomy = plantAnatomy;
    }

    public Long getPoisonousToHumans() {
        return mPoisonousToHumans;
    }

    public void setPoisonousToHumans(Long poisonousToHumans) {
        mPoisonousToHumans = poisonousToHumans;
    }

    public Long getPoisonousToPets() {
        return mPoisonousToPets;
    }

    public void setPoisonousToPets(Long poisonousToPets) {
        mPoisonousToPets = poisonousToPets;
    }

    public List<String> getPropagation() {
        if (mPropagation == null) {
            return new ArrayList<>();
        }
        return mPropagation;
    }

    public void setPropagation(List<String> propagation) {
        mPropagation = propagation;
    }

    public List<Object> getPruningCount() {
        if (mPruningCount == null) {
            return new ArrayList<>();
        }

        return mPruningCount;
    }

    public void setPruningCount(List<Object> pruningCount) {
        mPruningCount = pruningCount;
    }

    public List<String> getPruningMonth() {
        if (mPruningMonth == null) {
            return new ArrayList<>();
        }
        return mPruningMonth;
    }

    public void setPruningMonth(List<String> pruningMonth) {
        mPruningMonth = pruningMonth;
    }

    public Boolean getSaltTolerant() {
        return mSaltTolerant;
    }

    public void setSaltTolerant(Boolean saltTolerant) {
        mSaltTolerant = saltTolerant;
    }

    public List<String> getScientificName() {
        if (mScientificName == null) {
            return new ArrayList<>();
        }
        return mScientificName;
    }

    public void setScientificName(List<String> scientificName) {
        mScientificName = scientificName;
    }

    public Long getSeeds() {
        return mSeeds;
    }

    public void setSeeds(Long seeds) {
        mSeeds = seeds;
    }

    public List<Object> getSoil() {
        return mSoil;
    }

    public void setSoil(List<Object> soil) {
        mSoil = soil;
    }

    public List<String> getSunlight() {
        if (mSunlight == null) {
            return new ArrayList<>();
        }
        return mSunlight;
    }

    public void setSunlight(List<String> sunlight) {
        mSunlight = sunlight;
    }

    public Boolean getThorny() {
        return mThorny;
    }

    public void setThorny(Boolean thorny) {
        mThorny = thorny;
    }

    public Boolean getTropical() {
        return mTropical;
    }

    public void setTropical(Boolean tropical) {
        mTropical = tropical;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public List<Object> getVolumeWaterRequirement() {
        return mVolumeWaterRequirement;
    }

    public void setVolumeWaterRequirement(List<Object> volumeWaterRequirement) {
        mVolumeWaterRequirement = volumeWaterRequirement;
    }

    public String getWatering() {
        return mWatering;
    }

    public void setWatering(String watering) {
        mWatering = watering;
    }

    public WateringGeneralBenchmark getWateringGeneralBenchmark() {
        return mWateringGeneralBenchmark;
    }

    public void setWateringGeneralBenchmark(WateringGeneralBenchmark wateringGeneralBenchmark) {
        mWateringGeneralBenchmark = wateringGeneralBenchmark;
    }

    public Object getWateringPeriod() {
        return mWateringPeriod;
    }

    public void setWateringPeriod(Object wateringPeriod) {
        mWateringPeriod = wateringPeriod;
    }


    public boolean hasImage()
    {
        if (this.getDefaultImage() != null)
        {
            return this.getDefaultImage().getThumbnail() != null;
        }

        return false;

    }

    public String getThumbnail()
    {
        return this.hasImage()?this.getDefaultImage().getThumbnail():"";
    }

}
