package com.example.yajmana;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditFragment extends Fragment {
    int real_uncle_count, real_uncle_count_id_start, real_uncle_count_id, real_uncle_button_id;
    int cousin_uncle_count, cousin_uncle_count_id_start, cousin_uncle_count_id, cousin_uncle_button_id;
    int real_uncle_son_count, real_uncle_son_count_id_start, real_uncle_son_count_id, real_uncle_son_button_id;
    int cousin_uncle_son_count, cousin_uncle_son_count_id_start, cousin_uncle_son_count_id, cousin_uncle_son_button_id;
    int real_brother_count, real_brother_count_id_start, real_brother_count_id, real_brother_button_id;
    int real_brother_son_count, real_brother_son_count_id_start, real_brother_son_count_id, real_brother_son_button_id;
    int son_count, son_count_id_start, son_count_id, son_button_id;
    int MAXSIZE = 10;
    EditText fullName, cityName, talukaName, districtName, casteName, deathAnniversary, relationName;
    EditText yourFullName, fatherFullName, motherFullName, grandpaName, panjobaName, niPanjobaName;
    EditText mobileNo, tipText, dynamic;
    Button btnUpdate, btnCancel;
    LinearLayout L1, L2, L3, L4, L5, L6, L7;
    LinearLayout dynamicL1[], dynamicL2[], dynamicL3[], dynamicL4[], dynamicL5[], dynamicL6[], dynamicL7[];
    EditText t1, t2, t3, t4, t5, t6, t7;
    TextView add_more_real_uncle, add_more_cousin_uncle, add_more_real_uncle_son, add_more_cousin_uncle_son, add_more_real_brother, add_more_real_brother_son, add_more_son;
    EditText e1, e2, e3, e4, e5, e6, e7;
    Button B1, B2, B3, B4, B5, B6, B7;
    String late_fullname, vanshawalNo, city, taluka, district, caste, death_anniversary, relation, yajman, father, mother, grandpa, panjoba, nipanjoba, real_uncle, cousin_uncle, real_uncle_son, cousin_uncle_son, real_brother, real_brother_son, son, mobileno, tip, serverImagePath;
    String[] real_uncle_array, cousin_uncle_array, real_uncle_son_array, cousin_uncle_son_array, real_brother_array, real_brother_son_array, real_son_array;

    public EditFragment(){
        real_uncle_count=0;
        real_uncle_count_id_start=110;
        real_uncle_count_id=110;
        real_uncle_button_id = 210;

        cousin_uncle_count=0;
        cousin_uncle_count_id_start=120;
        cousin_uncle_count_id=120;
        cousin_uncle_button_id = 220;

        real_uncle_son_count=0;
        real_uncle_son_count_id_start=130;
        real_uncle_son_count_id=130;
        real_uncle_son_button_id = 230;

        cousin_uncle_son_count=0;
        cousin_uncle_son_count_id_start=140;
        cousin_uncle_son_count_id=140;
        cousin_uncle_son_button_id = 240;

        real_brother_count=0;
        real_brother_count_id_start=150;
        real_brother_count_id=150;
        real_brother_button_id = 250;

        real_brother_son_count=0;
        real_brother_son_count_id_start=160;
        real_brother_son_count_id=160;
        real_brother_son_button_id = 260;

        son_count=0;
        son_count_id_start=170;
        son_count_id=170;
        son_button_id = 270;
    }

    public EditFragment(String vanshawalNo) {
        // Required empty public constructor
        Log.d("vanshawalNo", vanshawalNo);
        this.vanshawalNo = vanshawalNo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("onCreate", "Inside EditFragment onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("onCreateView", "Inside EditFragment onCreateView()");
        View view = inflater.inflate(R.layout.fragment_edit, container, false);
        getUIReferences(view);
        populateEditVanshawal();
        return view;
    }

    private void getUIReferences(View view){
        Log.d("getUIReferences","Inside getUIReferences()");
        fullName = view.findViewById(R.id.full_name);
        cityName = view.findViewById(R.id.city);
        talukaName = view.findViewById(R.id.taluka);
        districtName = view.findViewById(R.id.district);
        casteName = view.findViewById(R.id.caste);
        deathAnniversary = view.findViewById(R.id.death_anniversary);
        relationName = view.findViewById(R.id.relation);
        yourFullName = view.findViewById(R.id.your_full_name);
        fatherFullName = view.findViewById(R.id.fathers_full_name);
        motherFullName = view.findViewById(R.id.mothers_full_name);
        grandpaName = view.findViewById(R.id.grandpa_name);
        panjobaName = view.findViewById(R.id.panjoba_name);
        niPanjobaName = view.findViewById(R.id.ni_panjoba_name);
        mobileNo = view.findViewById(R.id.mobile_no);
        tipText = view.findViewById(R.id.tip);
        L1 = view.findViewById(R.id.L1);
        L1.setOrientation(LinearLayout.VERTICAL);
        L2 = view.findViewById(R.id.L2);
        L2.setOrientation(LinearLayout.VERTICAL);
        L3 = view.findViewById(R.id.L3);
        L3.setOrientation(LinearLayout.VERTICAL);
        L4 = view.findViewById(R.id.L4);
        L4.setOrientation(LinearLayout.VERTICAL);
        L5 = view.findViewById(R.id.L5);
        L5.setOrientation(LinearLayout.VERTICAL);
        L6 = view.findViewById(R.id.L6);
        L6.setOrientation(LinearLayout.VERTICAL);
        L7 = view.findViewById(R.id.L7);
        L7.setOrientation(LinearLayout.VERTICAL);

        dynamicL1 = new LinearLayout[MAXSIZE];
        dynamicL2 = new LinearLayout[MAXSIZE];
        dynamicL3 = new LinearLayout[MAXSIZE];
        dynamicL4 = new LinearLayout[MAXSIZE];
        dynamicL5 = new LinearLayout[MAXSIZE];
        dynamicL6 = new LinearLayout[MAXSIZE];
        dynamicL7 = new LinearLayout[MAXSIZE];

        add_more_real_uncle = view.findViewById(R.id.add_more_real_uncle);
        add_more_cousin_uncle = view.findViewById(R.id.add_more_cousin_uncle);
        add_more_real_uncle_son = view.findViewById(R.id.add_more_real_uncle_son);
        add_more_cousin_uncle_son = view.findViewById(R.id.add_more_cousin_uncle_son);
        add_more_real_brother = view.findViewById(R.id.add_more_real_brother);
        add_more_real_brother_son = view.findViewById(R.id.add_more_real_brother_son);
        add_more_son = view.findViewById(R.id.add_more_son);
        btnUpdate=view.findViewById(R.id.btnUpdate);
        btnCancel=view.findViewById(R.id.btnCancel);

        add_more_real_uncle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_more_real_uncle();
            }
        });
        add_more_cousin_uncle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_more_cousin_uncle();
            }
        });
        add_more_real_uncle_son.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_more_real_uncle_son();
            }
        });
        add_more_cousin_uncle_son.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_more_cousin_uncle_son();
            }
        });
        add_more_real_brother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_more_real_brother();
            }
        });
        add_more_real_brother_son.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_more_real_brother_son();
            }
        });
        add_more_son.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_more_son();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_vanshawal();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelUpdate();
            }
        });
    }

    private void populateEditVanshawal(){
        Log.d("populateEditVanshawal", "Inside populateEditVanshawal()");
        late_fullname = fullName.getText().toString().trim();
        city = cityName.getText().toString().trim();
        taluka = talukaName.getText().toString().trim();
        district = districtName.getText().toString().trim();
        caste = casteName.getText().toString().trim();
        death_anniversary = deathAnniversary.getText().toString().trim();
        relation = relationName.getText().toString().trim();
        yajman = yourFullName.getText().toString().trim();
        father = fatherFullName.getText().toString().trim();
        mother = motherFullName.getText().toString().trim();
        grandpa = grandpaName.getText().toString().trim();
        panjoba = panjobaName.getText().toString().trim();
        nipanjoba = niPanjobaName.getText().toString().trim();
        mobileno = mobileNo.getText().toString().trim();
        tip = tipText.getText().toString().trim();

        Call<List<Vanshawal>> call = Api.getClient().getVanshawalDetails(this.vanshawalNo);
        call.enqueue(new Callback<List<Vanshawal>>() {
            @Override
            public void onResponse(Call<List<Vanshawal>> call, Response<List<Vanshawal>> response) {
                Log.e("onResponse", "Inside onResponse:" + new Gson().toJson(response.body()));
                List<Vanshawal> vanshawal = response.body();
                if(vanshawal.get(0).getMobile().equals("")){
                    Toast.makeText(getContext(),"Yajman does not exist.",Toast.LENGTH_SHORT).show();
                }else{
                   fullName.setText(vanshawal.get(0).getFirst_name() + " " + vanshawal.get(0).getMiddle_name() + " " + vanshawal.get(0).getLast_name());
                   cityName.setText(vanshawal.get(0).getCity());
                   talukaName.setText(vanshawal.get(0).getTaluka());
                   districtName.setText(vanshawal.get(0).getDistrict());
                   casteName.setText(vanshawal.get(0).getCaste());
                   deathAnniversary.setText(vanshawal.get(0).getDeath_anniversary());
                   relationName.setText(vanshawal.get(0).getRelation());
                   yourFullName.setText(vanshawal.get(0).getYajman());
                   fatherFullName.setText(vanshawal.get(0).getFather());
                   motherFullName.setText(vanshawal.get(0).getMother());
                   grandpaName.setText(vanshawal.get(0).getGrandpa());
                   panjobaName.setText(vanshawal.get(0).getPanjoba());
                   niPanjobaName.setText(vanshawal.get(0).getNipanjoba());

                   //Real Uncle
                   String real_uncle_name_string = vanshawal.get(0).getReal_uncle();
                   if(real_uncle_name_string!=null && !real_uncle_name_string.equals("")){
                        if(real_uncle_name_string.contains(",")){
                            real_uncle_array = real_uncle_name_string.split(",");
                        } else {
                            real_uncle_array = new String[]{ real_uncle_name_string };
                        }
                        for(int i=0;i<real_uncle_array.length;i++){
                            dynamicL1[i] = new LinearLayout(getContext());
                            LinearLayout p = dynamicL1[i];
                            p.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayout.LayoutParams dParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            p.setLayoutParams(dParams);

                            t1=new EditText(getActivity());
                            t1.setId(real_uncle_count_id);
                            t1.setText(real_uncle_array[i]);
                            t1.setTextSize(18);
                            LinearLayout.LayoutParams t1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
                            t1.setLayoutParams(t1Params);
                            p.addView(t1);

                            B1=new Button(getContext());
                            B1.setText("-");
                            B1.setTextSize(18);
                            LinearLayout.LayoutParams b1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f);
                            B1.setLayoutParams(b1Params);
                            B1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    delete_dynamic_layout(p,"real_uncle");
                                }
                            });
                            if(i < (real_uncle_array.length-1)){
                               B1.setEnabled(false);
                            }
                            p.addView(B1);
                            L1.addView(p);
                            real_uncle_count_id++;
                            real_uncle_count++;
                        }
                   }

                   //Cousin Uncle
                   String cousin_uncle_name_string = vanshawal.get(0).getCousin_uncle();
                   if (cousin_uncle_name_string!=null && !cousin_uncle_name_string.equals("")){
                        if(cousin_uncle_name_string.contains(",")){
                            cousin_uncle_array = cousin_uncle_name_string.split(",");
                        } else {
                            cousin_uncle_array = new String[]{ cousin_uncle_name_string };
                        }
                        for(int i=0;i < cousin_uncle_array.length;i++){
                            dynamicL2[i] = new LinearLayout(getContext());
                            LinearLayout p = dynamicL2[i];
                            p.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayout.LayoutParams dParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            p.setLayoutParams(dParams);

                            t2=new EditText(getActivity());
                            t2.setId(cousin_uncle_count_id);
                            t2.setText(cousin_uncle_array[i]);
                            t2.setTextSize(18);
                            LinearLayout.LayoutParams t1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
                            t2.setLayoutParams(t1Params);
                            p.addView(t2);

                            B2=new Button(getContext());
                            B2.setText("-");
                            B2.setTextSize(18);
                            LinearLayout.LayoutParams b2Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f);
                            B2.setLayoutParams(b2Params);
                            B2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    delete_dynamic_layout(p,"cousin_uncle");
                                }
                            });
                            if(i < (cousin_uncle_array.length-1)){
                                B2.setEnabled(false);
                            }
                            p.addView(B2);

                            L2.addView(p);
                            cousin_uncle_count++;
                            cousin_uncle_count_id++;
                        }
                   }

                   //Real Uncle Son
                   String real_uncle_son_name_string = vanshawal.get(0).getReal_uncle_son();
                   if (real_uncle_son_name_string!=null && !real_uncle_son_name_string.equals("")){
                        if(real_uncle_son_name_string.contains(",")){
                            real_uncle_son_array = real_uncle_son_name_string.split(",");
                        } else {
                            real_uncle_son_array = new String[]{ real_uncle_son_name_string };
                        }
                        for(int i=0;i < real_uncle_son_array.length;i++)
                        {
                            dynamicL3[i] = new LinearLayout(getContext());
                            LinearLayout p = dynamicL3[i];
                            p.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayout.LayoutParams dParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            p.setLayoutParams(dParams);

                            t3=new EditText(getActivity());
                            t3.setText(real_uncle_son_array[i]);
                            t3.setId(real_uncle_son_count_id);
                            t3.setTextSize(18);
                            LinearLayout.LayoutParams t3Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
                            t3.setLayoutParams(t3Params);
                            p.addView(t3);

                            B3=new Button(getContext());
                            B3.setText("-");
                            B3.setTextSize(18);
                            LinearLayout.LayoutParams b3Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f);
                            B3.setLayoutParams(b3Params);
                            B3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    delete_dynamic_layout(p,"real_uncle_son");
                                }
                            });
                            if(i < (real_uncle_son_array.length-1)){
                                B3.setEnabled(false);
                            }
                            p.addView(B3);


                            L3.addView(p);
                            real_uncle_son_count++;
                            real_uncle_son_count_id++;
                        }
                    }

                   //Cousin Uncle Son
                   String cousin_uncle_son_name_string = vanshawal.get(0).getCousin_uncle_son();
                   if (cousin_uncle_son_name_string!=null && !cousin_uncle_son_name_string.equals("")){
                        if(cousin_uncle_son_name_string.contains(",")){
                            cousin_uncle_son_array = cousin_uncle_son_name_string.split(",");
                        } else {
                            cousin_uncle_son_array = new String[]{ cousin_uncle_son_name_string };
                        }
                        for(int i=0;i < cousin_uncle_son_array.length;i++)
                        {
                            dynamicL4[i] = new LinearLayout(getContext());
                            LinearLayout p = dynamicL4[i];
                            p.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayout.LayoutParams dParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            p.setLayoutParams(dParams);

                            t4=new EditText(getActivity());
                            t4.setText(cousin_uncle_son_array[i]);
                            t4.setTextSize(18);
                            t4.setId(cousin_uncle_son_count_id);
                            LinearLayout.LayoutParams t4Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
                            t4.setLayoutParams(t4Params);
                            p.addView(t4);

                            B4=new Button(getContext());
                            B4.setText("-");
                            B4.setTextSize(18);
                            LinearLayout.LayoutParams b4Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f);
                            B4.setLayoutParams(b4Params);
                            B4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    delete_dynamic_layout(p,"cousin_uncle_son");
                                }
                            });
                            if(i < (cousin_uncle_son_array.length-1)){
                                B4.setEnabled(false);
                            }
                            p.addView(B4);

                            L4.addView(p);
                            cousin_uncle_son_count_id++;
                            cousin_uncle_son_count++;
                        }
                    }

                   //Real Brother
                   String real_brother_name_string = vanshawal.get(0).getReal_brother();
                   if (real_brother_name_string!=null && !real_brother_name_string.equals("")){
                        if(real_brother_name_string.contains(",")){
                            real_brother_array = real_brother_name_string.split(",");
                        } else {
                            real_brother_array = new String[]{ real_brother_name_string };
                        }
                        for(int i=0;i < real_brother_array.length;i++)
                        {
                            dynamicL5[i] = new LinearLayout(getContext());
                            LinearLayout p = dynamicL5[i];
                            p.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayout.LayoutParams dParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            p.setLayoutParams(dParams);

                            t5=new EditText(getActivity());
                            t5.setText(real_brother_array[i]);
                            t5.setTextSize(18);
                            t5.setId(real_brother_count_id);
                            LinearLayout.LayoutParams t5Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
                            t5.setLayoutParams(t5Params);
                            p.addView(t5);

                            B5=new Button(getContext());
                            B5.setText("-");
                            B5.setTextSize(18);
                            LinearLayout.LayoutParams b5Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f);
                            B5.setLayoutParams(b5Params);
                            B5.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    delete_dynamic_layout(p,"real_brother");
                                }
                            });
                            if(i < (real_brother_array.length-1)){
                                B5.setEnabled(false);
                            }
                            p.addView(B5);

                            L5.addView(p);
                            real_brother_count_id++;
                            real_brother_count++;
                        }
                    }

                   //Real Brother Son
                   String real_brother_son_name_string = vanshawal.get(0).getReal_brother_son();
                   if (real_brother_son_name_string!=null && !real_brother_son_name_string.equals("")){
                        if(real_brother_son_name_string.contains(",")){
                            real_brother_son_array = real_brother_son_name_string.split(",");
                        } else {
                            real_brother_son_array = new String[]{ real_brother_son_name_string };
                        }
                        for(int i=0;i < real_brother_son_array.length;i++)
                        {
                            dynamicL6[i] = new LinearLayout(getContext());
                            LinearLayout p = dynamicL6[i];
                            p.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayout.LayoutParams dParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            p.setLayoutParams(dParams);

                            t6=new EditText(getActivity());
                            t6.setText(real_brother_son_array[i]);
                            t6.setTextSize(18);
                            t6.setId(real_brother_son_count_id);
                            LinearLayout.LayoutParams t6Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
                            t6.setLayoutParams(t6Params);
                            p.addView(t6);

                            B6=new Button(getContext());
                            B6.setText("-");
                            B6.setTextSize(18);
                            LinearLayout.LayoutParams b6Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f);
                            B6.setLayoutParams(b6Params);
                            B6.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    delete_dynamic_layout(p,"real_brother_son");
                                }
                            });
                            if(i < (real_brother_son_array.length-1)){
                                B6.setEnabled(false);
                            }
                            p.addView(B6);
                            L6.addView(p);
                            real_brother_son_count_id++;
                            real_brother_son_count++;
                        }
                    }

                   //Real Son
                   String real_son_name_string = vanshawal.get(0).getReal_son();
                   if (real_son_name_string!=null && !real_son_name_string.equals("")){
                        if(real_son_name_string.contains(",")){
                            real_son_array = real_son_name_string.split(",");
                        } else {
                            real_son_array = new String[]{ real_son_name_string };
                        }
                        for(int i=0;i < real_son_array.length;i++)
                        {
                            dynamicL7[i] = new LinearLayout(getContext());
                            LinearLayout p = dynamicL7[i];
                            p.setOrientation(LinearLayout.HORIZONTAL);
                            LinearLayout.LayoutParams dParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                            p.setLayoutParams(dParams);

                            t7=new EditText(getActivity());
                            t7.setText(real_son_array[i]);
                            t7.setTextSize(18);
                            t7.setId(son_count_id);
                            LinearLayout.LayoutParams t7Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
                            t7.setLayoutParams(t7Params);
                            p.addView(t7);

                            B7=new Button(getContext());
                            B7.setText("-");
                            B7.setTextSize(18);
                            LinearLayout.LayoutParams b7Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,0.2f);
                            B7.setLayoutParams(b7Params);
                            B7.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    delete_dynamic_layout(p,"son");
                                }
                            });
                            if(i < (real_son_array.length-1)){
                                B7.setEnabled(false);
                            }
                            p.addView(B7);
                            L7.addView(p);
                            son_count_id++;
                            son_count++;
                        }
                   }

                   mobileNo.setText(vanshawal.get(0).getMobile());
                   tipText.setText(vanshawal.get(0).getTip());
                }
            }
            @Override
            public void onFailure(Call<List<Vanshawal>> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });

        Log.d("late_fullname:",late_fullname);
        Log.d("city_name:",city);
        Log.d("taluka_name:",taluka);
        Log.d("district_name:",district);
        Log.d("caste_name:",caste);
        Log.d("death_anniversary:",death_anniversary);
        Log.d("relation:",relation);
        Log.d("yajman:",yajman);
        Log.d("father:",father);
        Log.d("mother:",mother);
        Log.d("grandpa:",grandpa);
        Log.d("panjoba:",panjoba);
        Log.d("nipanjoba:",nipanjoba);
        Log.d("mobileno:",mobileno);
        Log.d("tip:", tip);
        Log.d("loadDetails", "loadDetails() ends");
    }

    private void update_vanshawal() {
        Log.d("update_vanshawal","Inside update_vanshawal()");
        late_fullname = fullName.getText().toString().trim();
        city = cityName.getText().toString().trim();
        taluka = talukaName.getText().toString().trim();
        district = districtName.getText().toString().trim();
        caste = casteName.getText().toString().trim();
        death_anniversary = deathAnniversary.getText().toString().trim();
        relation = relationName.getText().toString().trim();
        yajman = yourFullName.getText().toString().trim();
        father = fatherFullName.getText().toString().trim();
        mother = motherFullName.getText().toString().trim();
        grandpa = grandpaName.getText().toString().trim();
        panjoba = panjobaName.getText().toString().trim();
        nipanjoba = niPanjobaName.getText().toString().trim();

        if(real_uncle_count!=0){
            Log.d("real_uncle_count", String.valueOf(real_uncle_count));
        }

        for (int cnt=0; cnt < dynamicL1.length; cnt++){
            if(dynamicL1[cnt]!=null){
                Log.d("count", String.valueOf(cnt));
                int childCounts = dynamicL1[cnt].getChildCount();
                Log.d("children", String.valueOf(childCounts));
                for(int p=0;p < childCounts; p++){
                    View element=dynamicL1[cnt].getChildAt(p);
                    if(element instanceof EditText){
                        Log.d("Element", String.valueOf(((EditText) element).getText()));
                        if (real_uncle != null) {
                            real_uncle = real_uncle + "," + ((EditText) element).getText().toString().trim();
                            Log.d("if real_uncle", real_uncle);
                        } else {
                            real_uncle = ((EditText) element).getText().toString().trim();
                            Log.d("else real_uncle", real_uncle);
                        }
                        Log.d("real_uncle_string", real_uncle);
                    }
                }
            }
        }

        for (int cnt=0; cnt < dynamicL2.length; cnt++) {
            if (dynamicL2[cnt] != null) {
                int childCounts = dynamicL2[cnt].getChildCount();
                for (int p = 0; p < childCounts; p++) {
                    View element = dynamicL2[cnt].getChildAt(p);
                    if (element instanceof EditText) {
                        Log.d("Element", String.valueOf(((EditText) element).getText()));
                        if (cousin_uncle != null) {
                            cousin_uncle = cousin_uncle + "," + ((EditText) element).getText().toString().trim();
                        } else {
                            cousin_uncle = ((EditText) element).getText().toString().trim();
                        }
                    }
                }
            }
        }

        for (int cnt=0; cnt < dynamicL3.length; cnt++) {
            if (dynamicL3[cnt] != null) {
                int childCounts = dynamicL3[cnt].getChildCount();
                for (int p = 0; p < childCounts; p++) {
                    View element = dynamicL3[cnt].getChildAt(p);
                    if (element instanceof EditText) {
                        Log.d("Element", String.valueOf(((EditText) element).getText()));
                        if (real_uncle_son != null) {
                            real_uncle_son = real_uncle_son + "," + ((EditText) element).getText().toString().trim();
                        } else {
                            real_uncle_son = ((EditText) element).getText().toString().trim();
                        }
                    }
                }
            }
        }

        for (int cnt=0; cnt <  dynamicL4.length; cnt++){
            if(dynamicL4[cnt]!=null) {
                int childCounts = dynamicL4[cnt].getChildCount();
                for (int p = 0; p < childCounts; p++) {
                    View element = dynamicL4[cnt].getChildAt(p);
                    if (element instanceof EditText) {
                        Log.d("Element", String.valueOf(((EditText) element).getText()));
                        if (cousin_uncle_son != null) {
                            cousin_uncle_son = cousin_uncle_son + "," + ((EditText) element).getText().toString().trim();
                        } else {
                            cousin_uncle_son = ((EditText) element).getText().toString().trim();
                        }
                    }
                }
            }
        }

        for (int cnt=0; cnt <  dynamicL5.length; cnt++){
            if(dynamicL5[cnt]!=null) {
                int childCounts = dynamicL5[cnt].getChildCount();
                for (int p = 0; p < childCounts; p++) {
                    View element = dynamicL5[cnt].getChildAt(p);
                    if (element instanceof EditText) {
                        Log.d("Element", String.valueOf(((EditText) element).getText()));
                        if (real_brother != null) {
                            real_brother = real_brother + "," + ((EditText) element).getText().toString().trim();
                        } else {
                            real_brother = ((EditText) element).getText().toString().trim();
                        }
                    }
                }
            }
        }

        for (int cnt=0; cnt <  dynamicL6.length; cnt++){
            if(dynamicL6[cnt]!=null) {
                int childCounts = dynamicL6[cnt].getChildCount();
                for (int p = 0; p < childCounts; p++) {
                    View element = dynamicL6[cnt].getChildAt(p);
                    if (element instanceof EditText) {
                        Log.d("Element", String.valueOf(((EditText) element).getText()));
                        if (real_brother_son != null) {
                            real_brother_son = real_brother_son + "," + ((EditText) element).getText().toString().trim();
                        } else {
                            real_brother_son = ((EditText) element).getText().toString().trim();
                        }
                    }
                }
            }
        }

        for (int cnt=0; cnt <  dynamicL7.length; cnt++){
            if(dynamicL7[cnt]!=null) {
                int childCounts = dynamicL7[cnt].getChildCount();
                for (int p = 0; p < childCounts; p++) {
                    View element = dynamicL7[cnt].getChildAt(p);
                    if (element instanceof EditText) {
                        Log.d("Element", String.valueOf(((EditText) element).getText()));
                        if (son != null) {
                            son = son + "," + ((EditText) element).getText().toString().trim();
                        } else {
                            son = ((EditText) element).getText().toString().trim();
                        }
                    }
                }
            }
        }

        mobileno = mobileNo.getText().toString().trim();
        tip = tipText.getText().toString().trim();

        Log.d("update_vanshawal", "update_vanshawal() ends");
        Call<SignUpResponse> call = Api.getClient().updateYajamana(late_fullname, city, taluka, district, caste, death_anniversary, relation, yajman, father, mother, grandpa, panjoba, nipanjoba, real_uncle, cousin_uncle, real_uncle_son, cousin_uncle_son, real_brother, real_brother_son, son, mobileno, tip, vanshawalNo);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.d("onResponse", new Gson().toJson(response.body()));
                if (response!=null && response.body().getResponseCode()==1) {
                    Log.d("UpdateSql", response.body().getMessage());
                    Toast.makeText(getContext(), "सदर माहिती बदल सफल.", Toast.LENGTH_LONG).show();
                    btnCancel.setText(R.string.vanshawal_list);
                } else {
                    Toast.makeText(getContext(), "माहिती बदल असफल.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cancelUpdate() {
        getParentFragmentManager().popBackStack();
    }

    @SuppressLint("ResourceType")
    private void add_more_real_uncle() {
        if(this.real_uncle_count < 10) {
            if(this.real_uncle_count > 0){
               View btnView = dynamicL1[this.real_uncle_count - 1].getChildAt(1);
               btnView.setEnabled(false);
            }
            dynamicL1[this.real_uncle_count] = new LinearLayout(getContext());
            LinearLayout p = dynamicL1[this.real_uncle_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e1 = new EditText(getContext());
            e1.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.8f);
            Log.d("RealUncle", String.valueOf(real_uncle_count_id));
            e1.setId(real_uncle_count_id++);
            e1.setHint(R.string.real_uncle_name);
            e1.setLayoutParams(params);
            p.addView(e1);

            //Add Delete Button (-)
            B1 = new Button(getContext());
            B1.setId(real_uncle_button_id++);
            B1.setText("-");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.2f);
            B1.setLayoutParams(buttonParams);
            p.addView(B1);
            L1.addView(p);

            B1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "real_uncle");
                }
            });
            e1.requestFocus();
            e1.setFocusable(true);
            e1.setFocusableInTouchMode(true);
            this.real_uncle_count++;
        } else {
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_cousin_uncle() {
        if(this.cousin_uncle_count < 10) {
            if(this.cousin_uncle_count > 0){
                View btnView = dynamicL2[this.cousin_uncle_count - 1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL2[this.cousin_uncle_count] = new LinearLayout(getContext());
            LinearLayout p = dynamicL2[this.cousin_uncle_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e2 = new EditText(getContext());
            e2.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.8f);
            Log.d("CousinUncle", String.valueOf(cousin_uncle_count_id));
            e2.setId(cousin_uncle_count_id++);
            e2.setHint(R.string.cousin_uncle_name);
            e2.setLayoutParams(params);
            p.addView(e2);

            //Add Delete Button (-)
            B2 = new Button(getContext());
            B2.setId(cousin_uncle_button_id++);
            B2.setText("-");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.2f);
            B2.setLayoutParams(buttonParams);
            B2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "cousin_uncle");
                }
            });
            p.addView(B2);
            L2.addView(p);
            e2.requestFocus();
            e2.setFocusable(true);
            e2.setFocusableInTouchMode(true);
            this.cousin_uncle_count++;
        } else {
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_real_uncle_son() {
        if(this.real_uncle_son_count < 10) {
            if(this.real_uncle_son_count > 0){
                View btnView = dynamicL3[this.real_uncle_son_count - 1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL3[this.real_uncle_son_count] = new LinearLayout(getContext());
            LinearLayout p = dynamicL3[this.real_uncle_son_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e3 = new EditText(getContext());
            e3.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.8f);
            Log.d("RealUncleSon", String.valueOf(real_uncle_son_count_id));
            e3.setId(real_uncle_son_count_id++);
            e3.setHint(R.string.real_uncle_son_name);
            e3.setLayoutParams(params);
            p.addView(e3);

            //Add Delete Button (-)
            B3 = new Button(getContext());
            B3.setId(real_uncle_son_button_id++);
            B3.setText("-");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.2f);
            B3.setLayoutParams(buttonParams);
            B3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "real_uncle_son");
                }
            });
            p.addView(B3);
            L3.addView(p);
            e3.requestFocus();
            e3.setFocusable(true);
            e3.setFocusableInTouchMode(true);
            this.real_uncle_son_count++;
        } else {
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_cousin_uncle_son() {
        if(this.cousin_uncle_son_count < 10) {
            if(this.cousin_uncle_son_count > 0){
                View btnView = dynamicL1[this.cousin_uncle_son_count - 1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL4[this.cousin_uncle_son_count] = new LinearLayout(getContext());
            LinearLayout p = dynamicL4[this.cousin_uncle_son_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e4 = new EditText(getContext());
            e4.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.8f);
            Log.d("CousinUncleSon", String.valueOf(cousin_uncle_son_count_id));
            e4.setId(cousin_uncle_son_count_id++);
            e4.setHint(R.string.cousin_uncle_son_name);
            e4.setLayoutParams(params);
            p.addView(e4);

            //Add Delete Button (-)
            B4 = new Button(getContext());
            B4.setId(cousin_uncle_button_id++);
            B4.setText("-");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.2f);
            B4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "cousin_uncle_son");
                }
            });
            B4.setLayoutParams(buttonParams);
            p.addView(B4);
            L4.addView(p);
            e4.requestFocus();
            e4.setFocusable(true);
            e4.setFocusableInTouchMode(true);
            this.cousin_uncle_son_count++;
        } else {
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_real_brother() {
        if(this.real_brother_count < 10) {
            if(this.real_brother_count > 0){
                View btnView = dynamicL5[this.real_brother_count - 1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL5[this.real_brother_count] = new LinearLayout(getContext());
            LinearLayout p = dynamicL5[this.real_brother_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e5 = new EditText(getContext());
            e5.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.8f);
            Log.d("RealBrotherCount", String.valueOf(real_brother_count_id));
            e5.setId(real_brother_count_id++);
            e5.setHint(R.string.real_brother_name);
            e5.setLayoutParams(params);
            p.addView(e5);

            //Add Delete Button (-)
            B5 = new Button(getContext());
            B5.setId(real_brother_button_id++);
            B5.setText("-");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.2f);
            B5.setLayoutParams(buttonParams);
            B5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "real_brother");
                }
            });
            p.addView(B5);
            L5.addView(p);
            e5.requestFocus();
            e5.setFocusable(true);
            e5.setFocusableInTouchMode(true);
            this.real_brother_count++;
        } else {
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_real_brother_son() {
        if(this.real_brother_son_count < 10) {
            if(this.real_brother_son_count > 0){
                View btnView = dynamicL6[this.real_brother_son_count - 1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL6[this.real_brother_son_count] = new LinearLayout(getContext());
            LinearLayout p = dynamicL6[this.real_brother_son_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e6 = new EditText(getContext());
            e6.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
            Log.d("RealBrotherSon", String.valueOf(real_brother_son_count_id));
            e6.setId(real_brother_son_count_id++);
            e6.setHint(R.string.real_brother_son_name);
            e6.setLayoutParams(params);
            p.addView(e6);

            //Add Delete Button (-)
            B6 = new Button(getContext());
            B6.setId(real_brother_son_button_id++);
            B6.setText("-");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f);
            B6.setLayoutParams(buttonParams);
            B6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "real_brother_son");
                }
            });
            p.addView(B6);
            L6.addView(p);

            e6.requestFocus();
            e6.setFocusable(true);
            e6.setFocusableInTouchMode(true);
            this.real_brother_son_count++;
        } else {
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_son() {
        if(this.son_count < 10) {
            if(this.son_count > 0){
                View btnView = dynamicL6[this.son_count - 1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL7[this.son_count] = new LinearLayout(getContext());
            LinearLayout p = dynamicL7[this.son_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e7 = new EditText(getContext());
            e7.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f);
            Log.d("son_count_id", String.valueOf(son_count_id));
            e7.setId(son_count_id++);
            e7.setHint(R.string.son_name);
            e7.setLayoutParams(params);
            p.addView(e7);

            //Add Delete Button (-)
            B7 = new Button(getContext());
            B7.setId(son_button_id++);
            B7.setText("-");
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.2f);
            B7.setLayoutParams(buttonParams);
            B7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "son");
                }
            });
            p.addView(B7);
            L7.addView(p);

            e7.requestFocus();
            e7.setFocusable(true);
            e7.setFocusableInTouchMode(true);
            this.son_count++;
        } else {
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete_dynamic_layout(LinearLayout parent, String relative){
        int index;
        switch (relative){
            case "real_uncle":
                index = this.real_uncle_count_id - this.real_uncle_count_id_start - 1;
                //Log.d("index", String.valueOf(index));
                dynamicL1[index]=null;
                this.real_uncle_count_id--;
                this.real_uncle_count--;
                if(index > 0){
                    View btnView = dynamicL1[index-1].getChildAt(1);
                    btnView.setEnabled(true);
                }
/*
        Log.d("id1_cnt", String.valueOf(id1_cnt));
        index = id1[id1_cnt] - this.real_uncle_count_id_start;
        Log.d("index", String.valueOf(index));
        Log.d("real_uncle_count", String.valueOf(real_uncle_count));
        if(index >= 0 && index <= this.dynamicL1.length){
            for(int i=0; i < this.real_uncle_count; i++){
                if(i == index){
                    continue;
                }
                this.dynamicL1[k++]=dynamicL1[i];
            }
        }
        this.real_uncle_count = k;
        Log.d("real_uncle_count", String.valueOf(real_uncle_count));
        for(int j=0;j < this.real_uncle_count;j++){
            Log.d("dynamicL1", String.valueOf(dynamicL1[j].getChildCount()));
            int count = dynamicL1[j].getChildCount();
            for(int p=0;p < count;p++){
                View element=dynamicL1[j].getChildAt(p);
                if(element instanceof EditText){
                  Log.d("Element", String.valueOf(((EditText) element).getText()));
                }
            }
        }
*/
                break;
            case "cousin_uncle":
                index = this.cousin_uncle_count_id - this.cousin_uncle_count_id_start - 1;
                dynamicL2[index]=null;
                this.cousin_uncle_count_id--;
                this.cousin_uncle_count--;
                if(index > 0){
                    View btnView = dynamicL2[index-1].getChildAt(1);
                    btnView.setEnabled(true);
                }
/*
        Log.d("id2_cnt", String.valueOf(id2_cnt));
        index = id2[id2_cnt] - this.cousin_uncle_count_id_start;
        Log.d("index", String.valueOf(index));
        if(index >= 0 && index <= dynamicL2.length){
            for(int i=0; i < this.cousin_uncle_count; i++){
                if(i == index){
                    continue;
                }
                dynamicL2[k++]=dynamicL2[i];
            }
        }
        this.cousin_uncle_count = k;
*/
                break;
            case "real_uncle_son":
                index = this.real_uncle_son_count_id - this.real_uncle_son_count_id_start - 1;
                dynamicL3[index]=null;
                this.real_uncle_son_count_id--;
                this.real_uncle_son_count--;
                if(index > 0){
                    View btnView = dynamicL3[index-1].getChildAt(1);
                    btnView.setEnabled(true);
                }
                break;

            case "cousin_uncle_son":
                index = this.cousin_uncle_son_count_id - this.cousin_uncle_son_count_id_start - 1;
                dynamicL4[index]=null;
                this.cousin_uncle_son_count_id--;
                this.cousin_uncle_son_count--;
                if(index > 0){
                    View btnView = dynamicL4[index-1].getChildAt(1);
                    btnView.setEnabled(true);
                }
                break;

            case "real_brother":
                index = this.real_brother_count_id - this.real_brother_count_id_start - 1;
                dynamicL5[index]=null;
                this.real_brother_count_id--;
                this.real_brother_count--;
                if(index > 0){
                    View btnView = dynamicL5[index-1].getChildAt(1);
                    btnView.setEnabled(true);
                }
                break;

            case "real_brother_son":
                index = this.real_brother_son_count_id - this.real_brother_son_count_id_start - 1;
                dynamicL6[index]=null;
                this.real_brother_son_count_id--;
                this.real_brother_son_count--;
                if(index > 0){
                    View btnView = dynamicL6[index-1].getChildAt(1);
                    btnView.setEnabled(true);
                }
                break;

            case "son":
                index = this.son_count_id - this.son_count_id_start - 1;
                dynamicL7[index]=null;
                this.son_count_id--;
                this.son_count--;
                if(index > 0){
                    View btnView = dynamicL7[index-1].getChildAt(1);
                    btnView.setEnabled(true);
                }
                break;
        }
        parent.removeAllViews();
    }
}