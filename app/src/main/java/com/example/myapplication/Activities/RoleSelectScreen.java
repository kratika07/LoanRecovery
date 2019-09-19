package com.example.myapplication.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.utils.CommonMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.myapplication.utils.CommonMethods.mShowAlert;

public class RoleSelectScreen extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.mtelecaller)
    CircleImageView mtelecaller;

    @BindView(R.id.mfieldexecutive)
    CircleImageView mfieldexecutive;

    @BindView(R.id.mrecoveryagent)
    CircleImageView mrecoveryagent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_your_role_screen);

        ButterKnife.bind(this);


        if (!CommonMethods.isOnline(RoleSelectScreen.this))
        {
            mShowAlert("please check your internet connection", RoleSelectScreen.this);

        }



        mtelecaller.setOnClickListener(this);
        mfieldexecutive.setOnClickListener(this);
        mrecoveryagent.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.mtelecaller:

                String str = "3";
                Intent intent = new Intent(RoleSelectScreen.this, LoginActivity.class);
                intent.putExtra("message", str);
                startActivity(intent);
                finish();
                break;


            case R.id.mfieldexecutive:
                String str2 = "6";
                Intent intent2 = new Intent(RoleSelectScreen.this, LoginActivity.class);
                intent2.putExtra("message", str2);
                startActivity(intent2);
                finish();
                break;


            case R.id.mrecoveryagent:
                String str3 = "7";
                Intent intent3 = new Intent(RoleSelectScreen.this, LoginActivity.class);
                intent3.putExtra("message", str3);
                startActivity(intent3);
                finish();
                break;



        }


    }
}
