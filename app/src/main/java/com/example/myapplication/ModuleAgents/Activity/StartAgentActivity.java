package com.example.myapplication.ModuleAgents.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.Activities.RoleSelectScreen;
import com.example.myapplication.Activities.TaskDetailActivity;
import com.example.myapplication.Helper.AppController;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.R;
import com.example.myapplication.Services.GoogleService;
import com.example.myapplication.utils.CommonMethods;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import static com.example.myapplication.utils.CommonMethods.hidePDialog;

public class StartAgentActivity extends AppCompatActivity {



    @BindView(R.id.mEditFirstname)
    EditText mEditFirstname;

    @BindView(R.id.mEditSecondname)
    EditText mEditSecondname;


    @BindView(R.id.mEditlastname)
    EditText mEditlastname;

    @BindView(R.id.mEditAddress1)
    EditText mEditAddress1;


    @BindView(R.id.mEditAddress2)
    EditText mEditAddress2;


    @BindView(R.id.mEditMobile)
    EditText mEditMobile;


    @BindView(R.id.mEditEmiDue)
    EditText mEditEmiDue;



    @BindView(R.id.mEditdueemiamount)
    EditText mEditdueemiamount;



    @BindView(R.id.mEditEmiamountrecieved)
    EditText mEditEmiamountrecieved;



 /*   @BindView(R.id.mEditDate)
    public static EditText mEditDate;*/


    @BindView(R.id.mEditEmiAmountrecievedby)
    EditText mEditEmiAmountrecievedby;



    @BindView(R.id.mEditState)
    EditText mEditState;



    @BindView(R.id.mEditcity)
    EditText mEditcity;




    @BindView(R.id.mEditvillage)
    EditText mEditvillage;

    @BindView(R.id.mImgBackButton)
    ImageView mbackbutton;


    @OnClick(R.id.mImgBackButton)
    void mFunToBack() {
        finish();
    }


    @BindView(R.id.mEditbranchname)
    EditText mEditbranchname;


    @BindView(R.id.mEditbankname)
    EditText mEditbankname;


    @BindView(R.id.mEditloantype)
    EditText mEditloantype;


    @BindView(R.id.mEditpaymentmethod)
    EditText mEditpaymentmethod;


    @BindView(R.id.mEditSignatureofagent)
    EditText mEditSignatureofagent;

    @BindView(R.id.mEditSignatureofcustomer)
    EditText mEditSignatureofcustomer;


    @BindView(R.id.img1)
    ImageView mImage1;

    @BindView(R.id.img2)
    ImageView mImage2;

    @BindView(R.id.img3)
    ImageView mImage3;

    @OnClick(R.id.img1)
    void FunToImage1() {
    mCurrentImageClick = mImage1;
    showPictureDialog();
    }

    @OnClick(R.id.img2)
    void FunToImage2() {
        mCurrentImageClick = mImage2;
        showPictureDialog();
    }

    @OnClick(R.id.img3)
    void FunToImage3() {
        mCurrentImageClick = mImage3;
        showPictureDialog();
    }


    @BindView(R.id.mButtonSubmit)
    Button mButtonSubmit;


    @OnClick(R.id.mButtonSubmit)
    void FunToSubmit() {


     //   mButtonSubmit.setEnabled(true);

        String mStrFirstName = mEditFirstname.getText().toString();
        String mStrLastName = mEditlastname.getText().toString();
        String mStrAddress=mEditAddress1.getText().toString();
        String mStrAddress2=mEditAddress2.getText().toString();
        String mStrMobile=mEditMobile.getText().toString();
        String mStrNoDueEmi=mEditEmiDue.getText().toString();
        String mStrmEditEmiamountrecieved=mEditEmiamountrecieved.getText().toString();
        String mStrDate=mEditDate.getText().toString();
        String mStrAmountRecievedBy=mEditEmiAmountrecievedby.getText().toString();
        String mStrState=mEditState.getText().toString();
        String mStrCity=mEditcity.getText().toString();
        String mStrVillage=mEditvillage.getText().toString();
        String mStrDob=mEditdob.getText().toString();
        String mStrBranchName=mEditbranchname.getText().toString();
        String mStrBankName=mEditbankname.getText().toString();
        String mStrLoanType=mEditloantype.getText().toString();
        String mStrPaymentMethod=mEditpaymentmethod.getText().toString();

        if(TextUtils.isEmpty(mStrFirstName)) {
            mEditFirstname.setError("Enter first name");
            return;
        }

        if(TextUtils.isEmpty(mStrLastName)) {
            mEditlastname.setError("Enter last name");
            return;
        }

        if(TextUtils.isEmpty(mStrAddress)) {
            mEditAddress1.setError("Enter Address");
            return;
        }

        if(TextUtils.isEmpty(mStrAddress2)) {
            mEditAddress2.setError("Enter Address");
            return;
        }

        if(TextUtils.isEmpty(mStrMobile)) {
            mEditMobile.setError("Enter Mobile");
            return;
        }

        if(TextUtils.isEmpty(mStrNoDueEmi)) {
            mEditEmiDue.setError("Enter No of Due Emi");
            return;
        }
        if(TextUtils.isEmpty(mStrmEditEmiamountrecieved)) {
            mEditEmiamountrecieved.setError("Enter amount recieved");
            return;
        }
        if(TextUtils.isEmpty(mStrDate)) {
            mEditDate.setError("Enter Date");
            return;
        }
        if(TextUtils.isEmpty(mStrAmountRecievedBy)) {
            mEditEmiAmountrecievedby.setError("Enter amount recieved by");
            return;
        }
        if(TextUtils.isEmpty(mStrState)) {
            mEditState.setError("Enter State");
            return;
        }if(TextUtils.isEmpty(mStrCity)) {
            mEditcity.setError("Enter City");
            return;
        }if(TextUtils.isEmpty(mStrVillage)) {
            mEditvillage.setError("Enter Village");
            return;
        }if(TextUtils.isEmpty(mStrDob)) {
            mEditdob.setError("Enter Date of Birth");
            return;
        }if(TextUtils.isEmpty(mStrBranchName)) {
            mEditbranchname.setError("Enter Branch Name");
            return;
        }if(TextUtils.isEmpty(mStrBankName)) {
            mEditbankname.setError("Enter Bank Name");
            return;
        }if(TextUtils.isEmpty(mStrLoanType)) {
            mEditloantype.setError("Enter Loan Type");
            return;
        }if(TextUtils.isEmpty(mStrPaymentMethod)) {
            mEditpaymentmethod.setError("Enter Payment Method");
            return;
        }


         DueAmountRecieved=mEditEmiamountrecieved.getText().toString();

         mFuntocall2();



    }





 /*   @OnClick(R.id.mEditDate)
    void FuntoDate()
    {

    }
*/

/* public void showTruitonDatePickerDialog2(View v) {
     DialogFragment newFragment = new DatePickerFragment2();
     newFragment.show(getSupportFragmentManager(), "datePicker");
 }*/




    public void showTruitonDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    Button btn_start;
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;
    TextView tv_latitude, tv_longitude, tv_address,tv_area,tv_locality;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude,longitude;
    Geocoder geocoder;
    String mCustomerId,mDueEmi,DueAmountRecieved;
    DatePickerDialog picker;
    public   EditText mEditDate,mEditdob;
    public static  EditText mInstanceEdittext;

    private static final String IMAGE_DIRECTORY = "/Loanrecovery";
    private int GALLERY = 1, CAMERA = 2;
    ImageView mCurrentImageClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_agent);
        ButterKnife.bind(this);

        mEditDate=findViewById(R.id.mEditDate);
        mEditdob=findViewById(R.id.mEditdob);

        mEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInstanceEdittext=mEditDate;
                showTruitonDatePickerDialog( v);
            }
        });
        mEditdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInstanceEdittext=mEditdob;
                showTruitonDatePickerDialog( v);
            }
        });


       /* btn_start = (Button) findViewById(R.id.btn_start);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_latitude = (TextView) findViewById(R.id.tv_latitude);
        tv_longitude = (TextView) findViewById(R.id.tv_longitude);
        tv_area = (TextView) findViewById(R.id.tv_area);
        tv_locality = (TextView) findViewById(R.id.tv_locality);*/


        geocoder = new Geocoder(this, Locale.getDefault());
        mPref = android.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCustomerId=bundle.getString("id");
            mDueEmi=bundle.getString("dueemi");
        }

        mEditdueemiamount.setText(mDueEmi);

        requestMultiplePermissions();



     /*   btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boolean_permission) {
                    if (mPref.getString("service", "").matches("")) {
                        medit.putString("service", "service").commit();
                        Intent intent = new Intent(getApplicationContext(), GoogleService.class);
                        intent.putExtra("inputExtra", "Foreground Service Example in Android");
                        ContextCompat.startForegroundService(StartAgentActivity.this, intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), GoogleService.class);
                        intent.putExtra("inputExtra", "Foreground Service Example in Android");
                        ContextCompat.startForegroundService(StartAgentActivity.this, intent);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please enable the gps", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

          //  fn_permission();
        //    mFuntocall();





    }

    public  void mFuntocall2()
    {
        StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "getFormData",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        System.out.println("<><><>detail" + str);
                        try {
                            JSONObject response = new JSONObject(str);

                            String mStrStatus = response.getString("status");

                            if (mStrStatus.equals("1"))
                            {
                                CommonMethods.mShowAlert("Data  submitted successfully", StartAgentActivity.this);



                                Intent intent = new Intent(StartAgentActivity.this, NewTaskAgent.class);
                                startActivity(intent);
                                finish();

                              //  mButtonSubmit.setEnabled(false);
                             /*   Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                }, 10);
*/

                            }

                            else if (mStrStatus.equals("2")) {
                                CommonMethods.mShowAlert("Your session has expired, Please Login again", StartAgentActivity.this);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(StartAgentActivity.this, RoleSelectScreen.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                CommonMethods.mShowAlert("Something went wrong", StartAgentActivity.this);
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
                        CommonMethods.mShowAlert("Something went wrong", StartAgentActivity.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", PreferenceManager.getLOANUSERID(StartAgentActivity.this));
                params.put("auth_token", PreferenceManager.getLOANTOKEN(StartAgentActivity.this));
                params.put("assignment_id",mCustomerId);
                params.put("due_emi", DueAmountRecieved);


                return params;

            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strRequest);
    }








    public  void mFuntocall()
{
    StringRequest strRequest = new StringRequest(Request.Method.POST, CommonMethods.BASE_URL + "saveLatLong",
            new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String str) {
                    hidePDialog();
                    System.out.println("<><><>detail" + str);
                    try {
                        JSONObject response = new JSONObject(str);

                        String mStrStatus = response.getString("status");

                        if (mStrStatus.equals("1"))
                        {

                            Toast.makeText(StartAgentActivity.this, "lat long submitted successfully", Toast.LENGTH_SHORT).show();

                        }

                            else if (mStrStatus.equals("2")) {
                            CommonMethods.mShowAlert("Your session has expired, Please Login again", StartAgentActivity.this);
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(StartAgentActivity.this, RoleSelectScreen.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }, 1000);
                        } else {
                            CommonMethods.mShowAlert("Something went wrong", StartAgentActivity.this);
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
                    CommonMethods.mShowAlert("Something went wrong", StartAgentActivity.this);
                }
            }) {
        @Override
        protected Map<String, String> getParams() {

            Map<String, String> params = new HashMap<String, String>();
            params.put("id", PreferenceManager.getLOANUSERID(StartAgentActivity.this));
            params.put("auth_token", PreferenceManager.getLOANTOKEN(StartAgentActivity.this));
            params.put("assignment_id",mCustomerId);
            params.put("latitude", String.valueOf(latitude));
            params.put("longitude", String.valueOf(longitude));

            return params;

        }
    };
    strRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 48, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    AppController.getInstance().addToRequestQueue(strRequest);
}

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(StartAgentActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {


            } else {
                ActivityCompat.requestPermissions(StartAgentActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION

                        },
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    boolean_permission = true;

                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }
        }
    }







    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));

            List<Address> addresses = null;

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String countryName = addresses.get(0).getAddressLine(2);

               /* tv_area.setText(addresses.get(0).getAdminArea());
                tv_locality.setText(stateName);
                tv_address.setText(countryName);*/



            } catch (IOException e1) {
                e1.printStackTrace();
            }


          /*  tv_latitude.setText(latitude+"");
            tv_longitude.setText(longitude+"");
            tv_address.getText();
*/

        }
    };




    @Override
    protected void onResume() {
        super.onResume();
       // registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

    }

    @Override
    protected void onPause() {
        super.onPause();
      //  unregisterReceiver(broadcastReceiver);
    }
    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            mInstanceEdittext.setText(day + "/" + (month + 1) + "/" + year);

        }
    }

 /*   public static class DatePickerFragment2 extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user

            mEditdob.setText(day + "/" + (month + 1) + "/" + year);


        }
    }
*/






    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    Toast.makeText(StartAgentActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    mCurrentImageClick.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(StartAgentActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            mCurrentImageClick.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(StartAgentActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void  requestMultiplePermissions(){
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                           // Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
    }



















