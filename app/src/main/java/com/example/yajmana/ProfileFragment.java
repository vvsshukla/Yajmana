package com.example.yajmana;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    TextView fullName, email, mobileNo;
    ImageView imageView1, imageView2, imageView3;
    ProgressBar progressBar;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreateView:", "Inside onCreateView");
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        progressBar = view.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        ((NavigateActivity)getActivity()).setActionBarTitle(getString(R.string.profile_heading));
        fullName = view.findViewById(R.id.profile_name_value);
        mobileNo = view.findViewById(R.id.profile_mobile);
        email = view.findViewById(R.id.profile_email_value);
        ImageView img = view.findViewById(R.id.profileImageView);
        imageView1 = view.findViewById(R.id.adhikar_patra_1);
        imageView2 = view.findViewById(R.id.adhikar_patra_2);
        imageView3 = view.findViewById(R.id.adhikar_patra_3);
        imageView1.setOnClickListener(view12 -> ((NavigateActivity)getActivity()).loadFragment(new LetterHeadFragment(getString(R.string.letterHeadOne)), getString(R.string.letterHeadOne)));
        imageView2.setOnClickListener(view13 -> ((NavigateActivity)getActivity()).loadFragment(new LetterHeadFragment(getString(R.string.letterHeadTwo)), getString(R.string.letterHeadTwo)));
        imageView3.setOnClickListener(view14 -> ((NavigateActivity)getActivity()).loadFragment(new LetterHeadFragment(getString(R.string.letterHeadThree)), getString(R.string.letterHeadThree)));
        Glide.with(this)
                .load(R.drawable.profile_image)
                .into(img);
//        Button exitProfile = view.findViewById(R.id.exitProfile);
//        exitProfile.setOnClickListener(view1 -> ((NavigateActivity)getActivity()).loadFragment(new HomeFragment(),getActivity().getResources().getString(R.string.vanshawal_list)));
        loadProfile(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.profile_menu,menu);
        MenuItem item = menu.findItem(R.id.edit_profile);
        item.setOnMenuItemClickListener(menuItem -> {
            String title = getActivity().getResources().getString(R.string.edit_profile_heading);
            ((NavigateActivity)getActivity()).loadFragment(new EditProfileFragment(), title);
            return true;
        });
    }

    public void loadProfile(View view){
        Log.d("loadProfile:", "Inside loadProfile");
        Call<List<Profile>> call = Api.getClient(this.getActivity()).getProfile();
        call.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {
                Log.e("onResponse", "Inside onResponse:" + response.body());
                Log.e("onResponse", "Inside onResponse:" + new Gson().toJson(response.body()));
                List<Profile> profile = response.body();
                if(profile.get(0).getMobileNo().equals("")){
                   Toast.makeText(getContext(),"Profile does not exist.",Toast.LENGTH_SHORT).show();
                }else{
                   String fullname = profile.get(0).getFirstName() + " " + profile.get(0).getMiddleName() + " " + profile.get(0).getLastName();
                   fullName.setText(fullname);
                   mobileNo.setText(profile.get(0).getMobileNo());
                   email.setText(profile.get(0).getEmail());
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<List<Profile>> call, Throwable t) {
                if(t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
        Log.d("loadProfile", "loadProfile ends.");
    }
}