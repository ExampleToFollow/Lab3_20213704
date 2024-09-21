package com.example.lab3_20213704.ConsumoWebServices.Interfaces;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthUsuario {


    @FormUrlEncoded
    @POST("/auth/login")
    Call<RespuestaAuth> login(@Field("username") String username, @Field("password") String password);

}
