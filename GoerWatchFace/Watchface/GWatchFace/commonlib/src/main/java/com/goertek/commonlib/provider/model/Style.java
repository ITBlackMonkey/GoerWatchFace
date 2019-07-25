package com.goertek.commonlib.provider.model;

import com.google.gson.annotations.SerializedName;

public class Style {

    @SerializedName("@index")
    private String index;

    @SerializedName("@background_selected_option")
    private String backgroundSelectedOption;

    @SerializedName("@color_selected_option")
    private String colorSelectedOption;

    @SerializedName("@date_selected_option")
    private String dateSelectedOption;


    @SerializedName("@widget_available_containers")
    private String timeSelectedOption;

    @SerializedName("@dial_selected_option")
    private String dialSelectedOption;

    @SerializedName("@widget_available_containers")
    private String widgetAvailableContainers;

    @SerializedName("@container_selected_options")
    private String containerSelectedOptions;

    @SerializedName("@background_available_option")
    private String backgroundAvailableOption;

    @SerializedName("@date_available_option")
    private String dateAvailableOption;

    @SerializedName("@time_available_option")
    private String timeAvailableOption;

    @SerializedName("@dial_available_option")
    private String dialAvailableOption;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getBackgroundSelectedOption() {
        return backgroundSelectedOption;
    }

    public void setBackgroundSelectedOption(String backgroundSelectedOption) {
        this.backgroundSelectedOption = backgroundSelectedOption;
    }

    public String getColorSelectedOption() {
        return colorSelectedOption;
    }

    public void setColorSelectedOption(String colorSelectedOption) {
        this.colorSelectedOption = colorSelectedOption;
    }

    public String getDateSelectedOption() {
        return dateSelectedOption;
    }

    public void setDateSelectedOption(String dateSelectedOption) {
        this.dateSelectedOption = dateSelectedOption;
    }

    public String getTimeSelectedOption() {
        return timeSelectedOption;
    }

    public void setTimeSelectedOption(String timeSelectedOption) {
        this.timeSelectedOption = timeSelectedOption;
    }

    public String getDialSelectedOption() {
        return dialSelectedOption;
    }

    public void setDialSelectedOption(String dialSelectedOption) {
        this.dialSelectedOption = dialSelectedOption;
    }

    public String getWidgetAvailableContainers() {
        return widgetAvailableContainers;
    }

    public void setWidgetAvailableContainers(String widgetAvailableContainers) {
        this.widgetAvailableContainers = widgetAvailableContainers;
    }

    public String getContainerSelectedOptions() {
        return containerSelectedOptions;
    }

    public void setContainerSelectedOptions(String containerSelectedOptions) {
        this.containerSelectedOptions = containerSelectedOptions;
    }

    public String getBackgroundAvailableOption() {
        return backgroundAvailableOption;
    }

    public void setBackgroundAvailableOption(String backgroundAvailableOption) {
        this.backgroundAvailableOption = backgroundAvailableOption;
    }

    public String getDateAvailableOption() {
        return dateAvailableOption;
    }

    public void setDateAvailableOption(String dateAvailableOption) {
        this.dateAvailableOption = dateAvailableOption;
    }

    public String getTimeAvailableOption() {
        return timeAvailableOption;
    }

    public void setTimeAvailableOption(String timeAvailableOption) {
        this.timeAvailableOption = timeAvailableOption;
    }

    public String getDialAvailableOption() {
        return dialAvailableOption;
    }

    public void setDialAvailableOption(String dialAvailableOption) {
        this.dialAvailableOption = dialAvailableOption;
    }

    @Override
    public String toString() {
        return "Style{" +
                       "index='" + index + '\'' +
                       ", backgroundSelectedOption='" + backgroundSelectedOption + '\'' +
                       ", colorSelectedOption='" + colorSelectedOption + '\'' +
                       ", dateSelectedOption='" + dateSelectedOption + '\'' +
                       ", timeSelectedOption='" + timeSelectedOption + '\'' +
                       ", dialSelectedOption='" + dialSelectedOption + '\'' +
                       ", widgetAvailableContainers='" + widgetAvailableContainers + '\'' +
                       ", containerSelectedOptions='" + containerSelectedOptions + '\'' +
                       ", backgroundAvailableOption='" + backgroundAvailableOption + '\'' +
                       ", dateAvailableOption='" + dateAvailableOption + '\'' +
                       ", timeAvailableOption='" + timeAvailableOption + '\'' +
                       ", dialAvailableOption='" + dialAvailableOption + '\'' +
                       '}';
    }
}
