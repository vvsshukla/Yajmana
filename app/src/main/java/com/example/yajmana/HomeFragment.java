package com.example.yajmana;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yajmana.ui.login.LoginActivity;
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
    View rootView;
    MenuItem item;

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
        rootView = view.findViewById(R.id.rootLayout);
        ((NavigateActivity)getContext()).setActionBarTitle(getString(R.string.vanshawal_list));
        progressBar = view.findViewById(R.id.loading);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView = view.findViewById(R.id.recyclerview);
        emptyText = view.findViewById(R.id.emptyText);
        setHasOptionsMenu(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.d("FetchData", "Fetching Vanshawal");
        Call<List<Vanshawal>> call = Api.getClient(this.getActivity()).getVanshawal();// Get vanshawal data
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
                if(t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                }
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
        menu.clear();
        inflater.inflate(R.menu.list_menu,menu);
        item = menu.findItem(R.id.app_bar_search);
        if(item!=null){
//            searchView = new SearchView(((NavigateActivity)getContext()).getSupportActionBar().getThemedContext());
            searchView = (SearchView) item.getActionView();
            searchView.setIconifiedByDefault(true);
//            searchView.setSubmitButtonEnabled(true);
            searchView.setQueryHint(getString(R.string.vanshawal_no_hint));
            searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
            item.setActionView(searchView);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    Log.d("keyboard","Dismissed");
                } catch (Exception e){
                    Log.e("exception", e.getMessage());
                }
                if(s.equals("")){
                    Toast.makeText(getContext(),"वंशावळ क्रमांक भरणे अत्यावश्यक.", Toast.LENGTH_SHORT).show();
                    return false;
                } else if(!s.equals("") && ((s.length() < 3) || (s.length() > 6))){
                    Toast.makeText(getContext(),"३ ते ६ अंकी वंशावळ क्र टाका.", Toast.LENGTH_SHORT).show();
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
        super.onCreateOptionsMenu(menu, inflater);
//        return;
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
        Call<List<Vanshawal>> call = Api.getClient(this.getActivity()).searchByVanshawalCode(s);// Get searched data
        call.enqueue(new Callback<List<Vanshawal>>() {
            @Override
            public void onResponse(Call<List<Vanshawal>> call, Response<List<Vanshawal>> response) {
                List<Vanshawal> vanshawal = response.body();
                if(response.body()!=null){
                   Log.d("Vanshawal JSON",new Gson().toJson(response.body()));
                   VanshawalAdapter vanshawalAdapter = new VanshawalAdapter(getContext(), vanshawal);
                   emptyText.setVisibility(View.INVISIBLE);
                   vanshawalAdapter.notifyDataSetChanged();
                   recyclerView.setAdapter(vanshawalAdapter); // set the Adapter to RecyclerView
                   progressBar.setVisibility(View.GONE);
                }else{
                   Log.d("Vanshawal JSON Empty",new Gson().toJson(response.body()));
                   recyclerView.setVisibility(View.GONE);
                   progressBar.setVisibility(View.GONE);
                   emptyText.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Vanshawal>> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });

        searchView.setIconified(true);
        searchView.onActionViewCollapsed();
        getBack();
    }

    public void open_add_record(View view){
        Fragment fragment = new ScrollingFragment();
        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    public void getBack(){
        Context context = this.getActivity();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    Log.d("action_down", "onKey: Hi");
                    searchView.clearFocus();
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        searchView.setIconified(true);
                        searchView.onActionViewCollapsed();
                        progressBar.setVisibility(View.VISIBLE);
                        if(recyclerView.getVisibility() != View.VISIBLE){
                           recyclerView.setVisibility(View.VISIBLE);
                        }
                        if(emptyText.getVisibility() == View.VISIBLE){
                            emptyText.setVisibility(View.GONE);
                        }
                        Call<List<Vanshawal>> call = Api.getClient(context).getVanshawal();// Get vanshawal data
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
                    }
                }
                return true;
            }
        });
    }
}