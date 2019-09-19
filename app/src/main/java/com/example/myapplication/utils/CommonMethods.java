package com.example.myapplication.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidadvance.topsnackbar.TSnackbar;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * Created by VS00481543 on 31-10-2017.
 */

public class CommonMethods {
    public static ProgressDialog pDialog;

    final String TAGCM="Inside Service";
    Calendar cal=Calendar.getInstance();

    public static final String BASE_URL = "http://cpx.covetus.com/recovery/api/v1/";

    public static final int LOCATION_INTERVAL = 10000;
    public static final int FASTEST_LOCATION_INTERVAL = 5000;



    public String getDate()
    {
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH)+1;
        int day=cal.get(Calendar.DATE);
        String date=String.valueOf(day)+"_"+String.valueOf(month)+"_"+String.valueOf(year);

        Log.d(TAGCM, "Date "+date);
        return date;
    }


public static void toast(Context context,String msg)

{

    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();

}


    public String getTIme()
    {
        String am_pm="";
        int sec=cal.get(Calendar.SECOND);
        int min=cal.get(Calendar.MINUTE);
        int hr=cal.get(Calendar.HOUR);
        int amPm=cal.get(Calendar.AM_PM);
        if(amPm==1)
            am_pm="PM";
        else if(amPm==0)
            am_pm="AM";

        String time=String.valueOf(hr)+":"+String.valueOf(min)+":"+String.valueOf(sec)+""+am_pm;

        Log.d(TAGCM, "Date "+time);
        return time;
    }

    public String getPath()
    {
        String internalFile=getDate();
        File file=new File(Environment.getExternalStorageDirectory()+"/My Records/");
        File file1=new File(Environment.getExternalStorageDirectory()+"/My Records/"+internalFile+"/");
        if(!file.exists())
        {
            file.mkdir();
        }
        if(!file1.exists())
            file1.mkdir();

        String path=file1.getAbsolutePath();


        return path;
    }

    public String getContactName(final String number,Context context)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(number));
        String[] projection=new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        String contactName="";
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);
        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }

        if(contactName!=null && !contactName.equals(""))
            return contactName;
        else
            return "";
    }

    public static void show(Context context) {
        pDialog = new ProgressDialog(context);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
        pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog.setContentView(R.layout.progress_box);
        ImageView imgProgress = (ImageView) pDialog.findViewById(R.id.imgProgress);
        Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.rotating);
        imgProgress.startAnimation(animation1);
    }

    public static void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    public static void mShowAlert(String str, Activity activity) {

        TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), str, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(activity.getResources().getColor(R.color.colorPrimary));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        snackbar.show();

    }

    public static byte[] getFileDataFromDrawable(Context context, Uri uri) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];

            int len = 0;
            if (iStream != null) {
                while ((len = iStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

    public static boolean isOnline(Context context) {


        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }


}}
