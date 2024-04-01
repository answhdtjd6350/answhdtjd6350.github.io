package com.example.jongseong3;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class fragonoff extends Fragment {
    private View view;

    //  TCP연결 관련
    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private int port = 9999;
    private final String ip = "192.168.10.105";
    private MyHandler myHandler;
    private MyThread myThread;

    Switch sw_outingmode,sw_porch,sw_kitchen,sw_inner,sw_toilet,sw_tv,sw_aircon,sw_airclean,sw_induction;

    int outingmode, porch, kitchen, inner, toilet, tv, aircon, airclean, induction;

    //on/off초기화
    final int[] outingmode_state = new int[1];
    final int[] porch_state = new int[1];
    final int[] kitchen_state = new int[1];
    final int[] inner_state = new int[1];
    final int[] toilet_state = new int[1];
    final int[] tv_state = new int[1];
    final int[] aircon_state = new int[1];
    final int[] airclena_state = new int[1];
    final int[] induction_state = new int[1];


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_onoff, container, false);

        // Switch관련 연동
        Switch sw_outingmode = (Switch) view.findViewById(R.id.sw_outingmode);
        Switch sw_porch = (Switch) view.findViewById(R.id.sw_porch);
        Switch sw_kitchen = (Switch) view.findViewById(R.id.sw_kitchen);
        Switch sw_inner = (Switch) view.findViewById(R.id.sw_inner);
        Switch sw_toilet = (Switch) view.findViewById(R.id.sw_toilet);
        Switch sw_tv = (Switch) view.findViewById(R.id.sw_tv);
        Switch sw_aircon = (Switch) view.findViewById(R.id.sw_aircon);
        Switch sw_airclean = (Switch) view.findViewById(R.id.sw_airclean);
        Switch sw_induction = (Switch)view.findViewById(R.id.sw_induction);

        // StrictMode는 개발자가 실수하는 것을 감지하고 해결할 수 있도록 돕는 일종의 개발 툴
        // - 메인 스레드에서 디스크 접근, 네트워크 접근 등 비효율적 작업을 하려는 것을 감지하여
        //   프로그램이 부드럽게 작동하도록 돕고 빠른 응답을 갖도록 함, 즉  Android Not Responding 방지에 도움
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            clientSocket = new Socket(ip, port);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("***화면 : ", "onoff 화면 연결");

        myHandler = new MyHandler();
        myThread = new MyThread();
        myThread.start();
        socketOut.println("화면 상태 : onoff");

        //UI상태정보 저장 테스트
        final SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        //UI 상태 저장
        outingmode_state[0] = pref.getInt("outingmode_state", 0);
        if (outingmode_state[0] == 0) {
            sw_outingmode.setChecked(false);
            outingmode = 0;
        } else {
            sw_outingmode.setChecked(true);
            outingmode = 1;
        }
        sw_outingmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outingmode = 1 - outingmode;
                if (outingmode == 0) {
                    sw_outingmode.setChecked(false);
                    editor.putInt("outingmode_state", 0);
                    editor.commit();
                    socketOut.println(999);
                } else {
                    sw_outingmode.setChecked(true);
                    editor.putInt("outingmode_state", 1);
                    editor.commit();
                    socketOut.println(111);
                }
            }
        });

        porch_state[0] = pref.getInt("porch_state", 0);
        if (porch_state[0] == 0) {
            sw_porch.setChecked(false);
            porch = 0;
        } else {
            sw_porch.setChecked(true);
            porch = 1;
        }
        sw_porch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                porch = 1 - porch;
                if (porch == 0) {
                    sw_porch.setChecked(false);
                    editor.putInt("porch_state", 0);
                    editor.commit();
                    socketOut.println(9);
                } else {
                    sw_porch.setChecked(true);
                    editor.putInt("porch_state", 1);
                    editor.commit();
                    socketOut.println(10);
                }
            }
        });

      kitchen_state[0] = pref.getInt("kitchen_state", 0);
        if (kitchen_state[0] == 0) {
            sw_kitchen.setChecked(false);
            kitchen = 0;
        } else {
            sw_kitchen.setChecked(true);
            kitchen = 1;
        }
        sw_kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kitchen = 1 - kitchen;
                if (kitchen == 0) {
                    sw_kitchen.setChecked(false);
                    editor.putInt("kitchen_state", 0);
                    editor.commit();
                    socketOut.println(15);
                } else {
                    sw_kitchen.setChecked(true);
                    editor.putInt("kitchen_state", 1);
                    editor.commit();
                    socketOut.println(16);
                }
            }
        });

        inner_state[0] = pref.getInt("inner_state", 0);
        if (inner_state[0] == 0) {
            sw_inner.setChecked(false);
            inner = 0;
        } else {
            sw_inner.setChecked(true);
            inner = 1;
        }
        sw_inner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inner = 1 - inner;
                if (inner == 0) {
                    sw_inner.setChecked(false);
                    editor.putInt("inner_state", 0);
                    editor.commit();
                    socketOut.println(11);
                } else {
                    sw_inner.setChecked(true);
                    editor.putInt("inner_state", 1);
                    editor.commit();
                    socketOut.println(12);
                }
            }
        });

        toilet_state[0] = pref.getInt("toilet_state", 0);
        if (toilet_state[0] == 0) {
            sw_toilet.setChecked(false);
            toilet = 0;
        } else {
            sw_toilet.setChecked(true);
            toilet = 1;
        }
        sw_toilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toilet = 1 - toilet;
                if (toilet == 0) {
                    sw_toilet.setChecked(false);
                    editor.putInt("toilet_state", 0);
                    editor.commit();
                    socketOut.println(13);
                } else {
                    sw_toilet.setChecked(true);
                    editor.putInt("toilet_state", 1);
                    editor.commit();
                    socketOut.println(14);
                }
            }
        });

        tv_state[0] = pref.getInt("tv_state", 0);
        if (tv_state[0] == 0) {
            sw_tv.setChecked(false);
            tv = 0;
        } else {
            sw_tv.setChecked(true);
            tv = 1;
        }
        sw_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv = 1 - tv;
                if (tv == 0) {
                    sw_tv.setChecked(false);
                    editor.putInt("tv_state", 0);
                    editor.commit();
                    socketOut.println(1);
                } else {
                    sw_tv.setChecked(true);
                    editor.putInt("tv_state", 1);
                    editor.commit();
                    socketOut.println(2);
                }
            }
        });

        aircon_state[0] = pref.getInt("aircon_state", 0);
        if (aircon_state[0] == 0) {
            sw_aircon.setChecked(false);
            aircon = 0;
        } else {
            sw_aircon.setChecked(true);
            aircon = 1;
        }
        sw_aircon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aircon = 1 - aircon;
                if (aircon == 0) {
                    sw_aircon.setChecked(false);
                    editor.putInt("aircon_state", 0);
                    editor.commit();
                    socketOut.println(3);
                } else {
                    sw_aircon.setChecked(true);
                    editor.putInt("aircon_state", 1);
                    editor.commit();
                    socketOut.println(4);
                }
            }
        });

        airclena_state[0] = pref.getInt("airclena_state", 0);
        if (airclena_state[0] == 0) {
            sw_airclean.setChecked(false);
            airclean = 0;
        } else {
            sw_airclean.setChecked(true);
            airclean = 1;
        }
        sw_airclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                airclean = 1 - airclean;
                if (airclean == 0) {
                    sw_airclean.setChecked(false);
                    editor.putInt("airclena_state", 0);
                    editor.commit();
                    socketOut.println(5);
                } else {
                    sw_airclean.setChecked(true);
                    editor.putInt("airclena_state", 1);
                    editor.commit();
                    socketOut.println(6);
                }
            }
        });

        induction_state[0] = pref.getInt("induction_state", 0);
        if (induction_state[0] == 0) {
            sw_induction.setChecked(false);
            induction = 0;
        } else {
            sw_induction.setChecked(true);
            induction = 1;
        }
        sw_induction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                induction = 1 - induction;
                if (induction == 0) {
                    sw_induction.setChecked(false);
                    editor.putInt("induction_state", 0);
                    editor.commit();
                    socketOut.println(7);
                } else {
                    sw_induction.setChecked(true);
                    editor.putInt("induction_state", 1);
                    editor.commit();
                    socketOut.println(8);
                }
            }
        });

       return view;
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    // InputStream의 값을 읽어와서 data에 저장
                    String data = socketIn.readLine();
                    Log.d("***", "data저장성공");
                    // Message 객체를 생성, 핸들러에 정보를 보낼 땐 이 메세지 객체를 이용
                    Message msg = myHandler.obtainMessage();
                    msg.obj = data;
                    myHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
        }
    }

    //어플리케이션 종료 후 UI저장
    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences pref = this.getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.commit();
    }
}
