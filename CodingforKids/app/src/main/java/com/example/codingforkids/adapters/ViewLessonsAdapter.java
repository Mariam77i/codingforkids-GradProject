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
import com.example.codingforkids.admin.ManageLessonsActivity;
import com.example.codingforkids.admin.UpdateLessonActivity;
import com.example.codingforkids.models.Lesson;
import com.example.codingforkids.parent.ViewLessonDetailsActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewLessonsAdapter extends RecyclerView.Adapter<ViewLessonsAdapter.ViewHolder> {

    private List<Lesson> mData;
    public Context context;

    public ViewLessonsAdapter(Context context, List<Lesson> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup lesson, int viewType) {
        View view = LayoutInflater.from(lesson.getContext())
                .inflate(R.layout.layout7, lesson, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson data = mData.get(position);
        holder.txtName.setText(data.getLesson_title());
        holder.txtLang.setText(data.getLesson_language());
        holder.txtLevel.setText(data.getLesson_level());
        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ViewLessonDetailsActivity.class);
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
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtLang, txtLevel;
        public ImageView imgView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtTitle);
            txtLang = itemView.findViewById(R.id.txtLang);
            txtLevel = itemView.findViewById(R.id.txtLevel);
            imgView = itemView.findViewById(R.id.imgView);
        }
    }
}
