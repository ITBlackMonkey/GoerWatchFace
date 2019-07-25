package com.goertek.commonlib.custom;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.goertek.commonlib.R;
import com.goertek.commonlib.custom.adapter.ElementOptionListAdapter;
import com.goertek.commonlib.custom.widget.WatchFacePagerSnapHelper;
import com.goertek.commonlib.provider.manager.AssetPackage;
import com.goertek.commonlib.provider.manager.AssetPackageHolder;
import com.goertek.commonlib.provider.model.Style;
import com.goertek.commonlib.provider.model.Styles;
import com.goertek.commonlib.utils.CustomizeUtil;

import java.util.List;

public abstract class BaseCustomFragment extends Fragment implements Comparable<BaseCustomFragment>{
    private static final String TAG = "BaseCustomFragment";

    private RelativeLayout blueBorderBg;

    private static final int INVALID_STYLE = -1;

    private int currentItemPosition;

    private ElementOptionListAdapter mAdapter;

    private AssetPackage mAssetPackage;

    private RecyclerView mElementOptionListView;

    private String mLabel;

    private PagerSnapHelper mPagerSnapHelper;

    private TextView mTvTitle;

    public void onAttach(Context paramContext) {
        super.onAttach(paramContext);
        mAssetPackage = AssetPackageHolder.getInstance().getAssetPackage();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mLabel = getLabel();
        View rootView = inflater.inflate(R.layout.fragment_base_customize, container, false);
        mTvTitle = rootView.findViewById(R.id.tv_element_title);
        mElementOptionListView = rootView.findViewById(R.id.rv_element_option_selector);
        blueBorderBg = rootView.findViewById(R.id.rv_blue_border_bg);
        return rootView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mTvTitle.setText(CustomizeUtil.getTabNameByLabel(getActivity(), mLabel));
        mElementOptionListView.setLayoutManager(layoutManager);
        mElementOptionListView.setNestedScrollingEnabled(false);
        mAdapter = new ElementOptionListAdapter(getContext(), mLabel, mAssetPackage, blueBorderBg);
        mElementOptionListView.setAdapter(mAdapter);
        mPagerSnapHelper = new WatchFacePagerSnapHelper();
        mPagerSnapHelper.attachToRecyclerView(mElementOptionListView);
        mElementOptionListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager localLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState != 0) {
                    return;
                }
                newState = localLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                setPosition(newState);
                if (TextUtils.equals(getLabel(), "styles")) {
                    //TODO 在样式页面
                }
                newState = recyclerView.getChildLayoutPosition(mPagerSnapHelper.findSnapView(layoutManager));
                mAdapter.updateSelectedOption(newState);
            }
        });
        mElementOptionListView.scrollToPosition(obtainCurrentStyle());
    }

    @Override
    public int compareTo(BaseCustomFragment temp) {
       if(this.getSortNum()==temp.getSortNum()){
           return 0;
       }else{
           return this.getSortNum()>temp.getSortNum()?1:-1;
       }
    }

    private int obtainCurrentStyle() {
        Styles styles = mAssetPackage.getElementProvider().getStyles();
        if (styles == null) {
            return INVALID_STYLE;
        }
        List<Style> styleList = styles.getStyles();
        String selectedStyle = styles.getSelectedStyle();
        if (TextUtils.isEmpty(selectedStyle)) {
            return INVALID_STYLE;
        }
        Style style = styles.getStyle(selectedStyle);
        if (style == null) {
            return INVALID_STYLE;
        }
        return styleList.indexOf(style);
    }

    private void setPosition(int position) {
        if (position < 0) {
            return;
        }
        currentItemPosition = position;
    }

    public int getCurrentPosition() {
        return currentItemPosition;
    }

    /**
     * 获取标签，区别fragment的类型
     * @return 标签
     */
    public abstract String getLabel();

    protected abstract int getSortNum();

    public void notifyDataChanged() {
        if (!(isVisible())) {
            return;
        }
        mAdapter.notifyDataChanged();
    }

}
