package com.example.jongseong3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Switch Switch_home;
    Switch Switch_onoff;
    View view;

    FragmentManager fragmentManager =getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    private BottomNavigationView bottomNavigationView; //바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private fraghome fraghome;
    private fragcctv fragcctv;
    private fragdaily fragdaily;
    private fragonoff fragonoff;
    private fragoption fragoption;

    /**  TCP연결 관련
    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private int port = 9999;
    private final String ip = "192.168.10.105"; **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        /**
        try {
            clientSocket = new Socket(ip, port);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }**/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        setFrag(0);
                        break;
                    case R.id.menu_cctv:
                        setFrag(1);
                        break;
                    case R.id.menu_onoff:
                        setFrag(2);
                        break;
                    case R.id.menu_dailykit:
                        setFrag(3);
                        break;
                    case R.id.menu_option:
                        setFrag(4);
                        break;
                }
                return true;
            }
        });
        fraghome = new fraghome();
        fragcctv = new fragcctv();
        fragonoff = new fragonoff();
        fragdaily = new fragdaily();
        fragoption = new fragoption();

        setFrag(0);//첫 프래그먼트 화면 지정
    }

    // 프래그먼트 교체가 일어나는 실행문
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n) {
            case 0:
                if(!fraghome.isAdded()) {
                    ft.add(R.id.fragments_frame, fraghome);
                    ft.commit();
                }
                if(fraghome != null)fragmentManager.beginTransaction().show(fraghome).commit();
                if(fragcctv != null)fragmentManager.beginTransaction().hide(fragcctv).commit();
                if(fragonoff != null)fragmentManager.beginTransaction().hide(fragonoff).commit();
                if(fragdaily != null)fragmentManager.beginTransaction().hide(fragdaily).commit();
                if(fragoption != null)fragmentManager.beginTransaction().hide(fragoption).commit();

                break;
            case 1:
                if(!fragcctv.isAdded())
                {
                    ft.add(R.id.fragments_frame, fragcctv);
                    ft.commit();
                }

                if(fraghome != null)fragmentManager.beginTransaction().hide(fraghome).commit();
                if(fragcctv != null)fragmentManager.beginTransaction().show(fragcctv).commit();
                if(fragonoff != null)fragmentManager.beginTransaction().hide(fragonoff).commit();
                if(fragdaily != null)fragmentManager.beginTransaction().hide(fragdaily).commit();
                if(fragoption != null)fragmentManager.beginTransaction().hide(fragoption).commit();
                break;

            case 2:
                if(!fragonoff.isAdded())
                {
                    ft.add(R.id.fragments_frame,fragonoff);
                    ft.commit();
                }
                if(fraghome != null)fragmentManager.beginTransaction().hide(fraghome).commit();
                if(fragcctv != null)fragmentManager.beginTransaction().hide(fragcctv).commit();
                if(fragonoff != null)fragmentManager.beginTransaction().show(fragonoff).commit();
                if(fragdaily != null)fragmentManager.beginTransaction().hide(fragdaily).commit();
                if(fragoption != null)fragmentManager.beginTransaction().hide(fragoption).commit();
                break;
            case 3:
                if(!fragdaily.isAdded())
                {
                    ft.add(R.id.fragments_frame,fragdaily);
                    ft.commit();
                }
                if(fraghome != null)fragmentManager.beginTransaction().hide(fraghome).commit();
                if(fragcctv != null)fragmentManager.beginTransaction().hide(fragcctv).commit();
                if(fragonoff != null)fragmentManager.beginTransaction().hide(fragonoff).commit();
                if(fragdaily != null)fragmentManager.beginTransaction().show(fragdaily).commit();
                if(fragoption != null)fragmentManager.beginTransaction().hide(fragoption).commit();
                break;
            case 4:
                if (!fragoption.isAdded())
                {
                    ft.add(R.id.fragments_frame,fragoption);
                    ft.commit();
                }

                if(fraghome != null)fragmentManager.beginTransaction().hide(fraghome).commit();
                if(fragcctv != null)fragmentManager.beginTransaction().hide(fragcctv).commit();
                if(fragonoff != null)fragmentManager.beginTransaction().hide(fragonoff).commit();
                if(fragdaily != null)fragmentManager.beginTransaction().hide(fragdaily).commit();
                if(fragoption != null)fragmentManager.beginTransaction().show(fragoption).commit();

                break;

        }
    }


}

