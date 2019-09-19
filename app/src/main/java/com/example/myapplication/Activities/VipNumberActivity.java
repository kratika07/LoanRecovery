package com.example.myapplication.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Adapter.VipNumberAdapter;
import com.example.myapplication.Helper.AppController;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.Model.VipUser;
import com.example.myapplication.R;
import com.example.myapplication.utils.CommonMethods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.myapplication.utils.CommonMethods.hidePDialog;
import static com.example.myapplication.utils.CommonMethods.mShowAlert;
import static com.example.myapplication.utils.CommonMethods.show;

public class VipNumberActivity extends AppCompatActivity {



    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    ArrayList<VipUser> userVipNumberList = new ArrayList<>();


    private VipNumberAdapter vipNumberAdapter;

    @BindView(R.id.mImgBack)
    ImageView mImgBack;

    @OnClick(R.id.mImgBack)
    void FunToBackButton() {
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_number);
        ButterKnife.bind(this);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);



        if (CommonMethods.isOnline(VipNumberActivity.this))
        {
            show(VipNumberActivity.this);
            getVipNumber();
        }

        else if (!CommonMethods.isOnline(VipNumberActivity.this))
        {
            mShowAlert("please check your internet connection", VipNumberActivity.this);

        }





    }



    public void getVipNumber() {

        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getVipNumbers",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><kv><>" + str);

                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("status");
                            if (mStrStatus.equals("1")) {
                                JSONArray jsonArray = response.getJSONArray("response");

                                if (jsonArray.length() <= 0) {
                                    CommonMethods.mShowAlert("No record found", VipNumberActivity.this);
                                    return;
                                }


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String mStrId = jsonObject.getString("id");
                                    String mStrCustomerName = jsonObject.getString("name");
                                    String mStrCustomerNumber = jsonObject.getString("number");
                                    String mStrCreatedat = jsonObject.getString("created_at");

                                    VipUser vipUser = new VipUser();
                                    vipUser.setmStrName(mStrCustomerName);
                                    vipUser.setmStrNumber(mStrCustomerNumber);

                                    userVipNumberList.add(vipUser);


                                }
                                vipNumberAdapter = new VipNumberAdapter(VipNumberActivity.this, userVipNumberList);
                                mRecyclerView.setAdapter(vipNumberAdapter);
                            } else if (mStrStatus.equals("2")) {
                                CommonMethods.mShowAlert("Your session has expired, Please Login again", VipNumberActivity.this);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(VipNumberActivity.this, RoleSelectScreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                CommonMethods.mShowAlert("Something went wrong", VipNumberActivity.this);
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
                        CommonMethods.mShowAlert("Something went wrong", VipNumberActivity.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(VipNumberActivity.this));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(VipNumberActivity.this));
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }


}




