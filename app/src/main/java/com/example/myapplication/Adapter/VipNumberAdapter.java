package com.example.myapplication.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Model.VipUser;
import com.example.myapplication.R;

import java.util.ArrayList;

public class VipNumberAdapter extends RecyclerView.Adapter<VipNumberAdapter.ViewHolder> {
    private ArrayList<VipUser> vipUsers;
    private OnItemClickListener mListener;
    Activity context;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public VipNumberAdapter(Activity context, ArrayList<VipUser> vipUsers) {
        this.context = context;
        this.vipUsers = vipUsers;
    }

    @Override
    public VipNumberAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vipnumber_adapter, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.name.setText(vipUsers.get(i).getmStrName());
        viewHolder.phonenumber.setText(vipUsers.get(i).getmStrNumber());
    }


    @Override
    public int getItemCount() {
        return vipUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, phonenumber, loantype;
        private Button detailsbtn, call;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.vipname);
            phonenumber = (TextView) view.findViewById(R.id.vipphonenumber);
            call = (Button) view.findViewById(R.id.call);


            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    //  VipNumberActivity.assignment_id=vipNumbers.get(position).getId();

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + vipUsers.get(position).getmStrNumber()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling


                        return;
                    }
                    context.startActivity(callIntent);

                }
            });



        }
    }






}