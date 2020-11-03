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
    EditText fullNameText;
    EditText contentText;
    private String feedbackContent;
    private String vanshawalCode;
    private String fullName;
    public AddFeedbackFragment() {}
    public AddFeedbackFragment( String vanshawalCode, String fullName ) {
        this.vanshawalCode = vanshawalCode;
        this.fullName = fullName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_feedback, container, false);
        feedbackSave = view.findViewById(R.id.feedback_save);
        feedbackCancel = view.findViewById(R.id.feedback_cancel);
        fullNameText = view.findViewById(R.id.full_name);
        vanshawalCodeText = view.findViewById(R.id.vanshawal_no);
        contentText = view.findViewById(R.id.content_text);
        if((this.vanshawalCode!=null && !this.vanshawalCode.trim().isEmpty()) && (this.fullName!=null && !this.fullName.trim().isEmpty())){
            vanshawalCodeText.setText(this.vanshawalCode);
            fullNameText.setText(this.fullName);
        }
        feedbackSave.setOnClickListener(view1 -> saveFeedback());
        feedbackCancel.setOnClickListener(view12 -> cancelFeedback());
        return view;
    }

    public boolean isValidInput(){
        fullName = fullNameText.getText().toString();
        vanshawalCode = vanshawalCodeText.getText().toString();
        feedbackContent = contentText.getText().toString();
        Log.d("fullName",fullName);
        Log.d("vanshwalCode",vanshawalCode);
        Log.d("content",feedbackContent);
        if(fullName==null || fullName.trim().isEmpty()){
           Toast.makeText(getActivity().getApplicationContext(), getString(R.string.vanshawal_no_validation), Toast.LENGTH_SHORT).show();
           return false;
        }else if(feedbackContent == null || feedbackContent.trim().isEmpty()){
           Toast.makeText(getActivity().getApplicationContext(), getString(R.string.feedback_content_validation), Toast.LENGTH_SHORT).show();
           return false;
        }
        return true;
    }

    private void saveFeedback() {
        boolean isInputValid = isValidInput();
        if(isInputValid){
            Call<LoginResponse> call = Api.getClient().addFeedback(fullName, vanshawalCode, feedbackContent);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Log.d("Response", "Status: " + response.body().getSuccess() + ", Message: "+ response.body().getMessage());
                    if (response.body().getSuccess().equals("1")){
                       Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                       ((NavigateActivity)getActivity()).loadFragment(new FeedbackFragment(), getString(R.string.feedback_heading));
                    } else if(response.body().getSuccess().equals("-1")){
                        Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("onFailure:", "Error:"+ t.toString());
                }
            });
        }
    }

    private void cancelFeedback() {
        getParentFragmentManager().popBackStack();
    }

}