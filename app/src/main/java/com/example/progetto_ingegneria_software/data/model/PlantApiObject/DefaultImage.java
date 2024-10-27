
package com.example.progetto_ingegneria_software.data.model.PlantApiObject;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DefaultImage {

    @SerializedName("license")
    private Long mLicense;
    @SerializedName("license_name")
    private String mLicenseName;
    @SerializedName("license_url")
    private String mLicenseUrl;
    @SerializedName("medium_url")
    private String mMediumUrl;
    @SerializedName("original_url")
    private String mOriginalUrl;
    @SerializedName("regular_url")
    private String mRegularUrl;
    @SerializedName("small_url")
    private String mSmallUrl;
    @SerializedName("thumbnail")
    private String mThumbnail;

    public Long getLicense() {
        return mLicense;
    }

    public void setLicense(Long license) {
        mLicense = license;
    }

    public String getLicenseName() {
        return mLicenseName;
    }

    public void setLicenseName(String licenseName) {
        mLicenseName = licenseName;
    }

    public String getLicenseUrl() {
        return mLicenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        mLicenseUrl = licenseUrl;
    }

    public String getMediumUrl() {
        return mMediumUrl;
    }

    public void setMediumUrl(String mediumUrl) {
        mMediumUrl = mediumUrl;
    }

    public String getOriginalUrl() {
        return mOriginalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        mOriginalUrl = originalUrl;
    }

    public String getRegularUrl() {
        return mRegularUrl;
    }

    public void setRegularUrl(String regularUrl) {
        mRegularUrl = regularUrl;
    }

    public String getSmallUrl() {
        return mSmallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        mSmallUrl = smallUrl;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String thumbnail) {
        mThumbnail = thumbnail;
    }

}
