package com.example.yajmana;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class LetterHeadFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    TextView textView;
    String title;
    public LetterHeadFragment(String title) {
        // Required empty public constructor
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_letter_head, container, false);
        textView = view.findViewById(R.id.letter_head_title);
        textView.setText(this.title);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.letter_head_fragment, menu);
        MenuItem item = menu.findItem(R.id.close_letter_head);
        item.setOnMenuItemClickListener(menuItem -> {
            String title = getActivity().getResources().getString(R.string.profile_heading);
            ((NavigateActivity)getActivity()).loadFragment(new ProfileFragment(), title);
            return true;
        });
    }
}