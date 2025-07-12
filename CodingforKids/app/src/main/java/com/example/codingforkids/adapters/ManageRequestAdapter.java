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
import com.example.codingforkids.admin.ChangeRequestStatusActivity;
import com.example.codingforkids.admin.ManageLessonsActivity;
import com.example.codingforkids.admin.ManageRequestsActivity;
import com.example.codingforkids.models.LessonRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageRequestAdapter extends RecyclerView.Adapter<ManageRequestAdapter.ViewHolder> {
    private List<LessonRequest> mData;
    public Context context;

    public ManageRequestAdapter(Context context, List<LessonRequest> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup lessonRequest, int viewType) {
        View view = LayoutInflater.from(lessonRequest.getContext())
                .inflate(R.layout.layout5, lessonRequest, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LessonRequest data = mData.get(position);
        holder.txtName.setText(data.getLesson_title());
        holder.txtStatus.setText(data.getRequest_lang() + " (" + data.getRequest_type() + " Request)");
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.delete);
                builder.setMessage(R.string.confirm_delete);
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Remove the item from the RecyclerView
                        DeleteLessonRequest(v.getContext(), data.getRequest_id());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ChangeRequestStatusActivity.class);
                i.putExtra("id", data.getRequest_id());
                i.putExtra("title", data.getLesson_title());
                i.putExtra("lang", data.getRequest_lang());
                i.putExtra("topic", data.getRequest_topic());
                i.putExtra("desc", data.getRequest_desc());
                i.putExtra("state", data.getRequest_status());
                i.putExtra("type", data.getRequest_type());
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

        public TextView txtName, txtStatus;
        public ImageView imgDelete, imgEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
        }
    }
    public void DeleteLessonRequest(Context context1, String id){
        class DeleteLSN extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Request ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                JsonParser jParser = new JsonParser();
                final String TAG_SUCCESS = "success";
                final String TAG_MESSAGE = "message";
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("reqID", id));
                    JSONObject json = jParser.makeHttpRequest(Links.url_delete_request_link, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        Intent intent = new Intent(context1, ManageRequestsActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        return json.getString(TAG_MESSAGE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        DeleteLSN ui = new DeleteLSN();
        ui.execute();
    }
}
