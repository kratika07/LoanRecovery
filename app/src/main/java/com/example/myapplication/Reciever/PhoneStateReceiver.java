package com.example.myapplication.Reciever;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.example.myapplication.Activities.DashBoardFieldExecutive;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.ModuleAgents.Activity.DashBoardAgent;

import java.io.File;

import static com.example.myapplication.ModuleAgents.Activity.NewTaskAgent.funAgent;
import static com.example.myapplication.ModuleTelecaller.Activity.NewTaskTelecaller.funTelecaller;


public class PhoneStateReceiver extends BroadcastReceiver {
    static final String TAG="State";
    static final String TAG1=" Inside State";
    public static String phoneNumber;
    public static String name;
    public static String mRoleId;
    public static String dirfilepath;

    @Override
    public void onReceive(Context context, Intent intent) {



        phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            try {
                Bundle extras = intent.getExtras();
                String state = extras.getString(TelephonyManager.EXTRA_STATE);
                if (extras != null) {
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

                        Log.d(TAG1, "Inside " + state);
                        Log.e("check number",phoneNumber);

                    }

                    else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK ) )
                         {
                             /*Intent reivToServ = new Intent(context, RecorderService.class);
                             reivToServ.putExtra("number", phoneNumber);
                             context.startService(reivToServ);*/

                         }


                    else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {

                        String number= PreferenceManager.getCUSTOMERNUMBER(context);

                        String deviceMan = android.os.Build.MANUFACTURER;


                        Log.e("check", deviceMan);


                        if (deviceMan.equals("Xiaomi"))
                        {
                            try {

                                File main = new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));

                                // end of SD card ch
                                File[] dirFiles = main.listFiles();
                                if (dirFiles.length != 0) {
                                    for (int ii = 0; ii < dirFiles.length; ii++) {
                                        //  dirFiles[ii].delete();


                                        if (dirFiles[ii].getName().toString().contains("MIUI")) {

                                            Log.e("<><><>record", dirFiles[ii].getName().toString().contains("MIUI") + "");

                                            Log.e("check file name1", dirFiles[ii].getName());

                                            File file = new File(Environment.getExternalStorageDirectory() + "/" + dirFiles[ii].getName());

                                            File[] direcfile = file.listFiles();

                                            for (int jj = 0; jj < direcfile.length; jj++) {

                                                Log.e("check file", direcfile[jj].getName());

                                                if (direcfile[jj].getName().toString().contains("sound_recorder")) {

                                                    Log.e("<><><>sound record", direcfile[jj].getName().toString().contains("sound_recorder") + "");

                                                    Log.e("check file name2", direcfile[jj].getName());

                                                    File mainJ = new File(Environment.getExternalStorageDirectory() + "/" + dirFiles[ii].getName() + "/" + direcfile[jj].getName() );

                                                    File[] dirFilesmainJ = mainJ.listFiles();


                                                    for (int jjj = 0; jjj < dirFilesmainJ.length; jjj++) {

                                                        Log.e("all directory file", dirFilesmainJ[jjj].getName());

                                                        if (dirFilesmainJ[jjj].getName().toString().contains("call_rec")) {

                                                            Log.e("<><><>sound record2", dirFilesmainJ[jjj].getName().toString().contains("call_rec") + "");

                                                            Log.e("check file name3", dirFilesmainJ[jjj].getName());

                                                            File main2 = new File(Environment.getExternalStorageDirectory() + "/" + dirFiles[ii].getName() + "/" + direcfile[jj].getName() + "/" +dirFilesmainJ[jjj].getName() );

                                                            File[] dirFilesmainJ1 = main2.listFiles();


                                                            for (int k = 0; k < dirFilesmainJ1.length; k++) {


                                                                Log.e("k  directory file", dirFilesmainJ1[k].getName());


                                                                if (dirFilesmainJ1[k].getName().contains(number))

                                                                {
                                                                    dirfilepath=dirFilesmainJ1[k].getAbsolutePath();

                                                                    mRoleId= com.example.myapplication.Helper.PreferenceManager.getLoanUserroleId(context);
                                                                    if (mRoleId.equals("3"))
                                                                    {
                                                                        funTelecaller();
                                                                    }
                                                                    else if (mRoleId.equals("6"))
                                                                    {
                                                                        // funTelecaller();
                                                                    }
                                                                    else if (mRoleId.equals("7"))
                                                                    {
                                                                        funAgent();

                                                                    }

                                                                    Log.e("path",dirfilepath);

                                                                }
                                                    } }

                                                    }

                                                    }
                                            }

                                        }
                                        Log.e("check file", dirFiles[ii].toString());

                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("<><><><><", e.toString());
                            }


                        }
                        else if (deviceMan.equals("OPPO"))
                        {
                        try {

                            File main=new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));

                            // end of SD card ch
                            File[] dirFiles = main.listFiles();
                            if (dirFiles.length != 0) {
                                for (int ii = 0; ii < dirFiles.length; ii++) {

                                    if (dirFiles[ii].getName().toLowerCase().contains("record")) {

                                        Log.e("<><><>record",dirFiles[ii].getName().toLowerCase().contains("record")+"");

                                        Log.e("check file name",dirFiles[ii].getName());



                                        File file = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName());

                                        if (file.isDirectory()) {
                                            Log.e("directory", dirFiles[ii].getName());

                                            File main2 = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName());

                                            File[] dirFiles2 = main2.listFiles();


                                            for (int j=0;j<dirFiles2.length;j++)
                                            {

                                                Log.e("all directory file",dirFiles2[j].getName());



                                                if (dirFiles2[j].isDirectory()) {

                                                    Log.e("directory2",dirFiles2[j].getName());


                                                    File main3 = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName()+"/"+ dirFiles2[j].getName());

                                                    File[] dirFiles3 = main3.listFiles();

                                                    for (int k=0;k<dirFiles3.length;k++) {

                                                        Log.e("directory3",dirFiles3[k].getName());



                                                        if (dirFiles3[k].getName().contains(number))

                                                        {
                                                             dirfilepath=dirFiles3[k].getAbsolutePath();


                                                            mRoleId= com.example.myapplication.Helper.PreferenceManager.getLoanUserroleId(context);
                                                            if (mRoleId.equals("3"))
                                                            {
                                                                funTelecaller();
                                                            }
                                                            else if (mRoleId.equals("6"))
                                                            {
                                                                // funTelecaller();
                                                            }
                                                            else if (mRoleId.equals("7"))
                                                            {
                                                                funAgent();

                                                            }


                                                            Log.e("path",dirfilepath);

                                                        }

                                                    }
                                                }

                                            }

                                        }

                                    }
                                    Log.e("check file",dirFiles[ii].toString());

                                }}

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("<><><><><",e.toString());
                        }
                        }

                        else if (deviceMan.equals("samsung"))
                        {
                            try {
                              //  Toast.makeText(context, "call"+number, Toast.LENGTH_SHORT).show();

                                Log.e("enter samsung","enter");

                                File main=new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));

                                // end of SD card ch
                                File[] dirFiles = main.listFiles();
                                if (dirFiles.length != 0) {
                                    for (int ii = 0; ii < dirFiles.length; ii++) {
                                        //  dirFiles[ii].delete();
                                        Log.e("enter samsung",dirFiles[ii].getName());

                                        if (dirFiles[ii].getName().toString().contains("Call")) {

                                            Log.e("<><><>record",dirFiles[ii].getName().toString().contains("Call")+"");

                                            Log.e("check file name",dirFiles[ii].getName());

                                            File file = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName());

                                            if (file.isDirectory()) {
                                                Log.e("directory", dirFiles[ii].getName());

                                                File main2 = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName());

                                                File[] dirFiles2 = main2.listFiles();


                                                for (int j=0;j<dirFiles2.length;j++)
                                                {

                                                    Log.e("all directory file",dirFiles2[j].getName());

                                                    Log.e("direc checkdi",dirFiles2[j].getName().contains(number)+"");


                                                   if (dirFiles2[j].getName().contains(number))

                                                    {
                                                        dirfilepath=dirFiles2[j].getAbsolutePath();

                                                       Log.e("dirfilepath check",dirFiles2[j].getName().contains(number)+"");



                                                     mRoleId= com.example.myapplication.Helper.PreferenceManager.getLoanUserroleId(context);

                                                        if (mRoleId.equals("3"))
                                                        {
                                                            funTelecaller();
                                                        }
                                                        else if (mRoleId.equals("6"))
                                                        {
                                                            // funTelecaller();
                                                        }
                                                        else if (mRoleId.equals("7"))
                                                        {
                                                            funAgent();

                                                        }

                                                    }
                                                }

                                            }

                                        }
                                        Log.e("check file",dirFiles[ii].toString());

                                    }}

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("<><><><><",e.toString());
                            }

                        }
                        else if (deviceMan.equals("vivo")) {
                            try {
                                File main=new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));
                                File[] dirFiles = main.listFiles();
                                if (dirFiles.length != 0) {
                                    for (int ii = 0; ii < dirFiles.length; ii++) {
                                        //  dirFiles[ii].delete();


                                        if (dirFiles[ii].getName().toString().contains("Record")) {

                                            Log.e("<><><>record",dirFiles[ii].getName().toString().contains("Record")+"");

                                            Log.e("check file name",dirFiles[ii].getName());



                                            File file = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName());

                                            if (file.isDirectory()) {
                                                Log.e("directory", dirFiles[ii].getName());

                                                File main2 = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName());

                                                File[] dirFiles2 = main2.listFiles();


                                                for (int j=0;j<dirFiles2.length;j++)
                                                {

                                                    Log.e("all directory file",dirFiles2[j].getName());



                                                    if (dirFiles2[j].isDirectory()) {

                                                        Log.e("directory2",dirFiles2[j].getName());


                                                        File main3 = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName()+"/"+ dirFiles2[j].getName());

                                                        File[] dirFiles3 = main3.listFiles();

                                                        for (int k=0;k<dirFiles3.length;k++) {

                                                            Log.e("directory3",dirFiles3[k].getName());


                                                            if (dirFiles3[k].getName().contains(number))

                                                            {
                                                                dirfilepath=dirFiles3[k].getAbsolutePath();


                                                                mRoleId= com.example.myapplication.Helper.PreferenceManager.getLoanUserroleId(context);
                                                                if (mRoleId.equals("3"))
                                                                {
                                                                    funTelecaller();
                                                                }
                                                                else if (mRoleId.equals("6"))
                                                                {
                                                                    // funTelecaller();
                                                                }
                                                                else if (mRoleId.equals("7"))
                                                                {
                                                                    funAgent();

                                                                }

                                                             //    dirFiles3[k].delete();



                                                                Log.e("path",dirfilepath);

                                                            }

                                                        }
                                                    }

                                                }

                                            }

                                        }
                                        Log.e("check file",dirFiles[ii].toString());

                                    }}

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("<><><><><",e.toString());



                        }}
                        else if (deviceMan.equals("motorola")) {



                        }

                        else
                        {

                           /* try {

                                File main=new File(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath()));

                                // end of SD card ch
                                File[] dirFiles = main.listFiles();
                                if (dirFiles.length != 0) {
                                    for (int ii = 0; ii < dirFiles.length; ii++) {
                                        //  dirFiles[ii].delete();


                                        if (dirFiles[ii].getName().toLowerCase().contains("record")) {

                                            Log.e("<><><>record",dirFiles[ii].getName().toLowerCase().contains("record")+"");

                                            Log.e("check file name",dirFiles[ii].getName());



                                            File file = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName());

                                            if (file.isDirectory()) {
                                                Log.e("directory", dirFiles[ii].getName());

                                                File main2 = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName());

                                                File[] dirFiles2 = main2.listFiles();


                                                for (int j=0;j<dirFiles2.length;j++)
                                                {

                                                    Log.e("all directory file",dirFiles2[j].getName());



                                                    if (dirFiles2[j].isDirectory()) {

                                                        Log.e("directory2",dirFiles2[j].getName());


                                                        File main3 = new File(Environment.getExternalStorageDirectory() +"/"+ dirFiles[ii].getName()+"/"+ dirFiles2[j].getName());

                                                        File[] dirFiles3 = main3.listFiles();

                                                        for (int k=0;k<dirFiles3.length;k++) {

                                                            Log.e("directory3",dirFiles3[k].getName());


                                                            if (dirFiles3[k].getName().contains(phoneNumber))

                                                            {
                                                                dirfilepath=dirFiles3[k].getAbsolutePath();


                                                                mRoleId= com.example.myapplication.Helper.PreferenceManager.getLoanUserroleId(context);
                                                                if (mRoleId.equals("3"))
                                                                {
                                                                    funTelecaller();
                                                                }
                                                                else if (mRoleId.equals("6"))
                                                                {
                                                                    // funTelecaller();
                                                                }
                                                                else if (mRoleId.equals("7"))
                                                                {
                                                                    funAgent();

                                                                }


                                                                Log.e("path",dirfilepath);

                                                            }

                                                        }
                                                    }

                                                }

                                            }

                                        }
                                        Log.e("check file",dirFiles[ii].toString());

                                    }}

                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.e("<><><><><",e.toString());
                            }
*/

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}






