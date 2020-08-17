package com.example.yajmana;

import android.app.Activity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.Gson;

public class SearchFragment extends Fragment {
    Button btnSearch,btnCancelSearch;
    RecyclerView recyclerView;
    String late_firstname, late_middlename, late_lastname, caste, city, taluka, district, death_anniversary, mobile;
    RelativeLayout search_params;
    private TextView emptyText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        recyclerView = view.findViewById(R.id.search_result);
        recyclerView.setVisibility(View.INVISIBLE);
        search_params = view.findViewById(R.id.search_parameters);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnCancelSearch = view.findViewById(R.id.btnCancelSearch);
        btnSearch.setOnClickListener(view1 -> search_validation(view));
        btnCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(view);
            }
        });
        emptyText = view.findViewById(R.id.emptyText);
        emptyText.setVisibility(View.INVISIBLE);
        return view;
    }

    public void search_validation( View view ){
        Log.d("View first_name:", String.valueOf(R.id.first_name));
        EditText firstname = view.findViewById(R.id.first_name);
        EditText middlename = view.findViewById(R.id.middle_name);
        EditText lastname = view.findViewById(R.id.last_name);
        EditText casteText = view.findViewById(R.id.caste);
        EditText cityText = view.findViewById(R.id.city);
        EditText talukaText = view.findViewById(R.id.taluka);
        EditText districtText = view.findViewById(R.id.district);
        EditText deathAnniversaryText = view.findViewById(R.id.death_anniversary);
        EditText mobileNoText = view.findViewById(R.id.mobile_no);

        late_firstname = firstname.getText().toString().trim();
        late_middlename = middlename.getText().toString().trim();
        late_lastname = lastname.getText().toString().trim();
        caste = casteText.getText().toString().trim();
        city = cityText.getText().toString().trim();
        taluka = talukaText.getText().toString().trim();
        district = districtText.getText().toString().trim();
        death_anniversary = deathAnniversaryText.getText().toString().trim();
        mobile = mobileNoText.getText().toString().trim();

        Log.d("first_name", late_firstname);
        Log.d("middle_name", late_middlename);
        Log.d("last_name", late_lastname);
        Log.d("caste", caste);
        Log.d("city", city);
        Log.d("taluka", taluka);
        Log.d("district", district);
        Log.d("death_anniversary", death_anniversary);
        Log.d("mobile_no", mobile);

        if( !late_firstname.equals("") || !late_middlename.equals("") || !late_lastname.equals("") || !caste.equals("") || !city.equals("") || !taluka.equals("") || !district.equals("") || !death_anniversary.equals("") || !mobile.equals("") ){
           Log.d("validation_success","Valid Input");
           search_vanshawal();
        }else{
           Log.d("validation_failed","Invalid Input");
           Toast.makeText(getActivity() , R.string.search_validation_msg, Toast.LENGTH_LONG).show();
        }
    }

    public void search_vanshawal(){
        Log.d("search_vanshawal", "Inside search_vanshawal()");
        Call<List<Vanshawal>> call = Api.getClient().searchVanshawal( late_firstname, late_middlename, late_lastname, caste, city, taluka, district, death_anniversary, mobile);
        call.enqueue(new Callback<List<Vanshawal>>() {
            @Override
            public void onResponse(Call<List<Vanshawal>> call, Response<List<Vanshawal>> response) {
                Log.e("JSON:",new Gson().toJson(response.body()));
                if(response!=null){
                    List<Vanshawal> vanshawal = response.body();
                    VanshawalAdapter vanshawalAdapter = new VanshawalAdapter(getContext(), vanshawal);
                    String title=getActivity().getResources().getString(R.string.search_result_heading);
                    ((NavigateActivity)getActivity()).setActionBarTitle(title);
                    search_params.setVisibility(View.INVISIBLE);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(0, 0);
                    search_params.setLayoutParams(lp);
                    recyclerView.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(vanshawalAdapter); //Set the Adapter to RecyclerView
                }else{
                    search_params.setVisibility(View.INVISIBLE);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(0, 0);
                    search_params.setLayoutParams(lp);
                    emptyText.setVisibility(View.VISIBLE);
                }
                Log.e("Done","Finished");
            }

            @Override
            public void onFailure(Call<List<Vanshawal>> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
    }

    public void cancel(View view){
        Fragment fragment = new HomeFragment();
        getParentFragmentManager().beginTransaction().
                                   replace(R.id.fragment_container, fragment).
                                   commit();
        String title = getActivity().getResources().getString(R.string.vanshawal_list);
        ((NavigateActivity)getActivity()).setActionBarTitle(title);
    }
}