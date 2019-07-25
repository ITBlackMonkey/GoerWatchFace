package com.goertek.commonlib.view.element;


import android.graphics.Canvas;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.goertek.commonlib.provider.manager.AssetPackage;
import com.goertek.commonlib.provider.model.Element;
import com.goertek.commonlib.provider.model.Layer;
import com.goertek.commonlib.provider.model.Option;
import com.goertek.commonlib.provider.model.Styles;
import com.goertek.commonlib.utils.UIDrawUnitFactory;
import com.goertek.commonlib.utils.WatchFaceUtil;
import com.goertek.commonlib.view.unit.base.IDrawUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BaseElement implements IDrawElement {
    protected AssetPackage assetPackage;
    private boolean isAmbientMode;
    private String mLabel;
    protected List<IDrawUnit> mDrawUnits = new ArrayList<>(0);

    public BaseElement(String lable, AssetPackage assertPackage) {
        this.assetPackage = assertPackage;
        this.mLabel = lable;
        addUnit();
    }

    public void addUnit() {
        //add view
        List<Element> elements = assetPackage.getElementProvider().getElements();
        if (elements != null && !elements.isEmpty()) {
            for (Element element : elements) {
                boolean isOption = WatchFaceUtil.getBoolValue(element.getIsSupportOption());
                if (isOption) {
                    Option option = assetPackage.getElementProvider().getSelectedOption(element.getLabel());
                    List<Layer> layers = option.layers;
                    if (layers != null && !layers.isEmpty()) {
                        for (Layer layer : layers) {
                            IDrawUnit drawUnit = UIDrawUnitFactory.createDrawUnit(assetPackage, layer);
                            mDrawUnits.add(drawUnit);
                        }
                    }
                } else {
                    List<Layer> layers = element.getLayers();
                    if (layers != null && !layers.isEmpty()) {
                        for (Layer layer : layers) {
                            IDrawUnit drawUnit = UIDrawUnitFactory.createDrawUnit(assetPackage, layer);
                            mDrawUnits.add(drawUnit);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void preDraw(Canvas canvas, boolean isAmbientMode) {
    }

    @Override
    public void draw(Canvas canvas) {
        for (IDrawUnit drawUnit : mDrawUnits) {
            drawUnit.preDraw(canvas, isAmbientMode);
            drawUnit.draw(canvas, isAmbientMode);
        }
    }

    @Override
    public void onAmbientModeChanged(boolean inAmbientMode) {
        this.isAmbientMode = inAmbientMode;
        for (IDrawUnit drawUnit : mDrawUnits) {
            drawUnit.isAmbientModeChanged(inAmbientMode);
        }
    }

    @Override
    public void onClick(int x, int y) {
        for (IDrawUnit drawUnit : mDrawUnits) {
            drawUnit.onClick(x, y);
        }
    }

    protected Element getElement() {
        return assetPackage.getElementProvider().getElement(mLabel);
    }

    protected Styles getStyles() {
        return assetPackage.getElementProvider().getStyles();
    }
}
