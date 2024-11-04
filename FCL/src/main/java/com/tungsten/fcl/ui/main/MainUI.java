package com.tungsten.fcl.ui.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.tungsten.fcl.FCLApplication;
import com.tungsten.fcl.R;
import com.tungsten.fcl.game.TexturesLoader;
import com.tungsten.fcl.setting.Accounts;
import com.tungsten.fcl.util.AndroidUtils;
import com.tungsten.fclcore.auth.Account;
import com.tungsten.fclcore.fakefx.beans.property.ObjectProperty;
import com.tungsten.fclcore.fakefx.beans.property.SimpleObjectProperty;
import com.tungsten.fclcore.task.Schedulers;
import com.tungsten.fclcore.task.Task;
import com.tungsten.fclcore.util.Logging;
import com.tungsten.fclcore.util.io.HttpRequest;
import com.tungsten.fclcore.util.io.NetworkUtils;
import com.tungsten.fcllibrary.component.dialog.FCLAlertDialog;
import com.tungsten.fcllibrary.component.theme.ThemeEngine;
import com.tungsten.fcllibrary.component.ui.FCLCommonUI;
import com.tungsten.fcllibrary.component.view.FCLButton;
import com.tungsten.fcllibrary.component.view.FCLTextView;
import com.tungsten.fcllibrary.component.view.FCLUILayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class MainUI extends FCLCommonUI implements View.OnClickListener {

    public static final String ANNOUNCEMENT_URL = FCLApplication.appConfig.getProperty("announcement-url","https://raw.githubusercontent.com/hyplant-team/FoldCraftLauncher/doc/announcement/latest.json");

    private LinearLayoutCompat announcementContainer;
    private LinearLayoutCompat announcementLayout;
    private FCLTextView title;
    private FCLTextView announcementView;
    private FCLTextView date;
    private FCLButton hide;
    private Announcement announcement = null;

    private ObjectProperty<Account> currentAccount;

    public MainUI(Context context, FCLUILayout parent, int id) {
        super(context, parent, id);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        announcementContainer = findViewById(R.id.announcement_container);
        announcementLayout = findViewById(R.id.announcement_layout);
        title = findViewById(R.id.title);
        announcementView = findViewById(R.id.announcement);
        date = findViewById(R.id.date);
        hide = findViewById(R.id.hide);
        ThemeEngine.getInstance().registerEvent(announcementLayout, () -> announcementLayout.getBackground().setTint(ThemeEngine.getInstance().getTheme().getColor()));
        hide.setOnClickListener(this);

        checkAnnouncement();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public Task<?> refresh(Object... param) {
        return Task.runAsync(() -> {

        });
    }

    private void checkAnnouncement() {
        announcementContainer.setVisibility(View.INVISIBLE);
        String remoteData = null;
        Announcement announcementData = null;
        if(FCLApplication.appConfig.getProperty("enable-announcement","true").equals("true")){
            title.setText(getContext().getString(R.string.announcement));
            announcementView.setText(getContext().getString(R.string.announcement_loading));
            date.setText(new String(ANNOUNCEMENT_URL));
            announcementContainer.setVisibility(View.VISIBLE);
            CompletableFuture<Announcement> future = CompletableFuture.supplyAsync(() -> {
                try {
                    remoteData = NetworkUtils.doGet(NetworkUtils.toURL(ANNOUNCEMENT_URL), FCLApplication.deviceInfoUtils.toString());
                }catch (Exception e) {
                    e.printStackTrace();
                    return new Announcement(
                        -1, true, false, -1, -1, new ArrayList<>(),
                        new ArrayList<>(Collections.singletonList(new Announcement.Content(null, getContext().getString(R.string.announcement_error_network)))),
                        new String(ANNOUNCEMENT_URL),
                        new ArrayList<>(Collections.singletonList(new Announcement.Content(null, getContext().getString(R.string.announcement_error_network_content) + "\n" + ANNOUNCEMENT_URL)))
                    );
                }
                try {
                    announcementData = new Gson().fromJson(remoteData, Announcement.class);
                }catch (Exception e) {
                    e.printStackTrace();
                    return new Announcement(
                        -1, true, false, -1, -1, new ArrayList<>(),
                        new ArrayList<>(Collections.singletonList(new Announcement.Content(null, getContext().getString(R.string.announcement_error_format)))),
                        new String(ANNOUNCEMENT_URL),
                        new ArrayList<>(Collections.singletonList(new Announcement.Content(null, getContext().getString(R.string.announcement_error_format_content) + "\n" + remoteData)))
                }
                return announcementData;
            });
            future.thenAccept(announcement -> new Handler(Looper.getMainLooper()).post(() -> {
                this.announcement = announcement;
                try {
                    if (!announcement.shouldDisplay(getContext())) {
                        announcementContainer.setVisibility(View.INVISIBLE);
                        return;
                    }
                    title.setText(this.announcement.getDisplayTitle(getContext()));
                    announcementView.setText(this.announcement.getDisplayContent(getContext()));
                    date.setText(this.announcement.getDate());
                }catch(Exception e) {
                    title.setText(getContext().getString(R.string.announcement_error_data));
                    announcementView.setText(ANNOUNCEMENT_URL);
                    date.setText(getContext().getString(R.string.announcement_error_data_content) + "\n" + remoteData);
                }
            }));
        }
    }

    private void hideAnnouncement() {
        announcementContainer.setVisibility(View.GONE);
        if (announcement != null) {
            announcement.hide(getContext());
        }
    }

    @Override
    public void onBackPressed() {
        checkAnnouncement();
        FCLAlertDialog.Builder builder = new FCLAlertDialog.Builder(getContext());
        builder.setAlertLevel(FCLAlertDialog.AlertLevel.INFO);
        builder.setCancelable(true);
        builder.setMessage(getContext().getString(R.string.menu_settings_force_exit_msg));
        builder.setPositiveButton(getContext().getString(com.tungsten.fcllibrary.R.string.dialog_negative), null);
        builder.setNegativeButton(getContext().getString(com.tungsten.fcllibrary.R.string.dialog_positive), () -> {
            getActivity().finish();
            System.exit(0);
        });
        builder.create().show();
    }

    @Override
    public void onClick(View view) {
        if (view == hide) {
            if (announcement != null && announcement.isSignificant()) {
                FCLAlertDialog.Builder builder = new FCLAlertDialog.Builder(getContext());
                builder.setAlertLevel(FCLAlertDialog.AlertLevel.ALERT);
                builder.setCancelable(true);
                builder.setMessage(getContext().getString(R.string.announcement_significant));
                builder.setPositiveButton(null, null);
                builder.setNegativeButton(getContext().getString(com.tungsten.fcllibrary.R.string.dialog_positive), null);
                builder.create().show();
            } else {
                hideAnnouncement();
            }
        }
    }
}
