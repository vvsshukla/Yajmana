package com.example.yajmana;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.BulletSpan;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFragment extends Fragment {
    private String vanshawalNo, signatureImagePath, signatureImageURL;
    TextView fullName, cityName, talukaName, districtName, casteName, deathAnniversary, relationName, gender;
    TextView yourFullName, fatherFullName, motherFullName, grandpaName, panjobaName, niPanjobaName;
    ImageView signatureImage;
    TextView mobileNo, tipText;
    LinearLayout L1, L2, L3, L4, L5, L6, L7;
    TextView t1[], t2[], t3[], t4[], t5[], t6[], t7[];
    String[] real_uncle_array, cousin_uncle_array, real_uncle_son_array, cousin_uncle_son_array, real_brother_array, real_brother_son_array, real_son_array, signatureSplit;
    private static final String base_url = "http://eastro.in";

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
        getUIReferences(view);
        loadDetails();
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
        tipText = view.findViewById(R.id.tip);
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
                   fullName.setText(vanshawal.get(0).getFirst_name() + " " + vanshawal.get(0).getMiddle_name() + " " + vanshawal.get(0).getLast_name());
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
                           SpannableString s = new SpannableString(real_uncle_array[i]);
                           s.setSpan(new BulletSpan(15), 0, real_uncle_array[i].length(), 0);
                           t1[i] = new TextView(getActivity());
                           t1[i].setText(s);
                           t1[i].setTextSize(18);
                           L1.addView(t1[i]);
                       }
                   }else{
                       TextView t = new TextView(getContext());
                       t.setText("-");
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
                       for (int i = 0; i < cousin_uncle_array.length; i++) {
                           SpannableString s = new SpannableString(cousin_uncle_array[i]);
                           s.setSpan(new BulletSpan(15), 0, cousin_uncle_array[i].length(), 0);
                           t2[i] = new TextView(getActivity());
                           t2[i].setText(s);
                           t2[i].setTextSize(18);
                           L2.addView(t2[i]);
                       }
                   }else{
                      TextView t = new TextView(getContext());
                      t.setText("-");
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
                       for (int i = 0; i < real_uncle_son_array.length; i++) {
                           SpannableString s = new SpannableString(real_uncle_son_array[i]);
                           s.setSpan(new BulletSpan(15), 0, real_uncle_son_array[i].length(), 0);
                           t3[i] = new TextView(getActivity());
                           t3[i].setText(s);
                           t3[i].setTextSize(18);
                           L3.addView(t3[i]);
                       }
                   }else{
                      TextView t = new TextView(getContext());
                      t.setText("-");
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
                       for (int i = 0; i < cousin_uncle_son_array.length; i++) {
                            SpannableString s = new SpannableString(cousin_uncle_son_array[i]);
                            s.setSpan(new BulletSpan(15), 0, cousin_uncle_son_array[i].length(), 0);
                            t4[i] = new TextView(getActivity());
                            t4[i].setText(s);
                            t4[i].setTextSize(18);
                            L4.addView(t4[i]);
                       }
                   }else{
                      TextView t = new TextView(getContext());
                      t.setText("-");
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
                        for (int i = 0; i < real_brother_array.length; i++) {
                            SpannableString s = new SpannableString(real_brother_array[i]);
                            s.setSpan(new BulletSpan(15), 0, real_brother_array[i].length(), 0);
                            t5[i] = new TextView(getActivity());
                            t5[i].setText(s);
                            t5[i].setTextSize(18);
                            L5.addView(t5[i]);
                        }
                   }else{
                      TextView t = new TextView(getContext());
                      t.setText("-");
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
                       for (int i = 0; i < real_brother_son_array.length; i++) {
                           SpannableString s = new SpannableString(real_brother_son_array[i]);
                           s.setSpan(new BulletSpan(15), 0, real_brother_son_array[i].length(), 0);
                           t6[i] = new TextView(getActivity());
                           t6[i].setText(s);
                           t6[i].setTextSize(18);
                           L6.addView(t6[i]);
                       }
                   }else{
                       TextView t = new TextView(getContext());
                       t.setText("-");
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
                       for (int i = 0; i < real_son_array.length; i++) {
                           SpannableString s = new SpannableString(real_son_array[i]);
                           s.setSpan(new BulletSpan(15), 0, real_son_array[i].length(), 0);
                           t7[i] = new TextView(getActivity());
                           t7[i].setText(s);
                           t7[i].setTextSize(18);
                           L7.addView(t7[i]);
                       }
                   }else{
                      TextView t = new TextView(getContext());
                      t.setText("-");
                      L7.addView(t);
                   }
                   mobileNo.setText(vanshawal.get(0).getMobile());
                   tipText.setText(vanshawal.get(0).getTip());
                   signatureImagePath = vanshawal.get(0).getSignatureImagePath();
                   Log.d("signatureImagePath", signatureImagePath);
                   signatureSplit = signatureImagePath.split("/");
                   signatureImageURL = base_url+"/"+signatureSplit[4]+"/"+signatureSplit[5]+"/"+signatureSplit[6];
                   Log.d("signatureImageURL", signatureImageURL);
                   Glide.with(getContext()).load(signatureImageURL)
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(signatureImage);
                }
            }
            @Override
            public void onFailure(Call<List<Vanshawal>> call, Throwable t) {
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
        Log.d("loadDetails", "loadDetails() ends");
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, url);
            Log.d("LoadImageFromWeb", d.toString());
            return d;
        } catch (Exception e) {
            return null;
        }
    }
}