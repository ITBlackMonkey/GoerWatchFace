package com.goertek.commonlib.provider.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Providers {
    @SerializedName("provider")
    private Provider provider;

    public Provider getProvider() {
        return provider;
    }

    public void setProviders(Provider provider) {
        this.provider = provider;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" provider { provider=");
        stringBuilder.append(this.provider);
        return stringBuilder.toString();
    }
}
