package com.example.myapplication.ModuleAgents.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.utils.CommonMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.myapplication.ModuleAgents.Activity.NewTaskAgent.mGetLatitude;
import static com.example.myapplication.ModuleAgents.Activity.NewTaskAgent.mGetLongitude;
import static com.example.myapplication.utils.CommonMethods.mShowAlert;


public class MoreNavAgent extends AppCompatActivity {



    @BindView(R.id.mTextCutomerName)
    TextView mTextCutomerName;

    @BindView(R.id.mTextCutomerAddressName)
    TextView mTextCutomerAddressName;

    @BindView(R.id.mBtnNavigation)
    Button mBtnNavigation;

    @BindView(R.id.mBtnStart)
    Button mBtnStart;

    @BindView(R.id.mImgBack)
    ImageView mImgBack;

    @BindView(R.id.mDueEMi)
    TextView mDueEMi;

    @BindView(R.id.mEmiAMount)
    TextView mEmiAMount;

    @BindView(R.id.mBankName)
    TextView mBankName;

    @OnClick(R.id.mImgBack)
    void FunToBackButton() {
        finish();
    }

    @OnClick(R.id.mBtnStart)
    void FunToStart()
    {
        Intent intent=new Intent(MoreNavAgent.this,StartAgentActivity.class);
        intent.putExtra("dueemi",mDueEmi);
        intent.putExtra("id",mCustomerId);
        startActivity(intent);
    }

    @OnClick(R.id.mBtnNavigation)
    void FunToNavigate() {
        if (CommonMethods.isOnline(MoreNavAgent.this))
        {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr=" + mGetLatitude + "," + mGetLongitude + "&daddr=" + mCustomerLattitude + "," + mCustomerLongitude));
            startActivity(intent);
        }
        else if (!CommonMethods.isOnline(MoreNavAgent.this))
        {
            mShowAlert("please check your internet connection", MoreNavAgent.this);
        }
    }


    String mCustomerName,mCustomerAddress,mCustomerLattitude,mCustomerLongitude,mCustomerId,mDueEmi,mEmiAmount,mstrBankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_nav_agent);
        ButterKnife.bind(this);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCustomerName = bundle.getString("name");
            mCustomerAddress = bundle.getString("address");
            mCustomerLattitude = bundle.getString("lattitude");
            mCustomerLongitude = bundle.getString("longitude");
            mCustomerId=bundle.getString("id");
            mDueEmi=bundle.getString("dueemi");
            mEmiAmount=bundle.getString("emiamount");
            mstrBankName=bundle.getString("bankname");
        }

        mTextCutomerName.setText(mCustomerName);
        mTextCutomerAddressName.setText(mCustomerAddress);

        mEmiAMount.setText(mEmiAmount);
        mDueEMi.setText(mDueEmi);
        mBankName.setText(mstrBankName);

    }
}
