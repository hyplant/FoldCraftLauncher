package com.tungsten.fcl.ui.setting;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.tungsten.fcl.FCLApplication;
import com.tungsten.fcl.R;
import com.tungsten.fclcore.task.Task;
import com.tungsten.fcllibrary.component.ui.FCLCommonPage;
import com.tungsten.fcllibrary.component.view.FCLLinearLayout;
import com.tungsten.fcllibrary.component.view.FCLUILayout;

public class AboutPage extends FCLCommonPage implements View.OnClickListener {

    private FCLLinearLayout about_a;
    private FCLLinearLayout about_b;
    private FCLLinearLayout about_c;

    public AboutPage(Context context, int id, FCLUILayout parent, int resId) {
        super(context, id, parent, resId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        about_a = findViewById(R.id.about_a);
        about_b = findViewById(R.id.about_b);
        about_c = findViewById(R.id.about_c);
        about_a.setOnClickListener(this);
        about_b.setOnClickListener(this);
        about_c.setOnClickListener(this);
    }

    @Override
    public Task<?> refresh(Object... param) {
        return null;
    }

    @Override
    public void onClick(View v) {
        Uri uri = null;

        if (v == about_a) {
            uri = Uri.parse(FCLApplication.appConfig.getProperty("about-a","https://github.com/hyplant/FoldCraftLauncher"));
        }
        if (v == about_b) {
            uri = Uri.parse(FCLApplication.appConfig.getProperty("about-b","https://github.com/hyplant/FoldCraftLauncher/actions"));
        }
        if (v == about_c) {
            uri = Uri.parse(FCLApplication.appConfig.getProperty("about-c","https://github.com/hyplant/FoldCraftLauncher/issues"));
        }

        if (uri != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(intent);
        }
    }
}
