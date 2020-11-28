package com.example.yajmana;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.util.Log;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {
    private String vanshawalNo, signatureImagePath, signatureImageURL, tipString, mobileNoString;
    private final String NA = "उपलब्ध नाही.";
    TextView fullName, cityName, talukaName, districtName, casteName, deathAnniversary, relationName, gender;
    TextView yourFullName, fatherFullName, motherFullName, grandpaName, panjobaName, niPanjobaName, signatureLable, tipLable;
    ImageView signatureImage;
    TextView mobileNo, tipText;
    LinearLayout L1, L2, L3, L4, L5, L6, L7, I1, I2, I3, I4, I5, I6, I7;
    TextView t1[], t2[], t3[], t4[], t5[], t6[], t7[];
    ProgressBar progressBar;
    String[] real_uncle_array, cousin_uncle_array, real_uncle_son_array, cousin_uncle_son_array, real_brother_array, real_brother_son_array, real_son_array, signatureSplit;
    private static final String base_url = "http://eastro.in";
    protected View detailView;

    public DetailFragment(String vanshawalNo) {
        // Required empty public constructor
        this.vanshawalNo = vanshawalNo;
        Log.d("vanshawalNo",vanshawalNo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        this.detailView = view;
        progressBar = view.findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);
        getUIReferences(view);
        loadDetails();
        getBack();
        return view;
    }

    private void getUIReferences(View view) {
        fullName = view.findViewById(R.id.full_name);
        cityName = view.findViewById(R.id.city);
        talukaName = view.findViewById(R.id.taluka);
        districtName = view.findViewById(R.id.district);
        casteName = view.findViewById(R.id.caste);
        deathAnniversary = view.findViewById(R.id.death_anniversary);
        relationName = view.findViewById(R.id.relation);
        gender = view.findViewById(R.id.genderValue);
        yourFullName = view.findViewById(R.id.your_full_name);
        fatherFullName = view.findViewById(R.id.fathers_full_name);
        motherFullName = view.findViewById(R.id.mothers_full_name);
        grandpaName = view.findViewById(R.id.grandpa_name);
        panjobaName = view.findViewById(R.id.panjoba_name);
        niPanjobaName = view.findViewById(R.id.ni_panjoba_name);
        L1 = view.findViewById(R.id.real_uncle_name);
        L2 = view.findViewById(R.id.cousin_uncle_name);
        L3 = view.findViewById(R.id.real_uncle_son_name);
        L4 = view.findViewById(R.id.cousin_uncle_son_name);
        L5 = view.findViewById(R.id.real_brother_name);
        L6 = view.findViewById(R.id.real_brother_son_name);
        L7 = view.findViewById(R.id.son_name);
        mobileNo = view.findViewById(R.id.mobile_no);
        tipLable = view.findViewById(R.id.tip_label);
        tipText = view.findViewById(R.id.tip);
        signatureLable = view.findViewById(R.id.signatureLabel);
        signatureImage = view.findViewById(R.id.signatureImage);
    }

    private void loadDetails() {
        Log.d("loadDetails", "Inside loadDetails()");
        Call<List<Vanshawal>> call = Api.getClient().getVanshawalDetails(this.vanshawalNo);
        call.enqueue(new Callback<List<Vanshawal>>() {
            @Override
            public void onResponse(Call<List<Vanshawal>> call, Response<List<Vanshawal>> response) {
                Log.e("onResponse", "Inside onResponse:" + new Gson().toJson(response.body()));
                List<Vanshawal> vanshawal = response.body();
                if(vanshawal.get(0).getMobile().equals("")){
                   Toast.makeText(getContext(),"Yajman does not exist.",Toast.LENGTH_SHORT).show();
                }else{
                   String fullnameString = vanshawal.get(0).getFirst_name()+ " " + vanshawal.get(0).getMiddle_name() + " " + vanshawal.get(0).getLast_name() ;
                   fullName.setText(fullnameString);
                   cityName.setText(vanshawal.get(0).getCity());
                   talukaName.setText(vanshawal.get(0).getTaluka());
                   districtName.setText(vanshawal.get(0).getDistrict());
                   casteName.setText(vanshawal.get(0).getCaste());
                   deathAnniversary.setText(vanshawal.get(0).getDeath_anniversary());
                   relationName.setText(vanshawal.get(0).getRelation());
                   gender.setText(vanshawal.get(0).getGender());
                   yourFullName.setText(vanshawal.get(0).getYajman());
                   fatherFullName.setText(vanshawal.get(0).getFather());
                   motherFullName.setText(vanshawal.get(0).getMother());
                   grandpaName.setText(vanshawal.get(0).getGrandpa());
                   panjobaName.setText(vanshawal.get(0).getPanjoba());
                   niPanjobaName.setText(vanshawal.get(0).getNipanjoba());

                   Drawable image=(Drawable)getResources().getDrawable(R.drawable.oval);

                   LinearLayout.LayoutParams paramsExternal = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1f);
                   paramsExternal.leftMargin = -10;

                   LinearLayout.LayoutParams paramsInternal = new LinearLayout.LayoutParams(0,64,1f);
                   paramsInternal.leftMargin = 10;
                   paramsInternal.topMargin = 10;

                   //Real Uncle
                   String real_uncle_name_string = vanshawal.get(0).getReal_uncle();
                   if(real_uncle_name_string!=null && !real_uncle_name_string.equals("")) {
                       if(real_uncle_name_string.contains(",")){
                           real_uncle_array = real_uncle_name_string.split(",");
                       } else {
                           real_uncle_array = new String[]{ real_uncle_name_string };
                       }
                       t1 = new TextView[real_uncle_array.length];
                       L1.setWeightSum(real_uncle_array.length);
                       for (int i = 0; i < real_uncle_array.length; i++) {
                           LinearLayout p = new LinearLayout(getContext());
                           p.setOrientation(LinearLayout.HORIZONTAL);
                           p.setWeightSum(1);
//                           SpannableString s = new SpannableString(real_uncle_array[i]);
//                           s.setSpan(new BulletSpan(15), 0, real_uncle_array[i].length(), 0);
                           t1[i] = new TextView(getActivity());
                           t1[i].setText(real_uncle_array[i]);
                           t1[i].setTextSize(14);
                           t1[i].setBackground(image);
                           t1[i].setLayoutParams(paramsInternal);
                           t1[i].setGravity(Gravity.CENTER_VERTICAL);
                           t1[i].setPadding(15,10,10,10);
                           t1[i].setEms(10);
                           p.addView(t1[i]);
                           L1.addView(p);
                           L1.setLayoutParams(paramsExternal);
                       }
                   }else{
                       TextView t = new TextView(getContext());
                       t.setText(NA);
                       t.setPadding(15,10,10,10);
                       L1.addView(t);
                   }

                   //Cousin Uncle
                   String cousin_uncle_name_string = vanshawal.get(0).getCousin_uncle();
                   if(cousin_uncle_name_string!=null && !cousin_uncle_name_string.equals("")) {
                       if(cousin_uncle_name_string.contains(",")){
                           cousin_uncle_array = cousin_uncle_name_string.split(",");
                       } else {
                           cousin_uncle_array = new String[]{ cousin_uncle_name_string };
                       }
                       t2 = new TextView[cousin_uncle_array.length];
                       L2.setWeightSum(cousin_uncle_array.length);
                       for (int i = 0; i < cousin_uncle_array.length; i++) {
                           LinearLayout p = new LinearLayout(getContext());
                           p.setOrientation(LinearLayout.HORIZONTAL);
                           p.setWeightSum(1);
                           SpannableString s = new SpannableString(cousin_uncle_array[i]);
                           s.setSpan(new BulletSpan(15), 0, cousin_uncle_array[i].length(), 0);
                           t2[i] = new TextView(getActivity());
                           t2[i].setText(s);
                           t2[i].setTextSize(14);
                           t2[i].setBackground(image);
                           t2[i].setLayoutParams(paramsInternal);
                           t2[i].setGravity(Gravity.CENTER_VERTICAL);
                           t2[i].setPadding(15,10,10,10);
                           t2[i].setEms(10);
                           p.addView(t2[i]);
                           L2.addView(p);
                           L2.setLayoutParams(paramsExternal);
                       }
                   }else{
                      TextView t = new TextView(getContext());
                      t.setText(NA);
                      t.setPadding(15,10,10,10);
                      L2.addView(t);
                   }

                   //Real Uncle Son
                   String real_uncle_son_name_string = vanshawal.get(0).getReal_uncle_son();
                   if(real_uncle_son_name_string!=null && !real_uncle_son_name_string.equals("")) {
                       if(real_uncle_son_name_string.contains(",")){
                           real_uncle_son_array = real_uncle_son_name_string.split(",");
                       } else {
                           real_uncle_son_array = new String[]{ real_uncle_son_name_string };
                       }
                       t3 = new TextView[real_uncle_son_array.length];
                       L3.setWeightSum(real_uncle_son_array.length);
                       for (int i = 0; i < real_uncle_son_array.length; i++) {
                           LinearLayout p = new LinearLayout(getContext());
                           p.setOrientation(LinearLayout.HORIZONTAL);
                           p.setWeightSum(1);
//                           SpannableString s = new SpannableString(real_uncle_son_array[i]);
//                           s.setSpan(new BulletSpan(15), 0, real_uncle_son_array[i].length(), 0);
                           t3[i] = new TextView(getActivity());
                           t3[i].setText(real_uncle_son_array[i]);
                           t3[i].setTextSize(14);
                           t3[i].setBackground(image);
                           t3[i].setLayoutParams(paramsInternal);
                           t3[i].setGravity(Gravity.CENTER_VERTICAL);
                           t3[i].setPadding(15,10,10,10);
                           t3[i].setEms(10);
                           p.addView(t3[i]);
                           L3.addView(p);
                           L3.setLayoutParams(paramsExternal);
                       }
                   }else{
                      TextView t = new TextView(getContext());
                      t.setText(NA);
                      t.setPadding(15,10,10,10);
                      L3.addView(t);
                   }

                   //Cousin Uncle Son
                   String cousin_uncle_son_name_string = vanshawal.get(0).getCousin_uncle_son();
                   if(cousin_uncle_son_name_string!=null && !cousin_uncle_son_name_string.equals("")) {
                       if(cousin_uncle_son_name_string.contains(",")){
                           cousin_uncle_son_array = cousin_uncle_son_name_string.split(",");
                       } else {
                           cousin_uncle_son_array = new String[]{ cousin_uncle_son_name_string };
                       }
                       t4 = new TextView[cousin_uncle_son_array.length];
                       L4.setWeightSum(cousin_uncle_son_array.length);
                       for (int i = 0; i < cousin_uncle_son_array.length; i++) {
                           LinearLayout p = new LinearLayout(getContext());
                           p.setOrientation(LinearLayout.HORIZONTAL);
                           p.setWeightSum(1);
//                           SpannableString s = new SpannableString(cousin_uncle_son_array[i]);
//                           s.setSpan(new BulletSpan(15), 0, cousin_uncle_son_array[i].length(), 0);
                           t4[i] = new TextView(getActivity());
                           t4[i].setText(cousin_uncle_son_array[i]);
                           t4[i].setTextSize(14);
                           t4[i].setBackground(image);
                           t4[i].setLayoutParams(paramsInternal);
                           t4[i].setGravity(Gravity.CENTER_VERTICAL);
                           t4[i].setPadding(15,10,10,10);
                           t4[i].setEms(10);
                           p.addView(t4[i]);
                           L4.addView(p);
                           L4.setLayoutParams(paramsExternal);
                       }
                   }else{
                      TextView t = new TextView(getContext());
                       t.setText(NA);
                       t.setPadding(15,10,10,10);
                      L4.addView(t);
                   }

                   //Real Brother
                   String real_brother_name_string = vanshawal.get(0).getReal_brother();
                   if(real_brother_name_string!=null && !real_brother_name_string.equals("")) {
                        if(real_brother_name_string.contains(",")){
                            real_brother_array = real_brother_name_string.split(",");
                        } else {
                            real_brother_array = new String[]{ real_brother_name_string };
                        }
                        t5 = new TextView[real_brother_array.length];
                        L5.setWeightSum(real_brother_array.length);
                        for (int i = 0; i < real_brother_array.length; i++) {
                            LinearLayout p = new LinearLayout(getContext());
                            p.setOrientation(LinearLayout.HORIZONTAL);
                            p.setWeightSum(1);
//                            SpannableString s = new SpannableString(real_brother_array[i]);
//                            s.setSpan(new BulletSpan(15), 0, real_brother_array[i].length(), 0);
                            t5[i] = new TextView(getActivity());
                            t5[i].setText(real_brother_array[i]);
                            t5[i].setTextSize(14);
                            t5[i].setBackground(image);
                            t5[i].setLayoutParams(paramsInternal);
                            t5[i].setGravity(Gravity.CENTER_VERTICAL);
                            t5[i].setPadding(15,10,10,10);
                            t5[i].setEms(10);
                            p.addView(t5[i]);
                            L5.addView(p);
                            L5.setLayoutParams(paramsExternal);
                        }
                   }else{
                      TextView t = new TextView(getContext());
                      t.setText(NA);
                      t.setPadding(15,10,10,10);
                      L5.addView(t);
                   }

                   //Real Brother Son
                   String real_brother_son_name_string = vanshawal.get(0).getReal_brother_son();
                   if(real_brother_son_name_string!=null && !real_brother_son_name_string.equals("")) {
                       if(real_brother_son_name_string.contains(",")){
                           real_brother_son_array = real_brother_son_name_string.split(",");
                       } else {
                           real_brother_son_array = new String[]{ real_brother_son_name_string };
                       }
                       t6 = new TextView[real_brother_son_array.length];
                       L6.setWeightSum(real_brother_son_array.length);
                       for (int i = 0; i < real_brother_son_array.length; i++) {
                           LinearLayout p = new LinearLayout(getContext());
                           p.setOrientation(LinearLayout.HORIZONTAL);
                           p.setWeightSum(1);
//                           SpannableString s = new SpannableString(real_brother_son_array[i]);
//                           s.setSpan(new BulletSpan(15), 0, real_brother_son_array[i].length(), 0);
                           t6[i] = new TextView(getActivity());
                           t6[i].setText(real_brother_son_array[i]);
                           t6[i].setTextSize(14);
                           t6[i].setBackground(image);
                           t6[i].setLayoutParams(paramsInternal);
                           t6[i].setGravity(Gravity.CENTER_VERTICAL);
                           t6[i].setPadding(15,10,10,10);
                           t6[i].setEms(10);
                           p.addView(t6[i]);
                           L6.addView(p);
                           L6.setLayoutParams(paramsExternal);
                       }
                   }else{
                       TextView t = new TextView(getContext());
                       t.setText(NA);
                       t.setPadding(15,10,10,10);
                       L6.addView(t);
                   }

                   //Real Son
                   String real_son_name_string = vanshawal.get(0).getReal_son();
                   if(real_son_name_string!=null && !real_son_name_string.equals("")) {
                       if(real_son_name_string.contains(",")){
                           real_son_array = real_son_name_string.split(",");
                       } else {
                           real_son_array = new String[]{ real_son_name_string };
                       }
                       t7 = new TextView[real_son_array.length];
                       L7.setWeightSum(real_son_array.length);
                       for (int i = 0; i < real_son_array.length; i++) {
                           LinearLayout p = new LinearLayout(getContext());
                           p.setOrientation(LinearLayout.HORIZONTAL);
                           p.setWeightSum(1);
//                           SpannableString s = new SpannableString(real_son_array[i]);
//                           s.setSpan(new BulletSpan(15), 0, real_son_array[i].length(), 0);
                           t7[i] = new TextView(getActivity());
                           t7[i].setText(real_son_array[i]);
                           t7[i].setTextSize(14);
                           t7[i].setBackground(image);
                           t7[i].setLayoutParams(paramsInternal);
                           t7[i].setGravity(Gravity.CENTER_VERTICAL);
                           t7[i].setPadding(15,10,10,10);
                           t7[i].setEms(10);
                           p.addView(t7[i]);
                           L7.addView(p);
                           L7.setLayoutParams(paramsExternal);
                       }
                   }else{
                      TextView t = new TextView(getContext());
                       t.setText(NA);
                       t.setPadding(15,10,10,10);
                      L7.addView(t);
                   }

                   mobileNoString = vanshawal.get(0).getMobile();
                   if(mobileNoString!=null && !mobileNoString.trim().isEmpty()){
                       mobileNo.setText(mobileNoString);
                   } else {
                       mobileNo.setText(NA);
                   }

                   tipString = vanshawal.get(0).getTip();
                   if (tipString!=null && !tipString.trim().isEmpty()) {
                       tipText.setText(tipString);
                   } else {
                       tipLable.setVisibility(View.GONE);
                       tipText.setVisibility(View.GONE);
                   }

                   signatureImagePath = vanshawal.get(0).getSignatureImagePath();
                   if(signatureImagePath!=null && !signatureImagePath.trim().isEmpty()){
                       Log.d("signatureImagePath", signatureImagePath);
                       signatureSplit = signatureImagePath.split("/");
                       signatureImageURL = base_url+"/"+signatureSplit[4]+"/"+signatureSplit[5]+"/"+signatureSplit[6];
                       Log.d("signatureImageURL", signatureImageURL);
                       Glide.with(getContext()).load(signatureImageURL).placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(signatureImage);
                   } else {
                       signatureLable.setVisibility(View.GONE);
                       signatureImage.setVisibility(View.GONE);
                   }
                }
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onFailure(Call<List<Vanshawal>> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
        Log.d("loadDetails", "loadDetails() ends");
    }

    public void getBack(){
        detailView.setFocusableInTouchMode(true);
        detailView.requestFocus();
        detailView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, new HomeFragment())
                                .addToBackStack(null)
                                .commit();
                        ((NavigateActivity)getActivity()).setActionBarTitle(getString(R.string.vanshawal_heading));
                    }
                }
                return true;
            }
        });
    }
}