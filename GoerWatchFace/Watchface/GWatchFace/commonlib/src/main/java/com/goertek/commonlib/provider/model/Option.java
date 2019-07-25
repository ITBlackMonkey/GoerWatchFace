package com.goertek.commonlib.provider.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Option {
    @SerializedName("@index")
    public String index;
    @SerializedName("@data_type")
    public String data_type;
    @SerializedName("@layer_count")
    public String layer_count;
    @SerializedName("@layer")
    public List<Layer> layers;
    //option数量
    @SerializedName("@optionNum")
    public String optionNum;
    //所选择的option
    @SerializedName("@selectedOption")
    public String selectedOption;

    @SerializedName("@res_border_preview")
    private String resBorderPreview;

    @SerializedName("@res_preview")
    private String resPreview;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getDataType() {
        return data_type;
    }

    public void setDataType(String data_type) {
        this.data_type = data_type;
    }

    public String getLayer_count() {
        return layer_count;
    }

    public void setLayer_count(String layer_count) {
        this.layer_count = layer_count;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public void setLayers(List<Layer> layers) {
        this.layers = layers;
    }

    public String getResBorderPreview() {
        return resBorderPreview;
    }

    public void setResBorderPreview(String resBorderPreview) {
        this.resBorderPreview = resBorderPreview;
    }

    public String getOptionNum() {
        return optionNum;
    }

    public void setOptionNum(String optionNum) {
        this.optionNum = optionNum;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getResPreview() {
        return resPreview;
    }

    public void setResPreview(String resPreview) {
        this.resPreview = resPreview;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Option { layers=");
        stringBuilder.append(this.layers);
        stringBuilder.append(" , index=");
        stringBuilder.append(this.index);
        stringBuilder.append(" , resPreview=");
        stringBuilder.append(this.resPreview);
        stringBuilder.append(" , dataType=");
        stringBuilder.append(this.data_type);
        stringBuilder.append(" ,resBoredrPreview=");
        stringBuilder.append(this.resBorderPreview);
        stringBuilder.append(" } ");
        return stringBuilder.toString();
    }

}
