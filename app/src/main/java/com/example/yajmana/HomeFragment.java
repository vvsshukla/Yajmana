package com.example.yajmana;
import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    List<Vanshawal> vanshawal;
    ProgressBar progressBar;
    SearchView searchView;
    RecyclerView recyclerView;
    TextView emptyText;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("onCreateView", "Inside onCreateView()");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((NavigateActivity)getContext()).setActionBarTitle(getString(R.string.vanshawal_list));
        progressBar = view.findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recyclerview);
        emptyText = view.findViewById(R.id.emptyText);
        setHasOptionsMenu(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.d("FetchData", "Fetching Vanshawal");
        Call<List<Vanshawal>> call = Api.getClient().getVanshawal();// Get vanshawal data
        call.enqueue(new Callback<List<Vanshawal>>() {
            @Override
            public void onResponse(Call<List<Vanshawal>> call, Response<List<Vanshawal>> response) {
                List<Vanshawal> vanshawal = response.body();
                Log.e("Vanshawal JSON:",new Gson().toJson(response.body()));
                VanshawalAdapter vanshawalAdapter = new VanshawalAdapter(getContext(), vanshawal);// Call the constructor of vanshawalAdapter to send the reference and data to Adapter
                vanshawalAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(vanshawalAdapter); //set the Adapter to RecyclerView
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Vanshawal>> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(view1 -> {
             open_add_record(view);
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.list_menu,menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        Log.d("app_bar_search", "Inside app_bar_search");
        searchView = new SearchView(((NavigateActivity)getContext()).getSupportActionBar().getThemedContext());
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint(getString(R.string.vanshawal_no_hint));
        item.setActionView(searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("onQueryTextSubmit", "Inside onQueryTextSubmit");
                if(s.equals("")){
                    Toast.makeText(getContext(),"वंशावळ क्रमांक भरणे अत्यावश्यक.", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(!s.equals("") && s.length() != 6){
                    Toast.makeText(getContext(),"वंशावळ क्रमांक अवैध.", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    searchByVanshawalCode(s);
                    return true;
                }
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("onQueryTextChange", "Inside onQueryTextChange");
                return true;
            }

        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return true;
            }
        });
        item.expandActionView();
        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                emptyText.setVisibility(View.INVISIBLE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                searchView.setQuery("", false);
                searchView.setIconified(true);
                return true;
            }
        });
        return;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("item_selected", String.valueOf(item.getItemId()));
        switch(item.getItemId()){
            case R.id.advanced_search_filter:
                Log.d("loading","Search fragment loading.");
                Fragment fragment = new SearchFragment();
                getParentFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, fragment).
                        commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchByVanshawalCode(String s) {
        Log.d("search_key",s);
        Call<List<Vanshawal>> call = Api.getClient().searchByVanshawalCode(s);// Get searched data
        call.enqueue(new Callback<List<Vanshawal>>() {
            @Override
            public void onResponse(Call<List<Vanshawal>> call, Response<List<Vanshawal>> response) {
                List<Vanshawal> vanshawal = response.body();
                if(response.body()!=null){
                    Log.e("Vanshawal JSON",new Gson().toJson(response.body()));
                    VanshawalAdapter vanshawalAdapter = new VanshawalAdapter(getContext(), vanshawal);
                    emptyText.setVisibility(View.INVISIBLE);
                    vanshawalAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(vanshawalAdapter); // set the Adapter to RecyclerView
                    progressBar.setVisibility(View.GONE);
                }else{
                    emptyText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Vanshawal>> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
    }

    public void open_add_record(View view){
        Log.d("loading","Scrolling fragment loading.");
        Fragment fragment = new ScrollingFragment();
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        Log.d("loaded","Scrolling fragment loaded.");
    }
}