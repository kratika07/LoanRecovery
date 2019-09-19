package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Helper.AppController;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.R;
import com.example.myapplication.utils.CommonMethods;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import static com.example.myapplication.utils.CommonMethods.hidePDialog;
import static com.example.myapplication.utils.CommonMethods.mShowAlert;
import static com.example.myapplication.utils.CommonMethods.show;

public class TaskDetailActivity extends AppCompatActivity {

    @BindView(R.id.mback)
    ImageView back;

    @BindView(R.id.mCustomerName)
    TextView mCustomerName;

    @BindView(R.id.mTextCutomerName)
    TextView mTextCutomerName;

    @BindView(R.id.mPersonalMobile)
    TextView mPersonalMobile;

    @BindView(R.id.mTextPersonalMobile)
    TextView mTextPersonalMobile;

    @BindView(R.id.mResidencialAddress)
    TextView mResidencialAddress;

    @BindView(R.id.mTextResidentialAddress)
    TextView mTextResidentialAddress;

    @BindView(R.id.mOfficeAddress)
    TextView mOfficeAddress;

    @BindView(R.id.mTextOfficeAddress)
    TextView mTextOfficeAddress;

    @BindView(R.id.mOfficeMobile)
    TextView mOfficeMobile;

    @BindView(R.id.mTextOfficeMobile)
    TextView mTextOfficeMobile;

    @BindView(R.id.mBankName)
    TextView mBankName;

    @BindView(R.id.mTextBankName)
    TextView mTextBankName;

    @BindView(R.id.mFinancer)
    TextView mFinancer;

    @BindView(R.id.mTextFinancer)
    TextView mTextFinancer;

    @BindView(R.id.mLoanType)
    TextView mLoanType;

    @BindView(R.id.mTextLoanType)
    TextView mTextLoanType;

    @BindView(R.id.mVerificationType)
    TextView mVerificationType;

    @BindView(R.id.mTextVerficationType)
    TextView mTextVerificationType;


    @BindView(R.id.mTextEmiAmount)
    TextView mTextEmiAmount;

    @BindView(R.id.mEmiAmount)
    TextView mEmiAmount;


    @BindView(R.id.mTextDueEmi)
    TextView mTextDueEmi;

    @BindView(R.id.mDueEMi)
    TextView mDueEMi;



    String mStrAssignmentId;


    @OnClick(R.id.mback)
    void FunToBack() {

        finish();
    }
    public  String mRoleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description_);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStrAssignmentId = bundle.getString("id");
        }

        mRoleId= PreferenceManager.getLoanUserroleId(TaskDetailActivity.this);

        if (CommonMethods.isOnline(TaskDetailActivity.this))
        {
            String str1="3",str2="6",str3="7";
            if (mRoleId.equals(str1))
            {
                show(TaskDetailActivity.this);
               mFunUserTaskDetail();
            }
            else if (mRoleId.equals(str2))
            {
                show(TaskDetailActivity.this);
                mFunUserTaskDetail2();
            }
            else if (mRoleId.equals(str3))
            {
                show(TaskDetailActivity.this);
                mFunUserTaskDetail3();
            }

        }
        else if (!CommonMethods.isOnline(TaskDetailActivity.this))
        {
            mShowAlert("please check your internet connection", TaskDetailActivity.this);
        }
    }



    void mFunUserTaskDetail() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getAssignmentDetails",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        System.out.println("<><><>detailtele" + str);
                        try {
                            JSONObject response = new JSONObject(str);

                            String mStrStatus = response.getString("status");

                            if (mStrStatus.equals("1")) {

                                JSONObject jsonObject = response.getJSONObject("response");

                                String mStrId = jsonObject.getString("id");
                                String mStrCustomerId = jsonObject.getString("customer_id");
                                String mStrCustomerName = jsonObject.getString("customer_name");
                                String mStrPersonalMobile = jsonObject.getString("personal_mobile");
                                String mStrBranchName = jsonObject.getString("branch_name");
                                String mStrResidentialAddress = jsonObject.getString("residential_address");
                                String mStrOfficeAddress = jsonObject.getString("office_address");
                                String mStrOfficeMobile = jsonObject.getString("office_mobile");
                                String mStrBankName = jsonObject.getString("bank_name");
                                String mStrFinancer = jsonObject.getString("financer");
                                String mStrLoanType = jsonObject.getString("loantype");
                                String mStrVerificationType = jsonObject.getString("verification_type");
                                String mStrTelecaller = jsonObject.getString("telecaller_name");
                               // String mStrLattitude = jsonObject.getString("latitude");
                               // String mStrLongitude = jsonObject.getString("longitude");
                                String mStrEmiAmount = jsonObject.getString("emi_amount");
                                String mStrDueEmi = jsonObject.getString("due_emi");

                                Log.e("check customer name",mStrCustomerName);

                                //Toast.makeText(TaskDetailActivity.this, mStrCustomerName, Toast.LENGTH_SHORT).show();




                                if (mStrCustomerName==null || mStrCustomerName.equals(""))
                                {
                                    mCustomerName.setVisibility(View.GONE);
                                    mTextCutomerName.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mCustomerName.setVisibility(View.VISIBLE);
                                    mTextCutomerName.setVisibility(View.VISIBLE);
                                    mCustomerName.setText(mStrCustomerName);
                                }

                                if (mStrPersonalMobile==null || mStrPersonalMobile.equals(""))
                                {
                                    mPersonalMobile.setVisibility(View.GONE);
                                    mTextPersonalMobile.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mPersonalMobile.setVisibility(View.VISIBLE);
                                    mTextPersonalMobile.setVisibility(View.VISIBLE);
                                    mPersonalMobile.setText(mStrPersonalMobile);
                                }

                                if (mStrResidentialAddress==null || mStrResidentialAddress.equals(""))
                                {
                                    mResidencialAddress.setVisibility(View.GONE);
                                    mTextResidentialAddress.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mResidencialAddress.setVisibility(View.VISIBLE);
                                    mTextResidentialAddress.setVisibility(View.VISIBLE);
                                    mResidencialAddress.setText(mStrResidentialAddress);
                                }

                                if (mStrOfficeAddress==null || mStrOfficeAddress.equals(""))
                                {
                                    mOfficeAddress.setVisibility(View.GONE);
                                    mTextOfficeAddress.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mOfficeAddress.setVisibility(View.VISIBLE);
                                    mTextOfficeAddress.setVisibility(View.VISIBLE);
                                    mOfficeAddress.setText(mStrOfficeAddress);
                                }

                                if (mStrOfficeMobile==null || mStrOfficeMobile.equals(""))
                                {
                                    mOfficeMobile.setVisibility(View.GONE);
                                    mTextOfficeMobile.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mOfficeMobile.setVisibility(View.VISIBLE);
                                    mTextOfficeMobile.setVisibility(View.VISIBLE);
                                    mOfficeMobile.setText(mStrOfficeMobile);
                                }



                                if (mStrBankName==null || mStrBankName.equals(""))
                                {
                                    mBankName.setVisibility(View.GONE);
                                    mTextBankName.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mBankName.setVisibility(View.VISIBLE);
                                    mTextBankName.setVisibility(View.VISIBLE);
                                    mBankName.setText(mStrBankName);
                                }


                                if (mStrFinancer==null || mStrFinancer.equals(""))
                                {
                                    mFinancer.setVisibility(View.GONE);
                                    mTextFinancer.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mFinancer.setVisibility(View.VISIBLE);
                                    mTextFinancer.setVisibility(View.VISIBLE);
                                    mFinancer.setText(mStrFinancer);
                                }


                                if (mStrLoanType==null || mStrLoanType.equals(""))
                                {
                                    mLoanType.setVisibility(View.GONE);
                                    mTextLoanType.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mLoanType.setVisibility(View.VISIBLE);
                                    mTextLoanType.setVisibility(View.VISIBLE);
                                    mLoanType.setText(mStrLoanType);
                                }

                                if (mStrVerificationType==null || mStrVerificationType.equals(""))
                                {
                                    mVerificationType.setVisibility(View.GONE);
                                    mTextVerificationType.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mVerificationType.setVisibility(View.VISIBLE);
                                    mTextVerificationType.setVisibility(View.VISIBLE);
                                    mVerificationType.setText(mStrVerificationType);
                                }


                                if (mStrEmiAmount==null || mStrEmiAmount.equals(""))
                                {
                                    mEmiAmount.setVisibility(View.GONE);
                                    mTextEmiAmount.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mEmiAmount.setVisibility(View.VISIBLE);
                                    mTextEmiAmount.setVisibility(View.VISIBLE);
                                    mEmiAmount.setText(mStrEmiAmount);
                                }


                                if (mStrDueEmi==null || mStrDueEmi.equals(""))
                                {
                                    mDueEMi.setVisibility(View.GONE);
                                    mTextDueEmi.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mDueEMi.setVisibility(View.VISIBLE);
                                    mTextDueEmi.setVisibility(View.VISIBLE);
                                    mDueEMi.setText(mStrDueEmi);
                                }


                            /*
                                 mCustomerName.setText(mStrCustomerName);
                                 mPersonalMobile.setText(mStrPersonalMobile);
                                 mResidencialAddress.setText(mStrResidentialAddress);
                                 mOfficeAddress.setText(mStrOfficeAddress);
                                 mOfficeMobile.setText(mStrOfficeMobile);
                                 mBankName.setText(mStrBankName);
                                 mFinancer.setText(mStrFinancer);
                                 mLoanType.setText(mStrLoanType);
                               mVerificationType.setText(mStrVerificationType);*/

                            } else if (mStrStatus.equals("2")) {
                                CommonMethods.mShowAlert("Your session has expired, Please Login again", TaskDetailActivity.this);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(TaskDetailActivity.this, RoleSelectScreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                CommonMethods.mShowAlert("Something went wrong", TaskDetailActivity.this);

                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                        }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        CommonMethods.mShowAlert("Something went wrong", TaskDetailActivity.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(TaskDetailActivity.this));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(TaskDetailActivity.this));
                params.put("assignment_id",mStrAssignmentId);
                return params;

            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }

    void mFunUserTaskDetail2() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getExecutiveAssignmentDetails",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        System.out.println("<><><>detail" + str);
                        try {
                            JSONObject response = new JSONObject(str);

                            String mStrStatus = response.getString("status");

                            if (mStrStatus.equals("1")) {

                                JSONObject jsonObject = response.getJSONObject("response");

                                String mStrId = jsonObject.getString("id");
                                String mStrCustomerId = jsonObject.getString("customer_id");
                                String mStrCustomerName = jsonObject.getString("customer_name");
                                String mStrPersonalMobile = jsonObject.getString("personal_mobile");
                                String mStrResidentialAddress = jsonObject.getString("residential_address");
                                String mStrResidentialLandmark = jsonObject.getString("residential_landmark");
                                String mStrCompanyName = jsonObject.getString("company_name");
                                String mStrOfficeAddress = jsonObject.getString("office_address");
                                String mStrOfficeLandmark = jsonObject.getString("office_landmark");
                                String mStrOfficeCity = jsonObject.getString("office_city");
                                String mStrOfficeState = jsonObject.getString("office_state");
                                String mStrOfficeMobile = jsonObject.getString("office_mobile");
                                String mStrBankName = jsonObject.getString("bank_name");
                                String mStrFinancer = jsonObject.getString("financer");
                                String mStrLoanType = jsonObject.getString("loantype");
                                String mStrVerificationType = jsonObject.getString("verification_type");
                                String mStrTelecaller = jsonObject.getString("telecaller_name");

                                String mStrLattitude = jsonObject.getString("latitude");
                                String mStrLongitude = jsonObject.getString("longitude");

                                if (mStrCustomerName==null || mStrCustomerName.equals(""))
                                {
                                    mCustomerName.setVisibility(View.GONE);
                                    mTextCutomerName.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mCustomerName.setVisibility(View.VISIBLE);
                                    mTextCutomerName.setVisibility(View.VISIBLE);
                                    mCustomerName.setText(mStrCustomerName);
                                }



                                if (mStrPersonalMobile==null || mStrPersonalMobile.equals(""))
                                {
                                    mPersonalMobile.setVisibility(View.GONE);
                                    mTextPersonalMobile.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mPersonalMobile.setVisibility(View.VISIBLE);
                                    mTextPersonalMobile.setVisibility(View.VISIBLE);
                                    mPersonalMobile.setText(mStrPersonalMobile);
                                }

                                if (mStrResidentialAddress==null || mStrResidentialAddress.equals(""))
                                {
                                    mResidencialAddress.setVisibility(View.GONE);
                                    mTextResidentialAddress.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mResidencialAddress.setVisibility(View.VISIBLE);
                                    mTextResidentialAddress.setVisibility(View.VISIBLE);
                                    mResidencialAddress.setText(mStrResidentialAddress);
                                }

                                if (mStrOfficeAddress==null || mStrOfficeAddress.equals(""))
                                {
                                    mOfficeAddress.setVisibility(View.GONE);
                                    mTextOfficeAddress.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mOfficeAddress.setVisibility(View.VISIBLE);
                                    mTextOfficeAddress.setVisibility(View.VISIBLE);
                                    mOfficeAddress.setText(mStrOfficeAddress);
                                }

                                if (mStrOfficeMobile==null || mStrOfficeMobile.equals(""))
                                {
                                    mOfficeMobile.setVisibility(View.GONE);
                                    mTextOfficeMobile.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mOfficeMobile.setVisibility(View.VISIBLE);
                                    mTextOfficeMobile.setVisibility(View.VISIBLE);
                                    mOfficeMobile.setText(mStrOfficeMobile);
                                }



                                if (mStrBankName==null || mStrBankName.equals(""))
                                {
                                    mBankName.setVisibility(View.GONE);
                                    mTextBankName.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mBankName.setVisibility(View.VISIBLE);
                                    mTextBankName.setVisibility(View.VISIBLE);
                                    mBankName.setText(mStrBankName);
                                }


                                if (mStrFinancer==null || mStrFinancer.equals(""))
                                {
                                    mFinancer.setVisibility(View.GONE);
                                    mTextFinancer.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mFinancer.setVisibility(View.VISIBLE);
                                    mTextFinancer.setVisibility(View.VISIBLE);
                                    mFinancer.setText(mStrFinancer);
                                }


                                if (mStrLoanType==null || mStrLoanType.equals(""))
                                {
                                    mLoanType.setVisibility(View.GONE);
                                    mTextLoanType.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mLoanType.setVisibility(View.VISIBLE);
                                    mTextLoanType.setVisibility(View.VISIBLE);
                                    mLoanType.setText(mStrLoanType);
                                }

                                if (mStrVerificationType==null || mStrVerificationType.equals(""))
                                {
                                    mVerificationType.setVisibility(View.GONE);
                                    mTextVerificationType.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mVerificationType.setVisibility(View.VISIBLE);
                                    mTextVerificationType.setVisibility(View.VISIBLE);
                                    mVerificationType.setText(mStrVerificationType);
                                }


                          /*      mCustomerName.setText(mStrCustomerName);
                                mPersonalMobile.setText(mStrPersonalMobile);
                                mResidencialAddress.setText(mStrResidentialLandmark);
                              //  mCompanyName.setText(mStrCompanyName);
                                mOfficeAddress.setText(mStrOfficeAddress);
                             //   mOfficeLandmark.setText(mStrOfficeLandmark);
                             //   mOfficeCity.setText(mStrOfficeCity);
                             //   mOfficeState.setText(mStrOfficeState);
                                mOfficeMobile.setText(mStrOfficeMobile);
                                mBankName.setText(mStrBankName);
                                mFinancer.setText(mStrFinancer);
                                mLoanType.setText(mStrLoanType);
                                mVerificationType.setText(mStrVerificationType);*/

                            } else if (mStrStatus.equals("2")) {
                                CommonMethods.mShowAlert("Your session has expired, Please Login again", TaskDetailActivity.this);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(TaskDetailActivity.this, RoleSelectScreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                CommonMethods.mShowAlert("Something went wrong", TaskDetailActivity.this);

                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        CommonMethods.mShowAlert("Something went wrong", TaskDetailActivity.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(TaskDetailActivity.this));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(TaskDetailActivity.this));
                params.put("assignment_id",mStrAssignmentId);
                return params;

            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }

    void mFunUserTaskDetail3() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getAgentAssignmentDetails",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        System.out.println("<><><>detail" + str);
                        try {
                            JSONObject response = new JSONObject(str);

                            String mStrStatus = response.getString("status");

                            if (mStrStatus.equals("1")) {

                                JSONObject jsonObject = response.getJSONObject("response");

                                String mStrId = jsonObject.getString("id");
                                String mStrCustomerId = jsonObject.getString("customer_id");
                                String mStrCustomerName = jsonObject.getString("customer_name");
                                String mStrPersonalMobile = jsonObject.getString("personal_mobile");
                                String mStrResidentialAddress = jsonObject.getString("residential_address");
                                String mStrOfficeAddress = jsonObject.getString("office_address");
                                String mStrOfficeMobile = jsonObject.getString("office_mobile");
                                String mStrBankName = jsonObject.getString("bank_name");
                                String mStrFinancer = jsonObject.getString("financer");
                                String mStrLoanType = jsonObject.getString("loantype");
                                String mStrVerificationType = jsonObject.getString("verification_type");
                                String mStrTelecaller = jsonObject.getString("telecaller_name");
                                String mStrLattitude = jsonObject.getString("latitude");
                                String mStrLongitude = jsonObject.getString("longitude");

                                String mStrEmiAmount = jsonObject.getString("emi_amount");
                                String mStrDueEmi = jsonObject.getString("due_emi");

                               if (mStrCustomerName==null || mStrCustomerName.equals(""))
                                {
                                    mCustomerName.setVisibility(View.GONE);
                                    mTextCutomerName.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mCustomerName.setVisibility(View.VISIBLE);
                                    mTextCutomerName.setVisibility(View.VISIBLE);
                                    mCustomerName.setText(mStrCustomerName);
                                }



                                if (mStrPersonalMobile==null || mStrPersonalMobile.equals(""))
                                  {
                                      mPersonalMobile.setVisibility(View.GONE);
                                      mTextPersonalMobile.setVisibility(View.VISIBLE);
                                  }
                                  else {
                                      mPersonalMobile.setVisibility(View.VISIBLE);
                                      mTextPersonalMobile.setVisibility(View.VISIBLE);
                                      mPersonalMobile.setText(mStrPersonalMobile);
                                  }

                                if (mStrResidentialAddress==null || mStrResidentialAddress.equals(""))
                                {
                                    mResidencialAddress.setVisibility(View.GONE);
                                    mTextResidentialAddress.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mResidencialAddress.setVisibility(View.VISIBLE);
                                    mTextResidentialAddress.setVisibility(View.VISIBLE);
                                    mResidencialAddress.setText(mStrResidentialAddress);
                                }

                                if (mStrOfficeAddress==null || mStrOfficeAddress.equals(""))
                                {
                                    mOfficeAddress.setVisibility(View.GONE);
                                    mTextOfficeAddress.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mOfficeAddress.setVisibility(View.VISIBLE);
                                    mTextOfficeAddress.setVisibility(View.VISIBLE);
                                    mOfficeAddress.setText(mStrOfficeAddress);
                                }

                                if (mStrOfficeMobile==null || mStrOfficeMobile.equals(""))
                                {
                                    mOfficeMobile.setVisibility(View.GONE);
                                    mTextOfficeMobile.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mOfficeMobile.setVisibility(View.VISIBLE);
                                    mTextOfficeMobile.setVisibility(View.VISIBLE);
                                    mOfficeMobile.setText(mStrOfficeMobile);
                                }



                                if (mStrBankName==null || mStrBankName.equals(""))
                                {
                                    mBankName.setVisibility(View.GONE);
                                    mTextBankName.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mBankName.setVisibility(View.VISIBLE);
                                    mTextBankName.setVisibility(View.VISIBLE);
                                    mBankName.setText(mStrBankName);
                                }


                                if (mStrFinancer==null || mStrFinancer.equals(""))
                                {
                                    mFinancer.setVisibility(View.GONE);
                                    mTextFinancer.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mFinancer.setVisibility(View.VISIBLE);
                                    mTextFinancer.setVisibility(View.VISIBLE);
                                    mFinancer.setText(mStrFinancer);
                                }


                                if (mStrLoanType==null || mStrLoanType.equals(""))
                                {
                                    mLoanType.setVisibility(View.GONE);
                                    mTextLoanType.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mLoanType.setVisibility(View.VISIBLE);
                                    mTextLoanType.setVisibility(View.VISIBLE);
                                    mLoanType.setText(mStrLoanType);
                                }

                                if (mStrVerificationType==null || mStrVerificationType.equals(""))
                                {
                                    mVerificationType.setVisibility(View.GONE);
                                    mTextVerificationType.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mVerificationType.setVisibility(View.VISIBLE);
                                    mTextVerificationType.setVisibility(View.VISIBLE);
                                    mVerificationType.setText(mStrVerificationType);
                                }


                                if (mStrEmiAmount==null || mStrEmiAmount.equals(""))
                                {
                                    mEmiAmount.setVisibility(View.GONE);
                                    mTextEmiAmount.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mEmiAmount.setVisibility(View.VISIBLE);
                                    mTextEmiAmount.setVisibility(View.VISIBLE);
                                    mEmiAmount.setText(mStrEmiAmount);
                                }


                                if (mStrDueEmi==null || mStrDueEmi.equals(""))
                                {
                                    mDueEMi.setVisibility(View.GONE);
                                    mTextDueEmi.setVisibility(View.VISIBLE);
                                }
                                else {
                                    mDueEMi.setVisibility(View.VISIBLE);
                                    mTextDueEmi.setVisibility(View.VISIBLE);
                                    mDueEMi.setText(mStrDueEmi);
                                }

                              /*  mCustomerName.setText(mStrCustomerName);
                                mPersonalMobile.setText(mStrPersonalMobile);
                                mResidencialAddress.setText(mStrResidentialAddress);
                                mOfficeAddress.setText(mStrOfficeAddress);
                                mOfficeMobile.setText(mStrOfficeMobile);
                                mBankName.setText(mStrBankName);
                                mFinancer.setText(mStrFinancer);
                                mLoanType.setText(mStrLoanType);
                                mVerificationType.setText(mStrVerificationType);
*/
                            } else if (mStrStatus.equals("2")) {
                                CommonMethods.mShowAlert("Your session has expired, Please Login again", TaskDetailActivity.this);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(TaskDetailActivity.this, RoleSelectScreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                CommonMethods.mShowAlert("Something went wrong", TaskDetailActivity.this);

                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        CommonMethods.mShowAlert("Something went wrong", TaskDetailActivity.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(TaskDetailActivity.this));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(TaskDetailActivity.this));
                params.put("assignment_id",mStrAssignmentId);
                return params;

            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }


}
