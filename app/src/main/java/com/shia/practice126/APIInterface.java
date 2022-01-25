package com.shia.practice126;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("getData.php")
    Call<List<Books>> getData();

    @POST("register.php")
    Call<Users> registerAccount(@Query("username") String username,
                        @Query("email") String email,
                        @Query("phone") String phone,
                        @Query("password") String password);

    @GET("login.php")
    Call<Users> loginAccount(@Query("email") String email,
                             @Query("password") String password);

    @GET("{url}")
    Call<List<Books>> getDataUsePath(@Path("url") String url);

}
