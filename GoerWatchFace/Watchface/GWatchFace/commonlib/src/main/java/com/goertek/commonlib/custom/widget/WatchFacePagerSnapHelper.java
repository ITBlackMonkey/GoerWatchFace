package com.goertek.commonlib.custom.widget;

import android.view.View;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class WatchFacePagerSnapHelper extends PagerSnapHelper {

    private static final int DISTANCE_NUM = 2;
    private static final int DIVIDE_FACTOR = 2;

    private OrientationHelper mHorizontalHelper;
    private OrientationHelper mVerticalHelper;

    private int distanceToCenter(RecyclerView.LayoutManager paramLayoutManager, View paramView, OrientationHelper paramOrientationHelper) {
        int childCenter = paramOrientationHelper.getDecoratedStart(paramView) +
                paramOrientationHelper.getDecoratedMeasurement(paramView) / 2;
        int containerCenter;
        if (paramLayoutManager.getClipToPadding())
            containerCenter = paramOrientationHelper.getStartAfterPadding() + paramOrientationHelper.getTotalSpace() / 2;
        else
            containerCenter = paramOrientationHelper.getEnd() / 2;
        return childCenter - containerCenter;
    }

    private View findCenterView(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper) {
        int childCount = paramLayoutManager.getChildCount();
        if (childCount == 0)
            return null;
        View closestChild = null;
        int center;
        if (paramLayoutManager.getClipToPadding())
            center = paramOrientationHelper.getStartAfterPadding() + paramOrientationHelper.getTotalSpace() / 2;
        else
            center = paramOrientationHelper.getEnd() / 2;

        int absClosest = Integer.MAX_VALUE;
        List<View> views = removeLastDistanceChildView(paramLayoutManager, paramOrientationHelper);
        if (views != null) {
            Iterator<View> localIterator = views.iterator();
            while (localIterator.hasNext()) {
                View view = localIterator.next();
                int absDistance = Math.abs(paramOrientationHelper.getDecoratedStart(view) +
                        paramOrientationHelper.getDecoratedMeasurement(view) / 2 - center);
                if (absDistance >= center)
                    continue;
            }
        }
        return closestChild;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager paramLayoutManager) {
        OrientationHelper localOrientationHelper = this.mHorizontalHelper;
        if ((localOrientationHelper == null) || (localOrientationHelper.getLayoutManager() != paramLayoutManager))
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(paramLayoutManager);
        return this.mHorizontalHelper;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager paramLayoutManager) {
        OrientationHelper localOrientationHelper = this.mVerticalHelper;
        if ((localOrientationHelper == null) || (localOrientationHelper.getLayoutManager() != paramLayoutManager))
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(paramLayoutManager);
        return this.mVerticalHelper;
    }

    private List<View> removeLastDistanceChildView(RecyclerView.LayoutManager paramLayoutManager, OrientationHelper paramOrientationHelper) {
        int childCount = paramLayoutManager.getChildCount();
        int center;
        ArrayList<View> localArrayList1 = new ArrayList(childCount);
        ArrayList<RecyclerViewBean> localArrayList2 = new ArrayList(childCount);
        if (paramLayoutManager.getClipToPadding())
            center = paramOrientationHelper.getStartAfterPadding() + paramOrientationHelper.getTotalSpace() / 2;
        else
            center = paramOrientationHelper.getEnd() / 2;
        int index = 0;
        while (index < childCount) {
            View localView = paramLayoutManager.getChildAt(index);
            localArrayList2.add(new RecyclerViewBean(localView,
                    Math.abs(paramOrientationHelper.getDecoratedStart(localView) +
                            paramOrientationHelper.getDecoratedMeasurement(localView) / 2 - center)));
            index += 1;
        }
        Collections.sort(localArrayList2);
        int size = localArrayList2.size();
        int i = 1;
        if (size <= 1)
            i = 0;
        while (i < localArrayList2.size()) {
            localArrayList1.add(localArrayList2.get(i).getView());
            i += 1;
        }
        return localArrayList1;
    }

    public int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager paramLayoutManager, View paramView) {
        int[] arrayOfInt = new int[2];
        if (paramLayoutManager.canScrollHorizontally())
            arrayOfInt[0] = distanceToCenter(paramLayoutManager, paramView, getHorizontalHelper(paramLayoutManager));
        else
            arrayOfInt[0] = 0;
        if (paramLayoutManager.canScrollVertically()) {
            arrayOfInt[1] = distanceToCenter(paramLayoutManager, paramView, getVerticalHelper(paramLayoutManager));
            return arrayOfInt;
        }
        arrayOfInt[1] = 0;
        return arrayOfInt;
    }

    public View findSnapView(RecyclerView.LayoutManager paramLayoutManager) {
        if (paramLayoutManager.canScrollVertically())
            return findCenterView(paramLayoutManager, getVerticalHelper(paramLayoutManager));
        if (paramLayoutManager.canScrollHorizontally())
            return findCenterView(paramLayoutManager, getHorizontalHelper(paramLayoutManager));
        return null;
    }
}
