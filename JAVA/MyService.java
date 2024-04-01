package com.example.jongseong3;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    NotificationManager Notifi_M;
    ServiceThread thread;
    fraghome fraghome = new fraghome();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notifi_M= (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread( handler );
        thread.stopForever();
        return START_STICKY;
        }

    //서비스가 종료될 때 할 작업
    @Override
    public void onDestroy() {
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.start();
    }

    public void start(){
        myServiceHandler handler = new myServiceHandler();
        thread= new ServiceThread(handler);
        thread.start();
    }

    public void stop(){
        myServiceHandler handler = new myServiceHandler();
        thread = new ServiceThread(handler);
        thread.stopForever();
    }

    public class myServiceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            //버전확인
            NotificationManager notificationManager = (NotificationManager)getSystemService( Context.NOTIFICATION_SERVICE );
            Intent intent = new Intent(MyService.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP );
            PendingIntent pendingIntent = PendingIntent.getActivity( MyService.this, 0, intent, PendingIntent.FLAG_ONE_SHOT );
            Uri soundUri = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant")
                NotificationChannel notificationChannel = new NotificationChannel( "my_notification", "n_channel", NotificationManager.IMPORTANCE_MAX );
                notificationChannel.setDescription( "description" ); notificationChannel.setName( "Channel Name" );
                assert notificationManager != null;
                notificationManager.createNotificationChannel( notificationChannel );
            }

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder( MyService.this )
                    .setSmallIcon( R.drawable.crying )
                    .setLargeIcon( BitmapFactory.decodeResource( getResources(), R.drawable.crying ) )
                    .setContentTitle( "Title" )
                    .setContentText( "ContentText" )
                    .setAutoCancel( true ) .setSound( soundUri )
                    .setContentIntent( pendingIntent )
                    .setDefaults( Notification.DEFAULT_ALL )
                    .setOnlyAlertOnce( true )
                    .setChannelId( "my_notification" )
                    .setColor( Color.parseColor( "#ffffff" ) );

            if(fraghome.fire_state==0){
                //fraghome.fire_createNotificationChannel();
                notificationManager.notify(0, notificationBuilder.build());
                thread.stopForever();
                System.out.println("MYService fire_state"+fraghome.fire_state);
            }
        }



        }
    }