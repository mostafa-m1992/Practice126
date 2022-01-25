package com.shia.practice126;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView txtRegister;
    RelativeLayout relativeLogin;
    Button btnLogin;

    APIInterface request;
    String url = "http://192.168.221.2/retrofit/";

    TextInputEditText loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (restoreStateUser()){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        request = APIClient.getApiClient(url).create(APIInterface.class);

        relativeLogin = findViewById(R.id.relativeLogin);
        relativeLogin.setOnClickListener(null);

        txtRegister = findViewById(R.id.txtRegister);
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser();
                saveStateUser();
            }
        });
    }

    private void getUser() {
        String strLoginEmail = loginEmail.getText().toString();
        String strLoginPassword = loginPassword.getText().toString();

        Call<Users> call = request.loginAccount(strLoginEmail, strLoginPassword);

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.body().getResponse().equals("USER_LOGIN")) {
                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else if (response.body().getResponse().equals("NO_ACCOUNT")) {
                    Toast.makeText(getApplicationContext(), "You must be register", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveStateUser() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("user_state", true);
        editor.commit();
    }

    private boolean restoreStateUser(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        boolean StateUser;
        StateUser = sharedPreferences.getBoolean("user_state", false);
        return StateUser;
    }
}