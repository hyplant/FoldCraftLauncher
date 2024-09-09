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

public class CommunityPage extends FCLCommonPage implements View.OnClickListener {

    private FCLLinearLayout community_a;
    private FCLLinearLayout community_b;
    private FCLLinearLayout community_c;

    public CommunityPage(Context context, int id, FCLUILayout parent, int resId) {
        super(context, id, parent, resId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        community_a = findViewById(R.id.community_a);
        community_b = findViewById(R.id.community_b);
        community_c = findViewById(R.id.community_c);
        community_a.setOnClickListener(this);
        community_b.setOnClickListener(this);
        community_c.setOnClickListener(this);
    }

    @Override
    public Task<?> refresh(Object... param) {
        return null;
    }

    @Override
    public void onClick(View v) {
        Uri uri = null;

        if (v == community_a) {
            uri = Uri.parse(FCLApplication.appConfig.getProperty("community-a","https://github.com/hyplant"));
        }
        if (v == community_b) {
            uri = Uri.parse(FCLApplication.appConfig.getProperty("community-b","https://github.com/hyplant"));
        }
        if (v == community_c) {
            uri = Uri.parse(FCLApplication.appConfig.getProperty("community-c","https://github.com/hyplant"));
        }

if (uri != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(intent);
        }
    }
}
