package com.tungsten.fcl.ui.download;

import android.content.Context;

import com.tungsten.fcl.R;
import com.tungsten.fcl.ui.PageManager;
import com.tungsten.fcl.ui.UIListener;
import com.tungsten.fcllibrary.component.ui.FCLCommonPage;
import com.tungsten.fcllibrary.component.view.FCLUILayout;

import java.util.ArrayList;

public class DownloadPageManager extends PageManager {

    public static final int PAGE_ID_DOWNLOAD_GAME = 15010;
    public static final int PAGE_ID_DOWNLOAD_MODPACK = 15011;
    public static final int PAGE_ID_DOWNLOAD_MOD = 15012;
    public static final int PAGE_ID_DOWNLOAD_RESOURCE_PACK = 15013;
    public static final int PAGE_ID_DOWNLOAD_WORLD = 15014;

    private static DownloadPageManager instance;

    private InstallVersionPage installVersionPage;
    private DownloadPage downloadModpackPage;
    private DownloadPage downloadModPage;
    private DownloadPage downloadResourcePackPage;
    private DownloadPage downloadWorldPage;

    public static DownloadPageManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DownloadPageManager not initialized!");
        }
        return instance;
    }

    public DownloadPageManager(Context context, FCLUILayout parent, int defaultPageId, UIListener listener) {
        super(context, parent, defaultPageId, listener);
        instance = this;
    }

    @Override
    public void init(UIListener listener) {
        installVersionPage = new InstallVersionPage(getContext(), PAGE_ID_DOWNLOAD_GAME, getParent(), R.layout.page_install_version);
        downloadModpackPage = new DownloadPage(getContext(), PAGE_ID_DOWNLOAD_MODPACK, getParent(), R.layout.page_download);
        downloadModPage = new DownloadPage(getContext(), PAGE_ID_DOWNLOAD_MOD, getParent(), R.layout.page_download);
        downloadResourcePackPage = new DownloadPage(getContext(), PAGE_ID_DOWNLOAD_RESOURCE_PACK, getParent(), R.layout.page_download);
        downloadWorldPage = new DownloadPage(getContext(), PAGE_ID_DOWNLOAD_WORLD, getParent(), R.layout.page_download);

        if (listener != null) {
            listener.onLoad();
        }
    }

    @Override
    public ArrayList<FCLCommonPage> getAllPages() {
        ArrayList<FCLCommonPage> pages = new ArrayList<>();
        pages.add(installVersionPage);
        pages.add(downloadModpackPage);
        pages.add(downloadModPage);
        pages.add(downloadResourcePackPage);
        pages.add(downloadWorldPage);
        return pages;
    }

}
