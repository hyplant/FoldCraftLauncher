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

    private FCLLinearLayout discord;
    private FCLLinearLayout qq;

    public CommunityPage(Context context, int id, FCLUILayout parent, int resId) {
        super(context, id, parent, resId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        discord = findViewById(R.id.discord);
        qq = findViewById(R.id.qq);
        discord.setOnClickListener(this);
        qq.setOnClickListener(this);
    }

    @Override
    public Task<?> refresh(Object... param) {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (v == discord) {
            Uri uri = Uri.parse(FCLApplication.appConfig.getProperty("discord","https://icraft.asia:90/titles/Discord"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(intent);
        }
        if (v == qq) {
            joinQQGroup(QQ_GROUP_KEY);
        }
    }

    private final static String QQ_GROUP_KEY = FCLApplication.appConfig.getProperty("qq-group-key","tUFbF4hOV-O0Xykwx0VyxISWgKyf8gqe");

    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key));
        try {
            getContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
