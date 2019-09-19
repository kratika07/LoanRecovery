package com.example.myapplication.ModuleAgents.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import static android.R.layout.simple_spinner_item;

import static com.example.myapplication.Reciever.PhoneStateReceiver.dirfilepath;
import static com.example.myapplication.Reciever.RecorderService.rec;
import static com.example.myapplication.utils.CommonMethods.getFileDataFromDrawable;
import static com.example.myapplication.utils.CommonMethods.hidePDialog;
import static com.example.myapplication.utils.CommonMethods.mShowAlert;
import static com.example.myapplication.utils.CommonMethods.show;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Activities.RoleSelectScreen;
import com.example.myapplication.BuildConfig;
import com.example.myapplication.Helper.AppController;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.Helper.VolleyMultipartRequest;
import com.example.myapplication.Model.UserTask;
import com.example.myapplication.R;
import com.example.myapplication.ModuleAgents.Adapter.NewTaskAdapterAgent;

import com.example.myapplication.utils.CommonMethods;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewTaskAgent extends AppCompatActivity {


    @BindView(R.id.mImgBackButton)
    ImageView mbackbutton;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    public static ArrayList<UserTask> userTasksList = new ArrayList<>();
    ArrayList<String> mTaskFeedbackContent = new ArrayList<>();
    public static HashMap<String, String> mContentId = new HashMap<String, String>();

    public static String mArrayContent[];
    public static EditText mEditExtraText;

    public static NewTaskAdapterAgent mNewTaskAdapterAgent;

    public static Activity mActivity;
    public static String mRoleId;
    public static String mAssignmentId;


    public static String mGetLatitude,mGetLongitude;

    @OnClick(R.id.mImgBackButton)
    void mFunToBack() {
        finish();
    }


    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private boolean mAlreadyStartedService = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_activty);
        ButterKnife.bind(this);


        mActivity = NewTaskAgent.this;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mNewTaskAdapterAgent = new NewTaskAdapterAgent(NewTaskAgent.this, userTasksList);
        mRecyclerView.setAdapter(mNewTaskAdapterAgent);



            if (CommonMethods.isOnline(NewTaskAgent.this)) {
                show(NewTaskAgent.this);
                userTasksList.clear();
                mGetUserTask();
                mGetFeedBackContent();
            } else if (!CommonMethods.isOnline(NewTaskAgent.this)) {
                mShowAlert("please check your internet connection", NewTaskAgent.this);
            }




/*

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {

                        mGetLatitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                        mGetLongitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);

                        if (mGetLongitude != null && mGetLatitude != null) {
                        //    Toast.makeText(NewTaskAgent.this, latitude+""+longitude, Toast.LENGTH_SHORT).show();
                       //     Log.e("checkk latlong",mGetLatitude+""+mGetLongitude);


                        }
                    }
                }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
        );
*/



        startStep1();







    }


    private void startStep1() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {

            //Passing null to indicate that it is executing for the first time.
            startStep2(null);

        } else {
            Toast.makeText(getApplicationContext(), R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }





    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        //Yes there is active internet connection. Next check Location is granted by user or not.
        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }



    private void promptInternetConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewTaskAgent.this);
        builder.setTitle(R.string.title_alert_no_intenet);
        builder.setMessage(R.string.msg_alert_no_internet);
        String positiveText = getString(R.string.btn_label_refresh);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (startStep2(dialog)) {
                            if (checkPermissions()) {
                                startStep3();
                            } else if (!checkPermissions()) {
                                requestPermissions();
                            }
                        }
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }




    private void startStep3() {
           /* Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);
            mAlreadyStartedService = true;*/
    }



    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (shouldProvideRationale || shouldProvideRationale2) {
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(NewTaskAgent.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(NewTaskAgent.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {


            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                startStep3();

            } else {

                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }


    /*@Override
    public void onDestroy() {
       *//* stopService(new Intent(this, LocationMonitoringService.class));
        mAlreadyStartedService = false;
        super.onDestroy();*//*
    }
*/





    public static void mGetUserTask() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getAgentAssignment",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><><>agent" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("status");

                            if (mStrStatus.equals("1")) {
                                JSONArray jsonArray = response.getJSONArray("response");

                                if (jsonArray.length() <= 0) {
                                    CommonMethods.mShowAlert("No record found", mActivity);
                                    return;
                                }


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String mStrId = jsonObject.getString("id");
                                    String mStrCustomerName = jsonObject.getString("customer_name");
                                    String mStrMobile = jsonObject.getString("personal_mobile");
                                    String mStrBank = jsonObject.getString("bank_name");
                                    String mStrFinancer = jsonObject.getString("financer");
                                    String mStrLoanType = jsonObject.getString("loantype");
                                    String mStrVerificationType = jsonObject.getString("verification_type");
                                    String mStrAgentName = jsonObject.getString("agent_name");
                                    String mStrExecutiveName = jsonObject.getString("executive_name");
                                    String mStrTelecallerName = jsonObject.getString("telecaller_name");
                                    String mStrLattitude = jsonObject.getString("latitude");
                                    String mStrLongitude = jsonObject.getString("longitude");
                                    String mStrAddress = jsonObject.getString("residential_address");
                                    String mStrDueEmi=jsonObject.getString("due_emi");
                                    String mStrEmiAmount=jsonObject.getString("emi_amount");

                                    UserTask userTask = new UserTask();
                                    userTask.setmStrAgentName(mStrAgentName);
                                    userTask.setmStrBank(mStrBank);
                                    userTask.setmStrCustomerName(mStrCustomerName);
                                    userTask.setmStrExecutiveName(mStrExecutiveName);
                                    userTask.setmStrFinancer(mStrFinancer);
                                    userTask.setmStrId(mStrId);
                                    userTask.setmStrLoanType(mStrLoanType);
                                    userTask.setmStrMobile(mStrMobile);
                                    userTask.setmStrTelecallerName(mStrTelecallerName);
                                    userTask.setmStrVerificationType(mStrVerificationType);
                                    userTask.setmStrLongitude(mStrLongitude);
                                    userTask.setmStrLattitude(mStrLattitude);
                                    userTask.setmStrResidentialAddress(mStrAddress);
                                    userTask.setmStrDueEmi(mStrDueEmi);
                                    userTask.setmStrEmiAmount(mStrEmiAmount);
                                    userTasksList.add(userTask);

                                }
                                mNewTaskAdapterAgent.notifyDataSetChanged();
                            } else if (mStrStatus.equals("2")) {
                                CommonMethods.mShowAlert("Your session has expired, Please Login again", mActivity);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(mActivity, RoleSelectScreen.class);
                                        mActivity.startActivity(intent);
                                        mActivity.finish();
                                    }
                                }, 1000);
                            } else {
                                CommonMethods.mShowAlert("Something went wrong", mActivity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        CommonMethods.mShowAlert("Something went wrong", mActivity);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(mActivity));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(mActivity));
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }




    void mGetFeedBackContent() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getAgentFeedBackContent",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("#<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("status");
                            if (mStrStatus.equals("1")) {
                                JSONArray jsonArray = response.getJSONArray("response");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String mStrContent = jsonObject.getString("title");
                                    String mStrId = jsonObject.getString("id");
                                    mTaskFeedbackContent.add(mStrContent);
                                    mContentId.put(mStrContent,mStrId);
                                }
                                mArrayContent = mTaskFeedbackContent.toArray(new String[mTaskFeedbackContent.size()]);
                            } else if (mStrStatus.equals("2")) {
                                CommonMethods.mShowAlert("Your session has expired, Please Login again", NewTaskAgent.this);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(NewTaskAgent.this, RoleSelectScreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);

                            }

                            else {
                                CommonMethods.mShowAlert("Something went wrong", NewTaskAgent.this);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        CommonMethods.mShowAlert("Something went wrong", NewTaskAgent.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(NewTaskAgent.this));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(NewTaskAgent.this));

                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }



    public static void funAgent() {

        final Dialog dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dialoge);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        mEditExtraText = dialog.findViewById(R.id.mEditExtraText);
        final Spinner mSpinnerOption = (Spinner) dialog.findViewById(R.id.mSpinnerOption);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mActivity, simple_spinner_item, mArrayContent);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        mSpinnerOption.setAdapter(spinnerArrayAdapter);
        Button mBtnSubmit = (Button) dialog.findViewById(R.id.mBtnSubmit);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mStrFeedback = mSpinnerOption.getSelectedItem().toString();
                if (mStrFeedback.equals("Select Option")) {
                    mShowAlert("Please select an option", mActivity);
                    return;
                }
                dialog.dismiss();
                show(mActivity);
                File file = new File(dirfilepath);
                Uri mAudioFile = Uri.fromFile(file);
                mSendAudioFile(mAudioFile,mEditExtraText.getText().toString(),mSpinnerOption.getSelectedItem().toString());
            }
        });


    }



    public static void mSendAudioFile(final Uri videoUri,final String mStrFeedback,final String mStrSpinner) {
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, CommonMethods.BASE_URL + "getAgentCallFeedBack",
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        hidePDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            System.out.println("<><><>responseagent" + jsonObject.toString());
                            String mStrStatus = jsonObject.getString("status");
                            if (mStrStatus.equals("1")) {
                                CommonMethods.mShowAlert("Call feedback submitted successfully", mActivity);

                                userTasksList.clear();
                                show(mActivity);
                                mGetUserTask();


                                String deviceMan = android.os.Build.MANUFACTURER;
                                if (deviceMan.equals("Xiaomi")) {
                                    File file = new File(dirfilepath);
                                    Log.e("check path",dirfilepath);
                                    final int random = new Random().nextInt(61) + 20;
                                    File file2 = new File("/storage/emulated/0/MIUI/sound_recorder/call_rec/Call" + random + ".mp3");
                                    if (file2.exists()) {
                                        try {
                                            throw new java.io.IOException("File already exists!");
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    boolean success = file.renameTo(file2);
                                    if (!success) {
                                        System.out.println("file not renamed");
                                    } else {
                                        System.out.println("File renamed successfully!");
                                    }
                                }
                                else if (deviceMan.equals("OPPO"))
                                {
                                    File file = new File(dirfilepath);
                                    Log.e("check path",dirfilepath);
                                    final int random = new Random().nextInt(61) + 20;

                                    File file2 = new File("/storage/emulated/0/Recordings/Call Recordings/recording" + random + ".amr");
                                    if (file2.exists()) {
                                        try {
                                            throw new java.io.IOException("File already exists!");
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    boolean success = file.renameTo(file2);
                                    if (!success) {
                                        System.out.println("file not renamed");
                                    } else {
                                        System.out.println("File renamed successfully!");
                                    }
                                }
                                else  if (deviceMan.equals("samsung")) {

                                    File file = new File(dirfilepath);
                                    Log.e("check path", dirfilepath);
                                    final int random = new Random().nextInt(61) + 20;
                                    File file2 = new File("/storage/emulated/0/Call/Call recording"+random+".m4a");
                                    if (file2.exists()) {
                                        try {
                                            throw new java.io.IOException("File already exists!");
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    boolean success = file.renameTo(file2);
                                    if (!success) {
                                        System.out.println("file not renamed");
                                    } else {
                                        System.out.println("File renamed successfully!");
                                    }
                                }

                                else  if (deviceMan.equals("vivo"))
                                {
                                    File file = new File(dirfilepath);
                                    Log.e("check path",dirfilepath);
                                    final int random = new Random().nextInt(61) + 20;
                                    File file2 = new File("/storage/emulated/0/Record/Call/recording"+random+".amr");                                    if (file2.exists()) {
                                        try {
                                            throw new java.io.IOException("File already exists!");
                                        }
                                        catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    boolean success = file.renameTo(file2);
                                    if (!success) {
                                        System.out.println("file not renamed");
                                    } else {
                                        System.out.println("File renamed successfully!");
                                    }


                                }







                            } else if (mStrStatus.equals("2")) {
                                CommonMethods.mShowAlert("Your session has expired, Please Login again", mActivity);
                                Intent intent = new Intent(mActivity, RoleSelectScreen.class);
                                mActivity.startActivity(intent);
                                mActivity.finish();
                            } else {
                                CommonMethods.mShowAlert("Something went wrong", mActivity);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("<><><> error",error.getMessage());
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(mActivity));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(mActivity));
                params.put("assignment_id", mAssignmentId);
                params.put("feedback", mStrFeedback);
                params.put("task_status", mContentId.get(mStrSpinner));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("audio_file", new DataPart(dirfilepath , getFileDataFromDrawable(mActivity, videoUri)));
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(mActivity).add(volleyMultipartRequest);
    }


}



