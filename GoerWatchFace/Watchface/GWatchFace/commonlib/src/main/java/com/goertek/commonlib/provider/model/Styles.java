package com.goertek.commonlib.provider.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Optional;

public class Styles {

    @SerializedName("@selected_style")
    private String selectedStyle;

    @SerializedName("@style_count")
    private String styleCount;

    @SerializedName("style")

    private List<Style> styles;

    public String getSelectedStyle() {
        return this.selectedStyle;
    }

    public Style getStyle(String selectedStyle) {
        List list = styles;
        if (list == null || list.size() <= 0) {
            return null;
        }
        for (Style style : this.styles) {
            if (style != null && TextUtils.equals(style.getIndex().trim(), selectedStyle.trim())){
                return style;
            }
        }
        return null;
    }

    public String getStyleCount() {
        return this.styleCount;
    }

    public List<Style> getStyles() {
        return this.styles;
    }

    public void setSelectedStyle(String paramString) {
        this.selectedStyle = paramString;
    }

    public void setStyleCount(String paramString) {
        this.styleCount = paramString;
    }

    public void setStyles(List<Style> paramList) {
        this.styles = paramList;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Styles{styles=");
        stringBuilder.append(this.styles);
        stringBuilder.append(", selectedStyle='");
        stringBuilder.append(this.selectedStyle);
        stringBuilder.append('\'');
        stringBuilder.append(", styleCount='");
        stringBuilder.append(this.styleCount);
        stringBuilder.append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}
