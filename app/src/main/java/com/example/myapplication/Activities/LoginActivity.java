package com.example.myapplication.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.iid.FirebaseInstanceId;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.example.myapplication.ModuleAgents.Activity.DashBoardAgent;
import com.example.myapplication.Helper.AppController;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.R;
import com.example.myapplication.ModuleTelecaller.Activity.DashBoardTelecaller;
import com.example.myapplication.utils.CommonMethods;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.myapplication.utils.CommonMethods.hidePDialog;
import static com.example.myapplication.utils.CommonMethods.mShowAlert;
import static com.example.myapplication.utils.CommonMethods.show;

public class LoginActivity extends Activity {


    String mEmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @BindView(R.id.mBtnSubmit)
    Button mBtnSubmit;

    @BindView(R.id.mEditPassword)
    EditText mEditPassword;

    @BindView(R.id.mEditEmail)
    EditText mEditEmail;

    String mStrEmail, mStrPassword, mStrUserType,refreshedToken;


    @OnClick(R.id.mBtnSubmit)
    void FunToLogin() {

        mStrEmail = mEditEmail.getText().toString();
        mStrPassword = mEditPassword.getText().toString();
        if (mStrEmail.length() <= 0) {
            mShowAlert("Please enter email address", LoginActivity.this);
            return;
        } else if (!mStrEmail.matches(mEmailPattern)) {
            mShowAlert("Please enter valid email address", LoginActivity.this);
            return;
        } else if (mStrPassword.length() <= 0) {
            mShowAlert("Please enter your password", LoginActivity.this);
            return;
        }

        //show progress dialog close


        if (CommonMethods.isOnline(LoginActivity.this))
        {
            show(LoginActivity.this);
            mFunUserSignIn();
        }

        else if (!CommonMethods.isOnline(LoginActivity.this))
        {
            mShowAlert("please check your internet connection", LoginActivity.this);

        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        ButterKnife.bind(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mStrUserType = bundle.getString("message");
        }

         refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.out.println("########: "+refreshedToken);



    }

    void mFunUserSignIn() {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "auth",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String str) {

                        hidePDialog();

                        try {

                            System.out.println("<><><>" + str);

                            JSONObject response = new JSONObject(str);

                            String mStrStatus = response.getString("status");

                            if (mStrStatus.equals("1")) {
                                mShowAlert("Login Successfully", LoginActivity.this);
                                JSONObject jsonObject = response.getJSONObject("response");
                                String mStrToken = jsonObject.getString("auth_token");
                                String mStrImage = jsonObject.getString("photo");
                                String mStrFirstName = jsonObject.getString("firstname");
                                String mStrLastName = jsonObject.getString("lastname");
                                String mStrName = jsonObject.getString("name");
                                String mStrId = jsonObject.getString("id");
                                String mStrRole = jsonObject.getString("role");

                                PreferenceManager.setLOANISLOGIN(LoginActivity.this, "1");

                                PreferenceManager.setLOANTOKEN(LoginActivity.this, mStrToken);
                                PreferenceManager.setLOANUSERIMAGE(LoginActivity.this, mStrImage);
                                PreferenceManager.setLOANUSERFIRSTNAME(LoginActivity.this, mStrFirstName);
                                PreferenceManager.setLOANUSERLASTNAME(LoginActivity.this, mStrLastName);
                                PreferenceManager.setLOANUSERNAME(LoginActivity.this, mStrName);
                                PreferenceManager.setLOANUSERID(LoginActivity.this, mStrId);
                                PreferenceManager.setLOANUSERROLE(LoginActivity.this, mStrRole);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        if (mStrUserType.equals("3"))
                                        {
                                            String str1 = "3";
                                            PreferenceManager.setLoanUserroleId(LoginActivity.this, str1);
                                            Intent intent=new Intent(LoginActivity.this, DashBoardTelecaller.class);
                                            startActivity(intent);
                                            finish();
                                        }

                                        else if(mStrUserType.equals("6"))
                                        {
                                            String str2 = "6";
                                            PreferenceManager.setLoanUserroleId(LoginActivity.this, str2);
                                            Intent intent=new Intent(LoginActivity.this, DashBoardFieldExecutive.class);
                                            startActivity(intent);
                                            finish();
                                        }


                                        else if(mStrUserType.equals("7"))
                                        {
                                            String str3 = "7";
                                            PreferenceManager.setLoanUserroleId(LoginActivity.this, str3);
                                            Intent intent=new Intent(LoginActivity.this, DashBoardAgent.class);
                                            startActivity(intent);
                                            finish();
                                        }




                                    }
                                }, 1000);
                            } else {
                                CommonMethods.mShowAlert("The email address or password you entered is incorrect", LoginActivity.this);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error",error.getMessage());

                        hidePDialog();
                        CommonMethods.mShowAlert("Something went wrong", LoginActivity.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", mStrEmail);
                params.put("password", mStrPassword);
                params.put("role", mStrUserType);
                params.put("platform", "Android");
                params.put("devicetoken", refreshedToken);
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }













/*    void mFunUserSignIn() {
        Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(APIUrl.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.userLogin(mStrEmail, mStrPassword, mStrUserType, "Android", "device_token");

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                //progress dialog close
                hidePDialog();
                if (response.body().getStatus() == 1) {
                    mShowAlert("Login Successfully",LoginActivity.this);
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getUser());
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(LoginActivity.this, DashBoardTelecaller.class));
                            finish();
                        }
                    }, 1000);
                } else {
                    mShowAlert("The email address or password you entered is incorrect", LoginActivity.this);
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                //progress dialog close
                hidePDialog();
                mShowAlert("Something went wrong", LoginActivity.this);
            }
        });
    }*/









}
