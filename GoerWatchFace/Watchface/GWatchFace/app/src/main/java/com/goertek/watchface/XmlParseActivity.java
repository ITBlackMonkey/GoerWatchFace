package com.goertek.watchface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.goertek.commonlib.provider.model.Providers;
import com.goertek.commonlib.utils.LogUtil;

public class XmlParseActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /*  功能已测通
       List<User> users = FileParseEngine.xmlParseFile(this,"user.xml");
        Log.e("weip","users:"+users);
        */
        Providers providers = FileParseEngine.parseWatchFileWithPull(this, "watch_face_simple_config.xml");
        LogUtil.e("davin",providers.toString());
    }
}
