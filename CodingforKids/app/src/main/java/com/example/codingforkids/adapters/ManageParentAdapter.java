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
import com.example.codingforkids.admin.ManageParentsActivity;
import com.example.codingforkids.models.Parent;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageParentAdapter extends RecyclerView.Adapter<ManageParentAdapter.ViewHolder> {

    private List<Parent> mData;
    public Context context;
    JsonParser jParser = new JsonParser();
    final String TAG_SUCCESS = "success";
    final String TAG_MESSAGE = "message";

    public ManageParentAdapter(Context context, List<Parent> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout3, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Parent data = mData.get(position);
        holder.txtName.setText(data.getParentName());
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
                        DeleteParent(v.getContext(), data.getParentID());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public ImageView imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }
    }
    public void DeleteParent(Context context1, String id){
        class DeletePRT extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Parent ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("parentID", id));
                    JSONObject json = jParser.makeHttpRequest(Links.url_delete_parent_link, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        Intent intent = new Intent(context1, ManageParentsActivity.class);
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
        DeletePRT ui = new DeletePRT();
        ui.execute();
    }
}
