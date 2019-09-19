package com.example.myapplication.ModuleAgents.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Activities.RoleSelectScreen;
import com.example.myapplication.Activities.VipNumberActivity;
import com.example.myapplication.Helper.AppController;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.R;
import com.example.myapplication.utils.CommonMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.myapplication.utils.CommonMethods.hidePDialog;
import static com.example.myapplication.utils.CommonMethods.mShowAlert;
import static com.example.myapplication.utils.CommonMethods.show;

public class DashBoardAgent extends AppCompatActivity {

    @BindView(R.id.mUserImg)
    ImageView mUserImg;

    @BindView(R.id.mTextRoleName)
    TextView mTextRoleName;

    @BindView(R.id.mTextTaskCount)
    TextView mTextTaskCount;

    @BindView(R.id.mLayoutNewTask)
    LinearLayout mLayoutNewTask;

    @BindView(R.id.mLayoutVipNumber)
    LinearLayout mLayoutVipNumber;

    @BindView(R.id.mLayoutLogout)
    LinearLayout mLayoutLogout;

    @OnClick(R.id.mLayoutLogout)
    void mFunToLogout() {
        logout();
    }

    @OnClick(R.id.mLayoutNewTask)
    void mFunNewTask() {
        Intent intent=new Intent(DashBoardAgent.this, NewTaskAgent.class);
        startActivity(intent);
    }

    @OnClick(R.id.mLayoutVipNumber)
    void mFunVipNumber() {
        startActivity(new Intent(DashBoardAgent.this, VipNumberActivity.class));

    }

    Calendar c = Calendar.getInstance();
    int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_agent);
        ButterKnife.bind(this);

        checkPermission();




        Glide.with(DashBoardAgent.this)
                .load(PreferenceManager.getLOANUSERIMAGE(DashBoardAgent.this))
                .apply(RequestOptions.circleCropTransform().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round))
                .into(mUserImg);

        if(timeOfDay >= 0 && timeOfDay < 12){
            mTextRoleName.setText("Good Morning, " + PreferenceManager.getLOANUSERFIRSTNAME(DashBoardAgent.this));
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            mTextRoleName.setText("Good Afternoon, " + PreferenceManager.getLOANUSERFIRSTNAME(DashBoardAgent.this));
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            mTextRoleName.setText("Good Evening, " + PreferenceManager.getLOANUSERFIRSTNAME(DashBoardAgent.this));
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            mTextRoleName.setText("Good Night, " + PreferenceManager.getLOANUSERFIRSTNAME(DashBoardAgent.this));
        }


        if (CommonMethods.isOnline(DashBoardAgent.this))
        {
            show(DashBoardAgent.this);
            mFunUserTaskCount();
        }
        else if (!CommonMethods.isOnline(DashBoardAgent.this))
        {
            mShowAlert("please check your internet connection", DashBoardAgent.this);
        }






    }


    private boolean checkPermission() {
        int i = 0;
        String[] perm = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS};
        List<String> reqPerm = new ArrayList<>();
        for (String permis : perm) {
            int resultPhone = ContextCompat.checkSelfPermission(DashBoardAgent.this, permis);
            if (resultPhone == PackageManager.PERMISSION_GRANTED)
                i++;
            else {
                reqPerm.add(permis);
            }
        }

        if (i == 5)
            return true;
        else
            return requestPermission(reqPerm);
    }


    private boolean requestPermission(List<String> perm) {
        String[] listReq = new String[perm.size()];
        listReq = perm.toArray(listReq);
        for (String permissions : listReq) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DashBoardAgent.this, permissions)) {
                Toast.makeText(getApplicationContext(), "Phone Permissions needed for " + permissions, Toast.LENGTH_LONG);
            }
        }
        ActivityCompat.requestPermissions(DashBoardAgent.this, listReq, 1);
        return false;
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getApplicationContext(), "Permission Granted to access Phone calls", Toast.LENGTH_LONG);
                else
                    Toast.makeText(getApplicationContext(), "You can't access Phone calls", Toast.LENGTH_LONG);
                break;
        }
    }


    void mFunUserTaskCount() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getAgentAssignmentCount",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("status");

                            if (mStrStatus.equals("1")) {
                                JSONObject jsonObject = response.getJSONObject("response");
                                String mStrTodayTaskCount = jsonObject.getString("today");
                                String mStrPendingTaskCount = jsonObject.getString("pending");

                                mTextTaskCount.setText("Today You have " + mStrTodayTaskCount + " assignments and " + mStrPendingTaskCount + " recovery loan amount for loan recovery to complete.");

                            }
                            else if (mStrStatus.equals("2")) {

                                CommonMethods.mShowAlert("Your session has expired, Please Login again", DashBoardAgent.this);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(DashBoardAgent.this, RoleSelectScreen.class);
                                        startActivity(intent);
                                       finish();
                                    }
                                }, 1000);
                            } else {
                                CommonMethods.mShowAlert("Something went wrong", DashBoardAgent.this);
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
                        CommonMethods.mShowAlert("Something went wrong", DashBoardAgent.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(DashBoardAgent.this));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(DashBoardAgent.this));

                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }


    private void logout() {
            StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "logout",
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String str) {
                            hidePDialog();
                            try {
                                System.out.println("<><><>" + str);
                                JSONObject response = new JSONObject(str);
                                String mStrStatus = response.getString("status");

                                if (mStrStatus.equals("1")) {


                                    mShowAlert("Logout Successfully", DashBoardAgent.this);
                                    PreferenceManager.setLOANISLOGIN(DashBoardAgent.this,"0");
                                    PreferenceManager.setLoanUserroleId(DashBoardAgent.this,"0");
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(DashBoardAgent.this, RoleSelectScreen.class));
                                            finish();
                                        }
                                    }, 1000);
                                }



                                else if (mStrStatus.equals("2")) {

                                    CommonMethods.mShowAlert("Your session has expired, Please Login again", DashBoardAgent.this);
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent = new Intent(DashBoardAgent.this, RoleSelectScreen.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }, 1000);
                                } else {
                                    CommonMethods.mShowAlert("Something went wrong", DashBoardAgent.this);
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
                            CommonMethods.mShowAlert("Something went wrong", DashBoardAgent.this);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("id", PreferenceManager.getLOANUSERID(DashBoardAgent.this));
                    params.put("auth_token", PreferenceManager.getLOANTOKEN(DashBoardAgent.this));

                    return params;
                }
            };
            strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(strRequest);
        }









    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

   @Override
    protected void onResume() {
       mFunUserTaskCount();
       super.onResume();
    }
}
