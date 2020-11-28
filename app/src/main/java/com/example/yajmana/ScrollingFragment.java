
package com.example.yajmana;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.gcacace.signaturepad.views.SignaturePad;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScrollingFragment extends Fragment {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    int real_uncle_count, real_uncle_count_id_start, real_uncle_count_id, real_uncle_button_id;
    int cousin_uncle_count, cousin_uncle_count_id_start, cousin_uncle_count_id, cousin_uncle_button_id;
    int real_uncle_son_count, real_uncle_son_count_id_start, real_uncle_son_count_id, real_uncle_son_button_id;
    int cousin_uncle_son_count, cousin_uncle_son_count_id_start, cousin_uncle_son_count_id, cousin_uncle_son_button_id;
    int real_brother_count, real_brother_count_id_start, real_brother_count_id, real_brother_button_id;
    int real_brother_son_count, real_brother_son_count_id_start, real_brother_son_count_id, real_brother_son_button_id;
    int son_count, son_count_id_start, son_count_id, son_button_id;
    EditText fullName, cityName, talukaName, districtName, casteName, deathAnniversary, relationName;
    EditText yourFullName, fatherFullName, motherFullName, grandpaName, panjobaName, niPanjobaName;
    EditText real_uncle_name, cousin_uncle_name, real_uncle_son_name, cousin_uncle_son_name, real_brother_name, real_brother_son_name, son_name;
    EditText mobileNo, tipText, dynamic;
    EditText e1, e2, e3, e4, e5, e6, e7;
    TextView add_more_real_uncle, add_more_cousin_uncle, add_more_real_uncle_son, add_more_cousin_uncle_son, add_more_real_brother, add_more_real_brother_son, add_more_son;
    LinearLayout[] parentL1, parentL2, parentL3, parentL4, parentL5, parentL6, parentL7;
    LinearLayout dynamicL1, dynamicL2, dynamicL3, dynamicL4, dynamicL5, dynamicL6, dynamicL7, parent;
    Button B1, B2, B3, B4, B5, B6, B7, saveSignature, clearSignature;
    RadioGroup genderGroup;
    RadioButton btnGender;
    SignaturePad signaturePad;
    String albumName = "SignaturePad";
    Uri signatureImageUri;
    String late_fullname, city, taluka, district, caste, death_anniversary, relation, yajman, father, mother, grandpa, panjoba, nipanjoba, real_uncle, cousin_uncle, real_uncle_son, cousin_uncle_son, real_brother, real_brother_son, son, mobileno, genderString, tip, serverImagePath, realPath, signatureImagePath;

    public ScrollingFragment() {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrolling, container, false);
        setHasOptionsMenu(true);

        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        parentL1 = new LinearLayout[10];
        parentL2 = new LinearLayout[10];
        parentL3 = new LinearLayout[10];
        parentL4 = new LinearLayout[10];
        parentL5 = new LinearLayout[10];
        parentL6 = new LinearLayout[10];
        parentL7 = new LinearLayout[10];

        add_more_real_uncle = view.findViewById(R.id.add_more_real_uncle);
        add_more_cousin_uncle = view.findViewById(R.id.add_more_cousin_uncle);
        add_more_real_uncle_son = view.findViewById(R.id.add_more_real_uncle_son);
        add_more_cousin_uncle_son = view.findViewById(R.id.add_more_cousin_uncle_son);
        add_more_real_brother = view.findViewById(R.id.add_more_real_brother);
        add_more_real_brother_son = view.findViewById(R.id.add_more_real_brother_son);
        add_more_son = view.findViewById(R.id.add_more_son);
        parent = view.findViewById(R.id.LayoutSix);

        dynamicL1 = view.findViewById(R.id.dynamicL1);
        dynamicL1.setOrientation(LinearLayout.VERTICAL);
        dynamicL2 = view.findViewById(R.id.dynamicL2);
        dynamicL2.setOrientation(LinearLayout.VERTICAL);
        dynamicL3 = view.findViewById(R.id.dynamicL3);
        dynamicL3.setOrientation(LinearLayout.VERTICAL);
        dynamicL4 = view.findViewById(R.id.dynamicL4);
        dynamicL4.setOrientation(LinearLayout.VERTICAL);
        dynamicL5 = view.findViewById(R.id.dynamicL5);
        dynamicL5.setOrientation(LinearLayout.VERTICAL);
        dynamicL6 = view.findViewById(R.id.dynamicL6);
        dynamicL6.setOrientation(LinearLayout.VERTICAL);
        dynamicL7 = view.findViewById(R.id.dynamicL7);
        dynamicL7.setOrientation(LinearLayout.VERTICAL);

        Log.d("onCreateView","Inside onCreateView");
        btnAdd.setOnClickListener(view1 -> add_new_record(view));
        btnCancel.setOnClickListener(view12 -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), NavigateActivity.class);
            startActivity(intent);
        });
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
        add_more_son.setOnClickListener(view13 -> add_more_son());
        initiateSignature(view);
        return view;
    }

    //Create Horizontal Linear Layout
    @SuppressLint("ResourceType")
    private void add_more_real_uncle() {
        if(this.real_uncle_count < 10){
            parentL1[this.real_uncle_count] = new LinearLayout(getContext());
            LinearLayout p = parentL1[this.real_uncle_count];
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
            e1.setId(real_uncle_count_id);
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
            dynamicL1.addView(p);

            B1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p,"real_uncle");
                }
            });
            if(this.real_uncle_count > 0){
                View btnView = parentL1[this.real_uncle_count-1].getChildAt(1);
                btnView.setEnabled(false);
            }
            real_uncle_count_id++;
            this.real_uncle_count++;
        }else{
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_cousin_uncle() {
        if(this.cousin_uncle_count < 10){
            parentL2[this.cousin_uncle_count] = new LinearLayout(getContext());
            LinearLayout p = parentL2[this.cousin_uncle_count];
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
            if(this.cousin_uncle_count > 0){
                View btnView = parentL2[this.cousin_uncle_count-1].getChildAt(1);
                btnView.setEnabled(false);
            }

            dynamicL2.addView(p);
            this.cousin_uncle_count++;
        }else{
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_real_uncle_son() {
        if(this.real_uncle_son_count < 10) {
            parentL3[this.real_uncle_son_count] = new LinearLayout(getContext());
            LinearLayout p = parentL3[this.real_uncle_son_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
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
            if(this.real_uncle_son_count > 0){
                View btnView = parentL3[this.real_uncle_son_count-1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL3.addView(p);
            this.real_uncle_son_count++;
        }else{
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_cousin_uncle_son() {
        if(this.cousin_uncle_son_count < 10) {
            parentL4[this.cousin_uncle_son_count] = new LinearLayout(getContext());
            LinearLayout p = parentL4[this.cousin_uncle_son_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e4 = new EditText(getContext());
            e4.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.8f);
            Log.d("RealUncle", String.valueOf(cousin_uncle_son_count_id));
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
            B4.setLayoutParams(buttonParams);
            p.addView(B4);
            if(this.cousin_uncle_son_count > 0){
                View btnView = parentL4[this.cousin_uncle_son_count-1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL4.addView(p);
            this.cousin_uncle_son_count++;
            B4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "cousin_uncle_son");
                }
            });
        }else{
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_real_brother() {
        if (this.real_brother_count < 10){
            parentL5[this.real_brother_count] = new LinearLayout(getContext());
            LinearLayout p = parentL5[this.real_brother_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e5 = new EditText(getContext());
            e5.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.8f);
            Log.d("CousinUncle", String.valueOf(real_brother_count_id));
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
            if(this.real_brother_count > 0){
                Log.d("real_brother_count", String.valueOf(real_brother_count));
                View btnView = parentL5[this.real_brother_count-1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL5.addView(p);
            this.real_brother_count++;
        }else{
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_real_brother_son() {
        if (this.real_brother_son_count < 10) {
            parentL6[this.real_brother_son_count] = new LinearLayout(getContext());
            LinearLayout p = parentL6[this.real_brother_son_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e6 = new EditText(getContext());
            e6.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.8f);
            Log.d("RealUncleSon", String.valueOf(real_brother_son_count_id));
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
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.2f);
            B6.setLayoutParams(buttonParams);
            B6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "real_brother_son");
                }
            });
            p.addView(B6);
            if(this.real_brother_son_count > 0){
                View btnView = parentL6[this.real_brother_son_count-1].getChildAt(1);
                btnView.setEnabled(false);
            }

            dynamicL6.addView(p);
            this.real_brother_son_count++;
        } else {
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("ResourceType")
    private void add_more_son() {
        if (this.son_count < 10) {
            parentL7[this.son_count] = new LinearLayout(getContext());
            LinearLayout p = parentL7[this.son_count];
            p.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            parentParams.topMargin = 5;
            p.setLayoutParams(parentParams);

            //Add EditText
            e7 = new EditText(getContext());
            e7.setBackgroundResource(R.drawable.oval);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.8f);
            Log.d("RealUncleSon", String.valueOf(son_count_id));
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
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    0.2f);
            B7.setLayoutParams(buttonParams);
            B7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_dynamic_layout(p, "son");
                }
            });
            p.addView(B7);
            if(this.son_count > 0){
                View btnView = parentL7[this.son_count-1].getChildAt(1);
                btnView.setEnabled(false);
            }
            dynamicL7.addView(p);
            this.son_count++;
        } else {
            Toast.makeText(getContext(), "+ ची कमाल मर्यादा पार.", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete_dynamic_layout(LinearLayout parent, String relative){
        int index;
        parent.removeAllViews();
        switch (relative){
            case "real_uncle":index = this.real_uncle_count_id - real_uncle_count_id_start - 1;
                              parentL1[index]=null;
                              this.real_uncle_count_id--;
                              this.real_uncle_count--;
                              if(index > 0){
                                 View btnView = parentL1[index - 1].getChildAt(1);
                                 btnView.setEnabled(true);
                              }
                              break;
            case "cousin_uncle":index = this.cousin_uncle_count_id - cousin_uncle_count_id_start - 1;
                                parentL2[index]=null;
                                this.cousin_uncle_count_id--;
                                this.cousin_uncle_count--;
                                if(index > 0){
                                    View btnView = parentL2[index - 1].getChildAt(1);
                                    btnView.setEnabled(true);
                                }
                                break;
            case "real_uncle_son":index = this.real_uncle_son_count_id - real_uncle_son_count_id_start - 1;
                                  parentL3[index]=null;
                                  this.real_uncle_son_count_id--;
                                  this.real_uncle_son_count--;
                                  if(index > 0){
                                    View btnView = parentL3[index - 1].getChildAt(1);
                                    btnView.setEnabled(true);
                                  }
                                  break;
            case "cousin_uncle_son":index = this.cousin_uncle_son_count_id - cousin_uncle_son_count_id_start - 1;
                                    parentL4[index]=null;
                                    this.cousin_uncle_son_count_id--;
                                    this.cousin_uncle_son_count--;
                                    if(index > 0){
                                        View btnView = parentL4[index - 1].getChildAt(1);
                                        btnView.setEnabled(true);
                                    }
                                    break;
            case "real_brother":index = this.real_brother_count_id - real_brother_count_id_start - 1;
                                parentL5[index]=null;
                                this.real_brother_count_id--;
                                this.real_brother_count--;
                                if(index > 0){
                                    View btnView = parentL5[index - 1].getChildAt(1);
                                    btnView.setEnabled(true);
                                }
                                break;
            case "real_brother_son":index = this.real_brother_son_count_id - real_brother_son_count_id_start - 1;
                                    parentL6[index]=null;
                                    this.real_brother_son_count_id--;
                                    this.real_brother_son_count--;
                                    if(index > 0){
                                        View btnView = parentL6[index - 1].getChildAt(1);
                                        btnView.setEnabled(true);
                                    }
                                    break;
            case "son": index = this.son_count_id - son_count_id_start - 1;
                        parentL7[index]=null;
                        this.son_count_id--;
                        this.son_count--;
                        if(index > 0){
                            View btnView = parentL7[index - 1].getChildAt(1);
                            btnView.setEnabled(true);
                        }
                        break;
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.nav_view);
        if (item!=null) {
            item.setVisible(false);
        }
    }

    public void add_new_record(View view){
        boolean validation = isValidInput(view);
        if(validation){
            add_vanshawal_record();
        }
    }

    public boolean isValidInput(View view){
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
        real_uncle_name = view.findViewById(R.id.real_uncle_name);
        cousin_uncle_name = view.findViewById(R.id.cousin_uncle_name);
        real_uncle_son_name = view.findViewById(R.id.real_uncle_son_name);
        cousin_uncle_son_name = view.findViewById(R.id.cousin_uncle_son_name);
        real_brother_name = view.findViewById(R.id.real_brother_name);
        real_brother_son_name = view.findViewById(R.id.real_brother_son_name);
        son_name = view.findViewById(R.id.son_name);
        mobileNo = view.findViewById(R.id.mobile_no);
        genderGroup =view.findViewById(R.id.genderGroup);
        int selectedId = genderGroup.getCheckedRadioButtonId();
        btnGender = view.findViewById(selectedId);
        tipText = view.findViewById(R.id.tip);
        Log.d("mobile no", "Value:"+mobileNo.getText().toString());
        if(mobileNo.getText().toString().equals("") && mobileNo.getText().length() != 10) {
            Log.d("Validation Mobile", "Failure");
            Toast.makeText(getContext(), "१० अंकी मोबाईल क्र. प्रविष्ट करा.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(genderGroup.getCheckedRadioButtonId()==-1) {
            Log.d("Validation Gender","Failure");
            Toast.makeText(getActivity(), "लिंग नमूद करणे आवश्यक.", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!yourFullName.getText().toString().equals("") && genderGroup.getCheckedRadioButtonId()!=-1 && !mobileNo.getText().toString().equals("")) {
            Log.d("Validation","Success");
            return true;
        } else {
            Log.d("Validation","Failure");
            Toast.makeText(getActivity(), "लिंग, पूर्ण नाव, मोबाईल क्र. भरणे आवश्यक.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void initiateSignature(View view) {
        signaturePad = view.findViewById(R.id.signature_pad);
        //saveSignature = view.findViewById(R.id.saveSignature);
        clearSignature = view.findViewById(R.id.clearSignature);
        verifyStoragePermissions(getActivity());
        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Log.d("startSigning", "OnStartSigning");
                //Toast.makeText(getContext(), "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                //saveSignature.setEnabled(true);
                clearSignature.setEnabled(true);
            }

            @Override
            public void onClear() {
                //saveSignature.setEnabled(false);
                clearSignature.setEnabled(false);
            }

        });
        clearSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signaturePad.clear();
            }

        });
        /*
            saveSignature.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
                    realPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath().concat("/"+albumName);
                    if (addJpgSignatureToGallery(signatureBitmap)) {
                        Log.d("success", "Jpg signature saved into the Gallery.");
                        Toast.makeText(getContext(), "Jpg Signature saved into the Gallery.", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("failure", "Unable to store the signature.");
                        Toast.makeText(getContext(), "Unable to store the signature.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        */
    }

    public void add_vanshawal_record(){
        Log.d("add_vanshawal_record","Inside add_vanshawal_record");
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

        real_uncle = real_uncle_name.getText().toString().trim();
        if(real_uncle_count!=0){
           for (int cnt=0; cnt < real_uncle_count; cnt++){
                //if(parentL1[this.real_uncle_count_id_start + cnt]!=null){
               if(parentL1[cnt]!=null){
                    dynamic = getView().findViewById(real_uncle_count_id_start + cnt);
                    Log.d("RealUncleDynamic", dynamic.getText().toString().trim());
                    real_uncle = real_uncle + "," + dynamic.getText().toString().trim();
                }
           }
        }
        Log.d("RealUncles", real_uncle);

        cousin_uncle = cousin_uncle_name.getText().toString().trim();
        if(cousin_uncle_count!=0) {
            for (int cnt = 0; cnt < cousin_uncle_count; cnt++) {
                if(parentL2[cnt]!=null){
                //if (parentL2[this.cousin_uncle_count_id_start + cnt] != null) {
                    dynamic = getView().findViewById(cousin_uncle_count_id_start + cnt);
                    Log.d("CousinUncleDynamic", dynamic.getText().toString().trim());
                    cousin_uncle = cousin_uncle + "," + dynamic.getText().toString().trim();
                }
            }
        }
        Log.d("CousinUncles", cousin_uncle);

        real_uncle_son = real_uncle_son_name.getText().toString().trim();
        if(real_uncle_son_count!=0) {
            for (int cnt = 0; cnt < real_uncle_son_count; cnt++) {
                if (parentL3[cnt] != null) {                            //this.real_uncle_son_count_id_start +
                    dynamic = getView().findViewById(real_uncle_son_count_id_start + cnt);
                    Log.d("RealUncleSonDynamic", dynamic.getText().toString().trim());
                    real_uncle_son = real_uncle_son + "," + dynamic.getText().toString().trim();
                }
            }
        }
        Log.d("RealUncleSon", real_uncle_son);

        cousin_uncle_son = cousin_uncle_son_name.getText().toString().trim();
        if(cousin_uncle_son_count!=0) {
            for (int cnt = 0; cnt < cousin_uncle_son_count; cnt++) {
                if (parentL4[cnt] != null) {               //this.cousin_uncle_son_count_id_start + cnt
                    dynamic = getView().findViewById(cousin_uncle_son_count_id_start + cnt);
                    Log.d("CousinUncleSonDynamic", dynamic.getText().toString().trim());
                    cousin_uncle_son = cousin_uncle_son + "," + dynamic.getText().toString().trim();
                }
            }
        }
        Log.d("CousinUncleSon", cousin_uncle_son);

        real_brother = real_brother_name.getText().toString().trim();
        if(real_brother_count!=0) {
            for (int cnt = 0; cnt < real_brother_count; cnt++) {
                if (parentL5[cnt] != null) {         //this.real_brother_count_id_start +
                    dynamic = getView().findViewById(real_brother_count_id_start + cnt);
                    Log.d("RealBrotherDynamic", dynamic.getText().toString().trim());
                    real_brother = real_brother + "," + dynamic.getText().toString().trim();
                }
            }
        }
        Log.d("RealBrother", real_brother);

        real_brother_son = real_brother_son_name.getText().toString().trim();
        if(real_brother_son_count!=0) {
            for (int cnt = 0; cnt < real_brother_son_count; cnt++) {
                if (parentL6[cnt] != null) {  //this.real_brother_son_count_id_start + cnt
                    dynamic = getView().findViewById(real_brother_son_count_id_start + cnt);
                    Log.d("RealBrotherSonDynamic", dynamic.getText().toString().trim());
                    real_brother_son = real_brother_son + "," + dynamic.getText().toString().trim();
                }
            }
        }
        Log.d("RealBrotherSon", real_brother_son);

        son = son_name.getText().toString().trim();
        if(son_count!=0) {
            for (int cnt = 0; cnt < son_count; cnt++) {
                if (parentL7[cnt] != null) {  //this.son_count_id_start + cnt
                    dynamic = getView().findViewById(son_count_id_start + cnt);
                    Log.d("SonDynamic", dynamic.getText().toString().trim());
                    son = son + "," + dynamic.getText().toString().trim();
                }
            }
        }
        Log.d("Son", son);
        mobileno = mobileNo.getText().toString().trim();
        tip = tipText.getText().toString().trim();
        genderString = btnGender.getText().toString().trim();

        Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
        realPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath().concat("/"+albumName);
        if (addJpgSignatureToGallery(signatureBitmap)) {
            Log.d("success", "Jpg signature saved into the Gallery.");
            Toast.makeText(getContext(), "Jpg Signature saved into the Gallery.", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("failure", "Unable to store the signature.");
            Toast.makeText(getContext(), "Unable to store the signature.", Toast.LENGTH_SHORT).show();
        }
        uploadFile(signatureImagePath, "Signature Image");
    }

    private void uploadFile(String selectedImageUrl, String desc ){
        //creating a file
        Log.d("uploadFile", "Inside uploadFile()");
        File file = new File(selectedImageUrl);
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        Call<SignUpResponse> call = Api.getClient().uploadSignature(fileToUpload);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                if (response!=null && response.body().getResponseCode()==1) {
                    serverImagePath = response.body().getMessage();
                    Toast.makeText(getContext(), "File Uploaded:"+serverImagePath, Toast.LENGTH_LONG).show();
                    updateYajamana();
                } else {
                    Toast.makeText(getContext(), "Unable to upload signature.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Log.d("onFailure", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateYajamana(){
        Call<SignUpResponse> call = Api.getClient().createVanshawal(late_fullname, city, taluka, district, caste, death_anniversary, relation, yajman, father, mother, grandpa, panjoba, nipanjoba, real_uncle, cousin_uncle, real_uncle_son, cousin_uncle_son, real_brother, real_brother_son, son, mobileno, genderString, tip, serverImagePath);
        call.enqueue(new Callback<SignUpResponse>() {
            @Override
            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                Log.d("UpdateResponse", "Status: " + response.body().getResponseCode() + ", Message: "+ response.body().getMessage());
                if( response.body().getResponseCode() == 1 ) {
                    Toast.makeText(getActivity().getApplicationContext(), "सदर यजमान यादीत जमा.",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity().getApplicationContext(),NavigateActivity.class));
                    getActivity().finish();
                } else if (response.body().getResponseCode() == 2) {
                    Toast.makeText(getActivity().getApplicationContext(), "सदर यजमान यादीत पूर्वीपासूनच अस्तित्वात आहे.",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "सदर यजमान यादीत जमा होण्यास असफल.",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "Error:"+ t.toString(),Toast.LENGTH_SHORT).show();
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        Log.d("getAlbumStorageDir", String.valueOf(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)));
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString(), albumName);
        Log.d("Directory Path",directory.getPath());
        if (!directory.exists()) {
            directory.mkdir();
        } else {
            Log.e("SignaturePad", "Directory already exists.");
        }
        return directory;
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            String imageName = String.format("Signature_%d.jpg", System.currentTimeMillis());
            signatureImagePath = realPath.concat("/"+imageName);
            Log.d("signatureImagePath", signatureImagePath);
            File photo = new File(getAlbumStorageDir(albumName), imageName);
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if( grantResults.length <= 0 || grantResults[0]!= PackageManager.PERMISSION_GRANTED ){
                    Toast.makeText(getContext(), "Cannot write images to external storage", Toast.LENGTH_SHORT).show();
                }
        }
    }

    /**
     * Checks if the app has permission to write to device storage
     * <p/>

     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity the activity from which permissions are checked
     */
    public static void verifyStoragePermissions(Activity activity){
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
}