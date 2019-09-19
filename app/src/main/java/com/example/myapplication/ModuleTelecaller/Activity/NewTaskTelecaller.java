package com.example.myapplication.ModuleTelecaller.Activity;

import android.app.Activity;
import android.app.Dialog;

import static android.R.layout.simple_spinner_item;

import static com.example.myapplication.Reciever.PhoneStateReceiver.dirfilepath;
import static com.example.myapplication.Reciever.RecorderService.rec;

import static com.example.myapplication.utils.CommonMethods.getFileDataFromDrawable;
import static com.example.myapplication.utils.CommonMethods.hidePDialog;
import static com.example.myapplication.utils.CommonMethods.mShowAlert;
import static com.example.myapplication.utils.CommonMethods.show;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
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
import com.example.myapplication.Helper.AppController;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.Helper.VolleyMultipartRequest;
import com.example.myapplication.Model.UserTask;


import com.example.myapplication.ModuleTelecaller.Adapter.NewTaskAdapterTelecaller;
import com.example.myapplication.R;

import com.example.myapplication.utils.CommonMethods;

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


public class NewTaskTelecaller extends AppCompatActivity {



    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    public static ArrayList<UserTask> userTasksList = new ArrayList<>();

    ArrayList<String> mTaskFeedbackContent = new ArrayList<>();

    public static HashMap<String, String> mContentId = new HashMap<String, String>();

    public static String mArrayContent[];
    public static EditText mEditExtraText;
    public static NewTaskAdapterTelecaller mNewTaskAdapterTelecaller;
    public static Activity mActivity;
    public static String mAssignmentId;

    @BindView(R.id.mImgBackButton)
    ImageView mbackbutton;


    @OnClick(R.id.mImgBackButton)
    void mFunToBack() {
        finish();
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_activty);
        ButterKnife.bind(this);
        mActivity = NewTaskTelecaller.this;

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mNewTaskAdapterTelecaller = new NewTaskAdapterTelecaller(NewTaskTelecaller.this, userTasksList);
        mRecyclerView.setAdapter(mNewTaskAdapterTelecaller);


            if (CommonMethods.isOnline(NewTaskTelecaller.this))
            {
                show(NewTaskTelecaller.this);
                userTasksList.clear();
                mGetUserTask();
                mGetFeedBackContent();
            }
            else if (!CommonMethods.isOnline(NewTaskTelecaller.this))
            {
                mShowAlert("please check your internet connection", NewTaskTelecaller.this);
            }

    }


    public static void mGetUserTask() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getAssignment",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
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

                                    userTasksList.add(userTask);

                                }
                                mNewTaskAdapterTelecaller.notifyDataSetChanged();


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
                        CommonMethods.mShowAlert("Something went wrong...", mActivity);
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
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getFeedBackContent",
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
                                CommonMethods.mShowAlert("Your session has expired, Please Login again", NewTaskTelecaller.this);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(NewTaskTelecaller.this, RoleSelectScreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);

                            }

                            else {
                                CommonMethods.mShowAlert("Something went wrong", NewTaskTelecaller.this);
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
                        CommonMethods.mShowAlert("Something went wrong", NewTaskTelecaller.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(NewTaskTelecaller.this));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(NewTaskTelecaller.this));

                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }

    public static void funTelecaller() {

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
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, CommonMethods.BASE_URL + "getCallFeedBack",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        hidePDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));
                            System.out.println("<><><>response" + jsonObject.toString());
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

                                    File file2 = new File("/storage/emulated/0/Record/Call/recording"+random+".amr");
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
                params.put("auth_token", PreferenceManager.getLOANTOKEN(mActivity));
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



