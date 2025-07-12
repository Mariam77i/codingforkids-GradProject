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
import com.example.codingforkids.admin.ManageExercisesActivity;
import com.example.codingforkids.admin.ManageLessonsActivity;
import com.example.codingforkids.admin.UpdateExerciseActivity;
import com.example.codingforkids.models.Exercise;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageExerciseAdapter extends RecyclerView.Adapter<ManageExerciseAdapter.ViewHolder> {

    private List<Exercise> mData;
    public Context context;

    public ManageExerciseAdapter(Context context, List<Exercise> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup exercise, int viewType) {
        View view = LayoutInflater.from(exercise.getContext())
                .inflate(R.layout.layout5, exercise, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercise data = mData.get(position);
        holder.txtName.setText(data.getExercise_title());
        holder.txtLesson.setText(data.getLesson_title() + "(" + data.getExercise_lang() + ")");
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
                        DeleteExercise(v.getContext(), data.getExercise_id());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UpdateExerciseActivity.class);
                i.putExtra("id", data.getExercise_id());
                i.putExtra("title", data.getExercise_title());
                i.putExtra("lang", data.getExercise_lang());
                i.putExtra("correct", data.getExercise_answer());
                i.putExtra("img1", data.getExercise_image1());
                i.putExtra("img2", data.getExercise_image2());
                i.putExtra("img3", data.getExercise_image3());
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

        public TextView txtName, txtLesson;
        public ImageView imgDelete, imgEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            txtLesson = itemView.findViewById(R.id.txtStatus);
        }
    }
    public void DeleteExercise(Context context1, String id){
        class DeleteEXR extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Exercise ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                JsonParser jParser = new JsonParser();
                final String TAG_SUCCESS = "success";
                final String TAG_MESSAGE = "message";
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("exerciseID", id));
                    JSONObject json = jParser.makeHttpRequest(Links.url_delete_exercise_link, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        Intent intent = new Intent(context1, ManageExercisesActivity.class);
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
        DeleteEXR ui = new DeleteEXR();
        ui.execute();
    }
}
