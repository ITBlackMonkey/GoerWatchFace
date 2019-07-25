package com.goertek.commonlib.view.element;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.goertek.commonlib.provider.data.manager.DataAdapter;
import com.goertek.commonlib.provider.manager.AssetPackage;
import com.goertek.commonlib.provider.model.Container;
import com.goertek.commonlib.provider.model.Element;
import com.goertek.commonlib.provider.model.Layer;
import com.goertek.commonlib.provider.model.Option;
import com.goertek.commonlib.provider.model.Style;
import com.goertek.commonlib.provider.model.Styles;
import com.goertek.commonlib.utils.AttributeUtils;
import com.goertek.commonlib.utils.UIDrawUnitFactory;
import com.goertek.commonlib.utils.WatchFaceUtil;
import com.goertek.commonlib.view.unit.UnitConstants;
import com.goertek.commonlib.view.unit.base.IDrawUnit;

import java.util.List;
import java.util.Optional;

public class WidgetElement extends BaseElement {
    private static final String TAG = "WidgetElement";

    public static final String LABLE = "widget";

    public WidgetElement(AssetPackage assertPackage) {
        super(LABLE, assertPackage);
    }

    @Override
    public void addUnit() {
       /* Element widgetElement = assetPackage.getElementProvider().getElementWithLabel(UnitConstants.LABEL_WIDGET);
        if (widgetElement != null) {
            List<Container> containers = widgetElement.getContainers();
            String selectedIndex = widgetElement.getSelectedContainer();
            if (!TextUtils.isEmpty(selectedIndex)) {
                List<String> indexList = WatchFaceUtil.getStringValues(selectedIndex);
                if (containers != null && !containers.isEmpty()) {
                    for (Container container : containers) {
                        if (indexList.contains(container.index)) {
                            boolean isOption = WatchFaceUtil.getBoolValue(container.is_support_option);
                            if (isOption) {
                                String selectedOption = container.selected_option;
                            } else {
                                List<Layer> allLayers = container.layers;
                                for (Layer layer : allLayers) {
                                    IDrawUnit drawUnit = UIDrawUnitFactory.createDrawUnit(assetPackage, layer);
                                    mDrawUnits.add(drawUnit);
                                }
                            }
                        }
                    }
                }
            }
        }*/
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(int x, int y) {
        Element element = getElement();
        if (element == null) {
            return;
        }
        List<String> obtainContainers = obtainContainerIndexWithStyles();
        List<Container> containers = element.getContainers();
        if (containers != null) {
            if (containers.size() <= 0) {
                return;
            }

            Container clickContainer = null;
            for (Container container : containers) {
                boolean isObtainContainer;
                if (container == null) {
                    return;
                }
                String containerIndex = container.getIndex();
                if (obtainContainers == null && obtainContainers.size() == 0) {
                    isObtainContainer = true;
                } else {
                    isObtainContainer = obtainContainers.contains(containerIndex);
                }

                if (WatchFaceUtil.isInBound(x, y, WatchFaceUtil.getRect(container.getRect())) && isObtainContainer) {
                   // 过滤满足点击事件的container
                    clickContainer = container;
                }
            }
            // 响应点击事件的clickContainer，这样可以保证每个点击事件只有一次响应
            if (clickContainer == null) {
                return;
            }
            DataAdapter.getInstance().doClick(clickContainer.getDataType());
        }
    }

    private List<String> obtainContainerIndexWithStyles() {
        return null;
    }
}
