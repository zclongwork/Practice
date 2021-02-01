package com.zcl.practice.plugin;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.zcl.practice.BaseActivity;
import com.zcl.practice.R;

import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 插件demo
 */
public class PluginDemoActivity extends BaseActivity {
    private static final String TAG = "PluginDemo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin_demo);
        findViewById(R.id.plugin_dex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runPluginMethod();
            }
        });

        findViewById(R.id.plugin_apk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runPluginMethod2();
            }
        });

        findViewById(R.id.plugin_apk_act).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.zcl.plugin", "com.zcl.plugin.MainActivity"));
                startActivity(intent);
            }
        });
    }

    //生成dex 通过sdk build tools dx --dex --output=output.dex input.class
    //cd /Users/zcl/code/Practice/JavaDemo/build/classes/java/main/
    //  dx --dex --output=test.dex com/zcl/plugin/Demo.class
    private String dexPath = "/sdcard/test.dex";

    //PathClassLoader和DexClassLoader都可以加载
    private void runPluginMethod() {
        DexClassLoader classLoader = new DexClassLoader(dexPath, this.getCacheDir().getAbsolutePath()
        , null, PluginDemoActivity.this.getClassLoader());

//        PathClassLoader classLoader = new PathClassLoader(dexPath, null);

        try {
            Class<?> clazz = classLoader.loadClass("com.zcl.plugin.Demo");
            Method method = clazz.getMethod("info");
            Object object = method.invoke(null);
            Toast.makeText(this, object.toString(), Toast.LENGTH_LONG).show();
            Log.d(TAG, object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void runPluginMethod2() {

        try {
            Class<?> clazz = getClassLoader().loadClass("com.zcl.plugin.Test");
            Method method = clazz.getMethod("info");
            Object object = method.invoke(null);
            Toast.makeText(this, object.toString(), Toast.LENGTH_LONG).show();
            Log.d(TAG, "apk:" + object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
