package com.goertek.commonlib.provider.manager;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.goertek.commonlib.provider.model.Container;
import com.goertek.commonlib.provider.model.Element;
import com.goertek.commonlib.provider.model.Layer;
import com.goertek.commonlib.provider.model.Option;
import com.goertek.commonlib.provider.model.Provider;
import com.goertek.commonlib.provider.model.Providers;
import com.goertek.commonlib.provider.model.Styles;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ElementProvider {

    private Provider mProvider;
    private Providers mProviders;
    private ResourceResolver mResolver;

    public ElementProvider(ResourceResolver resolver) {
        mResolver = resolver;
        mProviders = mResolver.parserConfigFile();
    }

    public List<Element> getElements() {
        return Collections.EMPTY_LIST;
    }

    public List<Option> getOptions(String elementLabel) {
        return Collections.EMPTY_LIST;
    }

    public List<Layer> getLayers(String label) {
        return Collections.EMPTY_LIST;
    }

    public List<Container> getContainer() {
        return Collections.EMPTY_LIST;
    }

    public Option getSelectedOption(String label) {
        return new Option();
    }

    public Element getElementWithLabel(String label) {
        return new Element();
    }

    public List<Container> getSelectedContainer() {
        return Collections.EMPTY_LIST;
    }

    public Styles getStyles() {
        return null;
    }

    public Element getElement(String lable) {
        Provider provider = this.mProvider;
        if (provider == null) {
            return null;
        }
        List<Element> elements = provider.getElements();
        if (elements == null || elements.size() <= 0) {
            return null;
        }
        for (Element element : elements) {
            if (TextUtils.equals(element.getLabel(), lable)) {
                return element;
            }
        }
        return null;
    }
}
