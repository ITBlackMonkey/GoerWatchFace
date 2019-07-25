package com.goertek.commonlib.provider.model;

import androidx.annotation.NonNull;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Provider {
    @SerializedName("@dpi")
    private String dpi;
    @SerializedName("element")
    private List<Element> elements;

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" provider { dpi=");
        stringBuilder.append(this.dpi);
        stringBuilder.append(" , elements=");
        stringBuilder.append(this.elements);
        stringBuilder.append(" } ");
        return stringBuilder.toString();
    }
}
