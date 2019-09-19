package com.example.myapplication.ModuleTelecaller.Adapter;

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

import com.example.myapplication.Activities.TaskDetailActivity;
import com.example.myapplication.Helper.PreferenceManager;
import com.example.myapplication.Model.UserTask;
import com.example.myapplication.ModuleTelecaller.Activity.NewTaskTelecaller;
import com.example.myapplication.R;
import java.util.ArrayList;


public class NewTaskAdapterTelecaller extends RecyclerView.Adapter<NewTaskAdapterTelecaller.ViewHolder> {
    ArrayList<UserTask> mListTask;

    private OnItemClickListener mListener;
    Activity context;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public NewTaskAdapterTelecaller(Activity context, ArrayList<UserTask> mListTask) {
        this.context = context;
        this.mListTask = mListTask;

    }

    @Override
    public NewTaskAdapterTelecaller.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_adapter_layout, viewGroup, false);
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
        RelativeLayout mLayoutCall, mLayoutTask;

        public ViewHolder(View view) {
            super(view);
            mTextCutomerName = view.findViewById(R.id.mTextCutomerName);
            mTextBankName = view.findViewById(R.id.mTextBankName);
            mTextLoanType = view.findViewById(R.id.mTextLoanType);
            mLayoutCall = view.findViewById(R.id.mLayoutCall);
            mLayoutTask = view.findViewById(R.id.mLayoutTask);

            mLayoutTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TaskDetailActivity.class);
                    intent.putExtra("id", mListTask.get(getAdapterPosition()).getmStrId());
                    context.startActivity(intent);
                }
            });

            mLayoutCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     int position = getAdapterPosition();
                    String number=mListTask.get(position).getmStrMobile().toString();
                    Log.e("agent number",number);
                    System.out.println("<><><>check number "+number);
                    PreferenceManager.setCUSTOMERNUMBER(context, number);


                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE},
                                        100);

                                NewTaskTelecaller.mAssignmentId = mListTask.get(position).getmStrId();
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + mListTask.get(position).getmStrMobile()));
                                context.startActivity(callIntent);
                                return;

                            } else {

                                NewTaskTelecaller.mAssignmentId = mListTask.get(position).getmStrId();
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