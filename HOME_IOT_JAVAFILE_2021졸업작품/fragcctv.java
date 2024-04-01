package com.example.jongseong3;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class fragcctv extends Fragment {
    private View view;

    //  TCP연결 관련
    private Socket clientSocket;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    private int port = 9999;
    private final String ip = "192.168.10.106";
    private MyHandler myHandler;
    private MyThread myThread;

    private WebView mWebView; // 웹뷰 선언

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cctv, container, false);
        Button morebtn = (Button) view.findViewById(R.id.button112);
        Button button2 = (Button) view.findViewById(R.id.tgbutton1);
        mWebView = (WebView)view.findViewById(R.id.webview1);
        mWebView.setPadding(0,0,0,0);
        mWebView.setInitialScale(160);
        String url ="http://192.168.10.106:8091/stream_simple.html";
        mWebView.loadUrl(url);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            clientSocket = new Socket(ip, port);
            socketIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            socketOut = new PrintWriter(clientSocket.getOutputStream(), true);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("***화면 : ","cctv 화면 연결");
        myHandler = new MyHandler();
        myThread = new MyThread();
        myThread.start();

        socketOut.println("화면 상태 : cctv");

        morebtn.setOnClickListener(new View.OnClickListener() {


            @Override

            public void onClick(View v) {
                // TODO Auto-generated method stub
                container.buildDrawingCache();
                //신고하기 버튼 클릭시 다이얼 112 호출
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:112"));
                startActivity(intent);
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
                    Log.d("***","data저장성공");
                    // Message 객체를 생성, 핸들러에 정보를 보낼 땐 이 메세지 객체를 이용
                    Message msg = myHandler.obtainMessage();
                    msg.obj = data;
                    myHandler.sendMessage(msg);
                }
                catch (Exception e) {
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

}
