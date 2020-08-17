package com.example.yajmana;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.yajmana.ui.login.LoginActivity;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class EditProfileFragment extends Fragment {

    EditText firstName, middleName, lastName, profileMobile, profileEmail;
    Button saveProfile, cancelEditProfile;
    String first_name, middle_name, last_name, profile_mobile, profile_email;

    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("onCreateView", "Inside EditProfileFragment onCreateView()");
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ImageView img = view.findViewById(R.id.profileImageView);
        Glide.with(this)
                .load(R.drawable.profile_image)
                .into(img);
        firstName = view.findViewById(R.id.first_name);
        middleName = view.findViewById(R.id.middle_name);
        lastName = view.findViewById(R.id.last_name);
        profileEmail = view.findViewById(R.id.profile_email);
        profileMobile = view.findViewById(R.id.profile_mobile);

        populateProfile();

        saveProfile = view.findViewById(R.id.saveProfile);
        cancelEditProfile = view.findViewById(R.id.cancelEditProfile);
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save_profile();
            }
        });
        cancelEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel_edit();
            }
        });
        return view;
    }

    private void populateProfile(){
        Log.d("populateProfile:", "Inside populateProfile()");
        Call<List<Profile>> call = Api.getClient().getProfile();
        call.enqueue(new Callback<List<Profile>>() {
                @Override
                public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                    Log.e("onResponse", "Inside onResponse:" + new Gson().toJson(response.body()));
                    List<Profile> profile = response.body();
                    if(profile.get(0).getMobileNo().equals("")){
                        Toast.makeText(getContext(),"Profile does not exist.",Toast.LENGTH_SHORT).show();
                    }else{
                        firstName.setText(profile.get(0).getFirstName());
                        middleName.setText(profile.get(0).getMiddleName());
                        lastName.setText(profile.get(0).getLastName());
                        profileMobile.setText(profile.get(0).getMobileNo());
                        profileEmail.setText(profile.get(0).getEmail());
                    }
                }
                @Override
                public void onFailure(Call<List<Profile>> call, Throwable t) {
                    Log.d("onFailure:", "Error:"+ t.toString());
                }
        });
    }

    private void save_profile() {
        Log.d("save_profile","Inside save_profile()");
        first_name = firstName.getText().toString();
        middle_name = middleName.getText().toString();
        last_name = lastName.getText().toString();
        profile_email = profileEmail.getText().toString();
        profile_mobile = profileMobile.getText().toString();

        Log.d("first_name", first_name);
        Log.d("middle_name", middle_name);
        Log.d("last_name", last_name);
        Log.d("profile_email", profile_email);
        Log.d("profile_mobile", profile_mobile);

        Call<LoginResponse> call = Api.getClient().updateProfile(first_name, middle_name, last_name, profile_mobile, profile_email);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("updateProfile Response", "Status: " + response.body().getSuccess() + ", Message: "+ response.body().getMessage());
                if (response.body().getSuccess().equals("1")){
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.edit_profile_success_message),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.edit_profile_failure_message), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
    }

    private void cancel_edit(){
        getParentFragmentManager().popBackStack();
    }
}