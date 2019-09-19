package com.example.myapplication.Reciever;

import android.app.Service;
import android.content.Intent;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.utils.CommonMethods;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by VS00481543 on 30-10-2017.
 */

public class RecorderService extends Service {

    MediaRecorder recorder;
    AudioRecord audioRecord;
    static final String TAGS=" Inside Service";
    public static String rec;
    //public static String mFileName;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent,int flags,int startId)
    {
        recorder = new MediaRecorder();
        recorder.reset();

        String phoneNumber=intent.getStringExtra("number");
        Log.d(TAGS, "Phone number in service: "+phoneNumber);
        String time=new CommonMethods().getTIme();
        String path=new CommonMethods().getPath();
        rec=path+"/"+phoneNumber+"_"+time+".mp4";






       /* recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);*/     //not working in moto

/*
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);  */ // not working in moto




        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_UPLINK+MediaRecorder.AudioSource.VOICE_DOWNLINK);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setAudioChannels(1);
        recorder.setAudioSamplingRate(44100);
        recorder.setAudioEncodingBitRate(192000);     // NOT WORKINNG mi,moto,samsung.,lenovo






        // this code run vivo on chirag sir phone and blu phone and my oppo

        //jyoti moto one side voice is coming and same with samsung device



        recorder.setOutputFile(rec);
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        recorder.start();

        Log.d(TAGS, "onStartCommand: "+"Recording started");

        return START_NOT_STICKY;
    }

    public void onDestroy()
    {
        super.onDestroy();
        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder=null;
        Log.d(TAGS, "onDestroy: "+"Recording stopped");

    }
}