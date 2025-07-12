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
import com.example.codingforkids.admin.AddExerciseActivity;
import com.example.codingforkids.admin.ManageTeacherActivity;
import com.example.codingforkids.admin.UpdateLessonActivity;
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

public class ManageChildAdapter extends RecyclerView.Adapter<ManageChildAdapter.ViewHolder> {

    private List<Child> mData;
    public Context context;
    JsonParser jParser = new JsonParser();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";

    public ManageChildAdapter(Context context, List<Child> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup child, int viewType) {
        View view = LayoutInflater.from(child.getContext())
                .inflate(R.layout.layout6, child, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Child data = mData.get(position);
        holder.txtName.setText(data.getChildName());
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
                        DeleteChild(v.getContext(), data.getChildID());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UpdateChildActivity.class);
                i.putExtra("id", data.getChildID());
                i.putExtra("name", data.getChildName());
                i.putExtra("email", data.getChildEmail());
                i.putExtra("username", data.getChildUsername());
                i.putExtra("pass", data.getChildPass());
                i.putExtra("age", data.getChildAge());
                i.putExtra("gender", data.getChildGender());
                i.putExtra("edu", data.getChildEdu());
                i.putExtra("parentID", data.getChildParentID());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
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
        public ImageView imgDelete, imgEdit, imgProgress;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgProgress = itemView.findViewById(R.id.imgProgress);
        }
    }
    public void DeleteChild(Context context1, String id){
        class DeleteCH extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Child ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("childID", id));
                    JSONObject json = jParser.makeHttpRequest(Links.url_delete_child_link, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        Intent intent = new Intent(context1, ManageChildActivity.class);
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
        DeleteCH ui = new DeleteCH();
        ui.execute();
    }
}
