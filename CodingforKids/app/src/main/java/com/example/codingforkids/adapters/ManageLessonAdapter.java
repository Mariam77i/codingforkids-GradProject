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
import com.example.codingforkids.LoginActivity;
import com.example.codingforkids.R;
import com.example.codingforkids.admin.AddExerciseActivity;
import com.example.codingforkids.admin.ManageLessonsActivity;
import com.example.codingforkids.admin.ManageParentsActivity;
import com.example.codingforkids.admin.UpdateExerciseActivity;
import com.example.codingforkids.admin.UpdateLessonActivity;
import com.example.codingforkids.models.Lesson;
import com.example.codingforkids.teacher.ManageTeacherLessonsActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManageLessonAdapter extends RecyclerView.Adapter<ManageLessonAdapter.ViewHolder> {

    private List<Lesson> mData;
    public Context context;

    public ManageLessonAdapter(Context context, List<Lesson> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup lesson, int viewType) {
        View view = LayoutInflater.from(lesson.getContext())
                .inflate(R.layout.layout4, lesson, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson data = mData.get(position);
        holder.txtName.setText(data.getLesson_title());
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
                        DeleteLesson(v.getContext(), data.getLesson_id());
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.show();
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UpdateLessonActivity.class);
                i.putExtra("id", data.getLesson_id());
                i.putExtra("title", data.getLesson_title());
                i.putExtra("lang", data.getLesson_language());
                i.putExtra("level", data.getLesson_level());
                i.putExtra("topic", data.getLesson_topic());
                i.putExtra("desc", data.getLesson_desc());
                i.putExtra("img", data.getLesson_imageURL());
                i.putExtra("sound", data.getLesson_soundURL());
                i.putExtra("video", data.getLesson_videoURL());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        holder.imgQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UpdateExerciseActivity.class);
                i.putExtra("id", data.getLesson_id());
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
        public ImageView imgDelete, imgEdit, imgQuestion;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgQuestion = itemView.findViewById(R.id.imgQuestion);
        }
    }
    public void DeleteLesson(Context context1, String id){
        class DeleteLSN extends AsyncTask<String, String, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(context1, "Deleting Lesson ..", null,true,true);
            }
            protected String doInBackground(String... args) {
                JsonParser jParser = new JsonParser();
                final String TAG_SUCCESS = "success";
                final String TAG_MESSAGE = "message";
                int success;
                try {
                    // Building Parameters
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("lessonID", id));
                    JSONObject json = jParser.makeHttpRequest(Links.url_delete_lesson_link, "POST", params);
                    success = json.getInt(TAG_SUCCESS);
                    if (success == 1) {
                        loading.dismiss();
                        if(LoginActivity.type.equals("admin")){
                            Intent intent = new Intent(context1, ManageLessonsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            return json.getString(TAG_MESSAGE);
                        }else{
                            Intent intent = new Intent(context1, ManageTeacherLessonsActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                            return json.getString(TAG_MESSAGE);
                        }
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
