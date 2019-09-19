package com.example.myapplication.ModuleAgents.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Activities.LoginActivity;
import com.example.myapplication.Activities.TaskDetailActivity;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.Model.UserTask;
import com.example.myapplication.ModuleAgents.Activity.MoreNavAgent;
import com.example.myapplication.ModuleAgents.Activity.NewTaskAgent;
import com.example.myapplication.R;

import java.util.ArrayList;

import static com.example.myapplication.ModuleAgents.Activity.NewTaskAgent.mGetLatitude;
import static com.example.myapplication.ModuleAgents.Activity.NewTaskAgent.mGetLongitude;


public class NewTaskAdapterAgent extends RecyclerView.Adapter<NewTaskAdapterAgent.ViewHolder> {
    ArrayList<UserTask> mListTask;

    private OnItemClickListener mListener;
    Activity context;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private boolean mAlreadyStartedService = false;




    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public NewTaskAdapterAgent(Activity context, ArrayList<UserTask> mListTask) {
        this.context = context;
        this.mListTask = mListTask;



    }

    @Override
    public NewTaskAdapterAgent.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_task_adapter_agent, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {


        viewHolder.mTextCutomerName.setText(mListTask.get(i).getmStrCustomerName());
        viewHolder.mTextBankName.setText(mListTask.get(i).getmStrBank());
        viewHolder.mTextLoanType.setText(mListTask.get(i).getmStrLoanType());
    }


    @Override
    public int getItemCount() {
        return mListTask.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextCutomerName, mTextBankName, mTextLoanType;
        RelativeLayout mLayoutCall, mLayoutTask,mLayoutNavigation;

        public ViewHolder(View view) {
            super(view);

            mTextCutomerName = view.findViewById(R.id.mTextCutomerName);
            mTextBankName = view.findViewById(R.id.mTextBankName);
            mTextLoanType = view.findViewById(R.id.mTextLoanType);

            mLayoutCall = view.findViewById(R.id.mLayoutCall);
            mLayoutTask = view.findViewById(R.id.mLayoutTask);
            mLayoutNavigation=view.findViewById(R.id.mLayoutNavigation);

            mLayoutTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TaskDetailActivity.class);
                    intent.putExtra("id", mListTask.get(getAdapterPosition()).getmStrId());
                    context.startActivity(intent);
                }
            });






            mLayoutNavigation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

/*
                    int position = getAdapterPosition();
                    NewTaskAgent.mAssignmentId = mListTask.get(position).getmStrId();
                    String strLat=mListTask.get(position).getmStrLattitude();
                    String strLong=mListTask.get(position).getmStrLongitude();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=" + mGetLatitude + "," + mGetLongitude + "&daddr=" + strLat + "," + strLong));
                    context.startActivity(intent);*/


                    Intent intent = new Intent(context, MoreNavAgent.class);
                    intent.putExtra("name", mListTask.get(getAdapterPosition()).getmStrCustomerName());
                    intent.putExtra("address",mListTask.get(getAdapterPosition()).getmStrResidentialAddress());
                    intent.putExtra("lattitude",mListTask.get(getAdapterPosition()).getmStrLattitude());
                    intent.putExtra("longitude",mListTask.get(getAdapterPosition()).getmStrLongitude());
                    intent.putExtra("id", mListTask.get(getAdapterPosition()).getmStrId());
                    intent.putExtra("dueemi",mListTask.get(getAdapterPosition()).getmStrDueEmi());
                    intent.putExtra("emiamount",mListTask.get(getAdapterPosition()).getmStrEmiAmount());
                    intent.putExtra("bankname",mListTask.get(getAdapterPosition()).getmStrBank());
                    context.startActivity(intent);



                }
            });



            mLayoutCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* int position = getAdapterPosition();
                    String number=mListTask.get(position).getmStrMobile().toString();
                    Log.e("agent number",number);
                    System.out.println("<><><>check number "+number);
                    //PreferenceManager.setCUSTOMERNUMBER(context, number);
*/
                    int position = getAdapterPosition();
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE},
                                        100);

                                NewTaskAgent.mAssignmentId = mListTask.get(position).getmStrId();


                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + mListTask.get(position).getmStrMobile()));
                                context.startActivity(callIntent);
                                return;

                            } else {

                                NewTaskAgent.mAssignmentId = mListTask.get(position).getmStrId();



                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + mListTask.get(position).getmStrMobile()));
                                context.startActivity(callIntent);

                            }
                        }


                    } catch (Exception e) {

                    }

                }
            });


        }
    }
}