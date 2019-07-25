package com.goertek.commonlib.provider.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Container {
    @SerializedName("@available_option")
    public String available_option;
    @SerializedName("@index")
    public String index;
    @SerializedName("@is_support_option")
    public String is_support_option;
    @SerializedName("@rect")
    public String rect;
    @SerializedName("@selected_option")
    public String selected_option;
    @SerializedName("@layers")
    public List<Layer> layers;
    @SerializedName("@data_type")
    private String dataType;
    @SerializedName("@is_available")
    private String isAvailable;
    @SerializedName("@res_preview")
    private String resPreview;

    public String getAvailableOption() {
        return available_option;
    }

    public void setAvailableOption(String available_option) {
        this.available_option = available_option;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getIsSupportOption() {
        return is_support_option;
    }

    public void setIsSupportOption(String isSupportOption) {
        this.is_support_option = isSupportOption;
    }

    public String getRect() {
        return rect;
    }

    public void setRect(String rect) {
        this.rect = rect;
    }

    public String getSelectedOption() {
        return selected_option;
    }

    public void setSelectedOption(String selectedOption) {
        this.selected_option = selectedOption;
    }

    public String getDataType() { return this.dataType; }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getResPreview() {
        return resPreview;
    }

    public void setResPreview(String resPreview) {
        this.resPreview = resPreview;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Container { index=");
        stringBuilder.append(this.index);
        stringBuilder.append(" , rect=");
        stringBuilder.append(this.rect);
        stringBuilder.append(" , isSupportOption=");
        stringBuilder.append(this.is_support_option);
        stringBuilder.append(" , selectedOption=");
        stringBuilder.append(this.selected_option);
        stringBuilder.append(" , availableOption=");
        stringBuilder.append(this.available_option);
        stringBuilder.append(" } ");
        return stringBuilder.toString();
    }
}
