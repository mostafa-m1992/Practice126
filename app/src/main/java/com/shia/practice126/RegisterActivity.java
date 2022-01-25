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

public class RegisterActivity extends AppCompatActivity {

    TextView txtLogin;
    RelativeLayout relativeRegister;

    APIInterface request;
    String url = "http://192.168.221.2/retrofit/";

    TextInputEditText username, email, phone, password;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (restoreStateUser()){
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

        request = APIClient.getApiClient(url).create(APIInterface.class);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser();
                saveStateUser();
            }
        });

        relativeRegister = findViewById(R.id.relativeRegister);
        relativeRegister.setOnClickListener(null);

        txtLogin = findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    private void getUser() {
        String strUsername = username.getText().toString();
        String strEmail = email.getText().toString();
        String strPhone = phone.getText().toString();
        String strPassword = password.getText().toString();

        Call<Users> call = request.registerAccount(strUsername, strEmail, strPhone, strPassword);

        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.body().getResponse().equals("USER_REGISTER")) {
                    Toast.makeText(getApplicationContext(), "you are registered", Toast.LENGTH_SHORT).show();
                } else if (response.body().getResponse().equals("SUCCESS")) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else if (response.body().getResponse().equals("WRONG")) {
                    Toast.makeText(getApplicationContext(), "somthing went wrong", Toast.LENGTH_SHORT).show();
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