package com.goertek.commonlib.custom.fragment;

import com.goertek.commonlib.custom.BaseCustomFragment;
import com.goertek.commonlib.view.unit.UnitConstants;

/**
 * 自定义控件 Fragment
 *
 * @author ww
 * @version 1.0.0
 * @since 2019/07/19
 */
public class WidgetFragment extends BaseCustomFragment {
    private static final String TAG = "WidgetFragment";

    public WidgetFragment() {
    }

    @Override
    public String getLabel() {
        return UnitConstants.LABEL_WIDGET;
    }

    @Override
    protected int getSortNum() {
        return UnitConstants.LABEL_WIDGET_SORT_NUM;
    }
}
