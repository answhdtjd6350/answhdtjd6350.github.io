package com.example.jongseong3;

import android.app.Activity;
import android.content.Context;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragoption extends Fragment {
    private View view;

    Switch switch_push;

    int push;

    final int[] push_state = new int[1];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_option, container,false);

        Switch switch_push = (Switch) view.findViewById(R.id.switch_push);

        final SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        switch_push.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putBoolean("key", true).apply();
                    Toast.makeText(getContext(), "알림 활성화", Toast.LENGTH_SHORT).show();
                } else {
                    PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putBoolean("key", false).apply();
                    Toast.makeText(getContext(), "알림 비활성화", Toast.LENGTH_SHORT).show();
                }

            }
        });

        boolean isSubscribed = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext())
                .getBoolean("key",false);
        if(isSubscribed)

            FirebaseMessaging.getInstance().unsubscribeFromTopic("news");




        push_state[0] = pref.getInt("outingmode_state", 0);
        if (push_state[0] == 0) {
            switch_push.setChecked(false);
            push = 0;
        } else {
            switch_push.setChecked(true);
            push = 1;
        }
        switch_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                push = 1 - push;
                if (push == 0) {
                    switch_push.setChecked(false);
                    editor.putInt("outingmode_state", 0);
                    editor.commit();

                } else {
                    switch_push.setChecked(true);
                    editor.putInt("outingmode_state", 1);
                    editor.commit();

                }
            }
        });

        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.commit();
    }
}
