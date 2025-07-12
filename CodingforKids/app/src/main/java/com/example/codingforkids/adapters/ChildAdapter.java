package com.example.codingforkids.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.codingforkids.R;
import com.example.codingforkids.models.ChildData;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder>{
    private List<ChildData> mData;
    public Context context;

    public ChildAdapter(Context context, List<ChildData> data) {
        mData = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup child, int viewType) {
        View view = LayoutInflater.from(child.getContext())
                .inflate(R.layout.layout2, child, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChildData data = mData.get(position);
        holder.txtName.setText(data.getChildName());
        holder.txtScore.setText("Score: " + data.getChildScore());
        holder.txtLevel.setText(data.getChildLevel());
        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog card_child = new Dialog(context);
                card_child.setContentView(R.layout.card_child);
                ImageView btnClose = card_child.findViewById(R.id.btn_close);
                TextView card_txtName = card_child.findViewById(R.id.txtName);
                TextView card_txtEmail = card_child.findViewById(R.id.txtEmail);
                TextView card_txtAge = card_child.findViewById(R.id.txtAge);
                TextView card_txtEdu = card_child.findViewById(R.id.txtEdu);
                TextView card_txtGender = card_child.findViewById(R.id.txtGender);

                card_child.show();
                card_txtName.setText(data.getChildName());
                card_txtEmail.setText(data.getChildEmail());
                card_txtAge.setText("Age: " + data.getChildAge());
                card_txtEdu.setText("Education: " + data.getChildEdu());
                card_txtGender.setText(data.getChildGender());

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card_child.hide();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName, txtScore, txtLevel;

        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtScore = itemView.findViewById(R.id.txtScore);
            txtLevel = itemView.findViewById(R.id.txtLevel);
        }
    }

}
