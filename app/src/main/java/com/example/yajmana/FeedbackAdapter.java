package com.example.yajmana;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    private List<Feedback> feedbackList;
    private Context context;

    public FeedbackAdapter(List<Feedback> feedbackList, Context context) {
        this.feedbackList = feedbackList;
        this.context = context;
        Log.e("feedbackList","Adapter List:"+ new Gson().toJson(this.feedbackList));
    }

    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.feedback_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position) {
            Feedback feedback = feedbackList.get(position);
            holder.feedback_fullname.setText(feedback.getFullName());
            holder.feedback_content.setText(feedback.getContent());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView feedback_fullname, feedback_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            feedback_fullname = itemView.findViewById(R.id.yajmana_fullname);
            feedback_content = itemView.findViewById(R.id.feedback_content);
        }
    }
}
