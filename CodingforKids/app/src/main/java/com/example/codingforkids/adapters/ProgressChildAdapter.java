package com.example.codingforkids.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codingforkids.JsonParser;
import com.example.codingforkids.Links;
import com.example.codingforkids.R;
import com.example.codingforkids.models.Child;
import com.example.codingforkids.parent.ChildProgressActivity;
import com.example.codingforkids.parent.ManageChildActivity;
import com.example.codingforkids.parent.UpdateChildActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProgressChildAdapter extends RecyclerView.Adapter<ProgressChildAdapter.ViewHolder> {

    private List<Child> mData;
    public Context context;
    public ProgressChildAdapter(Context context, List<Child> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup child, int viewType) {
        View view = LayoutInflater.from(child.getContext())
                .inflate(R.layout.layout10, child, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Child data = mData.get(position);
        holder.txtName.setText(data.getChildName());

        holder.imgProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ChildProgressActivity.class);
                i.putExtra("id", data.getChildID());
                i.putExtra("name", data.getChildName());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public ImageView imgProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgProgress = itemView.findViewById(R.id.imgProgress);
        }
    }

}
