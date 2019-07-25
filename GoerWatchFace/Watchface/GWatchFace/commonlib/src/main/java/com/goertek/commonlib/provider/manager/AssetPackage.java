package com.goertek.commonlib.provider.manager;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.text.TextUtils;

import com.goertek.commonlib.utils.ContextUtil;
import com.goertek.commonlib.utils.LogUtil;

import java.util.Collections;
import java.util.List;

/**
 * 主题资源包
 *
 * @author ww
 * @version 1.0.0
 * @since 2019/07/05
 */
public class AssetPackage {
    private static final String TAG = "AssetPackage";

    private ResourceResolver mResolver;

    private ResourceCache mResourceCache;

    private ElementProvider mProviders;

    /**
     * 构造方法
     *
     * @param path     资源路径
     * @param isAssets 是否assets
     */
    public AssetPackage(String path, boolean isAssets) {
        mResolver = new ResourceResolver(ContextUtil.getContext(), path, isAssets);
        mResourceCache = new ResourceCache(mResolver);
    }


    public ElementProvider getElementProvider() {
        if (mProviders == null) {
            mProviders = new ElementProvider(mResolver);
        }
        return mProviders;
    }

    /**
     * 获取bitmap对象
     *
     * @param info 资源信息
     * @return bitmap
     */
    public Bitmap getBitmap(String info) {
        Bitmap bitmap = null;
        if (!TextUtils.isEmpty(info)) {
            bitmap = mResourceCache.getBitmap(info.trim());
        } else {
            LogUtil.i(TAG, "getBitmap() info is null");
        }
        return bitmap;
    }

    /**
     * 获取bitmaps对象
     *
     * @param info 资源信息
     * @return bitmaps
     */
    public List<Bitmap> getBitmaps(String info) {
        if (TextUtils.isEmpty(info)) {
            LogUtil.i(TAG, "getBitmaps(), info: null");
            return Collections.emptyList();
        }
        LogUtil.e(TAG, "getBitmaps2(),info=" + info);
        return mResourceCache.getBitmaps(info.trim());
    }


    public Typeface getTypeface(String fontFace) {
        if (!TextUtils.isEmpty(fontFace)) {
            return mResourceCache.getTypeface(fontFace.trim());
        }
        LogUtil.i("AssetPackage", "getTypeface() label: info: null");
        return null;
    }

    public String getPath() {
        return mResolver.getAssetPath();
    }

}
