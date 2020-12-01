package com.example.yajmana.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.yajmana.Api;
import com.example.yajmana.LoginResponse;
import com.example.yajmana.NavigateActivity;
import com.example.yajmana.NoConnectivityException;
import com.example.yajmana.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ProgressBar loaderLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button loginButton = findViewById(R.id.login);
        loaderLogin = findViewById(R.id.loader_login);
        this.setActionBarTitle(getString(R.string.login_head));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText usernameEditText = findViewById(R.id.username);
                final EditText passwordEditText = findViewById(R.id.password);
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if( !username.equals("") && !password.equals("") ){
                    login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                } else {
                    if(username.equals("") && password.equals("")){
                        Toast.makeText(LoginActivity.this.getApplicationContext(),"Username and Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    } else if (username.equals("")) {
                        Toast.makeText(LoginActivity.this.getApplicationContext(),"Username cannot be empty.", Toast.LENGTH_SHORT).show();
                    } else if (password.equals("")) {
                        Toast.makeText(LoginActivity.this.getApplicationContext(),"Password cannot be empty.", Toast.LENGTH_SHORT).show();
                    } else if ( password.length() <= 5) {
                        Toast.makeText(LoginActivity.this.getApplicationContext(),"Password length must be greater than 5.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void login(String username, String password) {
        try {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),0);
            Log.d("keyboard","Dismissed");
        }catch (Exception e){
            Log.e("Exception:", e.getMessage());
        }
        // Api is a class in which we define a method getClient() that returns the API Interface class object
        // registration is a POST request type method in which we are sending our field's data
        // enqueue is used for callback response and error
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        loaderLogin.setVisibility(View.VISIBLE);
        (Api.getClient(this).checkLogin(username.toString().trim(), password.toString().trim())).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loaderLogin.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.d("Response", "Status: " + response.body().getSuccess() + ", Message: "+ response.body().getMessage());
                if (response.body().getSuccess().equals("1")){
                    startActivity(new Intent(LoginActivity.this, NavigateActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this.getApplicationContext(), response.body().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loaderLogin.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                if(t instanceof NoConnectivityException) {
                    // show No Connectivity message to user or do whatever you want.
                    Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                }
                Log.d("onFailure:", "Error:"+ t.toString());
            }
        });
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

}