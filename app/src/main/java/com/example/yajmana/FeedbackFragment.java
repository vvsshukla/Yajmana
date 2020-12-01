package com.example.yajmana;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedbackFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Feedback> feedbacks;
    private FloatingActionButton fab_add_feedback;
    private ProgressBar progressBar;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ((NavigateActivity)getActivity()).setActionBarTitle(getString(R.string.feedback_heading));
        recyclerView = view.findViewById(R.id.feedback_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        loadRecyclerViewData();
        fab_add_feedback = view.findViewById(R.id.fab_add_feedback);
        fab_add_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_new_feedback();
            }
        });
        return view;
    }

    private void loadRecyclerViewData() {
        Call<List<Feedback>> call = Api.getClient(this.getActivity()).getFeedback();
        call.enqueue(new Callback<List<Feedback>>() {
            @Override
            public void onResponse(Call<List<Feedback>> call, Response<List<Feedback>> response) {
                feedbacks = response.body();
                Log.e("Feedback JSON:",new Gson().toJson(response.body()));
                adapter = new FeedbackAdapter(feedbacks, getContext());
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Feedback>> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
                if(t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void open_new_feedback() {
        ((NavigateActivity)getActivity()).loadFragment(new AddFeedbackFragment(),getString(R.string.new_feedback));
    }
}