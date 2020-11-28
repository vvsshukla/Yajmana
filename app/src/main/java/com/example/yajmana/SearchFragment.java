package com.example.yajmana;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    ProgressBar progressBar;

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
        progressBar = view.findViewById(R.id.progressBar5);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnCancelSearch = view.findViewById(R.id.btnCancelSearch);
        btnSearch.setOnClickListener(view1 -> getUIReferences(view));
        btnCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel(view);
            }
        });
        emptyText = view.findViewById(R.id.emptyText);
        emptyText.setVisibility(View.GONE);
        return view;
    }

    public void getUIReferences(View view){
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
            Log.d("keyboard","Dismissed");
        }catch (Exception e){
            Log.e("Exception", e.getMessage());
        }
        progressBar.setVisibility(View.VISIBLE);
        EditText firstName = view.findViewById(R.id.first_name);
        EditText middleName = view.findViewById(R.id.middle_name);
        EditText lastName = view.findViewById(R.id.last_name);
        EditText casteText = view.findViewById(R.id.caste);
        EditText cityText = view.findViewById(R.id.city);
        EditText talukaText = view.findViewById(R.id.taluka);
        EditText districtText = view.findViewById(R.id.district);
        EditText deathAnniversaryText = view.findViewById(R.id.death_anniversary);
        EditText mobileNoText = view.findViewById(R.id.mobile_no);

        late_firstname = firstName.getText().toString().trim();
        late_middlename = middleName.getText().toString().trim();
        late_lastname = lastName.getText().toString().trim();
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
        search_validation(view);
    }

    public void search_validation( View view ){
        if(!late_firstname.equals("") || !late_middlename.equals("") || !late_lastname.equals("") || !caste.equals("") || !city.equals("") || !taluka.equals("") || !district.equals("") || !death_anniversary.equals("") || !mobile.equals("") ){
           Log.d("validation_success","Valid Input");
           if(!mobile.equals("") && mobile.length()!=10) {
               Toast.makeText(getActivity(), getString(R.string.valid_mobile_no_msg), Toast.LENGTH_SHORT).show();
           } else if(!death_anniversary.equals("")){
               String regex = "^(1[0-2]|0[1-9])-(3[01]|[12][0-9]|0[1-9])-[0-9]{4}$";
               Pattern pattern = Pattern.compile(regex);
               Matcher matcher = pattern.matcher(death_anniversary);
               boolean bool = matcher.matches();
               if (bool) {
                   search_vanshawal();
               } else {
                   Toast.makeText(getActivity(), getString(R.string.death_anniverary_msg), Toast.LENGTH_SHORT).show();
               }
           } else {
               search_vanshawal();
           }
        } else {
            Toast.makeText(getActivity(), getString(R.string.search_validation_msg), Toast.LENGTH_LONG).show();
        }
    }

    public void search_vanshawal(){
        Log.d("search_vanshawal", "Inside search_vanshawal()");
        Call<List<Vanshawal>> call = Api.getClient().searchVanshawal( late_firstname, late_middlename, late_lastname, caste, city, taluka, district, death_anniversary, mobile);
        call.enqueue(new Callback<List<Vanshawal>>() {
            @Override
            public void onResponse(Call<List<Vanshawal>> call, Response<List<Vanshawal>> response) {
                Log.e("JSON:",new Gson().toJson(response.body()));
                if(response.body()!=null){
                   List<Vanshawal> vanshawal = response.body();
                   VanshawalAdapter vanshawalAdapter = new VanshawalAdapter(getContext(), vanshawal);
                   String title=getActivity().getResources().getString(R.string.search_result_heading);
                   ((NavigateActivity)getActivity()).setActionBarTitle(title);
                   search_params.setVisibility(View.GONE);
                   progressBar.setVisibility(View.GONE);
                   recyclerView.setVisibility(View.VISIBLE);
                   LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                   recyclerView.setLayoutManager(linearLayoutManager);
                   recyclerView.setAdapter(vanshawalAdapter); //Set the Adapter to RecyclerView
                   emptyText.setVisibility(View.GONE);
                }else{
                   progressBar.setVisibility(View.GONE);
                   search_params.setVisibility(View.VISIBLE);
                   emptyText.setVisibility(View.VISIBLE);
                }
                Log.e("Done","Finished");
            }

            @Override
            public void onFailure(Call<List<Vanshawal>> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
        getBack();
    }

    public void cancel(View view){
        Fragment fragment = new HomeFragment();
        getParentFragmentManager().beginTransaction().
                                   replace(R.id.fragment_container, fragment).
                                   commit();
        String title = getActivity().getResources().getString(R.string.vanshawal_list);
        ((NavigateActivity)getActivity()).setActionBarTitle(title);
    }

    public void getBack(){
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        recyclerView.setVisibility(View.INVISIBLE);
                        search_params.setVisibility(View.VISIBLE);
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        search_params.setLayoutParams(lp);
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new SearchFragment())
                                .addToBackStack(null)
                                .commit();
                        ((NavigateActivity)getActivity()).setActionBarTitle(getString(R.string.search_fragment));
                    }
                }
                return true;
            }
        });
    }

}