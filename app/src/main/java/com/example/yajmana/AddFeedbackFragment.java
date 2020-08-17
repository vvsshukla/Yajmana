package com.example.yajmana;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFeedbackFragment extends Fragment {

    Button feedbackSave, feedbackCancel;
    EditText vanshawalCodeText;
    EditText contentText;
    private String feedbackContent;
    private String vanshawalCode;
    public AddFeedbackFragment() {}
    public AddFeedbackFragment( String vanshawalCode ) {
        this.vanshawalCode = vanshawalCode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_feedback, container, false);
        feedbackSave = view.findViewById(R.id.feedback_save);
        feedbackCancel = view.findViewById(R.id.feedback_cancel);
        vanshawalCodeText=view.findViewById(R.id.vanshawal_no);
        vanshawalCodeText.setText(this.vanshawalCode);
        contentText = view.findViewById(R.id.content_text);
        feedbackSave.setOnClickListener(view1 -> saveFeedback());
        feedbackCancel.setOnClickListener(view12 -> cancelFeedback());
        return view;
    }

    private void saveFeedback() {
        vanshawalCode = vanshawalCodeText.getText().toString();
        feedbackContent = contentText.getText().toString();
        Log.d("vanshwalCode",vanshawalCode);
        Log.d("content",feedbackContent);
        Call<LoginResponse> call = Api.getClient().addFeedback(vanshawalCode, feedbackContent);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("Response", "Status: " + response.body().getSuccess() + ", Message: "+ response.body().getMessage());
                if (response.body().getSuccess().equals("1")){
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.feedback_added), Toast.LENGTH_SHORT).show();
                    ((NavigateActivity)getActivity()).loadFragment(new FeedbackFragment(), getString(R.string.feedback_heading));
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.feedback_not_added), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
    }

    private void cancelFeedback() {
        getParentFragmentManager().popBackStack();
    }

}