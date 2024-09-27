package com.tungsten.fcl.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tungsten.fcl.FCLApplication;
import com.tungsten.fcl.R;
import com.tungsten.fcl.activity.SplashActivity;
import com.tungsten.fcl.util.AndroidUtils;
import com.tungsten.fclcore.util.io.IOUtils;
import com.tungsten.fclcore.util.io.NetworkUtils;
import com.tungsten.fcllibrary.component.FCLFragment;
import com.tungsten.fcllibrary.component.view.FCLButton;
import com.tungsten.fcllibrary.component.view.FCLProgressBar;
import com.tungsten.fcllibrary.component.view.FCLTextView;

import java.io.IOException;

public class EulaFragment extends FCLFragment implements View.OnClickListener {

    public static final String EULA_URL = FCLApplication.appConfig.getProperty("eula-url","https://raw.githubusercontent.com/hyplant/FoldCraftLauncher/doc/eula/latest.txt");

    private FCLProgressBar progressBar;
    private FCLTextView eula;

    private FCLButton next;

    private boolean load = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eula, container, false);

        progressBar = findViewById(view, R.id.progress);
        eula = findViewById(view, R.id.eula);

        next = findViewById(view, R.id.next);
        next.setOnClickListener(this);

        loadEula();

        return view;
    }

    private void loadEula() {
        new Thread(() -> {
            String str = getString(R.string.splash_eula_error);
            try {
                str = NetworkUtils.doGet(NetworkUtils.toURL(EULA_URL),FCLApplication.deviceInfoUtils.toString());
                load = true;
            } catch (IOException | IllegalArgumentException e) {
                e.printStackTrace();
            }
            if (!load) {
                try {
                    str = IOUtils.readFullyAsString(requireActivity().getAssets().open( "eula.txt"));
                    load = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            final String s = str;
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    eula.setText(s);
                });
            }
        }).start();
    }

    @Override
    public void onClick(View view) {
        if (view == next) {
            if (getActivity() != null) {
                if (load) {
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("launcher", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("is_first_launch", false);
                    editor.apply();
                }
                ((SplashActivity) getActivity()).start();
            }
        }
    }
}
