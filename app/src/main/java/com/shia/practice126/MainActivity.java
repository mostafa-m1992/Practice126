package com.shia.practice126;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    BooksAdapter adapter;
    List<Books> list = new ArrayList<>();

    APIInterface request;
    String url = "http://192.168.221.2/retrofit/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = APIClient.getApiClient(url).create(APIInterface.class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //getData();
        getDataUsePath();
    }

    private void getData(){
        request.getData().enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                list = response.body();
                adapter = new BooksAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getMessage() + "");
            }
        });
    }

    private void getDataUsePath(){
        request.getDataUsePath("getData.php").enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {
                list = response.body();
                adapter = new BooksAdapter(getApplicationContext(), list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getMessage() + "");
            }
        });
    }
}