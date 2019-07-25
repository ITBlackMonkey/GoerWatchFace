
/*
 * Copyright (c) 2016 - Goertek -All rights reserved
 */

package com.goertek.commonlib.provider.manager;

import android.graphics.Bitmap;
import android.graphics.Movie;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.goertek.commonlib.utils.LogUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * bitmap缓存管理
 *
 * @author ww
 * @version 1.0.0
 * @since 2019/07/08
 */
public class ResourceCache {
    private static final String TAG = "ResourceCache";

    private HashMap<String, Bitmap> mBitmapCache;

    private HashMap<String, Drawable> mDrawableCache;

    private HashMap<String, Movie> mMovieCache;

    private HashMap<String, Typeface> mTypefaceCache;

    private ResourceResolver mResolver;

    ResourceCache(ResourceResolver paramResourceResolver) {
        mResolver = paramResourceResolver;
        mBitmapCache = new HashMap(0);
        mDrawableCache = new HashMap(0);
        mMovieCache = new HashMap(0);
        mTypefaceCache = new HashMap(0);
    }

    Bitmap getBitmap(String assetPath) {
        Bitmap bitmap;
        if (!TextUtils.isEmpty(assetPath)) {
            Bitmap bitmap1 = (Bitmap) mBitmapCache.get(assetPath);
            bitmap = bitmap1;
            if (bitmap1 == null) {
                bitmap = mResolver.resolveBitmapByName(assetPath);
                if (bitmap != null) {
                    mBitmapCache.put(assetPath, bitmap);
                    return bitmap;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getBitmap(), no bitmap find of name: ");
                stringBuilder.append(assetPath);
                LogUtil.e("ResourceCache", stringBuilder.toString());
                return bitmap;
            }
        } else {
            LogUtil.e("ResourceCache", "getBitmap(), nameStr is null");
            bitmap = null;
        }
        return bitmap;
    }

    /**
     * 获取图片集合
     *
     * @param paramString 以“，”隔开的图片名称列表
     * @return 图片集合
     */
    List<Bitmap> getBitmaps(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            LogUtil.e("ResourceCache", "getBitmaps(), nameStr is null");
            return Collections.emptyList();
        }
        byte b = 0;
        ArrayList arrayList = new ArrayList(0);
        String[] arrayOfString = paramString.trim().split(",");
        int i = arrayOfString.length;
        while (b < i) {
            String str = arrayOfString[b].trim();
            if (str.endsWith(".png")) {
                Bitmap bitmap2 = (Bitmap) mBitmapCache.get(str);
                Bitmap bitmap1 = bitmap2;
                if (bitmap2 == null) {
//                    bitmap1 = this.mResolver.resolveBitmapByName(str);
                    if (bitmap1 != null) {
                        mBitmapCache.put(str, bitmap1);
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("getBitmaps(), no bitmap find of name: ");
                        stringBuilder.append(str);
                        LogUtil.e("ResourceCache", stringBuilder.toString());
                    }
                }
                arrayList.add(bitmap1);
            }
            b++;
        }
        return arrayList;
    }

    List<Drawable> getDrawables(String paramString) {
        if (TextUtils.isEmpty(paramString)) {
            LogUtil.e(TAG, "getDrawables(), nameStr is null");
            return Collections.emptyList();
        }
        byte b = 0;
        ArrayList arrayList = new ArrayList() {
            private static final long serialVersionUID = 903957977847728155L;
        };
        String[] arrayOfString = paramString.trim().split(",");
        int i = arrayOfString.length;
        while (b < i) {
            String str = arrayOfString[b].trim();
            if (str.endsWith(".png")) {
                Drawable drawable2 = (Drawable) mDrawableCache.get(str);
                Drawable drawable1 = drawable2;
                if (drawable2 == null) {
                    drawable1 = mResolver.resolveDrawableByName(str);
                    if (drawable1 != null) {
                        mDrawableCache.put(str, drawable1);
                    } else {
                        LogUtil.e(TAG, "getDrawables(), no drawable find of name: " + str);
                    }
                }
                arrayList.add(drawable1);
            }
            b++;
        }
        return arrayList;
    }

    /**
     * 内存释放
     */
    void onDestroy() {
        if (!mBitmapCache.isEmpty()) {
            Iterator iterator = mBitmapCache.entrySet().iterator();
            while (iterator.hasNext()) {
                Bitmap bitmap = (Bitmap) ((Map.Entry) iterator.next()).getValue();
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
            mBitmapCache.clear();
        }
        if (!mBitmapCache.isEmpty()){
            mBitmapCache.clear();
        }
    }

    Typeface getTypeface(String fontType) {
        Typeface typeface;
        if (!TextUtils.isEmpty(fontType)) {
            Typeface typeface1 = mTypefaceCache.get(fontType);
            typeface = typeface1;
            if (typeface1 == null) {
                typeface = this.mResolver.resolveTypefaceByName(fontType);
                if (typeface != null) {
                    this.mTypefaceCache.put(fontType, typeface);
                    return typeface;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getTypeface(), no typeface find of name: ");
                stringBuilder.append(fontType);
                LogUtil.e("ResourceCache", stringBuilder.toString());
                return typeface;
            }
        } else {
            LogUtil.e("ResourceCache", "getTypeface(), name is null");
            typeface = null;
        }
        return typeface;
    }

}
