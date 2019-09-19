package com.example.myapplication.Activities;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.myapplication.ModuleAgents.Activity.DashBoardAgent;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.MyJobService;
import com.example.myapplication.R;
import com.example.myapplication.ModuleTelecaller.Activity.DashBoardTelecaller;
import android.os.Handler;

import com.example.myapplication.utils.GPSTracker;
import com.google.firebase.iid.FirebaseInstanceId;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    GPSTracker gpsTracker;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);


            /*Intent serviceIntent = new Intent(this, MyJobService.class);
            serviceIntent.putExtra("inputExtra", "Foreground Service Example in Android");
            ContextCompat.startForegroundService(this, serviceIntent);*/

          /*  JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

            JobInfo jobInfo = new JobInfo.Builder(11, new ComponentName(this, MyJobService.class))
                    // only add if network access is required
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build();

            jobScheduler.schedule(jobInfo);*/



        /*    gpsTracker = new GPSTracker(SplashScreen.this);
            if (gpsTracker.canGetLocation()) {
                String currentLat = String.valueOf(gpsTracker.getLatitude());
                String currentLong = String.valueOf(gpsTracker.getLongitude());

                System.out.println("<><>##" +currentLat);
                System.out.println("<><>##" +currentLong);


            } else {
                gpsTracker.showSettingsAlert();
            }*/



      /*  String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("########: "+refreshedToken);*/




            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            System.out.println("########: "+refreshedToken);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



                if(PreferenceManager.getLOANISLOGIN(SplashScreen.this).equals("1")){

                    if(PreferenceManager.getLOANUSERROLE(SplashScreen.this).equals("Telecaller")){

                        startActivity(new Intent(SplashScreen.this, DashBoardTelecaller.class));
                        finish();

                    }

                   else if(PreferenceManager.getLOANUSERROLE(SplashScreen.this).equals("Executive")){

                        startActivity(new Intent(SplashScreen.this, DashBoardFieldExecutive.class));
                        finish();
                    }

                    else if(PreferenceManager.getLOANUSERROLE(SplashScreen.this).equals("Agent")){

                        startActivity(new Intent(SplashScreen.this, DashBoardAgent.class));
                        finish();
                    }
                }else {
                    Intent intent = new Intent(SplashScreen.this, RoleSelectScreen.class);
                    startActivity(intent);
                    finish();
                }








               /* if (SharedPrefManager.getInstance(SplashScreen.this).isLoggedIn()) {
                    String role = SharedPrefManager.getInstance(SplashScreen.this).getUser().getRole();

                    if (role.equals("Telecaller")) {
                        startActivity(new Intent(SplashScreen.this, DashBoardTelecaller.class));
                        finish();
                    }

                }

                else if (!SharedPrefManager.getInstance(SplashScreen.this).isLoggedIn())
                {

                    startActivity(new Intent(SplashScreen.this, RoleSelectScreen.class));
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreen.this, RoleSelectScreen.class);
                    startActivity(intent);
                    finish();
                }*/





            }
        }, 1000);

    }


}