package com.goertek.commonlib.custom;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.goertek.commonlib.R;
import com.goertek.commonlib.custom.adapter.ViewpagerAdapter;
import com.goertek.commonlib.custom.fragment.BackgroundFragment;
import com.goertek.commonlib.custom.fragment.DateFragment;
import com.goertek.commonlib.custom.fragment.DialFragment;
import com.goertek.commonlib.custom.fragment.StylesFragment;
import com.goertek.commonlib.custom.fragment.TimeFragment;
import com.goertek.commonlib.custom.fragment.WidgetFragment;
import com.goertek.commonlib.provider.manager.AssetPackage;
import com.goertek.commonlib.provider.manager.ElementProvider;
import com.goertek.commonlib.provider.model.Element;
import com.goertek.commonlib.provider.model.Styles;
import com.goertek.commonlib.view.unit.UnitConstants;

import java.util.List;

public abstract class BaseCustomWidgetActivity extends FragmentActivity {
    private static final String TAG = "BaseCustomWidgetActivit";

    private ViewPager mViewPager;

    private ViewpagerAdapter mVpAdapter;

    private List<BaseCustomFragment> mFragments;

    private BackgroundFragment mBackgroundFragment;

    private DialFragment mDialFragment;

    private TimeFragment mTimeFragment;

    private WidgetFragment mWidgetFragment;

    private StylesFragment mStylesFragment;

    private DateFragment mDateFragment;

    private AssetPackage mAssetPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_main);
        mViewPager = findViewById(R.id.custom_vp);
        initFragments();
        mVpAdapter = new ViewpagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mVpAdapter);
        mViewPager.setCurrentItem(0);
    }

    protected abstract AssetPackage getAssetPackage();

    private void initFragments() {
        mAssetPackage = getAssetPackage();
        ElementProvider elementProvider = mAssetPackage.getElementProvider();
        Styles styles = elementProvider.getStyles();
        if (styles != null) {
            mStylesFragment = new StylesFragment();
            mFragments.add(mStylesFragment);
        }

        Element background = elementProvider.getElementWithLabel(UnitConstants.LABEL_BACKGROUND);
        if (background != null) {
            mBackgroundFragment = new BackgroundFragment();
            mFragments.add(mBackgroundFragment);
        }
        Element time = elementProvider.getElementWithLabel(UnitConstants.LABEL_TIME);
        if (time != null) {
            mTimeFragment = new TimeFragment();
            mFragments.add(mTimeFragment);
        }
        Element date = elementProvider.getElementWithLabel(UnitConstants.LABEL_DATE);
        if (date != null) {
            mDateFragment = new DateFragment();
            mFragments.add(mDateFragment);
        }
        Element dial = elementProvider.getElementWithLabel(UnitConstants.LABEL_DIAL);
        if (dial != null) {
            mDialFragment = new DialFragment();
            mFragments.add(mDialFragment);
        }
        Element widget = elementProvider.getElementWithLabel(UnitConstants.LABEL_WIDGET);
        if (widget != null) {
            mWidgetFragment = new WidgetFragment();
            mFragments.add(mWidgetFragment);
        }
    }

    public void btnSure(View view) {
    }
}
