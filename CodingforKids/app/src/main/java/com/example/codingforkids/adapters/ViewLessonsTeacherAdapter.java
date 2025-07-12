package com.example.codingforkids.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codingforkids.LoginActivity;
import com.example.codingforkids.R;
import com.example.codingforkids.admin.UpdateLessonActivity;
import com.example.codingforkids.models.Lesson;
import com.example.codingforkids.parent.ViewLessonDetailsActivity;
import com.example.codingforkids.teacher.UpdateLessonRequestActivity;
import com.example.codingforkids.teacher.UpdateRequestDetailsActivity;

import java.util.List;

public class ViewLessonsTeacherAdapter extends RecyclerView.Adapter<ViewLessonsTeacherAdapter.ViewHolder> {

    private List<Lesson> mData;
    public Context context;
    String strID;

    public ViewLessonsTeacherAdapter(Context context, List<Lesson> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup lesson, int viewType) {
        View view = LayoutInflater.from(lesson.getContext())
                .inflate(R.layout.layout8, lesson, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Lesson data = mData.get(position);
        holder.txtName.setText(data.getLesson_title());
        holder.txtLang.setText(data.getLesson_language());
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
        holder.imgUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), UpdateRequestDetailsActivity.class);
                i.putExtra("title", data.getLesson_title());
                i.putExtra("lang", data.getLesson_language());
                i.putExtra("topic", data.getLesson_topic());
                i.putExtra("desc", data.getLesson_desc());
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

        public TextView txtName, txtLang;
        public ImageView imgView, imgUpdate;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtLang = itemView.findViewById(R.id.txtLang);
            imgUpdate = itemView.findViewById(R.id.imgUpdate);
            imgView = itemView.findViewById(R.id.imgView);
        }
    }
}
