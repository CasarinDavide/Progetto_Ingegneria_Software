package com.example.progetto_ingegneria_software.data.model.PlantApiObject;

import com.example.progetto_ingegneria_software.data.model.PlantApiObject.PestDiseaseList.PestDisease;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PlantRequestContainer<T> {

    @SerializedName("current_page")
    private Long mCurrentPage;
    @SerializedName("data")
    private List<T> mData;
    @SerializedName("from")
    private Long mFrom;
    @SerializedName("last_page")
    private Long mLastPage;
    @SerializedName("per_page")
    private Long mPerPage;
    @SerializedName("to")
    private Long mTo;
    @SerializedName("total")
    private Long mTotal;


    public PlantRequestContainer()
    {
        this.mData = new ArrayList<>();
    }

    public Long getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(Long currentPage) {
        mCurrentPage = currentPage;
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        mData = data;
    }

    public Long getFrom() {
        return mFrom;
    }

    public void setFrom(Long from) {
        mFrom = from;
    }

    public Long getLastPage() {
        return mLastPage;
    }

    public void setLastPage(Long lastPage) {
        mLastPage = lastPage;
    }

    public Long getPerPage() {
        return mPerPage;
    }

    public void setPerPage(Long perPage) {
        mPerPage = perPage;
    }

    public Long getTo() {
        return mTo;
    }

    public void setTo(Long to) {
        mTo = to;
    }

    public Long getTotal() {
        return mTotal;
    }

    public void setTotal(Long total) {
        mTotal = total;
    }
}




