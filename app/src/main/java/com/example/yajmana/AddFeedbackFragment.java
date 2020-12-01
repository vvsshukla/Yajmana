package com.example.yajmana;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
    protected View fView;
    private ProgressBar loader;
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
        this.fView = view;
        feedbackSave = view.findViewById(R.id.feedback_save);
        feedbackCancel = view.findViewById(R.id.feedback_cancel);
        fullNameText = view.findViewById(R.id.full_name);
        vanshawalCodeText = view.findViewById(R.id.vanshawal_no);
        contentText = view.findViewById(R.id.content_text);
        loader = view.findViewById(R.id.loader);
        if((this.vanshawalCode!=null && !this.vanshawalCode.trim().isEmpty()) && (this.fullName!=null && !this.fullName.trim().isEmpty())){
            vanshawalCodeText.setText(this.vanshawalCode);
            fullNameText.setText(this.fullName);
        }
        feedbackSave.setOnClickListener(view1 -> saveFeedback());
        feedbackCancel.setOnClickListener(view12 -> cancelFeedback());
        getBack();
        return view;
    }

    public boolean isValidInput(){
        fullName = fullNameText.getText().toString();
        vanshawalCode = vanshawalCodeText.getText().toString();
        feedbackContent = contentText.getText().toString();
        Log.d("validation_feednack", fullName+":"+vanshawalCode+":"+feedbackContent);
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
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
            Log.d("keyboard","Dismissed");
        }catch (Exception e){
            Log.e("Exception:", e.getMessage());
        }
        boolean isInputValid = isValidInput();
        if(isInputValid){
            ((NavigateActivity)getActivity()).disableUserInteraction();
            Call<LoginResponse> call = Api.getClient(this.getActivity()).addFeedback(fullName, vanshawalCode, feedbackContent);
            loader.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    loader.setVisibility(View.GONE);
                    Log.d("Response", "Status: " + response.body().getSuccess() + ", Message: "+ response.body().getMessage());
                    if (response.body().getSuccess().equals("1")){
                       Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                       ((NavigateActivity)getActivity()).loadFragment(new FeedbackFragment(), getString(R.string.feedback_heading));
                    } else if(response.body().getSuccess().equals("-1")){
                        Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    ((NavigateActivity)getActivity()).enableUserInteraction();
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    loader.setVisibility(View.GONE);
                    if(t instanceof NoConnectivityException) {
                        // show No Connectivity message to user or do whatever you want.
                        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    Log.d("onFailure:", "Error:"+ t.toString());
                    ((NavigateActivity)getActivity()).enableUserInteraction();
                }
            });
        } else {
            ((NavigateActivity)getActivity()).enableUserInteraction();
        }
    }

    private void cancelFeedback() {
        if(this.vanshawalCode!=null && !this.vanshawalCode.trim().isEmpty()){
           ((NavigateActivity)getActivity()).loadFragment(new HomeFragment(), getString(R.string.vanshawal_heading));
        }else{
           ((NavigateActivity)getActivity()).loadFragment(new FeedbackFragment(), getString(R.string.feedback_heading));
        }
    }

    public void getBack(){
        Fragment fragment;
        String title;
        if(this.vanshawalCode!=null && !this.vanshawalCode.trim().isEmpty()){
            fragment = new HomeFragment();//Previous fragment is HomeFragment
            title = getString(R.string.vanshawal_list);
        } else {
            fragment = new FeedbackFragment();
            title = getString(R.string.feedback_heading);
        }
        fView.setFocusableInTouchMode(true);
        fView.requestFocus();
        fView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                  Log.d("onKey","getBack");
//                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        Log.d("KEYCODE_BACK","KEYCODE_BACK");
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        ((NavigateActivity)getActivity()).loadFragment(fragment, title);
                    }
//                }
                return true;
            }
        });
    }

}