package com.goertek.commonlib.provider.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.goertek.commonlib.provider.model.Providers;
import com.goertek.commonlib.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResourceResolver {

    private static final String TAG = ResourceResolver.class.getSimpleName();

    private Context mContext;
    private String mAssetPath;
    private String mAssetName;
    private boolean mIsAssets;
    private String mResDirName;

    public ResourceResolver(Context context, String assetPath, boolean isAssets) {
        mContext = context;
        mAssetPath = assetPath;
        mIsAssets = isAssets;
        mAssetName = getAssetName(mAssetPath);
    }

    private String getAssetName(String mAssetPath) {
        if (TextUtils.isEmpty(mAssetPath)) {
            return "";
        }

        List<String> list = Arrays.asList(mAssetPath.split("/"));

        if (list.size() <= 0) {
            return "";
        }

        return (!mIsAssets && list.size() > 2) ? (String) list.get(list.size() - 2) : (String) list.get(list.size() - 1);
    }

    /**
     * 通过文件名获取 bitmap对象
     * @param str 文件名
     * @return bitmap对象
     */
    public Bitmap resolveBitmapByName(String str) {

        if (TextUtils.isEmpty(str)) {
            LogUtil.e(TAG, "resolveBitmapByName(), name is null");
            return null;
        }
         InputStream inputStream = getInputStreamFromResource(mResDirName, str);

        if (inputStream == null) {
            LogUtil.e(TAG, "this name is error :" + str);
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        try {
            inputStream.close();
            return bitmap;
        } catch (IOException e) {
            LogUtil.e(TAG, "resolveBitmapByName(), inputStream close exception");
            return bitmap;
        }
    }

    /**
     * 根据文件资源路径、名称 获取对应的流
     * @param mResDirName 文件资源路径
     * @param str 文件名称
     * @return 流对象
     */
    private InputStream getInputStreamFromResource(String mResDirName, String str) {
        if (TextUtils.isEmpty(str)) {
            LogUtil.e(TAG, "filename is null");
        } else {
            if (!TextUtils.isEmpty(mResDirName)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(mResDirName);
                stringBuilder.append(File.separator);
                stringBuilder.append(str);
                str = stringBuilder.toString();
            }
            if (mIsAssets) {
                try {
                    AssetManager assetManager = mContext.getAssets();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(mAssetPath);
                    stringBuilder.append(File.separator);
                    stringBuilder.append(str);
                    return assetManager.open(stringBuilder.toString());
                } catch (IOException e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getInputStreamFromResource()Assets IOException:");
                    stringBuilder.append(str);
                    stringBuilder.append(" in ");
                    stringBuilder.append(mAssetPath);
                    LogUtil.e(TAG, stringBuilder.toString());
                }
            } else {
                try {
                    return (new ZipFile(mAssetPath)).getInputStream(new ZipEntry(str));
                } catch (IOException e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getInputStreamFromResource() data IOException: ");
                    stringBuilder.append(str);
                    stringBuilder.append(" in ");
                    stringBuilder.append(mAssetPath);
                    LogUtil.e(TAG, stringBuilder.toString());
                }
            }
        }

        return null;
    }

    public String getAssetPath() {
        return this.mAssetPath;
    }

    /**
     * 通过文件名获取 Drawable对象
     * @param drawableName 文件名
     * @return Drawable对象
     */
    public Drawable resolveDrawableByName(String drawableName) {
        if (!TextUtils.isEmpty(drawableName)) {
           InputStream inputStream = getInputStreamFromResource(mResDirName, drawableName);
            if (inputStream == null) {
                LogUtil.e(TAG, "resolveDrawableByName(), inputStream is null of name:  ");
                return null;
            }
            Drawable drawable = Drawable.createFromResourceStream(mContext.getResources(), null, inputStream, drawableName);
            try {
                inputStream.close();
            } catch (IOException e) {
                LogUtil.e(TAG, "resolveDrawableByName(), inputStream close exception");
            }
            return drawable;
        }
        LogUtil.e(TAG, "resolveDrawableByName(), name is null");
        return null;
    }

    Typeface resolveTypefaceByName(String typeFaceName) {
        if (TextUtils.isEmpty(typeFaceName)) {
            LogUtil.e("ResourceResolver", "resolveTypefaceByName(), name is null");
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(mContext.getCacheDir());
        stringBuilder.append(File.separator);
        stringBuilder.append("font_cache");
        File file = new File(stringBuilder.toString());
        if (!file.exists() && !file.mkdirs()) {
            LogUtil.e("ResourceResolver", "resolveTypefaceByName(), fail to create cache dir");
            return null;
        }
        file = new File(file, typeFaceName);
        if (!file.exists()) {
            InputStream inputStream = getInputStreamFromFontResource("fonts", typeFaceName);
            if (inputStream == null){
                return null;
            }
            /*if (!FileUtil.writeToFile(inputStream, file)){
                return null;
            }*/
        }
        return Typeface.createFromFile(file);
    }

    private InputStream getInputStreamFromFontResource(String fonts, String typeFaceName) {
        InputStream stream = null;
        if (TextUtils.isEmpty(typeFaceName)) {
            LogUtil.e("ResourceResolver", "getInputStreamFromFontResource filename is null");
            return null;
        }
        if (!TextUtils.isEmpty(fonts)) {
            StringBuilder typeFaceBuilder = new StringBuilder();
            typeFaceBuilder.append(fonts);
            typeFaceBuilder.append(File.separator);
            typeFaceBuilder.append(typeFaceName);
            typeFaceName = typeFaceBuilder.toString();
        }
        InputStream typeFaceStream;
        try {
            typeFaceStream = mContext.getAssets().open(typeFaceName);
            return typeFaceStream;
        } catch (IOException e) {
            StringBuilder builder = new StringBuilder();
            builder.append("getInputStreamFromFontResource() Assets IOException:");
            builder.append(typeFaceName);
            builder.append(" in ");
            builder.append(this.mAssetPath);
            LogUtil.e("ResourceResolver", builder.toString());
            typeFaceStream = stream;
            try {
                stream = (new ZipFile(this.mAssetPath)).getInputStream(new ZipEntry(typeFaceName));
                StringBuilder assetPathBuilder = new StringBuilder();
                assetPathBuilder.append("getInputStreamFromFontResource from data file: ");
                assetPathBuilder.append(this.mAssetPath);
                LogUtil.i("ResourceResolver", assetPathBuilder.toString());
                return stream;
            } catch (IOException exception) {
                StringBuilder assetPathBuilder = new StringBuilder();
                assetPathBuilder.append("getInputStreamFromFontResource() data IOException: ");
                assetPathBuilder.append(typeFaceName);
                assetPathBuilder.append(" in ");
                assetPathBuilder.append(this.mAssetPath);
                LogUtil.e("ResourceResolver", exception.toString());
                return typeFaceStream;
            }
        }
    }

    public Providers parserConfigFile() {
        return new Providers();
    }
}
